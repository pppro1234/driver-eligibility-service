-- Immutable application snapshots. No global person deduplication or mutable driver master table.
CREATE TABLE applications (
    id uuid PRIMARY KEY,
    request_key varchar(100) NOT NULL,
    status varchar(16) NOT NULL CHECK (status IN ('APPROVED', 'REJECTED')),
    submitted_at timestamptz NOT NULL,
    evaluated_at timestamptz NOT NULL,
    evaluation_date date NOT NULL,
    rule_version varchar(40) NOT NULL,
    driver_first_name text NOT NULL,
    driver_last_name text NOT NULL,
    driver_birth_date date NOT NULL,
    license_number text NOT NULL,
    license_first_name text NOT NULL,
    license_last_name text NOT NULL,
    license_address text NOT NULL,
    license_birth_date date NOT NULL,
    license_jurisdiction text NOT NULL,
    license_issued date NOT NULL,
    license_expires date NOT NULL,
    policy_first_name text NOT NULL,
    policy_last_name text NOT NULL,
    policy_expires date NOT NULL,
    CONSTRAINT uq_applications_request_key UNIQUE (request_key)
);
CREATE INDEX idx_applications_submitted ON applications (submitted_at DESC, id DESC);
CREATE INDEX idx_applications_status_submitted ON applications (status, submitted_at DESC, id DESC);

CREATE TABLE application_vehicles (
    application_id uuid NOT NULL REFERENCES applications(id) ON DELETE CASCADE,
    position integer NOT NULL,
    model_year integer NOT NULL,
    color text NOT NULL,
    make text NOT NULL,
    model text NOT NULL,
    owner_name text NOT NULL,
    plate text NOT NULL,
    PRIMARY KEY (application_id, position),
    UNIQUE (application_id, plate)
);
CREATE TABLE application_insured_drivers (
    application_id uuid NOT NULL REFERENCES applications(id) ON DELETE CASCADE,
    position integer NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    PRIMARY KEY (application_id, position)
);
CREATE TABLE application_incidents (
    application_id uuid NOT NULL REFERENCES applications(id) ON DELETE CASCADE,
    position integer NOT NULL,
    scope varchar(10) NOT NULL CHECK (scope IN ('DRIVER', 'VEHICLE')),
    plate text,
    incident_date date NOT NULL,
    category varchar(30) NOT NULL CHECK (category IN ('MOVING', 'NONMOVING', 'CRASH')),
    incident_type varchar(80) NOT NULL,
    offender_first_name text,
    offender_last_name text,
    PRIMARY KEY (application_id, position),
    FOREIGN KEY (application_id, plate) REFERENCES application_vehicles(application_id, plate) DEFERRABLE INITIALLY DEFERRED,
    CHECK ((scope = 'DRIVER' AND plate IS NULL) OR (scope = 'VEHICLE' AND plate IS NOT NULL))
);
CREATE TABLE application_failures (
    application_id uuid NOT NULL REFERENCES applications(id) ON DELETE CASCADE,
    position integer NOT NULL,
    reason text NOT NULL,
    PRIMARY KEY (application_id, position)
);
