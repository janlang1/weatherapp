package cucumber;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Random;
import java.io.PrintStream;

import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.Keys;



import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;



import org.openqa.selenium.Keys;
/**
 * Step definitions for Cucumber tests.
 */
public class StepDefinitions {
	private static final String ROOT_URL = "https://localhost:8443/";
	private final WebDriver driver;
	String test_user1, test_user2;
	int number;
	String city_name;
	
	public StepDefinitions () {	
		ChromeOptions option= new ChromeOptions();
		option.addArguments("ignore-certificate-errors");
		option.addArguments("--kiosk");
		driver = new ChromeDriver(option);
		test_user1 = "example" + getRandomString() + "@example.com";
		test_user2 = "example" + getRandomString() + "@example.com";
		number = 0;
	}
	
	
	@Given("^I am on the home page$")
	public void i_am_on_the_home_page() {
		driver.get(ROOT_URL+"login.jsp");
//		driver.get(ROOT_URL);
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		driver.findElement(By.id("email")).sendKeys("example@example.com");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("login_btn")).click();
		
//		try {
//			Thread.sleep(30000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@When("^I click the home on nav bar$")
	public void i_click_the_home_link() {
		System.out.println("size of tag a   " + driver.findElements(By.tagName("a")).size());
		driver.findElements(By.tagName("a")).get(0).click();
		//checking for input text in textbox
		//driver.findElement(By.tagName("a")).sendKeys("hello");
		//Select s = new Select(driver.findElement(By.id("myselectbox")));	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Then("^I should see HomePage.jsp in url$")
	public void i_should_see_home_header() {
		//assertTrue(driver.findElement(By.cssSelector("h2")).getText().contains(string));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertTrue(driver.getCurrentUrl().contains("HomePage.jsp"));
	}
	
	
	@Given("^I am on home$")
	public void i_am_on_home() {
		driver.get(ROOT_URL+"login.jsp");
		driver.findElement(By.id("email")).sendKeys("example@example.com");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("login_btn")).click();
		
	}
	@When("^I input a city$")
	public void i_input_city() {
		driver.findElement(By.id("weatherloc")).sendKeys("los angeles");
		driver.findElement(By.id("submitbutton")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Then("^I should see city info$")
	public void i_should_see_info() {
		assertTrue(driver.findElement(By.id("temp")) != null);
	}
	
	
	@Given("^I am on the vacation page$")
	public void i_am_on_the_vacation_page() {
		driver.get(ROOT_URL+"login.jsp");
		driver.findElement(By.id("email")).sendKeys("example@example.com");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("login_btn")).click();
		
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@When("^I click the vacation on nav bar$")
	public void i_click_the_vacation_link() {
		System.out.println("url: " + driver.getCurrentUrl());
		System.out.println("size of tag a   " + driver.findElements(By.tagName("a")).size());
		driver.findElements(By.tagName("a")).get(1).click();
		//checking for input text in textbox
		//driver.findElement(By.tagName("a")).sendKeys("hello");
		//Select s = new Select(driver.findElement(By.id("myselectbox")));	
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Then("^I should see Vacation.jsp in url$")
	public void i_should_see_vacation_header() {
		//assertTrue(driver.findElement(By.cssSelector("h2")).getText().contains(string));
		assertTrue(driver.getCurrentUrl().contains("Vacation.jsp"));
	}
	
	
	@Given("^I am on vacation$")
	public void i_am_on_vacation() {
		driver.get(ROOT_URL+"login.jsp");
		driver.findElement(By.id("email")).sendKeys("example@example.com");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("login_btn")).click();
		driver.get(ROOT_URL+"Vacation.jsp");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@When("^I input valid input values$")
	public void i_input_valid_inputs() {
		System.out.println("this current url: " + driver.getCurrentUrl());
		driver.findElement(By.id("tempLow")).sendKeys("0");
		driver.findElement(By.id("tempHigh")).sendKeys("100");
		driver.findElement(By.id("numResults")).sendKeys("10");
		driver.findElement(By.id("currLocation")).sendKeys("los angeles");
		driver.findElement(By.tagName("button")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Then("^The table is displayed correctly$")
	public void table_is_displayed() {
		assertTrue(driver.findElement(By.tagName("td")) != null);
	}
	
	
	@Given("^I am on the activity page$")
	public void i_am_on_activity() throws InterruptedException {
		driver.get(ROOT_URL+"login.jsp");
		
		Thread.sleep(2000);
		driver.findElement(By.id("email")).sendKeys("example@example.com");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("login_btn")).click();
		driver.get(ROOT_URL+"Activity.jsp");
	}
	@When("^I click activity on nav bar$")
	public void i_click_activity_nav() {
		driver.findElements(By.tagName("a")).get(2).click();
	}
	@Then("^I should see Activity.jsp$")
	public void i_should_see_activity() {
		assertTrue(driver.getCurrentUrl().contains("Activity.jsp"));
	}
	
	
	
	@Given("^I am on the analysis page$")
	public void i_am_on_the_analysis_page() {
		driver.get(ROOT_URL+"login.jsp");
		driver.findElement(By.id("email")).sendKeys("example@example.com");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("login_btn")).click();
		/* driver.get(ROOT_URL+"HomePage.jsp"); */
	}
	@When("^I click analysis on nav bar$")
	public void i_click_analysis_nav() {
		driver.findElements(By.tagName("a")).get(3).click();
	}
	@Then("^I should see Analysis.jsp$")
	public void i_should_see_analysis() {
		assertTrue(driver.getCurrentUrl().contains("AnalysisPage.jsp"));
	}
	

	@When("I click login\\/logout on nav bar")
	public void i_click_login_logout_on_nav_bar() {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("url: " + driver.getCurrentUrl());
		System.out.println("size of tag a   " + driver.findElements(By.tagName("a")).size());
		driver.findElement(By.id("signout_btn")).click();
		driver.switchTo().alert().accept();
		//checking for input text in textbox
		//driver.findElement(By.tagName("a")).sendKeys("hello");
		//Select s = new Select(driver.findElement(By.id("myselectbox")));	
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Then("I should see login.jsp")
	public void i_should_see_login_jsp() throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("loging.jsp - " + driver.getCurrentUrl());
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().contains("login.jsp"));
	}
	
	@Given("^I am on the login page$")
	public void i_am_on_login_page() {
		driver.get(ROOT_URL + "login.jsp");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@When("^I input correct account information$")
	public void input_correct_information() {
		driver.findElement(By.id("email")).sendKeys("example@example.com");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("login_btn")).click();
		try {
			
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Then("^I should see home page logged in$")
	public void i_should_see_home_page() {
		boolean on_home_page = driver.getCurrentUrl().contains("HomePage.jsp"); 
		assertTrue(on_home_page);
	}
	
	@When("^I input incorrect account information$")
	public void input_incorrect_information() {
		driver.findElement(By.id("email")).sendKeys("ceacjleacealca");
		driver.findElement(By.id("password")).sendKeys("wrongpassword_random string");
		driver.findElement(By.id("login_btn")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Then("^I should see log in page$")
	public void i_should_see_login_page() {
		boolean on_home_page = driver.getCurrentUrl().contains("login.jsp"); 
		assertTrue(on_home_page);
	}
	
	@Given("^I am on the signup page$")
	public void i_am_on_signup_page() {
		driver.get(ROOT_URL + "signup.jsp");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@When("^I input correct account information for sign up$")
	public void input_incorrect_information_signup() {
		driver.findElement(By.id("email")).sendKeys("ceacjleacealca");
		driver.findElement(By.id("password")).sendKeys("cejalcea");
		driver.findElement(By.id("signup_btn")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	
	@After()
	public void after() {
		driver.quit();
	}
	
	@Given("^I am on the fireBase page$")
	public void i_am_on_the_fireBase_page() {
		driver.get(ROOT_URL+"login.jsp");
		driver.findElement(By.id("email")).sendKeys("example@example.com");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("login_btn")).click();
		driver.get(ROOT_URL+"fireBase.jsp");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@When("^I enter username and city$")
	public void i_enter_username_and_city() {
		driver.findElement(By.id("username")).sendKeys("agert16");
		driver.findElement(By.id("history")).sendKeys("philly");
		driver.findElement(By.id("submit")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Then("^The page should successfully accept the input$")
	public void the_page_should_successfully_accept_the_input() {
		//assertTrue(driver.findElement(By.cssSelector("h2")).getText().contains(string));
		assertTrue(driver.getCurrentUrl().contains("fireBase.jsp"));
	}
	
	
	

	@Then("I should see nothing")
	public void i_should_see_nothing() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(driver.getCurrentUrl().contains("fireBase.jsp"));
	}
	
	@When("I click on the number two below the table")
	public void i_click_on_the_number_two_below_the_table() {
		System.out.println("url: " + driver.getCurrentUrl());
		
		driver.findElements(By.tagName("form")).get(8).click();
		
		//checking for input text in textbox
		//driver.findElement(By.tagName("a")).sendKeys("hello");
		//Select s = new Select(driver.findElement(By.id("myselectbox")));	
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Then("I should see additional results on the second page")
	public void i_should_see_additional_results_on_the_second_page() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(driver.getCurrentUrl().contains("VAAServlet?pageNum=1&whatPage=vacation"));
	}
	
	@Then("I should see an input box for radius")
	public void i_should_see_an_input_box_for_radius() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(driver.findElements(By.id("radId")).get(0) != null);
	}
	
	@When("^I input account information that already exists$") 
	public void signup_account_exists() { 
	        driver.findElement(By.id("email")).sendKeys("example@example.com"); 
	        driver.findElement(By.id("password")).sendKeys("example"); 
	        driver.findElement(By.id("signup_btn")).click();
			try {
	                Thread.sleep(2000);
	        } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	        }
	}
	@Then("^I should be on signup page$")
	public void check_on_signup() {
		assertTrue(driver.getCurrentUrl().contains("signup.jsp"));
	}
	
	@Given("^I am on the home page without logging in")
	public void homepage_wo_login() {
		driver.get(ROOT_URL+"login.jsp");
	}
	@When("^Wait for a second")
	public void wait_for_second() { 
		
	}
	
	@When("^Sign up with valid acount$")
	public void signup() {
		driver.findElement(By.id("email")).sendKeys("example" + getRandomString() + "@example.com");
		driver.findElement(By.id("password")).sendKeys("password");
	    driver.findElement(By.id("signup_btn")).click();
	}
	
	@Then("^Should see home page loggedin$")
	public void seeing_homepage() {
		assertTrue(driver.getCurrentUrl().contains("HomePage.jsp"));
	}


@Then("the nav bar should have icons")
public void the_nav_bar_should_have_icons() {
    // Write code here that turns the phrase above into concrete actions
	assertTrue(driver.findElements(By.tagName("i")).get(1) != null);
}
	/*
	 * @Then("^I should see pagination numbers$") public void
	 * see_pagination_numbers() {
	 * assertTrue(driver.findElement(By.id("pagination-button")) != null); }
	 */

	protected String getRandomString() {
	    String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    StringBuilder str_builder = new StringBuilder();
	    Random rnd = new Random();
	    while (str_builder.length() < 6) { // length of the random string.
	        int index = (int) (rnd.nextFloat() * ALPHABET.length());
	        str_builder.append(ALPHABET.charAt(index));
	    }
	    String randStr = str_builder.toString();
	    return randStr;
	}
	@Given("^I start on vacation page$")
	public void start_on_vacation() {
		driver.get(ROOT_URL + "Vacation.jsp");
	}
	@When("^I fill input values$")
	public void fill_input_values() {
		driver.findElement(By.id("tempLow")).sendKeys("0");
		driver.findElement(By.id("tempHigh")).sendKeys("100");
		driver.findElement(By.id("numResults")).sendKeys("5");
		driver.findElement(By.id("currLocation")).sendKeys("los angeles");
		driver.findElement(By.tagName("button")).click();
	}
	@Then("^I should see pagination numbers$")
	public void see_pagination_numbers() {
		System.out.println("this is the text of pagination button: " + driver.findElement(By.id("pagination-button0")).getText());
		assertTrue(driver.findElement(By.id("pagination-button0")).getText().equals("1"));
	}
	
	@Given("^I start on vacation$")
	public void go_to_vacation() {
		driver.get(ROOT_URL + "Vacation.jsp");
	}
	@When("^I fill input values and go to page 2$") 
	public void fill_inputs() {
		driver.findElement(By.id("tempLow")).sendKeys("0");
		driver.findElement(By.id("tempHigh")).sendKeys("100");
		driver.findElement(By.id("numResults")).sendKeys("11");
		driver.findElement(By.id("currLocation")).sendKeys("los angeles");
		driver.findElement(By.tagName("button")).click();
		driver.findElement(By.id("pagination-button1")).click();
	}
	@Then("^I check for next/prev buttons$")
	public void check_nextPrev_buttons() {
		boolean prev = false;
		boolean next = false;
		if(driver.findElement(By.id("prevButton")) != null) prev = true;
		if(driver.findElement(By.id("nextButton")) != null) next = true;
		assertTrue(prev && next);
	}
	
	@Given("^I start on vacation2$")
	public void begin_on_vacation() {
		driver.get(ROOT_URL + "Vacation.jsp");
	}
	@When("^I fill input inputs such that there are more than 5 pages")
	public void create_six_pages() {
		driver.findElement(By.id("tempLow")).sendKeys("0");
		driver.findElement(By.id("tempHigh")).sendKeys("100");
		driver.findElement(By.id("numResults")).sendKeys("26");
		driver.findElement(By.id("currLocation")).sendKeys("los angeles");
		driver.findElement(By.tagName("button")).click();
	}
	@Then("^Check if only 5 pages are displayed")
	public void only_fivepages() {
		assertTrue(driver.findElements(By.className("pagination-button")).size() == 5);
	}
	
	@When("I click search history")
	public void move_to_search_history() {
		driver.findElement(By.id("seach_history_btn")).click();
	}
	@Then("I should see data")
	public void should_see_data() {
		boolean moved = driver.getCurrentUrl().contains("SearchHistory.jsp");
		boolean found_data = driver.findElements(By.id("results")).get(1) != null;
		assertTrue(moved && found_data);
	}
	
	@When("I click search history and select a history")
	public void select_history() {
		driver.findElement(By.id("seach_history_btn")).click();
		driver.findElements(By.id("results")).get(1).click();
		
	}
	@Then("I should see data2")
	public void check_past_search() {
		boolean moved = driver.getCurrentUrl().contains("SearchHistory.jsp");
		boolean found_data = driver.findElements(By.id("testthis")).get(0) != null;
		assertTrue(moved && found_data);
	}
	
	
	
	
//	Lauren's tests start LAUREN IS HERE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	

@When("I input new inputs for activity")
public void i_input_new_inputs_for_activity() {
    // Write code here that turns the phrase above into concrete actions
	
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("outdoorActivity")).sendKeys("outdoor");
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("radius")).sendKeys("100");
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("radius")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


@When("I input new inputs for vacation_radius")
public void i_input_new_inputs_for_vacation_radius() {
    // Write code here that turns the phrase above into concrete actions
	
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("tempLow")).sendKeys("0");
	
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("tempHigh")).sendKeys("100");
	
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("radius")).sendKeys("100");
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("radius")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


@Then("they should all be within the radius range")
public void they_should_all_be_within_the_radius_range() {
    // Write code here that turns the phrase above into concrete actions
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String x = driver.findElement(By.id("longest_distance")).getText();
    
    Integer z = Integer.parseInt(x);
    try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    assertTrue(z <= 100);
    try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

	
	@Then("I should see a toggle for unit change")
	public void i_should_see_a_toggle_for_unit_change() {
		boolean tog = (driver.findElement(By.name("toggle")) !=null);
		assertTrue(tog);
	}

	@When("I click on the toggle for homepage unit change")
	public void i_click_on_the_toggle_for_homepage_unit_change() {
	    // Write code here that turns the phrase above into concrete actions
		driver.findElement(By.id("toggle")).click();
	    
	}

	@SuppressWarnings("unlikely-arg-type")
	@Then("the temperature values should change home")
	public void the_temperature_values_should_change_home() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(driver.findElement(By.id("tempUnit")).getText().equals(" °C"));
	   
	}

	@Given("I am on analysis")
	public void i_am_on_analysis() {
	    // Write code here that turns the phrase above into concrete actions
		driver.get(ROOT_URL+"login.jsp");
		driver.findElement(By.id("email")).sendKeys("example@example.com");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("login_btn")).click();
		driver.get(ROOT_URL+"AnalysisPage.jsp");
	  
	}

//	@Then("I should see a toggle for unit change")
//	public void i_should_see_a_toggle_for_unit_change_home() {
//	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
//	}

	@When("I click on the toggle for analysis unit change")
	public void i_click_on_the_toggle_for_analysis_unit_change() {
	    // Write code here that turns the phrase above into concrete actions
	    
		driver.findElements(By.id("toggle")).get(0).click();
	}

	@SuppressWarnings("unlikely-arg-type")
	@Then("the temperature values should change analysis")
	public void the_temperature_values_should_change_analysis() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(driver.findElement(By.id("tempUnit")).equals(" °F"));
	   
	}

	@Then("I should see a nav bar")
	public void i_should_see_a_nav_bar() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(driver.findElements(By.name("navName")).get(0) != null);
	    
	}

	


	
	@Then("search history section should be at top of page")
	public void search_history_section_should_be_at_top_of_page() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(driver.findElements(By.name("searchHistory")).get(0) != null);
	}

	
	
	

@Given("I am on the vacation page_two")
public void i_am_on_the_vacation_page_two() {
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL+"login.jsp");
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL);
//	try {
//		Thread.sleep(2000);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		driver.findElement(By.id("email")).sendKeys("newemail@gmail.com");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("password")).sendKeys("12345678");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("login_btn")).click();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.get(ROOT_URL+ "HomePage.jsp");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.get(ROOT_URL+ "Vacation.jsp");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}



@Given("I am on the activity page_two")
public void i_am_on_the_activity_page_two() {
    // Write code here that turns the phrase above into concrete actions
	 // Write code here that turns the phrase above into concrete actions
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL+"login.jsp");
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL);
//	try {
//		Thread.sleep(2000);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		driver.findElement(By.id("email")).sendKeys("newemail@gmail.com");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("password")).sendKeys("12345678");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("login_btn")).click();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.get(ROOT_URL+ "HomePage.jsp");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.get(ROOT_URL+ "Activity.jsp");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

