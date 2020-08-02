Feature: User Data
		
	Scenario: Search history exist after logging in
		Given I am on the home page
		Then I should see search history
	
	Scenario: No NumResult on activity page
		Given  I am on the activity page
		Then no numResult input
		
	Scenario: No NumResult on vacation page
		Given  I am on the activity page
		Then no numResult input
	
	
	 