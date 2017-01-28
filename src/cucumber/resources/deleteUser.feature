Feature: removing users

    Scenario: client makes a call to DELETE /users/2 for removing the user
        Given the user already exists
        When the client makes a call to delete user
        Then the user has been deleted