@When("I click on the toggle for activity unit change")
public void i_click_on_the_toggle_for_activity_unit_change() {
    // Write code here that turns the phrase above into concrete actions
	driver.findElements(By.id("toggle")).get(0).click();
}

@Then("the temperature values should change activity")
public void the_temperature_values_should_change_activity() {
    // Write code here that turns the phrase above into concrete actions
	assertTrue(driver.findElement(By.id("tempUnit")).equals(" °C"));
}


@When("I click on the toggle for vacation unit change")
public void i_click_on_the_toggle_for_vacation_unit_change() {
    // Write code here that turns the phrase above into concrete actions
	driver.findElements(By.id("toggle")).get(0).click();
}

@Then("the temperature values should change vacation")
public void the_temperature_values_should_change_vacation() {
    // Write code here that turns the phrase above into concrete actions
	assertTrue(driver.findElement(By.id("tempUnit")).equals(" °C"));
}

@Then("I should see the search history results at the top of the page")
public void i_should_see_the_search_history_results_at_the_top_of_the_page() {
    // Write code here that turns the phrase above into concrete actions
	assertTrue(driver.findElements(By.id("historyHeader")).get(0) != null);
}

@Then("they should be in a horizontal form")
public void they_should_be_in_a_horizontal_form() {
    // Write code here that turns the phrase above into concrete actions
	assertTrue(driver.findElements(By.id("inlineForm")).get(0) != null);
}


