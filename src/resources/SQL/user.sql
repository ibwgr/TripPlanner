-----------------------------------------------------------
-- Drop tables if already exists !
-----------------------------------------------------------
drop table if exists user;

-----------------------------------------------------------
-- Create sequences and tables
-----------------------------------------------------------
CREATE SEQUENCE userdata_id_seq;

CREATE TABLE userdata (
                id INTEGER NOT NULL DEFAULT nextval('userdata_id_seq'),
                username VARCHAR(80) NOT NULL,
                email VARCHAR(200) NOT NULL,
                type VARCHAR(40) NOT NULL,
                name VARCHAR(80) NOT NULL,
                password VARCHAR(2000) NOT NULL,
                CONSTRAINT userdata_pk PRIMARY KEY (id)
);


ALTER SEQUENCE userdata_id_seq OWNED BY userdata.id;