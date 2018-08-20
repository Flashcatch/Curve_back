# --- !Ups


ALTER TABLE  blockbookings DROP COLUMN IF EXISTS duration;

ALTER TABLE blockbookings ADD COLUMN IF NOT EXISTS duration INTEGER;

ALTER TABLE public.blockbookings ALTER COLUMN description TYPE TEXT USING description::TEXT;

ALTER TABLE  blockbookings DROP COLUMN IF EXISTS starttime;

ALTER TABLE blockbookings ADD COLUMN IF NOT EXISTS starttime TIMESTAMP;

ALTER TABLE  blockbookings DROP COLUMN IF EXISTS finishtime;

ALTER TABLE blockbookings ADD COLUMN IF NOT EXISTS finishtime TIMESTAMP;

ALTER TABLE  appointments DROP COLUMN IF EXISTS starttime;

ALTER TABLE appointments ADD COLUMN IF NOT EXISTS starttime TIMESTAMP;