@Then("there should be a nav bar")
public void there_should_be_a_nav_bar() {
    // Write code here that turns the phrase above into concrete actions
	assertTrue(driver.findElements(By.name("navName")).get(0) != null);
}





@Then("there should be a field for the desired radius range as input")
public void there_should_be_a_field_for_the_desired_radius_range_as_input() {
    // Write code here that turns the phrase above into concrete actions
	assertTrue(driver.findElements(By.id("radius")).get(0) != null);
}



@Then("there should be a field for radius input")
public void there_should_be_a_field_for_radius_input() {
    // Write code here that turns the phrase above into concrete actions
	assertTrue(driver.findElements(By.id("radius")).get(0) != null);
}







@Given("I am on the home page_search")
public void i_am_on_the_home_page_search() {
    // Write code here that turns the phrase above into concrete actions
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL+"login.jsp");
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL);
//	try {
//		Thread.sleep(2000);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println ("in home page search test!!!!!!!!!!!!!!!!!!!!!");
//	driver.findElement(By.id("signup_button")).click();

	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
//	assertTrue(driver.getCurrentUrl().contains("signup.jsp"));
	
}
//
//@Then("there should be the last four results on the home page")
//public void there_should_be_the_last_four_results_on_the_home_page() {
//    // Write code here that turns the phrase above into concrete actions
//    throw new io.cucumber.java.PendingException();
//}

