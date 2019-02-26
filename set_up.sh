#!/bin/bash

# Database username
username=projectacornuser

# Set up postgresql username as environment variable
export DBUSERNAME=$username

# Create postgres user
psql -c "CREATE USER $username;"

# Create postgresql database
createdb -O $username project_acorn
createdb -O $username project_acorn_test

# Set up postgresql database urls as environment variables
export TESTDBURL=jdbc:postgresql://localhost:5432/project_acorn_test
export PRODDBURL=jdbc:postgresql://localhost:5432/project_acorn

# Run gradle task to set up database tables and columns
gradle -q migrateproductiondb
gradle -q migratetestingdb
