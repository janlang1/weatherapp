Feature: Hello
  Scenario: Open home page from nav bar
    Given I am on the home page
    When I click the home on nav bar
    Then I should see HomePage.jsp in url
  Scenario: Inputting city in home page
  	Given I am on home
  	When I input a city
  	Then I should see city info
  Scenario: Open vacation page from nav bar
  	Given I am on the home page
  	When I click the vacation on nav bar
  	Then I should see Vacation.jsp in url
  Scenario: Use valid inputs and check if table is displayed correctly
  	Given I am on vacation
  	When I input valid input values
  	Then The table is displayed correctly
  Scenario: Open activity page from nav bar
  	Given I am on the home page
  	When I click activity on nav bar
  	Then I should see Activity.jsp
  Scenario: Open analysis page from nav bar
  	Given I am on the analysis page
  	When I click analysis on nav bar
  	Then I should see Analysis.jsp
  Scenario: Open login page from nav bar
  	Given I am on the home page
  	When I click login/logout on nav bar
  	Then I should see login.jsp


  Scenario: Input username and city into fireBase.jsp
  	Given I am on the fireBase page
  	When I enter username and city
  	Then I should see nothing

  Scenario: Login with valid account
  	Given I am on the login page
  	When I input correct account information
  	Then I should see home page logged in 
  Scenario:Login with invalid account
  	Given I am on the login page
  	When I input incorrect account information
  	Then I should see log in page	
  Scenario: signup with existing account
  	Given I am on the signup page
  	When I input account information that already exists
  	Then I should be on signup page
  Scenario: signup with valid account info
  	Given I am on the signup page
  	When Sign up with valid acount
  	Then Should see home page loggedin
  Scenario: visit homepage without logging in
  	Given I am on the home page without logging in
  	When Wait for a second
  	Then I should see log in page  
  	
  
  Scenario: Check for pagination of results
  	Given I am on vacation 
  	When I input valid input values
  	And I click on the number two below the table
  	Then I should see additional results on the second page

  Scenario: Check for pagination on vacation page
  	Given I start on vacation
  	When I input valid input values
  	Then I should see pagination numbers
  	
  Scenario: There is a radius box
  	Given I am on vacation 
  	Then I should see an input box for radius
  	  	
  Scenario: icons in nav bar
  	Given I am on the home page
  	Then the nav bar should have icons
  	

  	Scenario: Check for next and previous buttons
  	Given I start on vacation
  	When I fill input values and go to page 2
  	Then I check for next/prev buttons
  Scenario: Check if only 5 pages are displayed if there are more than 5
  	Given I start on vacation2
  	When I fill input inputs such that there are more than 5 pages
  	Then Check if only 5 pages are displayed

  Scenario: Check if data is loaded on search history
  	Given I am on vacation
  	When I click search history
  	Then I should see data
  	
  Scenario: Check if data is loaded on search history and loads data
  	Given I am on vacation
  	When I click search history 
  	And select a history
  	Then I should see data2