Feature: Pagination
	Scenario: Check that previous and next buttons are spelled correctly on vacation
  		Given I am on vacation page logged in
  		When I submit valid inputs and go to page two of vacation
  		Then Previous and Next button should appear and spelled correctly
		
	Scenario: Check that current page is highlighted on vacation
		Given I am on vacation page logged in
		When I submit valid inputs and go to page two of vacation
		Then Page 2 should be highlighted
	Scenario: Check that previous button loads previous range on vacation
		Given I am on vacation page logged in
		When I submit valid inputs and go to page two of vacation
		And go to page 1
		Then Cities ranged 1-5 should show
		
	Scenario: Check that next button loads next range on vacation
		Given I am on vacation page logged in
		When I submit valid inputs and go to page two of vacation
		And go to page 1
		And go to page 2
		Then Cities ranged 6-10 should show
		
	Scenario: Check ascending distance sorting on vacation
		Given I am on vacation page logged in
		When I submit valid inputs and go to page two of vacation
		Then Distance should be in ascending order
	Scenario: Check descending distance sorting on vacation
		Given I am on vacation page logged in
		When I submit valid inputs and go to page two of vacation
		And Click distance sorting button
		Then Distance should be in descending order
	
	Scenario: Check that previous and next buttons are spelled correctly on activity
		Given I am on activity page logged in
		When I submit valid inputs and go to page two of activity
		Then Previous and Next button should appear and spelled correctly
		
	Scenario: Check that current page is highlighted on activity
		Given I am on activity page logged in
		When I submit valid inputs and go to page two of activity
		Then Page 2 should be highlighted
	Scenario: Check that previous button loads previous range on activity
		Given I am on activity page logged in
		When I submit valid inputs and go to page two of activity
		And go to page 1
		Then Cities ranged 1-5 should show
		
	Scenario: Check that next button loads next range on activity
		Given I am on activity page logged in
		When I submit valid inputs and go to page two of activity
		And go to page 1
		And go to page 2
		Then Cities ranged 6-10 should show
		
	Scenario: Check ascending distance sorting on activity
		Given I am on activity page logged in
		When I submit valid inputs and go to page two of activity
		Then Distance should be in ascending order
		
	Scenario: Check descending distance sorting on activity
		Given I am on activity page logged in
		When I submit valid inputs and go to page two of activity
		And Click distance sorting button
		Then Distance should be in descending order
		
	Scenario: Check for no num results button on vacation
		Given I am on vacation page logged in
		Then There is no num results button
	Scenario: Check for no num results button on activity
		Given I am on activity page logged in
		Then There is no num results button
		
	Scenario: Check ascending like sorting on vacation
		Given I am on vacation page logged in
		When I submit valid inputs and go to page two of vacation
		Then Likes should be in ascending order
	Scenario: Check ascending like sorting on activity
		Given I am on activity page logged in
		When I submit valid inputs and go to page two of activity
		Then Likes should be in ascending order
	Scenario: Check descending like sorting on vacation
		Given I am on vacation page logged in
		When I submit valid inputs and go to page two of vacation
		Then Likes should be in descending order
	Scenario: Check descending like sorting on activity
		Given I am on activity page logged in
		When I submit valid inputs and go to page two of activity
		Then Likes should be in descending order