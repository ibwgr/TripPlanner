-----------------------------------------------------------
-- Drop tables if already exists !
-----------------------------------------------------------
DROP TABLE IF EXISTS tp_activity;
DROP TABLE IF EXISTS tp_trip;
DROP TABLE IF EXISTS poi;
DROP TABLE IF EXISTS poi_category;
DROP TABLE IF EXISTS tp_user;

-----------------------------------------------------------
-- Create tables
-----------------------------------------------------------
CREATE TABLE poi_category
(
   id     varchar (50) PRIMARY KEY,
   name   varchar (1000)
);

CREATE TABLE poi
(
   id          varchar (50) PRIMARY KEY,
   longitude   varchar (20),
   latitude    varchar (20),
   category_id varchar (50),
   name        varchar (1000),
   FOREIGN KEY (category_id) REFERENCES poi_category (id)
);

CREATE SEQUENCE tp_user_id_seq;
CREATE TABLE tp_user
(
   id          int PRIMARY KEY NOT NULL DEFAULT nextval('tp_user_id_seq'),
   username    varchar (100),
   password    varchar (100),
   email       varchar (100),
   name        varchar (250),
   type        int -- 1 = User, 2 = Administrator
);
ALTER SEQUENCE tp_user_id_seq OWNED BY tp_user.id;


CREATE TABLE tp_trip
(
   id          int PRIMARY KEY,
   user_id     int,
   name        varchar(250),
   FOREIGN KEY (user_id) REFERENCES tp_user (id)
);

CREATE TABLE tp_activity
(
   id          int PRIMARY KEY,
   trip_id     int,
   poi_id      varchar(50),
   date        varchar(250),
   comment     varchar(250),
   FOREIGN KEY (trip_id) REFERENCES tp_trip (id),
   FOREIGN KEY (poi_id) REFERENCES poi (id)
);

-----------------------------------------------------------
-- Function for update when id already exists
-----------------------------------------------------------
CREATE OR REPLACE FUNCTION poi_insert_before_func()
RETURNS trigger AS

$BODY$

DECLARE
    exists VARCHAR;
BEGIN

    UPDATE poi SET longitude=new.longitude, latitude=new.latitude, category_id=new.category_id, name=new.name
    WHERE id=new.id
    RETURNING id INTO exists;

    -- If the above was successful, it would return non-null
    -- in that case we return NULL so that the triggered INSERT
    -- does not proceed
    IF exists is not null THEN
        RETURN NULL;
    END IF;

    -- Otherwise, return the new record so that triggered INSERT
    -- goes ahead
    RETURN new;


END;

$BODY$

LANGUAGE plpgsql VOLATILE
COST 100;

CREATE OR REPLACE FUNCTION poi_category_insert_before_func()
RETURNS trigger AS

$BODY$

DECLARE
    exists VARCHAR;
BEGIN

    UPDATE poi_category SET name=new.name
    WHERE id=new.id
    RETURNING id INTO exists;

    -- If the above was successful, it would return non-null
    -- in that case we return NULL so that the triggered INSERT
    -- does not proceed
    IF exists is not null THEN
        RETURN NULL;
    END IF;

    -- Otherwise, return the new record so that triggered INSERT
    -- goes ahead
    RETURN new;


END;

$BODY$

LANGUAGE plpgsql VOLATILE
COST 100;

-----------------------------------------------------------
-- Trigger for insert into poi table
-----------------------------------------------------------
CREATE TRIGGER poi_insert_before_trigger
   BEFORE INSERT
   ON public.poi
   FOR EACH ROW
EXECUTE PROCEDURE poi_insert_before_func ();

CREATE TRIGGER poi_category_insert_before_trigger
   BEFORE INSERT
   ON public.poi_category
   FOR EACH ROW
EXECUTE PROCEDURE poi_category_insert_before_func ();


-----------------------------------------------------------
-- some dummy data
-----------------------------------------------------------
insert into tp_user ( username, password, email, name, type ) /* 1 = User, 2 = Administrator */
values ('benutzer','benutzer','reto.kaufmann@adon.li','benutzer',1)
;
insert into tp_user ( username, password, email, name, type ) /* 1 = User, 2 = Administrator */
values ('admin','admin','reto.kaufmann@adon.li','admin',2)
;
commit
;
--select *
--from tp_user
--;