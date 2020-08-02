Feature: User Auth

	Scenario: logout button exists on home page
		Given I am on the home page
		Then confirm logout button exists
	
	Scenario: logout button exists on vacation page
		Given I am on the vacation page
		Then confirm logout button exists

	Scenario: logout button exists on activity page 
		Given I am on the activity page
		Then confirm logout button exists

	Scenario: logout button exists on analysis page
		Given I am on the analysis page
		Then confirm logout button exists


	Scenario: logout button on home page works
		Given I am on the home page		
		When logout from the page
		Then I should see login.jsp

	Scenario: logout button on vacation works
		Given I am on the vacation page
		When logout from the page
		Then I should see login.jsp
		
	Scenario: logout button on activity works		
		Given I am on the activity page
		When logout from the page
		Then I should see login.jsp

	Scenario: logout button on analysis works		
		Given I am on the analysis page
		When logout from the page
		Then I should see login.jsp
		
		
	Scenario: I can no longer access home page after logging out
		Given I am logged out
		When access home page
		Then I should see login.jsp

	Scenario: I can no longer access vacation page after logging out
		Given I am logged out
		When access vacation page
		Then I should see login.jsp
	
	Scenario: I can no longer access activity page after logging out
		Given I am logged out
		When access activity page
		Then I should see login.jsp	
	
	Scenario: I can no longer access analysis page after logging out
		Given I am logged out
		When access analysis page
		Then I should see login.jsp

	
	Scenario: Going to root url redirects to login page
		Given I am on the login page
		When access root url
		Then I should see login.jsp
	
	Scenario: User cannot access login page if already logged in
		Given I am on the home page
		When access login page
		Then I should see HomePage.jsp in url
		
	Scenario: User cannot access sign up page if already logged in
		Given I am on the home page
		When access sign up page
		Then I should see HomePage.jsp in url
	
	Scenario: User should kept logged in for a while after closing the tab
		Given I am on the home page
		When close and open tab, and access home page
		Then I should see HomePage.jsp in url
	
	Scenario: The website uses HTTPS
		Given I am on the login page
		Then URL includes https

