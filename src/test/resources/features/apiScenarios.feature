Feature: CityBikes

  @APITest
  Scenario: Retrieve networks
    Given I am using CityBikes API
    When I call retrieve networks list
    Then some entries are returned

  @APITest
  Scenario: Retrieve Frankfurt location
    Given I am using CityBikes API
    When I call network_id api for "visa-frankfurt"
    Then location is returned
    And country is returned
    And city is returned

  @APITest
  Scenario: Confirm Frankfurt is in Germany
    Given I am using CityBikes API
    When I call network_id api for "visa-frankfurt"
    Then "Frankfurt" is in "Germany"

  @APITest
  Scenario: Confirm Bath is in United Kingdom
    Given I am using CityBikes API
    When I call network_id api for "nextbike-bath"
    Then "Bath" is in "United Kingdom"

  @APITest
  Scenario Outline: Confirm city is in Country
    Given I am using CityBikes API
    When I call network_id api for <network_id>
    Then <city> is in <country>

    Examples:
      |  network_id     | city       | country         |
      |  visa-frankfurt | Frankfurt  | Germany         |
      |  nextbike-bath  | Bath       | United Kingdom  |