@Given("I create a new user_search")
public void i_create_a_new_user_search() {
    // Write code here that turns the phrase above into concrete actions
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL);
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL+"signup.jsp");
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("email")).sendKeys("example" + getRandomString() + "@example.com");
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}
	driver.findElement(By.id("password")).sendKeys("password");
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}
    driver.findElement(By.id("signup_btn")).click();
	try {
            Thread.sleep(4000);
    } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }		
}



@Then("there should be no prior searches")
public void there_should_be_no_prior_searches() {
    // Write code here that turns the phrase above into concrete actions
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
    assertTrue(driver.findElements(By.id("searchHistoryElements")).size() == 0);
    try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
}


@When("I search one city")
public void i_search_one_city() {
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("weatherloc")).sendKeys("Paris");
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Then("there should be only one city in the search history")
public void there_should_be_only_one_city_in_the_search_history() {
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	assertTrue(driver.findElements(By.id("searchHistoryElements")).size() == 1);
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
}

@When("I search a second city")
public void i_search_a_second_city() {
    // Write code here that turns the phrase above into concrete actions
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("weatherloc")).sendKeys("Paris");
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Seattle");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Then("there should be two cities in the search history")
public void there_should_be_two_cities_in_the_search_history() {
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	assertTrue(driver.findElements(By.id("searchHistoryElements")).size() == 2);
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
}


@When("I search a third city")
public void i_search_a_third_city() {
    // Write code here that turns the phrase above into concrete actions
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("weatherloc")).sendKeys("Paris");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Seattle");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Bellevue");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Then("there should be three cities in the search history")
public void there_should_be_three_cities_in_the_search_history() {
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	assertTrue(driver.findElements(By.id("searchHistoryElements")).size() == 3);
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	

}


@When("I search a fourth city")
public void i_search_a_fourth_city() {
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("weatherloc")).sendKeys("Paris");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Seattle");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Bellevue");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Portland");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@When("I search a fifth city")
public void i_search_a_fifth_city() {
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("weatherloc")).sendKeys("Paris");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Seattle");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Bellevue");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Portland");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Yakima");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


