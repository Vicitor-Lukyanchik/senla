@echo off
c:\PostgreSQL\bin\psql.exe -f db_create.sql postgresql://postgres:1234@localhost:5432
c:\PostgreSQL\bin\psql.exe -f db_schema.sql -f db_inserts.sql postgresql://postgres:1234@localhost:5432/hotel
pause