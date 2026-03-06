-- Idempotent PostgreSQL setup script for TaskTrack (run from psql)

SELECT 'CREATE DATABASE tasktrackdb'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'tasktrackdb')\gexec

GRANT ALL PRIVILEGES ON DATABASE tasktrackdb TO postgres;