@When("I search a duplicate city")
public void i_search_a_duplicate_city() {
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("weatherloc")).sendKeys("Paris");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Seattle");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Bellevue");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Portland");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Seattle");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Then("there should be four cities in the search history")
public void there_should_be_four_cities_in_the_search_history() {
    // Write code here that turns the phrase above into concrete actions
	try {
		Thread.sleep(4000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	assertTrue(driver.findElements(By.id("searchHistoryElements")).size() == 4);
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}




@When("I search an invalid city")
public void i_search_an_invalid_city() {
    // Write code here that turns the phrase above into concrete actions
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Paris");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Seattle");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Bellevue");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("Portland");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("weatherloc")).sendKeys("1");
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Then("the invalid city should not be in search history")
public void the_invalid_city_should_not_be_in_search_history() {
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    // Write code here that turns the phrase above into concrete actions
	boolean s1 = (driver.findElements(By.id("results")).get(0).getCssValue("value") != "1");
	boolean s2 = (driver.findElements(By.id("results")).get(1).getCssValue("value") != "1");
	boolean s3 = (driver.findElements(By.id("results")).get(2).getCssValue("value") != "1");
	boolean s4 = (driver.findElements(By.id("results")).get(3).getCssValue("value") != "1");
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	assertTrue(s1 && s2 && s3 && s4);
}

@Then("all search history results should be unique")
public void all_search_history_results_should_be_unique() {
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    // Write code here that turns the phrase above into concrete actions
	boolean s1 = (driver.findElements(By.name("Seattle")).size() == 1);	
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	assertTrue(s1);
}



@Given("that I am on home page_search_two")
public void that_I_am_on_home_page_search_two() {
    // Write code here that turns the phrase above into concrete actions
	// Write code here that turns the phrase above into concrete actions
		try {
	        Thread.sleep(4000);
		} catch (InterruptedException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		}	
		driver.get(ROOT_URL+"login.jsp");
		try {
	        Thread.sleep(4000);
		} catch (InterruptedException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		}	
		driver.findElement(By.id("email")).sendKeys("newemail@gmail.com");
		try {
	        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}
		driver.findElement(By.id("password")).sendKeys("12345678");
		try {
	        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}
	    driver.findElement(By.id("login_btn")).click();
		try {
	            Thread.sleep(4000);
	    } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	    }		
		driver.get(ROOT_URL +"HomePage.jsp");
		try {
            Thread.sleep(4000);
    } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }		
}

@When("I click on a city in search history")
public void i_click_on_a_city_in_search_history() {
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}		
    driver.findElements(By.id("results")).get(0).click();
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}		
	
}

@Then("the city data should load into the jumbotron")
public void the_city_data_should_load_into_the_jumbotron() {
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}		
	assertTrue(driver.findElements(By.id("location")).get(0).getText() != "");
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}		
}

@Then("the last city is the newest")
public void the_last_city_is_the_newest() {
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}		
   assertTrue((driver.findElements(By.name("Yakima")).get(0) != null));
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}		
}



@When("I input new small radius for activity")
public void i_input_new_small_radius_for_activity() {
    // Write code here that turns the phrase above into concrete actions

	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("outdoorActivity")).sendKeys("outdoor");
	
	
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("radius")).sendKeys("8");
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("radius")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Then("the only city in the table should be seattle")
public void the_only_city_in_the_table_should_be_seattle() {
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}		
   assertTrue((driver.findElements(By.id("Seattle")).get(0) != null));
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}		
}

@When("I input new small radius for vacation")
public void i_input_new_small_radius_for_vacation() {

	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("tempLow")).sendKeys("0");
	
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("tempHigh")).sendKeys("100");
	
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("radius")).sendKeys("8");
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.findElement(By.id("radius")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}





@Given("I login as lauren")
public void i_login_as_lauren() {
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL+"login.jsp");
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL);
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("email")).sendKeys("lauren@lauren.com");
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}
	driver.findElement(By.id("password")).sendKeys("12345678");
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}
    driver.findElement(By.id("login_btn")).click();
	try {
            Thread.sleep(4000);
    } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }		
	driver.get(ROOT_URL +"HomePage.jsp");
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}		
}

@When("I search atlanta")
public void i_search_atlanta() {
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("weatherloc")).sendKeys("Atlanta");
	try {
        Thread.sleep(3000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Then("I log out")
public void i_log_out() {
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("signout_btn")).click();
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.switchTo().alert().accept();
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	
}

@Then("I log back in with new email")
public void i_log_back_in_with_new_email() {
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL+"login.jsp");
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL);
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.findElement(By.id("email")).sendKeys("newemail@gmail.com");
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}
	driver.findElement(By.id("password")).sendKeys("12345678");
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}
    driver.findElement(By.id("login_btn")).click();
	try {
            Thread.sleep(4000);
    } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }		
	driver.get(ROOT_URL +"HomePage.jsp");
	try {
        Thread.sleep(4000);
} catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}		
}

