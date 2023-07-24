Feature: File Download Feature

  Scenario: Download a file with specific content type
    Given the file URL is "https://example.com/sample.txt"
    When I request the file
    Then the file should be downloaded successfully
    And the file should have the name "sample.txt"