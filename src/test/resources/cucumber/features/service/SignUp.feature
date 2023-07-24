Feature: User Signup Service

  Scenario: Successful User Signup
    Given a new user with the following details registering:
      | fullName  | username  | password    |
      | Marc Esso  | marc.esso   | Password@123 |
    When user attempts to signup
    Then user should be successfully registered with the following response:
      | fullName  | username  |
      | Marc Esso  | marc.esso   |