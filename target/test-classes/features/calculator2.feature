@qw
Feature: Calculator
  Scenario: Add two numbers
    Given I have a calculator
    When I add 4 and 5
    Then the result should be 9
