Feature: Get sources

  Scenario: Successful response from newsHttpClient for getting sources
    Given the newsHttpClient returns a successful response for getting sources
    When the user calls the getSources API
    Then the source response status should be 200 OK
