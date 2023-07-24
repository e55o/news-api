Feature: Get top headlines

  Scenario: Successful response from newsHttpClient
    Given the newsHttpClient returns a successful response
    When the user calls the getTopHeadlines API with pageSize 10 and pageNumber 1
    Then the response status should be 200 OK

  Scenario: Exception from newsHttpClient
    Given the newsHttpClient throws an exception
    When the user calls the getTopHeadlines API with pageSize 10 and pageNumber 1
    Then the response status should be 200 OK