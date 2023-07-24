Feature: Get File Controller

  Scenario: User downloads a file
    Given any request to file controller
      | fullName  | username  | password    |
      | Marc Esso  | marc.esso   | Password@123 |
    When the user attempts to download a file from url
    Then the user should get successful response