@Then("atlanta should not be in new email search history")
public void atlanta_should_not_be_in_new_email_search_history() {
	try {
		Thread.sleep(4000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    // Write code here that turns the phrase above into concrete actions
	boolean s1 = (driver.findElements(By.name("Atlanta")).size() == 0);	
	try {
		Thread.sleep(4000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	assertTrue(s1);
}



@When("I am on the analysis page search")
public void i_am_on_the_analysis_page_search() {
	
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL);
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL+"AnalysisPage.jsp");
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	

//	try {
//		Thread.sleep(2000);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

@Then("there should be no favorites in the favorites list")
public void there_should_be_no_favorites_in_the_favorites_list() {
    // Write code here that turns the phrase above into concrete actions
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    boolean cities = (driver.findElements(By.className("cityName")).size() == 0);
    try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    assertTrue(cities);
    try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}




@Then("I go to vacation page_like_count")
public void i_go_to_vacation_page_like_count() {

	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL);
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL+"Vacation.jsp");
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	

//	try {
//		Thread.sleep(2000);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}

@Then("I click favorites for the first city")
public void i_click_favorites_for_the_first_city() {
    // Write code here that turns the phrase above into concrete actions
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    driver.findElements(By.name("favorites_for_test")).get(0).click();
    try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}

@Then("the like count increases")
public void the_like_count_increases() {
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	boolean check = (driver.findElements(By.name("likes_for_test")).get(0).getText() != "0");
    try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Then("the city shows up on analysis page")
public void the_city_shows_up_on_analysis_page() {
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL);
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	
	driver.get(ROOT_URL+"AnalysisPage.jsp");
	try {
        Thread.sleep(4000);
	} catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	}	

//	try {
//		Thread.sleep(2000);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    boolean cities = (driver.findElements(By.name("cityName")).size() != 0);
    try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    assertTrue(cities);
    try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}




// Lauren's tests end

	
	
	
	
	

	// Jung begin Sprint 3
	@Given("^I am on vacation page logged in$")
	public void start_vacation() {
		driver.get(ROOT_URL+"login.jsp");
		try {
            Thread.sleep(1000);
    } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }
		driver.findElement(By.id("email")).sendKeys("jung@gmail.com"); 
        driver.findElement(By.id("password")).sendKeys("jung"); 
        driver.findElement(By.id("login_btn")).click();
		try {
                Thread.sleep(2000);
        } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
		driver.get(ROOT_URL+"Vacation.jsp");
	}
	@Given("^I am on activity page logged in$")
	public void start_activity() {
		driver.get(ROOT_URL+"login.jsp");
		try {
            Thread.sleep(1000);
    } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }
		driver.findElement(By.id("email")).sendKeys("jung@gmail.com"); 
        driver.findElement(By.id("password")).sendKeys("jung"); 
        driver.findElement(By.id("login_btn")).click();
		try {
                Thread.sleep(3000);
        } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
		driver.get(ROOT_URL+"Activity.jsp");
	}
	@When("I submit valid inputs and go to page two of vacation")
	public void submit_valid_inputs_vacation() {
		driver.findElement(By.id("tempLow")).sendKeys("0");
		driver.findElement(By.id("tempHigh")).sendKeys("100");
		//driver.findElement(By.id("numResults")).sendKeys("11");
	//	driver.findElement(By.id("currLocation")).sendKeys("los angeles");
		driver.findElement(By.id("radius")).sendKeys("50");
		try {
            Thread.sleep(5000);
    } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }
		driver.findElement(By.tagName("button")).click();
		//JavascriptExecutor jse = (JavascriptExecutor)driver;
		//jse.executeScript("window.scrollBy(0,250)");
		driver.findElement(By.id("nextButton")).click();
	}
	@When("I submit valid inputs and go to page two of activity")
	public void submit_valid_inputs_activity() {
		driver.findElement(By.id("outdoorActivity")).sendKeys("outdoor");
		driver.findElement(By.id("radius")).sendKeys("350");
		try {
            Thread.sleep(5000);
    } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }
		driver.findElement(By.tagName("button")).click();
		//JavascriptExecutor jse = (JavascriptExecutor)driver;
		//jse.executeScript("window.scrollBy(0,250)");

		driver.findElement(By.id("nextButton")).click();
	}
	@Then("Previous and Next button should appear and spelled correctly")
	public void check_prev_next_text() {
		boolean prev = false, next = false;
		if(driver.findElement(By.id("prevButton")) != null && driver.findElement(By.id("prevButton")).getText().equals("Previous")) prev = true;
		if(driver.findElement(By.id("nextButton")) != null && driver.findElement(By.id("nextButton")).getText().equals("Next")) next = true;
		assertTrue(prev && next);
	}
//	@Then("The cities shown should be different for pages 1 and 2")
//	public void next_pages_update() {
//		System.out.println("this: " + driver.findElements(By.className("temp")).get(0).getText());
//		assertTrue(driver.findElements(By.className("temp")).get(0).getText().equals("18"));
//	}
	@When("go to page 1")
	public void go_to_page_one() {		
		driver.findElement(By.id("prevButton")).click();
	}
	@When("go to page 2")
	public void go_to_page_two() {
		driver.findElement(By.id("nextButton")).click();
	}
//	@Then("Cities should be different for pages 1 and 2")
//	public void prev_pages_update() {
//		assertTrue(driver.findElements(By.className("temp")).get(0).getText().equals("18") == false);
//	}
//	@When("go to page 2") 
//	public void go_to_page_two() {
//		driver.findElement(By.id("nextButton")).click();
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	@Then("Page 2 should be highlighted")
	public void page_one_highlighted() {
		String color = driver.findElements(By.id("pagination-button")).get(1).getCssValue("background-color");
		String hexColor = Color.fromString(color).asHex();
	//	System.out.println(hexColor);
		assertTrue(hexColor.equals("#000000"));
	}
	@Then("Cities ranged 1-5 should show")
	public void previous_range() {
		boolean flag = false;
		List<WebElement> cityNums = driver.findElements(By.id("cityNum"));
		for(int i=0; i<5; i++) {
			if(Integer.parseInt(cityNums.get(i).getText()) != i+1) flag = true;
		}
		assertTrue(flag == false);
	}
	@Then("Cities ranged 6-10 should show")
	public void next_range() {
		boolean flag = false;
		List<WebElement> cityNums = driver.findElements(By.id("cityNum"));
		for(int i=5; i<10; i++) {
			if(Integer.parseInt(cityNums.get(i-5).getText()) != i+1) flag = true;
		}
		assertTrue(flag == false);
	}
	@When("Click distance sorting button")
	public void click_dist_sort() {
		driver.findElement(By.id("distsort")).click();
		try {
            Thread.sleep(1000);
    } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }
	}
	@Then("Distance should be in ascending order")
	public void ascending_order() {
		boolean flag = false;
		List<WebElement> cityDistances = driver.findElements(By.id("dist"));
		for(int i=0; i<4; i++) {
			if(Integer.parseInt(cityDistances.get(i).getText()) > Integer.parseInt(cityDistances.get(i+1).getText())) flag = true;
		}
		assertTrue(flag == false);
	}
	@Then("Distance should be in descending order")
	public void descending_order() {
		boolean flag = false;
		List<WebElement> cityDistances = driver.findElements(By.id("dist"));
		for(int i=0; i<4; i++) {
			if(Integer.parseInt(cityDistances.get(i).getText()) < Integer.parseInt(cityDistances.get(i+1).getText())) flag = true;
		}
		assertTrue(flag == false);
	}
	@Then("There is no num results button")
	public void no_numresults() {
		assertTrue(driver.findElements(By.id("numResults")).size() == 0);
	}
	@Then("Likes should be in ascending order")
	public void likes_ascending() {
		boolean flag = false;
		List<WebElement> cityDistances = driver.findElements(By.id("likes"));
		for(int i=0; i<4; i++) {
			if(Integer.parseInt(cityDistances.get(i).getText()) > Integer.parseInt(cityDistances.get(i+1).getText())) flag = true;
		}
		assertTrue(flag == false);
	}
	@Then("Likes should be in descending order")
	public void likes_descending() {
		boolean flag = false;
		List<WebElement> cityDistances = driver.findElements(By.id("likes"));
		for(int i=0; i<4; i++) {
			if(Integer.parseInt(cityDistances.get(i).getText()) < Integer.parseInt(cityDistances.get(i+1).getText())) flag = true;
		}
		assertTrue(flag == false);
	}
