Feature: User registration
  As a new user
  I want to register in the API
  So that I can receive an authentication token

  Scenario: Successful registration
    Given I send a POST request to "/api/register" with the registration data
    When the registration request is processed
    Then the registration response status code should be 200
    And the response should contain the user's id and token
