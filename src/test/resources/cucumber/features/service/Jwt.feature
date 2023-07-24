Feature: JWT Service Feature

  Scenario: Generate JWT Token
    Given the user details:
      | username | password |
      | marc.esso | Password@123 |
    When the JWT token is generated
    Then the token should be non-empty

  Scenario: Validate JWT Token
    Given the user details:
      | username | password |
      | marc.esso | Password@123 |
    And the JWT token is generated
    When the token is validated with the user details
    Then the token should be valid
