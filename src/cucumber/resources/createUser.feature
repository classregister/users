Feature: creating users

    Scenario: client makes a call to POST /users for creating a new  user
        Given the client has login and password "{ 'login': 'userLogin', 'password': 'userPassword' }"
        When the client makes a call to create user
        Then the client receives status code of 200
        And the client creates a new user with proper $values

    Scenario: client makes a call to POST /users for creating a new user
        Given the client has empty login and password "{ 'login': '', 'password': '' }"
        When the client makes a call to create user with invalid login and password
        Then the client receives status 400 Bad Request
