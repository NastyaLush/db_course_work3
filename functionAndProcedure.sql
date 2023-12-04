create or replace function give_an_order(person_to integer, person_from integer, text text,
                                         status status, title varchar(120)) returns void as
$$
insert into task(task_person_to, task_person_from, task_text, task_status, task_title)
values ($1, $2, $3, $4, $5);
$$ language sql;

create or replace function change_status_order(id integer, new_status status) returns void as
$$
update task
set task_status=new_status
where task_id = id;
$$ language sql;

create or replace function get_all_my_tasks(my_id integer)
    returns setof task as
$$
select *
from task
where task_person_to = my_id;
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