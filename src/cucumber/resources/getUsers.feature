Feature: getting users

    Scenario: client makes a call to GET /users for getting all of the users
        Given the example users data
        When the client makes a call to get users data
        Then the client receives list of users
