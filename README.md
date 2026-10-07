# Driver Eligibility Service

A local Java backend for rideshare driver eligibility applications. The service uses six validation strategies to evaluate applications, persist validation results, and query application history with pagination. It also provides JSON file import and an in-memory CLI. Examples use synthetic data for local demonstrations.

## Requirements and versions

- JDK **21** and the Gradle **8.14** wrapper.
- Spring Boot **3.5.16**, Spring Web, and Spring Data JPA. Dependency versions are managed by the Spring Boot BOM.
- PostgreSQL. Local integration tests run a real **PostgreSQL 14.22** process, with no H2 or mocked database.
- Flyway for schema migrations. Hibernate validates the schema; it does not create or update tables automatically.

The [Spring Boot 3.5 system requirements](https://docs.spring.io/spring-boot/3.5/system-requirements.html) and [Gradle compatibility matrix](https://docs.gradle.org/8.14/userguide/compatibility.html) support this Java and Gradle combination. Spring Boot 3.5.16 uses Jackson 2, which is compatible with the JSON parser. Dependency versions are pinned for reproducible builds.

On macOS with multiple JDKs, select Java 21 explicitly:

```sh
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
./gradlew --version
```

Run the following commands from the **repository root** (the directory containing `build.gradle`). The first build requires network access to download dependencies.

## Build and test

```sh
./gradlew clean build
```

This runs the complete test suite, real PostgreSQL HTTP integration tests, executable JAR restart tests, and the JaCoCo minimum coverage check of 20%. Test dependencies start PostgreSQL as a temporary local process; Docker, a system-wide PostgreSQL installation, and an existing database are not required. The test dependencies include macOS ARM64 and Linux AMD64 binaries. See the verification record for platforms actually tested. PostgreSQL cannot run as root. If its binaries cannot start, the tests fail rather than silently skip.

Reports:

- Tests: `build/reports/tests/test/index.html`
- Coverage: `build/jacocoHtml/index.html`
- PMD: `build/reports/pmd/main.html`

PMD uses an advisory configuration (`ignoreFailures=true`), so its findings do not fail the build. The [verification record](docs/verification.md) lists measured results, reproduction commands, and test limitations.

The repository-level `.github/workflows/java-backend.yml` configures `clean build` on Java 21 and uploads reports. Remote execution evidence, when available, belongs in the repository Actions history; configuration alone does not establish a passing run.

## Prepare a persistent database

The test database is temporary. Running the service outside the tests requires a persistent local PostgreSQL instance. Use an existing installation, or install PostgreSQL through a suitable local distribution such as Homebrew `postgresql@16` or Postgres.app. Start the database and make sure `psql` is available on your PATH. The application does not install a system-wide database service.

Connect as a local database administrator and create the role and database once. Set the password interactively:

```sh
psql postgres
```

```sql
CREATE ROLE driver_app LOGIN;
\password driver_app
CREATE DATABASE driver_applications OWNER driver_app;
\q
```

Reuse the role and database if they already exist. At startup, Flyway applies only pending migrations. `V1__application_snapshots.sql` creates five business tables; `flyway_schema_history` tracks migration versions and checksums. Do not edit a migration after it has been applied. Add a new version, such as V2, for subsequent schema changes.

## Configure and start the service

```sh
export DB_URL='jdbc:postgresql://127.0.0.1:5432/driver_applications'
export DB_USER='driver_app'
# Enter the local database password without placing it in shell history (zsh):
read -s 'DB_PASSWORD?Database password: '
export DB_PASSWORD
./gradlew bootRun
```

Alternatively, package and run the executable JAR with the same database configuration:

```sh
./gradlew bootJar
java -jar build/libs/driver-eligibility-service-2.0.0.jar
```

The service binds to `127.0.0.1:8080` by default. Set `PORT` to change the port. `SERVER_ADDRESS` can override the bind address, although only local operation was verified. Supply database credentials through environment variables and keep local credentials out of version control.

Rules use the current UTC date by default. For a reproducible demonstration, set `APP_EVALUATION_DATE=2026-09-22` before startup. Unset it to restore the current date. `submittedAt` and `evaluatedAt` always record actual UTC timestamps; `evaluationDate` separately records the date used by the rules.

## API examples

The examples have reproducible approval and rejection results when the evaluation date is fixed to **2026-09-22**. Their dates are synthetic fixtures, not permanently valid application data.

```sh
# Approved application: 201 Created, with the query URL in the Location header.
curl -i http://127.0.0.1:8080/api/applications \
  -H 'Content-Type: application/json' -H 'Idempotency-Key: demo-approved-001' \
  --data-binary @examples/approved.json

# Valid input that fails a business rule: 201, REJECTED, with saved failureReasons.
curl -i http://127.0.0.1:8080/api/applications \
  -H 'Content-Type: application/json' -H 'Idempotency-Key: demo-rejected-001' \
  --data-binary @examples/rejected.json

# Invalid history field type: 400, with no application saved.
curl -i http://127.0.0.1:8080/api/applications \
  -H 'Content-Type: application/json' -H 'Idempotency-Key: demo-invalid-001' \
  --data-binary @examples/invalid-history.json

# Reuse the first request key: 409, with no additional record created.
curl -i http://127.0.0.1:8080/api/applications \
  -H 'Content-Type: application/json' -H 'Idempotency-Key: demo-approved-001' \
  --data-binary @examples/approved.json

# Replace this UUID with the id returned by a successful POST.
curl -i http://127.0.0.1:8080/api/applications/00000000-0000-0000-0000-000000000000
curl -s 'http://127.0.0.1:8080/api/applications?page=0&size=10&status=APPROVED'
```

### Request keys and repeated applications

`Idempotency-Key` is required and case-sensitive. It must contain 1–100 ASCII letters, digits, dots, underscores, or hyphens. Input is validated before duplicate-key checking. **Reusing a key returns 409 whether the payload is identical or different.** The service does not replay the original response; retain the first response's `id` and `Location`.

A new key represents a new application. The same driver may apply again with a new key. A database unique constraint protects concurrent submissions; the preliminary application-level lookup is not the sole safeguard.

### Responses and pagination

Each application response contains `id`, `status`, `failureReasons`, `submittedAt`, `evaluatedAt`, `evaluationDate`, `ruleVersion`, and `application` (the normalized business-data snapshot). Reading a historical record does not rerun the rules against today's date. New submissions use `ruleVersion: eligibility-six-rules-v1`; historical records retain their stored version values.

Pages are zero-based. `size` defaults to 20 and must be between 1 and 100; `page * size` must not exceed 2147483647. Results are ordered by submission time descending, then UUID descending as a stable secondary sort. The optional `status` filter accepts `APPROVED` or `REJECTED`.

List responses contain `content`, `number`, `size`, `totalElements`, and `totalPages`. A page beyond the available results returns an empty list. Offset pagination does not provide a consistent snapshot across multiple requests while new records are being inserted.

| Situation | HTTP | Error code or business status | Persistence |
|---|---|---|---|
| Valid input, eligible driver | 201 | APPROVED | Application saved |
| Valid input, business rule failure | 201 | REJECTED + failureReasons | Application and reasons saved |
| Missing fields, invalid types/dates/history, duplicate JSON keys, invalid parameters | 400 | INVALID_REQUEST | Nothing saved |
| Application UUID not found | 404 | NOT_FOUND | No change |
| Request key already used | 409 | DUPLICATE_REQUEST | No additional record |
| Other database integrity conflict | 409 | DATA_CONFLICT | Transaction rolled back |
| Unexpected database or service failure | 500 | INTERNAL_ERROR | Transaction rolled back |
| Unsupported method or media type | 405 / 415 | METHOD_NOT_ALLOWED / UNSUPPORTED_MEDIA_TYPE | Nothing saved |

Errors use `{ "code": "...", "message": "...", "timestamp": "..." }` and do not expose stack traces, SQL, or database connection details. The [input contract](docs/input-contract.md) documents required fields and supported legacy formats.

## CLI and JSON import

The CLI entry point is `Registration.RideshareDriverValidator`:

```sh
# Sample dataset, current UTC date. Query by last name; enter exit or send EOF to finish.
./gradlew run --args='src/main/resources/all-cases-drivers.json'

# Two synthetic applications, fixed evaluation date, no database connection.
printf 'exit\n' | ./gradlew run --args='examples/cli-demo.json --date 2026-09-22'
```

The CLI shares the parser and all six validation strategies with the API. Its in-memory pool groups records by license jurisdiction and license number, reporting new entries, merged vehicles, duplicates, and conflicting data separately. The API uses the database and does not synchronize with this pool. Invalid file content causes the import to fail as a whole, preventing malformed incident history from being silently discarded.

## Documentation

- [Architecture and data model](docs/design.md): persistence, transaction boundaries, request deduplication, and design tradeoffs.
- [Input contract](docs/input-contract.md): required fields, supported formats, validation errors, and examples.
- [Validation rules and CLI behavior](docs/rules-and-legacy.md): eligibility rules, date boundaries, and in-memory grouping.
- [Testing and verification](docs/verification.md): reproducible checks, recorded results, and validation limits.
