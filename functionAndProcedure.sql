create or replace function give_an_order(person_to integer, person_from integer, text text,
                                         status status, title varchar(120)) returns void as
$$
insert into task(task_person_to, task_person_from, task_text, task_status, task_title)
values ($1, $2, $3, $4, $5);
$$ language sql;

create or replace function update_task_status(id integer, new_status status) returns void as
$$
update task
set task_status=new_status
where task_id = id;
$$ language sql;

create or replace function get_tasks_by_person_id_to(id integer)
    returns setof task as
$$
select *
from task
where task_person_to = id;
$$ language sql;
create or replace function get_tasks_by_person_id_from(id integer)
    returns setof task as
$$
select *
from task
where task_person_from = id;
$$ language sql;

create or replace function get_not_finished_my_tasks(my_id integer)
    returns setof task as
$$
select *
from task
where task_person_to = my_id
  and task_status != 'finished';
$$ language sql;

create or replace function get_not_finished_tasks()
    returns setof task as
$$
select *
from task
where task_status != 'finished';
$$ language sql;

create or replace function make_report(created_by integer, title varchar(20), text text) returns void as
$$
insert into report(report_created_by, report_title, report_text)
values (created_by, title, text);
$$ language sql;


create or replace function create_person(name varchar(20), role role, fraction_id integer) returns void as
$$
insert into person(person_name, person_role, person_fraction_id)
values ($1, $2, $3);
$$ language sql;

create or replace function get_free_places_infraction(fraction_id integer, from_time timestamp, to_time timestamp)
    returns table
            (
                id       integer,
                fraction varchar(20),
                name     varchar(20)
            )
as
$$
select place_id as id, fraction_name as fraction, place_name as name
from place
         left join fraction using (fraction_id)
         left join place_arendator using (place_id)
where fraction_id = get_free_places_infraction.fraction_id and
      (get_free_places_infraction.from_time < to_time AND get_free_places_infraction.to_time < to_time)
   OR (get_free_places_infraction.from_time > from_time AND get_free_places_infraction.to_time > from_time)
$$ language sql;

create or replace function arendate_place(place_id integer, arendator_id integer, from_time timestamp,
                                          to_time timestamp) returns void as
$$
insert into place_arendator(place_id, arendator_id, from_time, to_time)
values ($1, $2, $3, $4)
$$ language sql;

create or replace function add_test_result(person_id integer, fraction_id integer) returns void as
$$
insert into test_result(person_id, fraction_id)
values ($1, $2);
$$ language sql;

create or replace function add_user_on_event(person_id integer, event_id integer) returns void as
$$
insert into event_person(participant_id, event_id)
values ($1, $2);
$$ language sql;

create or replace function add_char_to_place(place_id integer, charectiristic_name varchar(20)) returns void as
$$
begin
    if NOT EXISTS(select * from charectiristic where charectiristic_name = add_char_to_place.charectiristic_name) then
        insert into charectiristic (charectiristic_name) values ($2);
    end if;
    insert into place_charectiristic(char_id, place_id)
    values ((select charectiristic_id
             from charectiristic
             where charectiristic_name = $2), $1);

end;
$$ language plpgsql;
create or replace function get_places_by_char(VARIADIC chars integer[]) returns setof place as
$$
declare
    place_record place;
begin
    FOR place_record IN (SELECT *
                         from place)
        LOOP
            if EXISTS(select *
                      from place_charectiristic
                      where place_id = place_record.place_id
                        and charectiristic = all (chars)) then
                RETURN NEXT place_record;
            end if;
        END LOOP;
end;

$$ language plpgsql;
CREATE OR REPLACE FUNCTION get_place_characteristics(place_id INT)
    RETURNS TABLE
            (
                characteristic_name VARCHAR(20),
                value               VARCHAR(20)
            )
AS
$$
BEGIN
    RETURN QUERY SELECT characteristic.charectiristic_name, place_characteristic.value
                 FROM characteristic
                          JOIN place_characteristic
                               ON characteristic.charectiristic_id = place_characteristic.characteristic_id
                 WHERE place_characteristic.place_id = place_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_reports_by_person_id(person_id INT)
    RETURNS TABLE
            (
                report_id    INT,
                report_title VARCHAR(20),
                report_text  TEXT,
                created_at   TIMESTAMP,
                updated_at   TIMESTAMP
            )
AS
$$
BEGIN
    RETURN QUERY SELECT report_id, report_title, report_text, created_at, updated_at
                 FROM report
                 WHERE report_created_by = person_id;
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION get_event_participants(event_id INT)
    RETURNS TABLE
            (
                person_id   INT,
                person_name VARCHAR(20)
            )
AS
$$
BEGIN
    RETURN QUERY SELECT person.person_id, person.person_name
                 FROM person
                          JOIN event_person ON person.person_id = event_person.participant_id
                 WHERE event_person.event_id = event_id;
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE PROCEDURE add_new_place(place_name VARCHAR(20), fraction_id INT)
AS
$$
BEGIN
    INSERT INTO place (place_name, fraction_id, created_at, updated_at)
    VALUES (place_name, fraction_id, NOW(), NOW());
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE PROCEDURE assign_place_to_arendator(place_id INT, arendator_id INT, from_time TIMESTAMP,
                                                      to_time TIMESTAMP)
AS
$$
BEGIN
    INSERT INTO place_arendator (place_id, arendator_id, from_time, to_time)
    VALUES (place_id, arendator_id, from_time, to_time);
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE PROCEDURE update_event_details(event_id INT, new_event_name VARCHAR(20))
AS
$$
BEGIN
    UPDATE event SET event_name = new_event_name, updated_at = NOW() WHERE event_id = event_id;
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE PROCEDURE add_characteristic_to_place(place_id INT, characteristic_id INT, value VARCHAR(20))
AS
$$
BEGIN
    INSERT INTO place_characteristic (place_id, characteristic_id, value)
    VALUES (place_id, characteristic_id, value);
END;
$$ LANGUAGE plpgsql;

