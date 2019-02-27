# Project Acorn

Project Acorn is a Java CRUD, create, read, update and delete, command line app for keeping track of investment opportunities. Opportunities have a

- `Name`
- `Description`
- `User`
- `Cost`
- `Date`
- `Stage` - *'To be discussed'*, *'In discussion'*, *'Approved'*, *'Declined'* and *'Expired'*

Users have the ability to:
- Create new opportunities that can be saved to the database
- Select an opportunity based on an opportunity id
- Display all opportunities that have been saved to the database
- Update any opportunity attribute
- Delete an opportunity

## Getting Started

#### Prerequisites
1. Install [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. Install [Homebrew](https://brew.sh/) by running `$ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`
3. Install [Postgresql](https://www.postgresql.org/) by running `$ brew install postgresql`
4. Install [Gradle](https://gradle.org/) by running `$ brew install gradle`
5. Install [Flyway](https://flywaydb.org/) by running `$ brew install flyway`

#### Running instructions

1. Clone the repository
2. In your terminal, navigate into the repository
3. Run shell script by running `$ . ./set_up.sh` 
4. Run the test suite by running `$ ./gradlew test`
5. Start the app by running `$ gradle --console plain run`

#### Roadmap

- [ ] Display a filtered list of opportunities based on an opportunity attribute e.g. all opportunities that are in the `In discussion` stage.
- [ ] Add an event type attribute to an opportunity e.g. education, sponsorship or outreach.

