-----------------------------------------------------------
-- Drop tables if already exists !
-----------------------------------------------------------
drop table if exists poi_category;
drop table if exists poi;

-----------------------------------------------------------
-- Create tables
-----------------------------------------------------------
create table poi_category (
	 id			  int PRIMARY KEY
	,name 		varchar(1000)
);

create table poi (
	 id			    varchar(50) PRIMARY KEY
	,longitude  varchar(20)
	,latitude   varchar(20)
	,category   int
	,name 		  varchar(1000)
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

    UPDATE poi SET longitude=new.longitude, latitude=new.latitude, category=new.category, name=new.name
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
COST 100

-----------------------------------------------------------
-- Trigger for insert into poi table
-----------------------------------------------------------
CREATE TRIGGER poi_insert_before_trigger
   BEFORE INSERT
   ON public.poi
   FOR EACH ROW
EXECUTE PROCEDURE poi_insert_before_func ();