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
Feature: project1


Scenario: Check that the input box has a default focus on the home page
	Given I am on the home page
	Then the input box should have a default focus

Scenario: Check that when the user clicks on the show me the weather button, then the city information is loaded
	Given I am on the home page
	When I click on the show me the weather button
	Then the city information is loaded
	

Scenario: Check that you can enter a desired temperature range on the vacation page
	Given I am on the vacation page_two
	Then there should be two inputs for temperature range
	

Scenario: Check that there is a button that reads find my vacation spot on the vacation page
	Given I am on the vacation page_two
	Then there should be a button that reads find my vacation spot

Scenario: On vacation page, when button clicked, Check that the relevant info in the city table area for cities is displayed
	Given I am on vacation page_two
	When I click on the find my vacation spot button
	Then the correct info should be displayed in the city table area for vacation

Scenario: Check that the columns are correct on the vacation page
	Given I am on the vacation page_two
	When I input correct inputs for vacation page_two
	And I click on the find my vacation spot button
	Then the columns are correct in the vacation page_two display
	

Scenario: On vacation, check that the cities are ranked by closest distance first by default
Scenario: On activity, check that the cities are ranked by closest distance first by default
	Given I am on the vacation page_two
	When I input correct inputs for vacation page_two
	And I click on the find my vacation spot button
	Then the first city distance should be less than the second city distance
	


Scenario: Check that you can sort by distance on activity
	Given I am on the activity page_two
	When I input correct inputs for activity page_two
	And I click on the find my activity spot button
	Then I click on the distance button
	Then the radius of the first city should be greater than 10
	
	
Scenario: Check that you can sort by distance on vacation
	Given I am on the vacation page_two
	When I input correct inputs for vacation page_two
	And I click on the find my vacation spot button
	Then I click on the distance button
	Then the radius of the first city should be greater than 10

Scenario: On activity, check that the user can enter a desired outdoor activity

Scenario: On the activity page, check that the button there is a button that reads Find My Activity Spot

Scenario: On activity page, when button clicked, check that the relevant info in the table area for cities is displayed

Scenario: On activity, check that the column headers are the right value


Scenario: On Analysis make sure that there is a favorite cities list
	

Scenario: On analysis, check to make sure the user can select a city from the favorite city list area by left clicking on the city's name

Scenario: Check that when the user clicks on a city on analysis, all of the correct info is displayed

Scenario: Check that there is a remove from favorites button on the analysis page, and that it reads remove from favorites


Scenario: Check that there is a togle on vacation page
	Given I am on the vacation page_two
	Then I should see a toggle for unit change

Scenario: Check that there is a toggle on activity page
	Given I am on the activity page_two
	Then I should see a toggle for unit change

Scenario: Check that the toggle on activity page changes degrees
	Given I am on the activity page_two
	When I click on the toggle for activity unit change
	Then the temperature values should change activity
	

Scenario: Check that the toggle on vacation page changes degrees
	Given I am on the vacation page_two 
	When I click on the toggle for vacation unit change
	Then the temperature values should change vacation
	
Scenario: Check that the search history section is at the top of the home page
	Given I am on the home page
	Then search history section should be at top of page
	
	
Scenario: Check that there is a toggle on the analysis page
	Given I am on analysis
	Then I should see a toggle for unit change
	
Scenario: Check that the toggle on the analysis page changes degrees
	Given I am on analysis
	When I click on the toggle for analysis unit change
	Then the temperature values should change analysis
Scenario: Check that the toggle on the home page changes degrees
	Given I am on the home page
	When I input a city
	When I click on the toggle for homepage unit change
	Then the temperature values should change home
	