Feature: NewJungTests
	Scenario: Check descending distance sorting on vacation
		Given I am on vacation page logged in
		When I submit valid inputs and go to page two of vacation
		And Click distance sorting button
		Then Distance should be in descending order
	Scenario: Check descending distance sorting on activity
		Given I am on activity page logged in
		When I submit valid inputs and go to page two of activity
		And Click distance sorting button
		Then Distance should be in descending order