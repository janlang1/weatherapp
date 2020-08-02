Feature: Search History


Scenario: Check that if a new user is created, there are no prior searches
	Given I create a new user_search
	When I am on the home page_search
	Then there should be no prior searches
Scenario: Check that if only search one then there is one
	Given I create a new user_search
	When I am on the home page_search
	And I search one city 
	Then there should be only one city in the search history

Scenario: Check that if only searched two there are two
	Given I create a new user_search
	When I am on the home page_search
	And I search a second city
	Then there should be two cities in the search history
Scenario: Check that if only searched three there are three
	Given I create a new user_search
	When I am on the home page_search
	And I search a third city
	Then there should be three cities in the search history
	
Scenario: Check that if only searched four there are four
	Given I create a new user_search
	When I am on the home page_search
	And I search a fourth city
	Then there should be four cities in the search history
	
Scenario: Check that if five cities have been searched, only four most recent show up
	Given I create a new user_search
	When I am on the home page_search
	And I search a fifth city
	Then there should be four cities in the search history
	
Scenario: Check invalid search history input
	Given I create a new user_search
	When I am on the home page_search
	And I search an invalid city
	Then the invalid city should not be in search history

Scenario: Check duplicate search history input
	Given I create a new user_search
	When I am on the home page_search
	And I search a duplicate city
	Then all search history results should be unique
	

Scenario: Check that when a city is clicked, the info loads onto the same homepage and updates what is currently there
	Given that I am on home page_search_two
	When I click on a city in search history
	Then the city data should load into the jumbotron
	
Scenario: Check to make sure that when a new city is searched it replaces the oldest city, not the newest
	Given I create a new user_search
	When I am on the home page_search
	And I search a fifth city
	Then the last city is the newest
	

Scenario: Check to make sure that the search history is unique per user
	Given I login as lauren
	When I am on the home page_search
	And I search atlanta
	Then I log out
	And I log back in with new email
	Then atlanta should not be in new email search history
	
	

Scenario: Check that if a new user signs up, they should have no favorites in their favorite cities list
	Given I create a new user_search
	When I am on the analysis page search
	Then there should be no favorites in the favorites list
	


Scenario: Check that when a new user logs in and they favorite a city, it increments the like count, then it will be added to the favorite cities list on analysis
	Given I create a new user_search
	When I am on the home page_search
	Then I go to vacation page_like_count
	And I input new inputs for vacation_radius
	And I click favorites for the first city
	And the like count increases
	And the city shows up on analysis page
