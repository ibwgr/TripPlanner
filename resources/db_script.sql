-----------------------------------------------------------
-- Drop tables if already exists !
-----------------------------------------------------------
DROP VIEW IF EXISTS tp_trip_full_v, tp_trip_aggr_v;
DROP TABLE IF EXISTS poi, poi_category, tp_user, tp_activity, tp_trip;

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
  id            varchar (50) PRIMARY KEY,
  longitude     varchar (20),
  latitude      varchar (20),
  category_id   varchar (50),
  name          varchar (1000),
  FOREIGN KEY (category_id) REFERENCES poi_category (id)
);

CREATE SEQUENCE tp_user_id_seq;

CREATE TABLE tp_user
(
  id         int PRIMARY KEY NOT NULL DEFAULT nextval ('tp_user_id_seq'),
  username   varchar (100),
  password   varchar (100),
  email      varchar (100),
  name       varchar (250),
  type       int                               -- 1 = User, 2 = Administrator
);

ALTER SEQUENCE tp_user_id_seq OWNED BY tp_user.id;


CREATE SEQUENCE tp_trip_id_seq;

CREATE TABLE tp_trip
(
  id        int PRIMARY KEY NOT NULL DEFAULT nextval ('tp_trip_id_seq'),
  user_id   int,
  name      varchar (250),
  FOREIGN KEY (user_id) REFERENCES tp_user (id)
);

ALTER SEQUENCE tp_trip_id_seq OWNED BY tp_trip.id;


CREATE SEQUENCE tp_activity_id_seq;

CREATE TABLE tp_activity
(
  id        int PRIMARY KEY NOT NULL DEFAULT nextval ('tp_activity_id_seq'),
  trip_id   int,
  poi_id    varchar (50),
  date      date,
  comment   varchar (250),
  FOREIGN KEY (trip_id) REFERENCES tp_trip (id) ON DELETE CASCADE,
  FOREIGN KEY (poi_id) REFERENCES poi (id)
);

ALTER SEQUENCE tp_activity_id_seq OWNED BY tp_activity.id;

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
-- VIEW: Reise (Komplett inkl. Aktivitaeten und POI)
-----------------------------------------------------------

CREATE OR REPLACE VIEW tp_trip_full_v
AS
  SELECT t.id AS trip_id,
    t.user_id,
    t.name  AS trip_name,
    a.id    AS activity_id,
    a.date,
    a.comment,
    a.poi_id,
    p.longitude,
    p.latitude,
    p.name  AS poi_name,
    p.category_id,
    pc.name AS poi_category_name
  FROM tp_trip t
    LEFT JOIN tp_activity a ON t.id = a.trip_id
    LEFT JOIN poi p ON a.poi_id = p.id
    LEFT JOIN poi_category pc ON p.category_id = pc.id;

/*
select *
from tp_trip_full_v
;
*/
-- VIEW: Reise (inkl. aggregierte Aktivitaets-Informationen)

CREATE OR REPLACE VIEW tp_trip_aggr_v
AS
  SELECT trip_id,
    user_id,
    trip_name,
    max (date)        AS max_date,
    min (date)        AS min_date,
    count (activity_id) AS count_acitvities
  FROM tp_trip_full_v
  GROUP BY trip_id, user_id, trip_name;

/*
select trip_id, user_id, trip_name, max_date, min_date, count_acitvities
from tp_trip_aggr_v
;
*/


-----------------------------------------------------------
-- some dummy USER data
-----------------------------------------------------------

INSERT INTO tp_user (username,
                     password,
                     email,
                     name,
                     type)                   /* 1 = User, 2 = Administrator */
VALUES ('benutzer',
        'benutzer',
        'benutzer@example.com',
        'benutzer',
        1);

INSERT INTO tp_user (username,
                     password,
                     email,
                     name,
                     type)                   /* 1 = User, 2 = Administrator */
VALUES ('admin',
        'admin',
        'admin@example.com',
        'admin',
        2);

COMMIT;
/*
select *
from tp_user
;
*/

-----------------------------------------------------------
-- some dummy TRIP data
-----------------------------------------------------------

INSERT INTO tp_trip (user_id, name)
VALUES (1, 'Sommerferien Schweden 2016');

INSERT INTO tp_trip (user_id, name)
VALUES (1, 'Frühlingsferien USA 2017 mit Kunzes aus Bünzen');

INSERT INTO tp_trip (user_id, name)
VALUES (1, 'Noch völlig unklarer Trip über Ostern');

COMMIT;
/*
select *
from tp_trip;
*/
-----------------------------------------------------------
-- some dummy TRIP_ACTIVITY data
-----------------------------------------------------------

INSERT INTO tp_activity (trip_id,
                         poi_id,
                         date,
                         comment)
VALUES (1,
        (  SELECT id
           FROM poi
           ORDER BY id
           LIMIT 1),
        to_date ('01.07.2016', 'dd.mm.yyyy'),
        'direkt nach dem Frühstück');

INSERT INTO tp_activity (trip_id,
                         poi_id,
                         date,
                         comment)
VALUES (1,
        (  SELECT id
           FROM poi
           ORDER BY longitude
           LIMIT 1),
        to_date ('01.07.2016', 'dd.mm.yyyy'),
        'direkt danach');

INSERT INTO tp_activity (trip_id,
                         poi_id,
                         date,
                         comment)
VALUES (1,
        (  SELECT id
           FROM poi
           ORDER BY latitude
           LIMIT 1),
        to_date ('02.07.2016', 'dd.mm.yyyy'),
        'je nach Wetter');

INSERT INTO tp_activity (trip_id,
                         poi_id,
                         date,
                         comment)
VALUES (2,
        (  SELECT id
           FROM poi
           ORDER BY name
           LIMIT 1),
        to_date ('08.08.2017', 'dd.mm.yyyy'),
        'da gibts das beste Bier!');

/*
select *
from tp_activity
*/