Feature: User Sign In Controller

  Scenario: Successful Sign In
    Given any request to signIn controller
    When user attempts to sign in with the following credentials:
      | username | password      |
      | marc.esso  | Password@123  |
    Then user should be successfully signed in with a valid token


