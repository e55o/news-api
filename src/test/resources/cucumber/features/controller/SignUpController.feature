Feature: User Signup Controller

  Scenario: Successful User Signup
    Given any request to signUp controller
      | fullName  | username  | password    |
      | Marc Esso  | marc.esso   | Password@123 |
    When the user attempts to signup
    Then the signup should succeed