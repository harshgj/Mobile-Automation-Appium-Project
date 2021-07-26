
@login
Feature: Verify login functionality on slideshare app

  Background: Create an instance of android or ios driver before each scenario
    Given User has slideshare app

  Scenario: User should be able to login on app with correct credentials and initial set up should be shown
    Given user has "valid" username and password
    When user enters credentials
    And taps on button
