Feature: Get News Controller

  Scenario: User fetches sources controller
    Given any request to news controller
      | fullName  | username  | password    |
      | Marc Esso  | marc.esso   | Password@123 |
    When user attempts to call sources endpoint
    Then response should be successful


  Scenario: User fetches top headlines controller
    Given any request to news controller
      | fullName  | username  | password    |
      | Marc Esso  | marc.esso   | Password@123 |
    When user attempts to call top headlines endpoint
    Then response should be successful