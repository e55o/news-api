Feature: Get sources

  Scenario: Successful response from newsHttpClient for getting sources
    Given the newsHttpClient returns a successful response for getting sources
    When the user calls the getSources
    Then the source response status should be 200 OK

  Scenario: Exception from newsHttpClient
    Given the newsHttpClient throws an exception for getSources
    When the user calls the getSources
    Then the source service should throw exception