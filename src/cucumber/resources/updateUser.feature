Feature: updating users data

    Scenario: client makes a call to PUT /users/1 for updating user data
        Given the client has login and password "{ 'login': 'user', 'password': 'user_password' }" for update existing account
        When the client makes a call to update user
        Then the client receives status code of 200 - succesful update
        And the client updates a user with proper $values
