Feature: API status check
  Scenario: Check if API is up
    Given the API is running
    When I send a GET request to "https://reqres.in/api/users?page=2"
    Then the response status code should be 200

