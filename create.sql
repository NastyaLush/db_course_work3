CREATE TYPE role AS ENUM ('user', 'boss', 'arendator', 'helper');


CREATE TABLE "fraction" (
    "fraction_id" SERIAL PRIMARY KEY,
    "fraction_name" varchar(20) UNIQUE NOT NULL,
    "created_at" timestamp DEFAULT 'now()',
    "updated_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "person" (
    "person_id" SERIAL PRIMARY KEY,
    "person_name" varchar(20) NOT NULL,
    "person_role" role DEFAULT 'user',
    "person_fraction_id" integer REFERENCES "fraction" ("fraction_id")on delete set null on update cascade,
    "created_at" timestamp DEFAULT 'now()',
    "updated_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "report" (
    "report_id" SERIAL PRIMARY KEY,
    "report_created_by" integer REFERENCES "person" ("person_id") on delete set null on update cascade,
    "report_title" varchar(20) NOT NULL,
    "report_text" text,
    "created_at" timestamp DEFAULT 'now()',
    "updated_at" timestamp DEFAULT 'now()'
);

CREATE TYPE status AS ENUM ('created', 'in_process', 'need_check', 'checked', 'answer', 'finished');

CREATE TABLE "task" (
    "task_id" SERIAL PRIMARY KEY,
    "task_person_to" integer REFERENCES "person" ("person_id") on delete set null on update cascade,
    "task_person_from" integer REFERENCES "person" ("person_id") on delete set null on update cascade,
    "task_text" text,
    "task_status" status DEFAULT 'created',
    "task_title" varchar(120),
    "created_at" timestamp DEFAULT 'now()',
    "updated_at" timestamp DEFAULT 'now()'

);

CREATE TABLE "test_result" (
    "test_result_id" SERIAL PRIMARY KEY,
    "person_id" integer UNIQUE REFERENCES "person" ("person_id") on delete set null on update cascade,
    "fraction_id" integer REFERENCES "fraction" ("fraction_id") on delete set null on update cascade,
    "created_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "event" (
     "event_id" SERIAL PRIMARY KEY,
     "event_name" varchar(20) NOT NULL,
     "created_at" timestamp DEFAULT 'now()',
     "updated_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "event_person" (
    "event_participant_id" SERIAL PRIMARY KEY,
    "participant_id" integer REFERENCES "person" ("person_id") on delete set null on update cascade,
    "event_id" integer REFERENCES "event" ("event_id") on delete set null on update cascade,
    "created_at" timestamp DEFAULT 'now()',
    "updated_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "place" (
    "place_id" SERIAL PRIMARY KEY,
    "fraction_id" integer REFERENCES "fraction" ("fraction_id") on delete set null on update cascade,
    "place_name" varchar(20) UNIQUE,
    "created_at" timestamp DEFAULT 'now()',
    "updated_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "place_arendator" (
    "place_arendator_id" SERIAL PRIMARY KEY,
    "place_id" integer  REFERENCES "place" ("place_id") on delete set null on update cascade,
    "arendator_id" integer  REFERENCES "person" ("person_id") on delete set null on update cascade,
    "from_time" timestamp DEFAULT 'now()',
    "to_time" timestamp DEFAULT 'now()'
);
CREATE TABLE "characteristic" (
    "charectiristic_id" SERIAL PRIMARY KEY,
    "charectiristic_name" varchar(20) UNIQUE NOT NULL,
    "created_at" timestamp DEFAULT 'now()',
    "updated_at" timestamp DEFAULT 'now()'
);
CREATE TABLE "place_characteristic" (
    "place_characteristic_id" SERIAL PRIMARY KEY,
    "characteristic_id" integer REFERENCES "characteristic" ("charectiristic_id") on delete set null on update cascade,
    "place_id" integer REFERENCES "place" ("place_id") on delete set null on update cascade,
    "value" varchar(20) UNIQUE
);
CREATE TABLE "event_place" (
    "event_place_id" SERIAL PRIMARY KEY,
    "event_id" integer REFERENCES "event" ("event_id") on delete set null on update cascade,
    "place_arendator_id" integer REFERENCES "place_arendator" ("place_arendator_id") on delete set null on update cascade,
    "created_at" timestamp DEFAULT 'now()',
    "updated_at" timestamp DEFAULT 'now()'
);