// Jung sprint 3 end



	// Input validation tests
	@When("I input empty value to low temp")
	public void i_input_empty_value_to_low_temp() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.id("tempHigh")).sendKeys("100");
		driver.findElement(By.id("radius")).sendKeys("100");
		driver.findElement(By.id("search")).click();
	}

	@Then("I should see highlighted Low Temp box")
	public void i_should_see_highlighted_Low_Temp_box() {
		assertEquals("2px solid rgb(255, 0, 0)", driver.findElement(By.id("tempLow")).getCssValue("border"));		
	}

	@Then("There to be an error message that reads Illegal value for input Low Temp")
	public void there_to_be_an_error_message_that_reads_Illegal_value_for_input_Low_Temp() {
		assertEquals("Illegal value for input Low Temp.", driver.findElement(By.id("error_message")).getText());
	}

	@When("I input empty value to high temp")
	public void i_input_empty_value_to_high_temp() {
		driver.findElement(By.id("tempLow")).sendKeys("0");
		driver.findElement(By.id("radius")).sendKeys("100");
		driver.findElement(By.id("search")).click();
	}

	@Then("I should see highlighted High Temp box")
	public void i_should_see_highlighted_High_Temp_box() {
		assertEquals("2px solid rgb(255, 0, 0)", driver.findElement(By.id("tempHigh")).getCssValue("border"));
	}

	@Then("There to be an error message that reads Illegal value for input High Temp")
	public void there_to_be_an_error_message_that_reads_Illegal_value_for_input_High_Temp() {
		assertEquals("Illegal value for input High Temp.", driver.findElement(By.id("error_message")).getText());
	}

	@When("I input empty value to Radius")
	public void i_input_empty_value_to_Radius() {
		driver.findElement(By.id("tempLow")).sendKeys("0");
		driver.findElement(By.id("tempHigh")).sendKeys("100");
		driver.findElement(By.id("search")).click();
	}

	@When("I input empty value to Radius on Activity Page")
	public void I_input_empty_value_to_Radius_on_Activity_Page() {
		driver.findElement(By.id("outdoorActivity")).sendKeys("snow");
		driver.findElement(By.id("search")).click();
	}	

	@Then("I should see highlighted Radius box")
	public void i_should_see_highlighted_Radius_box() {
		assertEquals("2px solid rgb(255, 0, 0)", driver.findElement(By.id("radius")).getCssValue("border"));
	}

	@Then("There to be an error message that reads Illegal value for input Radius")
	public void there_to_be_an_error_message_that_reads_Illegal_value_for_input_Radius() {
		assertEquals("Illegal value for input Radius.", driver.findElement(By.id("error_message")).getText());
	}

	@When("I input empty value to Activity")
	public void i_input_empty_value_to_Activity() {
		driver.findElement(By.id("radius")).sendKeys("100");
		driver.findElement(By.id("search")).click();
	}

	@Then("I should see highlighted Activity box")
	public void i_should_see_highlighted_Activity_box() {
		String style = driver.findElement(By.id("outdoorActivity")).getCssValue("border");
//		PrintStream out = System.out;
//		System.setOut(out);
//		System.out.println("style");
		assertEquals("2px solid rgb(255, 0, 0)", style);
	}

	@Then("There to be an error message that reads Illegal value for input Activity")
	public void there_to_be_an_error_message_that_reads_Illegal_value_for_input_Activity() {
		assertEquals("Illegal value for input Activity.", driver.findElement(By.id("error_message")).getText());
	}

	@When("I input empty value to city name")
	public void i_input_empty_value_to_city_name() {
		driver.findElement(By.id("weatherloc")).sendKeys("This City Does Not Exist");
		driver.findElement(By.id("submitbutton")).click();
	}	

	@Then("The message should read No Location Found")
	public void the_message_should_read_No_Location_Found() {
	    assertEquals("No Location Found", driver.findElement(By.id("location")).getText());
	}
	
	@When("I input negative value to Radius on Activity page")
	public void i_input_negative_value_to_Radius_on_Activity_page() {
		driver.findElement(By.id("outdoorActivity")).sendKeys("snow");
		driver.findElement(By.id("radius")).sendKeys("-100");
		driver.findElement(By.id("search")).click();
	}

	@When("I input negative value to Radius on Vacation page")
	public void i_input_negative_value_to_Radius_on_Vacation_page() {
		driver.findElement(By.id("tempLow")).sendKeys("0");
		driver.findElement(By.id("tempHigh")).sendKeys("100");
		driver.findElement(By.id("radius")).sendKeys("-100");
		driver.findElement(By.id("search")).click();
	}
	
	
	// For User Auth testing
	
	@Then("confirm logout button exists")
	public void confirm_logout_button_exists() {
	    WebElement btn = driver.findElement(By.id("signout_btn"));
	    assertTrue(btn != null);
	}


	@When("logout from the page")
	public void logout_from_the_page() throws InterruptedException {
		Thread.sleep(8000);
		driver.findElement(By.id("signout_btn")).click();
		Thread.sleep(3000);
		driver.switchTo().alert().accept();
		Thread.sleep(2000);
	}

	@Given("I am logged out")
	public void i_am_logged_out() throws InterruptedException {
		i_am_on_the_home_page();
		logout_from_the_page();
	}

	@When("access home page")
	public void access_home_page() {
		driver.get(ROOT_URL + "HomePage.jsp");
	}

	@When("access vacation page")
	public void access_vacation_page() {
		driver.get(ROOT_URL + "Vacation.jsp");
	}

	@When("access activity page")
	public void access_activity_page() {
		driver.get(ROOT_URL + "Activity.jsp");
	}

	@When("access analysis page")
	public void access_analysis_page() {
		driver.get(ROOT_URL + "AnalysisPage.jsp");
	}

	@When("access root url")
	public void access_root_url(){
		driver.get(ROOT_URL);
	}

	@When("access login page")
	public void access_login_page() {
		driver.get(ROOT_URL + "login.jsp");
	}

	@When("access sign up page")
	public void access_sign_up_pag() {
	    driver.get(ROOT_URL + "signup.jsp");
	}

	@When("close and open tab, and access home page")
	public void close_and_open_tab_and_access_home_page() throws InterruptedException {
		Thread.sleep(2000);

//		driver.findElement(By.tagName("body")).sendKeys(Keys.COMMAND +"t");
	    ((JavascriptExecutor)driver).executeScript("window.open()");

		Thread.sleep(3000);
	    
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
		Thread.sleep(3000);
		((JavascriptExecutor)driver).executeScript("window.opener = self;window.close()");
		Thread.sleep(3000);
		
		access_home_page();		
	}

	@Then("URL includes https")
	public void url_includes_https() {
		String url = driver.getCurrentUrl();
		assertTrue(url.contains("https"));
	}
	
	
	/*
	 * Below code is mainly for user_data.feature file
	 */
		
	@When("log out and log back in")
	public void log_out_and_log_back_in() throws InterruptedException {
		logout_from_the_page();
		
		Thread.sleep(20000);
		
		driver.findElement(By.id("email")).sendKeys("example@example.com");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("login_btn")).click();
	}
	
	@Then("favorite list size stays the same")
	public void favorite_list_size_stays_the_same() {
		
		
		
		
	}
	
	@Then("I should see search history")
	public void i_should_see_search_history() throws InterruptedException {
		Thread.sleep(20000);
		
		int size_of_history = driver.findElements(By.id("searchHistoryElements")).size();
		System.out.println(size_of_history);
		assertTrue(size_of_history > 0);
	}
	
	@When("search a city")
	public void search_a_city() {
	    driver.findElement(By.id("weatherloc")).sendKeys("Los Angeles");
	    driver.findElement(By.id("weatherloc")).sendKeys(Keys.ENTER);
	}

	@Then("I should see the city in search history")
	public void i_should_see_the_city_in_search_history() {
		String city_name = driver.findElement(By.id("result")).getAttribute("value");
		
		assertEquals("Los Angeles", city_name);
	}
		
	@When("I sign up")
	public void i_sign_up() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Then("There should be no city")
	public void there_should_be_no_city() {
	    int num = driver.findElements(By.id("fav_city")).size();
	    assertEquals(0, num);
	}
	
	@Then("there should be cities")
	public void there_should_be_cities() {
	    int num = driver.findElements(By.id("fav_city")).size();
	    assertTrue(num > 0);
	}
	
	@Then("there should be no weather info")
	public void there_should_be_no_weather_info() {
		String prompter = driver.findElement(By.id("prompter")).getText();
		assertEquals("Search for a city's weather!", prompter);
	}
			
	@When("favorite a city")
	public void favorite_a_city() {
		city_name = driver.findElement(By.id("location")).getAttribute("value");
		driver.findElement(By.id("fav_btn")).click();
	}
	
	@Then("the city should be in the list")
	public void the_city_should_be_in_the_list() {
		for(WebElement webEle : driver.findElements(By.id("fav_city_name"))) {
			if(city_name.equals(webEle.getAttribute("value"))){
				assertTrue(true);
				return;
			}
		}
		assertTrue(false);
	}
		
	@Then("sign up users")
	public void sign_up_users() throws InterruptedException {
		driver.get(ROOT_URL + "signup.jsp");
		driver.findElement(By.id("email")).sendKeys(test_user1);
		driver.findElement(By.id("password")).sendKeys("password");
	    driver.findElement(By.id("signup_btn")).click();
	    
	    logout_from_the_page();
	    
		driver.get(ROOT_URL + "signup.jsp");
		driver.findElement(By.id("email")).sendKeys(test_user2);
		driver.findElement(By.id("password")).sendKeys("password");
	    driver.findElement(By.id("signup_btn")).click();
	}
		
	@When("remove a city")
	public void remove_a_city() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Then("the city should not be in the list on vacation page")
	public void the_city_should_not_be_in_the_list_on_vacation_page() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Then("the city should not be in the list on analysis page")
	public void the_city_should_not_be_in_the_list_on_analysis_page() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Then("login as user1")
	public void login_as_user1() {
		driver.get(ROOT_URL + "login.jsp");
		driver.findElement(By.id("email")).sendKeys(test_user1);
		driver.findElement(By.id("password")).sendKeys("password");
	    driver.findElement(By.id("signup_btn")).click();
	}
	
	@Then("search on vacation page")
	public void search_on_vacation_page() {
		driver.findElement(By.id("tempLow")).sendKeys("0");
		driver.findElement(By.id("tempHigh")).sendKeys("100");
		driver.findElement(By.id("radius")).sendKeys("50");
		driver.findElement(By.id("search")).click();
	}
		
	@Then("logout and login as usre2")
	public void logout_and_login_as_usre2() {
		
	}
	
	@Then("the city should not be in the list")
	public void the_city_should_not_be_in_the_list() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	
	@Then("logout and login as user2")
	public void logout_and_login_as_user2() {
		driver.get(ROOT_URL + "login.jsp");
		driver.findElement(By.id("email")).sendKeys(test_user2);
		driver.findElement(By.id("password")).sendKeys("password");
	    driver.findElement(By.id("signup_btn")).click();
	}
	
	@Then("search on activity page")
	public void search_on_activity_page() {
		driver.findElement(By.id("outdoorActivity")).sendKeys("snow");
		driver.findElement(By.id("radius")).sendKeys("100");
		driver.findElement(By.id("search")).click();
	}
		
	
	@Then("the like should be incremented")
	public void the_like_should_be_incremented() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Then("the like should be decremented")
	public void the_like_should_be_decremented() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Then("there should be no search result")
	public void there_should_be_no_search_result() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Given("I am on the sign up page")
	public void i_am_on_the_sign_up_page() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}


	@Then("the city should not be in the list on activity page")
	public void the_city_should_not_be_in_the_list_on_activity_page() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Then("no numResult input")
	public void no_numResult_input() {
		int num = driver.findElements(By.id("numResult")).size();
		assertEquals(0, num);
	}
	
	
	
	

} 