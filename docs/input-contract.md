# Input contract

The REST API and JSON file importer use the same application parser. Parsing validates structure before eligibility rules run. For example, a valid `DUI` record is accepted as input and causes a stored business rejection.

## JSON format

- The API accepts one application object. A CLI input file accepts an application array, or an object containing only an `applications` array. An empty CLI array is valid.
- Standard JSON is required. Duplicate keys, multiple root values, comments, single quotes, and trailing commas are rejected.
- Field names are case-sensitive. Unknown fields are rejected at every object level.
- Text fields require JSON strings. Numbers and booleans are not converted to text. Leading and trailing whitespace is trimmed; required strings cannot be blank. `U+0000` is rejected because PostgreSQL text columns cannot store it.
- Dates require a valid, zero-padded `YYYY-MM-DD` calendar date with a year from `0001` through `9999`. Timestamps and impossible dates are rejected. Eligibility rules, rather than the parser, determine whether a valid date qualifies.
- Object-form vehicle `year` requires a 32-bit JSON integer. Decimal notation, exponent notation, strings, and out-of-range integers are rejected.
- Omission and `null` are different: every explicit `null` is invalid. Only the optional fields listed below may be omitted.

## Canonical application fields

All listed fields are required unless marked optional.

| Field | Shape |
| --- | --- |
| `driver` | Object with nonblank `firstName`, `lastName`, and a `birthDate` date. |
| `license` | Object with nonblank `number`, `firstName`, `lastName`, `address`, `countryAndState`; dates `birthDate`, `issuanceDate`, `expirationDate`. |
| `vehicles` | Nonempty array of vehicle objects. |
| Vehicle object | Integer `year`; nonblank `make`, `model`, `plate`. Optional `color` and `ownerName` default to empty strings and may explicitly be empty strings. |
| `insurance` | Object with `owner` name object and `expirationDate` date. Optional `drivers` defaults to an empty array. |
| Name object | Exactly two nonblank strings: `firstName` and `lastName`. |
| `insurance.drivers` | Array of name objects; an empty array is valid. The insurance rule determines whether the applicant is covered. |
| `driverHistory` | Optional array, defaulting to no records. Each object contains `date`, `category`, and `type`. |
| `vehicleHistory` | Optional array, defaulting to no records. Each object contains `plate`, `date`, `category`, `type`, and an `offender` name object. |

Vehicle plates must be unique within one application. Every vehicle-history plate must exactly match a vehicle in that application after text trimming; unmatched records are rejected, not discarded. Reusing a plate in another application is allowed.

Insurance coverage matches names under the documented business rule. These names are not global driver identities. The parser supplies a placeholder birth date only to satisfy the domain `Driver` constructor for covered names; the database stores only the supplied names for that collection.

## Import compatibility forms

Both entry points support the following alternatives. The canonical format above is preferred for new clients.

| Field | Supported alternative |
| --- | --- |
| `vehicles` | Alias `vehicle`. Either name accepts an array, one vehicle object, or one CSV string. Array entries may also be CSV strings. |
| Vehicle `ownerName` | Alias `owner`. |
| Vehicle `plate` | Alias `plateNumber`. |
| Insurance `owner` | Both `ownerFirst` and `ownerLast` as nonblank strings. |
| `insurance.drivers` | A single name object or name string instead of an array. Array entries may also be name strings. |
| Vehicle-history `offender` | Alias `offenderName`. Either field accepts a name object or name string. |

Providing both forms of an alias is invalid, even if their values agree. Insurance `owner` cannot be combined with either `ownerFirst` or `ownerLast`.

A CSV vehicle must have exactly six comma-separated values:

```text
YEAR,Color,Make,Model,OwnerName,Plate
```

The year must parse as a 32-bit integer; make, model, and plate cannot be blank. CSV has no quoted-field escaping. Use an object for text containing commas.

A name string must contain exactly two comma-separated components in `Last, First` order, or exactly two whitespace-separated words in `First Last` order. Use name objects for multiword names. Extra components are rejected rather than silently dropped.

## Incident categories and types

`category` and `type` are trimmed and converted to uppercase using `Locale.ROOT`. Each type must belong to its category. Display labels such as `Driving Under Influence` are not accepted as enum values.

| `category` | Allowed `type` values |
| --- | --- |
| `MOVING` or `MOVING_VIOLATION` | `DISTRACTED_DRIVING`, `RECKLESS_DRIVING`, `SPEEDING`, `DUI`, `FAILURE_TO_OBEY_SIGNS`, `DRIVING_WITHOUT_VALID_LICENSE_INSURANCE` |
| `NONMOVING` or `NONMOVING_VIOLATION` | `PARKING_VIOLATION`, `PAPERWORK_ISSUE`, `VEHICLE_PROBLEM` |
| `CRASH` | `FENDER_BENDER`, `NO_INJURY_CRASH`, `INJURY_CRASH` |

## Request key and outcomes

`POST /api/applications` requires an `Idempotency-Key` HTTP header, separate from the application JSON. The key must contain 1–100 ASCII letters, digits, dots, underscores, or hyphens.

| Input or outcome | HTTP response |
| --- | --- |
| Valid application that passes the rules | `201`, saved with status `APPROVED`. |
| Valid application that fails the rules | `201`, saved with status `REJECTED` and failure reasons. |
| Malformed JSON, missing required field, wrong type, invalid date, or malformed history | `400 INVALID_REQUEST`, with no application saved. |
| Reuse of a saved request key with otherwise valid input | `409 DUPLICATE_REQUEST`, with no additional application saved. |

The service validates input before checking for duplicate keys. Reapplying with a new key creates an independent application, even for the same driver and payload. See [transaction behavior](design.md#transaction-and-duplicate-request-behavior) for concurrent requests and response-loss limitations.

## Synthetic examples

| File | Expected behavior at evaluation date `2026-09-22` |
| --- | --- |
| [approved.json](../examples/approved.json) | Passes; includes a nonmoving violation and an older vehicle incident. |
| [rejected.json](../examples/rejected.json) | Valid input; rejected for a `DUI` record and saved for later retrieval. |
| [invalid-history.json](../examples/invalid-history.json) | A history object appears where an array is required; rejected as invalid input. |
| [cli-demo.json](../examples/cli-demo.json) | Batch containing one approved and one rejected application. |

```sh
curl -i -X POST http://localhost:8080/api/applications \
  -H 'Content-Type: application/json' \
  -H 'Idempotency-Key: demo-approved-001' \
  --data-binary @examples/approved.json
```

Repeating this request returns `409` after the first successful creation. Use a new key for a separate application. Example dates and vehicle years do not advance automatically; use the [README's fixed-date configuration](../README.md) to reproduce the listed outcomes. Normal operation uses the current UTC date.
