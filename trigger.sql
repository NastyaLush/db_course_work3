-- Add fraction after adding result
CREATE OR REPLACE FUNCTION add_fraction_to_user()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE "person" SET "person_fraction_id" = NEW."fraction_id";
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
        FROM places_arendator
        WHERE (NEW.from_time < "to_time" AND NEW.to_time < "to_time")
            OR (NEW.from_time > "from_time" AND NEW.to_time > "from_time")
    ) THEN
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
