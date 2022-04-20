Feature: Verify login functionality on slideshare app

  Background: Create an instance of android or ios driver before each scenario
    Given User has slideshare app

  @login1
  Scenario: User should be able to login on app with correct credentials
    Given user has "valid" username and password
    When user enters credentials
    And taps on button

  @login
  Scenario: User should be able to login on app with parameterised credentials
    Given user has "loginUsername" and "loginPassword"
    When user enters credentials
    And taps on button