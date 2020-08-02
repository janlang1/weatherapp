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
Feature: UI Tests

Scenario: Check that there is a toggle on the home page
	Given I am on the home page
	Then I should see a toggle for unit change
	


Scenario: Check that the home page has a nav bar and it is at bottom of screen
	Given I am on the home page
	Then I should see a nav bar 

Scenario: Check that the analysis page has a nav bar and that it is at the bottom of the screen
	Given I am on analysis
	Then I should see a nav bar 


	
	
	
	
Scenario: Check that the search history results are at the top of the home page and they are horizontal
	Given I am on the home page
	Then I should see the search history results at the top of the page
	And they should be in a horizontal form



Scenario: Check that the vacation page has a nav bar
	Given I am on the vacation page_two 
	Then there should be a nav bar

Scenario: Check that the activity page has a nav bar
	Given I am on the activity page_two
	Then there should be a nav bar

Scenario: On the vacation page, check that the user can enter a desired radius range as input
	Given I am on the vacation page_two
	Then there should be a field for the desired radius range as input



Scenario: On the activity page, check that user can enter a desired radius range as input
	Given I am on the activity page_two
	Then there should be a field for radius input






