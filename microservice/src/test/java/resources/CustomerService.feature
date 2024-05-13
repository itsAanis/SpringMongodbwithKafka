Feature: get Customer

  Scenario: request to return Customer by Id
    Given an customer with the provided Id 1 exists
    When a get request is made for the customer with ID 1
    Then an Customer is returned after caching data in redis