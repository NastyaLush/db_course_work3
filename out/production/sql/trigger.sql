-- Add fraction after adding result
CREATE OR REPLACE FUNCTION add_fraction_to_user()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE "person" SET "person_fraction_id" = NEW."fraction_id" WHERE "person_id" = NEW."person_id";
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER user_update_trigger
AFTER INSERT
ON "test_result"
FOR EACH ROW
EXECUTE FUNCTION add_fraction_to_user();


-- Before adding to places_arendator, check is free
CREATE OR REPLACE FUNCTION check_before_insert_into_places_arendator()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM place_arendator
        WHERE place_arendator.place_id = NEW.place_id
          AND (
                (NEW.from_time < place_arendator.to_time AND NEW.to_time > place_arendator.to_time)
                OR (NEW.from_time > place_arendator.from_time AND NEW.to_time > place_arendator.from_time)
            ) ) THEN
        RAISE EXCEPTION 'The time interval overlaps with existing data';
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER before_insert_trigger
    BEFORE INSERT ON place_arendator
    FOR EACH ROW
EXECUTE FUNCTION check_before_insert_into_places_arendator();


CREATE OR REPLACE FUNCTION prevent_delete_person_with_reports()
    RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT COUNT(*) FROM report WHERE report_created_by = OLD.person_id) > 0 THEN
        RAISE EXCEPTION 'Cannot delete a person with reports';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER prevent_delete_person
    BEFORE DELETE ON person
    FOR EACH ROW
EXECUTE FUNCTION prevent_delete_person_with_reports();


-- Role updated_at
CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Reports updated_at
CREATE TRIGGER reports_updated_at_trigger
BEFORE UPDATE ON "report"
FOR EACH ROW
EXECUTE FUNCTION update_updated_at();

-- User updated_at
CREATE TRIGGER user_updated_at_trigger
BEFORE UPDATE ON "person"
FOR EACH ROW
EXECUTE FUNCTION update_updated_at();

-- Task updated_at
CREATE TRIGGER task_updated_at_trigger
BEFORE UPDATE ON "task"
FOR EACH ROW
EXECUTE FUNCTION update_updated_at();

-- Fraction updated_at
CREATE TRIGGER fraction_updated_at_trigger
BEFORE UPDATE ON "fraction"
FOR EACH ROW
EXECUTE FUNCTION update_updated_at();

-- Event updated_at
CREATE TRIGGER event_updated_at_trigger
BEFORE UPDATE ON "event"
FOR EACH ROW
EXECUTE FUNCTION update_updated_at();

-- Event_places updated_at
CREATE TRIGGER event_places_updated_at_trigger
BEFORE UPDATE ON "event_place"
FOR EACH ROW
EXECUTE FUNCTION update_updated_at();

-- Places updated_at
CREATE TRIGGER places_updated_at_trigger
BEFORE UPDATE ON "place"
FOR EACH ROW
EXECUTE FUNCTION update_updated_at();

-- Characteristic updated_at
CREATE TRIGGER characteristic_updated_at_trigger
BEFORE UPDATE ON "characteristic"
FOR EACH ROW
EXECUTE FUNCTION update_updated_at();
CREATE OR REPLACE FUNCTION enforce_task_status_transition()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.task_status = 'in_process' AND OLD.task_status != 'created' THEN
        RAISE EXCEPTION 'Invalid task status transition';
    ELSIF NEW.task_status = 'checked' AND OLD.task_status NOT IN ('in_process', 'need_check') THEN
        RAISE EXCEPTION 'Invalid task status transition';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_update_enforce_status_transition
    BEFORE UPDATE ON task
    FOR EACH ROW
EXECUTE FUNCTION enforce_task_status_transition();

CREATE OR REPLACE FUNCTION prevent_update_finished_tasks()
    RETURNS TRIGGER AS $$
BEGIN
    IF OLD.task_status = 'finished' THEN
        RAISE EXCEPTION 'Cannot update finished reports';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_update_prevent_finished_tasks
    BEFORE UPDATE ON task
    FOR EACH ROW
EXECUTE FUNCTION prevent_update_finished_tasks();
CREATE OR REPLACE FUNCTION enforce_task_deletion_rules()
    RETURNS TRIGGER AS $$
BEGIN
    IF OLD.task_status IN ('in_process', 'checked') THEN
        RAISE EXCEPTION 'Cannot delete tasks in "in_process" or "checked" status';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_delete_enforce_task_rules
    BEFORE DELETE ON task
    FOR EACH ROW
EXECUTE FUNCTION enforce_task_deletion_rules();

