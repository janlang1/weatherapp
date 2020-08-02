Feature: Input Validation

	# Vacation Page
	Scenario: Input empty value to Low Temp on Vacation Page
		Given I am on vacation page logged in
		When I input empty value to low temp
		Then I should see highlighted Low Temp box 
		And There to be an error message that reads Illegal value for input Low Temp

	Scenario: Input empty value to High Temp on Vacation Page
		Given I am on vacation page logged in
		When I input empty value to high temp
		Then I should see highlighted High Temp box 
		And There to be an error message that reads Illegal value for input High Temp

	Scenario: Input empty value to Radius on Vacation Page
		Given I am on vacation page logged in
		When I input empty value to Radius
		Then I should see highlighted Radius box 
		And There to be an error message that reads Illegal value for input Radius
		
	Scenario: Input negative value to Radius on Vacation Page
		Given I am on vacation page logged in
		When I input negative value to Radius on Vacation page
		Then I should see highlighted Radius box
		And There to be an error message that reads Illegal value for input Radius

	# Activity Page
	Scenario: Input empty value to Activity on Activity Page
		Given I am on activity page logged in
		When I input empty value to Activity
		Then I should see highlighted Activity box
		And There to be an error message that reads Illegal value for input Activity

	Scenario: Input empty value to Radius on Activity Page
		Given I am on activity page logged in
		When I input empty value to Radius on Activity Page
		Then I should see highlighted Radius box
		And There to be an error message that reads Illegal value for input Radius
	
	Scenario: Input negative value to Radius on Activity Page
		Given I am on activity page logged in
		When I input negative value to Radius on Activity page
		Then I should see highlighted Radius box
		And There to be an error message that reads Illegal value for input Radius

	# Home Page
	Scenario: Input an invalid value to city name on Home Page
		Given I am on the home page
		When I input empty value to city name
		Then The message should read No Location Found
