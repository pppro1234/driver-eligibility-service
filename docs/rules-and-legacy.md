# Business rules and CLI behavior

The HTTP API and JSON importer construct `Registration.Entity.Application` objects and run the same `Registration.Validation.Validator`. The API persists submissions and results; the CLI retains accepted applications in a process-local `DriverPool`. Neither entry point synchronizes with the other's storage.

## Eligibility rules

All six strategies run for each application. A strategy returns at most one failure reason, so a rejected application may contain several reasons without listing every problem within a single category.

| Rule | Behavior and boundaries |
| --- | --- |
| Age | The applicant must be at least 21. The 21st birthday passes; the previous day fails. |
| License | Name and birth date must exactly match the applicant. The issuing country must be the US or Canada, issuance must be at least six months before evaluation, and the license must not be expired. Issuance exactly six months earlier and expiration on the evaluation date both pass. |
| Vehicle | Evaluation year minus model year must be at most 15 for every vehicle. A 15-year-old vehicle passes. Subtraction uses `long` to avoid integer overflow. |
| Insurance | The policy must not be expired. The applicant's name must match the policy owner or a listed insured driver. Expiration on the evaluation date passes. |
| Driving history | `RECKLESS_DRIVING`, `SPEEDING`, `DUI`, and `DRIVING_WITHOUT_VALID_LICENSE_INSURANCE` disqualify an applicant, without a time window. `DISTRACTED_DRIVING`, `FAILURE_TO_OBEY_SIGNS`, nonmoving violations, and crashes do not fail this strategy. |
| Vehicle history | A crash or moving violation on or after the date six calendar months before evaluation disqualifies the vehicle. Earlier events and nonmoving violations do not fail this strategy. |

The following boundaries are part of the current behavior:

- Country extraction uses the text before the first comma; without a comma, it uses the first whitespace-separated token. Normalization removes nonletters and accepts `US`, `USA`, `UNITEDSTATES`, `UNITEDSTATESOFAMERICA`, or `CANADA`. Use unambiguous forms such as `US, MA` or `Canada, ON`. Hyphenated jurisdiction strings such as `US-CA` do not qualify.
- The vehicle rule does not reject model years later than the evaluation year.
- The vehicle-history rule has no upper date bound: future-dated crashes and moving violations also fail it.
- Insurance coverage uses exact name equality, not verified identity. Names, dates, license numbers, and jurisdictions are supplied by the caller; the service does not contact government or insurance systems.
- Domain builders can construct incomplete objects for compatibility. API and file inputs must pass through the shared parser before they are treated as valid submissions. Missing histories mean no records; explicit `null`, wrong types, malformed records, and unknown fields are rejected.

See [the input contract](input-contract.md) for structural requirements and accepted incident values.

## Evaluation time

`Validator.defaultValidator(Clock)` captures one instant per evaluation and supplies a fixed clock to all date-dependent strategies. Their no-argument constructors use UTC; constructors accepting a `Clock` support deterministic tests. Saved API results are not reevaluated when retrieved.

The CLI accepts a file path and optional `--date YYYY-MM-DD` in either order. Without `--date`, it uses the current UTC date. The override affects evaluation only and does not change the system clock. Input exhaustion or `exit` ends interactive queries.

For a reproducible batch from the Gradle project directory:

```sh
./gradlew run --args='examples/cli-demo.json --date 2026-09-22' < /dev/null
```

The CLI parses the whole file, evaluates applications in parallel, adds accepted applications to its pool, and supports case-insensitive last-name queries. The pool is not durable across process restarts.

## CLI grouping and duplicates

The CLI groups accepted applications by a pair of strings: issuing jurisdiction and license number. Both are trimmed and uppercased with `Locale.ROOT`. Country aliases are not unified: `US, MA` and `USA, MA` are different grouping keys. This is local record grouping, not identity verification.

| Submission | `AddResult` | Effect |
| --- | --- | --- |
| New jurisdiction/license key | `ADDED` | Create a driver entry and add its vehicles. |
| Existing key, matching name and birth date, and a new plate | `MERGED` | Add previously unseen vehicles; retain the existing driver and driving-history snapshot. |
| Existing key and matching personal details, with no new plates | `DUPLICATE` | Leave the entry unchanged. |
| Existing key with a different name or birth date | `CONFLICT` | Leave the entry unchanged and count the conflict separately. |

The CLI reports accepted and rejected application counts separately from new entries, vehicle merges, duplicates, and conflicts. Passing the rules does not necessarily create a new driver entry. The compatible `addDriver` boolean method returns `true` only for `ADDED` or `MERGED`.

This grouping is independent of the API's UUIDs and request keys. A new HTTP request key allows another application from the same driver without changing an earlier record.

## Test responsibilities

- `ValidatorTest` exercises all six strategies, combined failure reports, date boundaries, injected clocks, invalid domain data, and extreme vehicle years.
- `ProspectiveDriverFileParserTest` checks the shared contract, accepted compatibility forms, and malformed input rejection.
- `DriverPoolTest` checks grouping, merging, duplicate and conflict handling, and pool queries.
- `RideshareDriverValidatorTest` checks the CLI with the real parser, validator, and pool.
- `ApplicationPostgresIntegrationTest` exercises the HTTP, rule, transaction, and PostgreSQL boundaries together.

Execution commands and recorded results are maintained in the [README](../README.md) and [verification report](verification.md).
