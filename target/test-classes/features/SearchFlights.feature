Feature: Search Flight Functions

  Scenario: Verify the available flights are showing or not
    Given User in the home page
    When User Enters the Origin and Destination city
    When User Selects Todays Date
    When User Select The Business Class
    And Clicks the the Search Button
    Then User Redirect to another page which displays Available Flights
    And Takes Screenshot of Available Flights
