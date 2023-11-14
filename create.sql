CREATE TABLE "role" (
                        "role_id" integer PRIMARY KEY,
                        "role_name" varchar(20) UNIQUE NOT NULL,
                        "created_at" timestamp DEFAULT 'now()',
                        "updated_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "reports" (
                           "report_id" integer PRIMARY KEY,
                           "report_created_by" integer,
                           "report_title" varchar(20) NOT NULL,
                           "report_text" text,
                           "created_at" timestamp DEFAULT 'now()',
                           "updated_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "task" (
                        "task_id" integer PRIMARY KEY,
                        "task_user_to" integer,
                        "task_user_from" integer,
                        "task_test" text,
                        "task_status" status,
                        "created_at" timestamp,
                        "updated_at" timestamp,
                        "task_title" varchar(120)
);

CREATE TABLE "test_result" (
                               "test_result_id" integer PRIMARY KEY,
                               "user_id" integer UNIQUE,
                               "fraction_id" integer,
                               "created_at" timestamp
);

CREATE TABLE "user" (
                        "user_id" integer PRIMARY KEY,
                        "user_name" varchar(20) NOT NULL,
                        "user_role_id" integer,
                        "user_fraction_id" integer,
                        "created_at" timestamp DEFAULT 'now()',
                        "updated_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "event_user" (
                              "event_participant_id" integer PRIMARY KEY,
                              "participation_id" integer,
                              "event_id" integer,
                              "created_at" timestamp,
                              "updated_at" timestamp
);

CREATE TABLE "event" (
                         "event_id" integer PRIMARY KEY,
                         "user_name" varchar(20) NOT NULL,
                         "created_at" timestamp,
                         "updated_at" timestamp
);

CREATE TABLE "fraction" (
                            "fraction_id" integer PRIMARY KEY,
                            "fraction_name" varchar(20) UNIQUE NOT NULL,
                            "created_at" timestamp,
                            "updated_at" timestamp
);
CREATE TYPE status AS ENUM ('created', 'in_process', 'need_check','checked','answer', 'finished');
-- CREATE TABLE "status" (
--                           "status_id" integer PRIMARY KEY,
--                           "status_name" varchar(20) UNIQUE NOT NULL,
--                           "created_at" timestamp,
--                           "updated_at" timestamp
-- );

CREATE TABLE "places_arendator" (
                                    "id" integer PRIMARY KEY,
                                    "place_id" integer,
                                    "arendator_id" integer,
                                    "from" timestamp,
                                    "to" timestamp,
                                    "created_at" timestamp,
                                    "updated_at" timestamp
);

CREATE TABLE "characteristic" (
                                  "charectiristic_id" integer PRIMARY KEY,
                                  "charectiristic_name" varchar(20) UNIQUE NOT NULL,
                                  "created_at" timestamp,
                                  "updated_at" timestamp
);

CREATE TABLE "places" (
                          "places_id" integer PRIMARY KEY,
                          "fraction_id" integer,
                          "places_name" varchar(20) UNIQUE,
                          "status" status_place,
                          "created_at" timestamp,
                          "updated_at" timestamp
);

CREATE TABLE "places_characteristic" (
                                         "places_characteristic_id" integer PRIMARY KEY,
                                         "characteristic_id" integer,
                                         "place_id" integer,
                                         "value" varchar(20) UNIQUE
);
CREATE TYPE status_place AS ENUM ('free', 'busy');
-- CREATE TABLE "status_place" (
--                                 "status_place_id" integer PRIMARY KEY,
--                                 "value" varchar(20) UNIQUE
-- );

CREATE TABLE "event_places" (
                                "event_place_id" integer PRIMARY KEY,
                                "event_id" integer,
                                "place_id" integer,
                                "event_date" timestamp,
                                "created_at" timestamp,
                                "updated_at" timestamp
);

ALTER TABLE "reports" ADD FOREIGN KEY ("report_created_by") REFERENCES "user" ("user_id");

ALTER TABLE "task" ADD FOREIGN KEY ("task_user_to") REFERENCES "user" ("user_id");

ALTER TABLE "task" ADD FOREIGN KEY ("task_user_from") REFERENCES "user" ("user_id");

ALTER TABLE "task" ADD FOREIGN KEY ("task_status_id") REFERENCES "status" ("status_id");

ALTER TABLE "test_result" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("user_id");

ALTER TABLE "test_result" ADD FOREIGN KEY ("fraction_id") REFERENCES "fraction" ("fraction_id");

ALTER TABLE "user" ADD FOREIGN KEY ("user_role_id") REFERENCES "role" ("role_id");

ALTER TABLE "user" ADD FOREIGN KEY ("user_fraction_id") REFERENCES "fraction" ("fraction_id");

ALTER TABLE "event_user" ADD FOREIGN KEY ("participation_id") REFERENCES "user" ("user_id");

ALTER TABLE "event_user" ADD FOREIGN KEY ("event_id") REFERENCES "event" ("event_id");

ALTER TABLE "places_arendator" ADD FOREIGN KEY ("place_id") REFERENCES "places" ("places_id");

ALTER TABLE "places_arendator" ADD FOREIGN KEY ("arendator_id") REFERENCES "user" ("user_id");

ALTER TABLE "places" ADD FOREIGN KEY ("fraction_id") REFERENCES "fraction" ("fraction_id");

-- ALTER TABLE "places" ADD FOREIGN KEY ("status_id") REFERENCES "status_place" ("status_place_id");

ALTER TABLE "places_characteristic" ADD FOREIGN KEY ("characteristic_id") REFERENCES "characteristic" ("charectiristic_id");

ALTER TABLE "places_characteristic" ADD FOREIGN KEY ("place_id") REFERENCES "places" ("places_id");

ALTER TABLE "event_places" ADD FOREIGN KEY ("event_id") REFERENCES "event" ("event_id");

ALTER TABLE "event_places" ADD FOREIGN KEY ("place_id") REFERENCES "places" ("places_id");
