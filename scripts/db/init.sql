CREATE DATABASE rest;

\connect rest;

CREATE USER springbootuser WITH ENCRYPTED PASSWORD 'admin';
GRANT ALL PRIVILEGES ON DATABASE rest TO springbootuser;
