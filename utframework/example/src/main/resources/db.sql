CREATE TABLE table1
(
  id integer NOT NULL,
  "value" character varying,
  CONSTRAINT table1_pkey PRIMARY KEY (id)
);
CREATE TABLE table2
(
  id serial NOT NULL,
  "name" character varying,
  CONSTRAINT table2_pkey PRIMARY KEY (id)
);