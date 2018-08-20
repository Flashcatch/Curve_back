# --- !Ups

create table IF NOT EXISTS appointments
(
	id bigserial not null
		constraint appointments_pk
			primary key,
	type boolean not null,
	starttime time with time zone not null,
	duration integer not null,
	patient_id bigint,
	blockbooking_id bigint
)
;

create table IF NOT EXISTS patients
(
	id bigserial not null
		constraint patients_pk
			primary key,
	firstname text not null,
	lastname text not null,
	birthdate timestamp not null
)
;

DO $$
BEGIN
  IF NOT exists(SELECT 1
                FROM pg_constraint
                WHERE conname = 'appointments_fk0')
  THEN
alter table IF EXISTS appointments
	add constraint appointments_fk0
		foreign key (patient_id) references patients
;;
  END IF;;
END;;
$$;


create table IF NOT EXISTS blockbookings
(
	id bigserial not null
		constraint blockbookings_pk
			primary key,
	description char,
	starttime time with time zone not null,
	duration time not null,
	recurtype varchar(50),
	finishtime time with time zone
)
;

DO $$
BEGIN
  IF NOT exists(SELECT 1
                FROM pg_constraint
                WHERE conname = 'appointments_fk1')
  THEN
alter table IF EXISTS appointments
	add constraint appointments_fk1
		foreign key (blockbooking_id) references blockbookings
;;
  END IF;;
END;;
$$;
