Feature: User Sign In

  Scenario: Successful Sign In
    Given a user with the following details is registered:
      | username | password      | fullName  |
      | marc.esso | Password@123  | Marc Esso |
    When the user attempts to sign in with the following credentials:
      | username | password      |
      | marc.esso  | Password@123  |
    Then the user should be successfully signed in with a valid token

  Scenario: Sign In with Incorrect Credentials
    Given a user with the following details is registered:
      | username | password      | fullName  |
      | marc.esso | Password@123  | Marc Esso |
    When the user attempts to sign in with the following incorrect credentials:
      | username | password     |
      | marc.esso | Password@12 |
    Then the sign-in should fail with an error message "Authentication failed"

