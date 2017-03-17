Feature: getting user

    Scenario: client makes a call to GET /users/3 for getting the user
        Given the example user data
        When the client makes a call to get user data
        Then the client receives a user with proper values
