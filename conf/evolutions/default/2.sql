# --- !Ups

ALTER TABLE appointments ALTER COLUMN type TYPE INTEGER USING type::INTEGER;