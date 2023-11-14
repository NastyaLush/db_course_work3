CREATE
OR REPLACE FUNCTION add_fruction_to_user () RETURNS trigger AS $$
BEGIN
update user set user."user_fraction_id"=NEW."fraction_id"
END;
$$
LANGUAGE  plpgsql;

CREATE TRIGGER user_update_trigger
    AFTER INSERT
    ON "user"
    FOR EACH ROW
    EXECUTE PROCEDURE add_fruction_to_user();

CREATE
OR REPLACE FUNCTION update_place_status () RETURNS trigger AS $$
BEGIN
update user set user."user_fraction_id"=NEW."fraction_id"
END;
$$
LANGUAGE  plpgsql;

CREATE TRIGGER user_update_trigger
    AFTER INSERT
    ON "user"
    FOR EACH ROW
    EXECUTE PROCEDURE add_fruction_to_user();
