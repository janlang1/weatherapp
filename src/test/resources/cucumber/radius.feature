#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: radius



Scenario: Check that all cities returned are within the radiues specified on activity page
	Given I am on the activity page_two
	When I input new inputs for activity
	Then they should all be within the radius range
	
Scenario: Check that all cities returned are within the radiues specified on vacation page
	Given I am on the vacation page_two
	When I input new inputs for vacation_radius
	Then they should all be within the radius range




Scenario: Check that if the radius entered is very small, then your current location is one of the only on the list activity
	Given I am on the activity page_two
	When I input new small radius for activity
	Then the only city in the table should be seattle
Scenario: Check that if the radius entered is very small, then your current location is one of the only on the list vacation
	Given I am on the vacation page_two
	When I input new small radius for vacation
	Then the only city in the table should be seattle
