#!/bin/bash

# Start psql server
brew services start postgresql

# Set up postgresql username as environment variable
export DBUSERNAME=projectacornuser

# Create postgres user
psql -c "CREATE USER projectacornuser;"

# Create postgresql database
createdb -O projectacornuser project_acorn
createdb -O projectacornuser project_acorn_test

# Grant database privileges
psql -c "grant all privileges on database project_acorn to projectacornuser;"
psql -c "grant all privileges on database project_acorn_test to projectacornuser;"
psql -c "GRANT USAGE ON SCHEMA public TO projectacornuser;"
psql -c "ALTER USER projectacornuser WITH SUPERUSER;"

# Set up postgresql database urls as environment variables
export TESTDBURL=jdbc:postgresql://localhost:5432/project_acorn_test
export PRODDBURL=jdbc:postgresql://localhost:5432/project_acorn

# Run gradle task to set up database tables and columns
gradle -q migrateproductiondb
gradle -q migratetestingdb
