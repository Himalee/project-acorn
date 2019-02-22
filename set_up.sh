#!/bin/bash

# Ask the user for a username
echo Hello, please enter a username to access your database:
read username

# Set up postgresql credentials as environment variables
export DBUSERNAME=$username

# Create postgresql database
createdb project_acorn
createdb project_acorn_test

# Set up postgresql database urls as environment variables
export TESTDBURL=jdbc:postgresql://localhost:5432/project_acorn_test
export PRODDBURL=jdbc:postgresql://localhost:5432/project_acorn

# Run gradle task to set up database tables and columns
gradle -q migrateproductiondb
gradle -q migratetestingdb
