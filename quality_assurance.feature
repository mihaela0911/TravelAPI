Feature: Flight search without maxFlights

  Scenario: Valid flight search without specifying maxFlights
    Given the user searches for flights from "SOF" to "MLE"
    When the user submits the request without specifying maxFlights
    Then the server should return all available flights between "SOF" and "MLE" ordered ascending by price
    And the response should contain flights with valid routes and prices
	
  Scenario: Flight search with whitespaces into origin
    Given the user searches for flights from "SOF   " to "MLE"
    When the user submits the request without specifying maxFlights
    Then the server should return all available flights between "SOF   " and "MLE" ordered ascending by price
    And the response should contain flights with valid routes and prices

Feature: Flight search with maxFlights

  Scenario: Valid flight search with a maxFlights parameter 1
    Given the user searches for flights from "SOF" to "MLE" with a maxFlights of 1
    When the user submits the request with the maxFlights parameter set to 1
    Then the server should return at most 1 flight between "SOF" and "MLE"
    And the response should contain a flight with a valid route and price
	
  Scenario: Valid flight search with a maxFlights parameter
    Given the user searches for flights from "SOF" to "MLE" with a maxFlights of 3
    When the user submits the request with the maxFlights parameter set to 3
    Then the server should return all available routes between "SOF" and "MLE" 
	using max 3 flights ordered by price ascending
    And the response should contain a flight with a valid route and price
	
Feature: Flight search with invalid airport codes

  Scenario: Empty origin airport code
    Given the user searches for flights from "" to "MLE"
    When the user submits the request with an invalid origin code ""
    Then the server should return an error response
	And the response should indicate with status code 400
	
  Scenario: Empty destination airport code
    Given the user searches for flights from "SOF" to ""
    When the user submits the request with an invalid destination code ""
    Then the server should return an error response
	And the response should indicate with status code 400

  Scenario: Not three letter origin airport code
    Given the user searches for flights from "SO" to "MLE"
    When the user submits the request with an invalid origin code "SO"
    Then the server should return an error response
	And the response should indicate with status code 400

  Scenario: Not three letter destination airport code
    Given the user searches for flights from "SOF" to "ME"
    When the user submits the request with an invalid destination code "ME"
    Then the server should return an error response
	And the response should indicate with status code 400
	
  Scenario: Symbol in origin airport code
    Given the user searches for flights from "SO*" to "MLE"
    When the user submits the request with an invalid origin code "SO*"
    Then the server should return an error response
	And the response should indicate with status code 400
	
  Scenario: Symbol in destination airport code
    Given the user searches for flights from "SOF" to "ME-"
    When the user submits the request with an invalid destination code "ME-"
    Then the server should return an error response
	And the response should indicate with status code 400
	
  Scenario: Number in destination airport code
    Given the user searches for flights from "SOF" to "ME1"
    When the user submits the request with an invalid destination code "ME1"
    Then the server should return an error response code 400
	
  Scenario: Number in origin airport code
    Given the user searches for flights from "SO1" to "MLE"
    When the user submits the request with an invalid origin code "SO1"
    Then the server should return an error response
	And the response should indicate with status code 400
	
  Scenario: Lower case letter in destination airport code
    Given the user searches for flights from "SOF" to "MeL"
    When the user submits the request with an invalid destination code "MeL"
    Then the server should return an error response
	And the response should indicate with status code 400
	
  Scenario: Lower case letter in origin airport code
    Given the user searches for flights from "SOf" to "MLE"
    When the user submits the request with an invalid origin code "SOf"
    Then the server should return an error response
	And the response should indicate with status code 400
	
  Scenario: Number origin airport code
    Given the user searches for flights from 1 to "MLE"
    When the user submits the request with an invalid origin code 1
    Then the server should return an error response
	And the response should indicate with status code 400
	
  Scenario: Number destination airport code
    Given the user searches for flights from "SOF" to 2.5
    When the user submits the request with an invalid destination code 2.5
    Then the server should return an error response
	And the response should indicate with status code 400
	
  Scenario: Origin and destination are the same
    Given the user searches for flights from "SOF" to "SOF"
    When the request is submitted
    Then the server should return an error message
    And the response should indicate with status code 400
	
Feature: Flight search with no available flights

  Scenario: Searching for flights with no available routes
    Given the user searches for flights from "SOF" to "XYZ"
    When the user submits the request with "XYZ" as the destination
    Then the server should return a response indicating no available flights
    And the response should contain the message "[]"
	
Feature: Flight search with invalid maxFlights parameter

  Scenario: Searching for flights with a negative maxFlights
    Given the user searches for flights from "SOF" to "MLE" with a maxFlights of -1
    When the user submits the request with maxFlights set to -1
    Then the server should return an error response
    And the response should indicate with status code 400

  Scenario: Searching for flights with zaro maxFlights
    Given the user searches for flights from "SOF" to "MLE" with a maxFlights of 0
    When the user submits the request with maxFlights set to 0
    Then the server should return an error response
    And the response should indicate with status code 400
	
  Scenario: Searching for flights with a string maxFlights
    Given the user searches for flights from "SOF" to "MLE" with a maxFlights of "one"
    When the user submits the request with maxFlights set to "one"
    Then the server should return an error response
    And the response should indicate with status code 400
	
  Scenario: Searching for flights with a double maxFlights
    Given the user searches for flights from "SOF" to "MLE" with a maxFlights of 1.3
    When the user submits the request with maxFlights set to 1.3
    Then the server should return an error response
    And the response should indicate with status code 400
	
Feature: Flight search with missing origin

  Scenario: Searching for flights with missing origin data
    Given the user searches for flights to "MLE"
    When the user submits the request with none origin data
    Then the server should return an error response
    And the response should indicate with status code 400
	
Feature: Flight search with missing destination

  Scenario: Searching for flights with missing destination data
    Given the user searches for flights from "SOF"
    When the user submits the request with none destination data
    Then the server should return an error response
    And the response should indicate with status code 400
	
Feature: Flight search with too many arguments

  Scenario: Searching for flights with extra field "flightHour": 15
    Given the user searches for flights with "flightHour": 15
    When the user submits the request with extra data
    Then the server should return an error response
    And the response should indicate with status code 400
	
Feature: Flight search invalid JSON file

  Scenario: Searching for flights with file missing a comma
    Given the user searches for flights invalid file
    When the user submits the request
    Then the server should return an error response
    And the response should indicate with status code 400
	
Feature: Flight search non POST action

  Scenario: Searching for flights with GET command
    Given the user searches for flights with invalid command
    When the user submits the request
    Then the server should return an error response
    And the response should indicate with status code 405