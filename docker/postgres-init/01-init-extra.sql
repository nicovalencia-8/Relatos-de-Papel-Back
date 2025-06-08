CREATE USER payments WITH PASSWORD 'R3l4t0SP4ym3ntS';
CREATE DATABASE paymentsdb OWNER payments;

\connect paymentsdb
ALTER SCHEMA public OWNER TO payments;

CREATE USER catalogue WITH PASSWORD 'R3l4t0SC4t4l0gu3';
CREATE DATABASE cataloguedb OWNER catalogue;

\connect cataloguedb
ALTER SCHEMA public OWNER TO catalogue;