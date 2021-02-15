package Amazon;

// Author:  Anthony McSharry
// Date:    27/11/2020
// Project: Amazon E2E Test


import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.awt.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Iterator;

public class AmazonE2ETest 
{
	
	
	private WebDriver driver;
	private String BaseURL;
	
	//Allows scrolling page
	JavascriptExecutor jse;		
	
	@BeforeMethod
	@BeforeClass
	
	public void setUp() throws Exception 
		{
		System.setProperty("webdriver.chrome.driver","/Users/anthonymcsharry/Desktop/subjects/cs265/chromedriver");
		driver= new ChromeDriver();
		BaseURL = "https://www.amazon.co.uk";
		
		// Maximize the browser's window
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					jse = (JavascriptExecutor)driver;	
		}
	
	
	@Test
	public void E2ETEST ()  throws Exception 
	{
		driver.get(BaseURL);
		
		//Accept Cookies
		Thread.sleep(2000);	
		WebElement AcceptCookies = driver.findElement(By.id("a-autoid-0"));
		AcceptCookies.click();
		Thread.sleep(2000);	
		//Sign in
		SignIn ();
		//Finds desired item
		FindAndAddItem ();
	}
	
	
	public void SignIn ()  throws Exception 
	
	{
		//Log In
		WebElement Log = driver.findElement(By.xpath("//div[@class='nav-line-1-container']"));
		Log.click();
		WebElement logIn = driver.findElement(By.id("ap_email"));
		logIn.sendKeys("AmazenTest@outlook.com");
		Thread.sleep(2000);	
		WebElement Continue = driver.findElement(By.id("continue"));
		Continue.click();
		Thread.sleep(2000);
		WebElement password = driver.findElement(By.id("ap_password"));
		password.sendKeys("Amazontest");
		Thread.sleep(2000);
		WebElement signIn = driver.findElement(By.id("signInSubmit"));
		signIn.click();
		Thread.sleep(2000);	
		
//		
//		Actions myAction = new Actions(driver);
//		WebElement mainElement = driver.findElement(By.id("nav-icon nav-arrow null"));
//		myAction.moveToElement(mainElement).perform();
//		Thread.sleep(2000);
//				
//		WebElement subElement = driver.findElement(By.xpath("//span[@class='nav-action-inner']"));
//		Thread.sleep(2000);
//		subElement.click();
		
		
		//WebElement Email = driver.findElement(By.xpath("//input[@name='email']"));
		
		//input[@id='ap_email']
		
	}
	
	
	//Finds desired item
	
	public void FindAndAddItem ()  throws Exception 
		{
		//Open Application
			//driver.get(BaseURL);
			Thread.sleep(2000);
			//Search for item
			WebElement SearchBar = driver.findElement(By.id("twotabsearchtextbox"));
			SearchBar.sendKeys("HD TV");
			WebElement Search = driver.findElement(By.id("nav-search-submit-text"));
			Search.click();
			Thread.sleep(2000);
			
			//WebElement selectTV = driver.findElement(By.id("MAIN-SEARCH_RESULTS-12"));
			//selectTV.click();
			
			//jse.executeScript("window.scrollBy(0, 5800)");
			Thread.sleep(2000);
			
			System.out.println("before!!!");
			for(int x=0; x<20; x++ )
			{
				System.out.println("its in!!!");
				
				try {
					WebElement find = driver.findElement(By.xpath("//img[@src='https://m.media-amazon.com/images/I/91ucG9ukLHL._AC_UY218_.jpg']"));

					
						if (find.isDisplayed())
						{
							find.click();
							System.out.println("Item found");
							Thread.sleep(2000);
							break;
						}
						else
						{
							System.out.println("item not found");
							jse.executeScript("window.scrollBy(0, 500)");	
						}
					}	
			
			//  Block of code to handle errors if item not found.
				catch(Exception e) 
					{
					  
						System.out.println("Item not found, searching next page");
						WebElement nextPage = driver.findElement(By.xpath("//li[@class='a-last']"));
						//Searches next page
						nextPage.click();
						Thread.sleep(2000);
					}
			}
			//Selects size selected item
			WebElement selectSize = driver.findElement(By.xpath("//li[@id='size_name_2']"));
			
			Thread.sleep(2000);
			selectSize.click();
			try {
				//Select quantity of items
			WebElement inStock = driver.findElement(By.xpath("//select[@id='quantity']"));
			
		
			//Checks if item is in stock
				if (inStock.isDisplayed())
					{
					Thread.sleep(2000);
					WebElement addToCart = driver.findElement(By.name("submit.add-to-cart"));
					Thread.sleep(2000);
					addToCart.click();
					Thread.sleep(2000);
					//Message box window
					//JFrame frame = new JFrame("Simple GUI");
					//JOptionPane.showMessageDialog(frame, "Eggs are not supposed to be green.");
					//WebElement popUp = driver.findElement(By.id("attachSiNoCoverage-announce"));
				
					//Deals with pop up which appears after user attempts to add to basket
					try {
						WebElement popUp = driver.findElement(By.xpath("//button[@id='siNoCoverage-announce']"));
						
							if (popUp.isDisplayed())
							{
								System.out.println("Pop up displayed");
								popUp.click();
								Thread.sleep(2000);
								String text = "Added to Basket";
								String bodyText = driver.findElement(By.tagName("Body")).getText();
								Assert.assertTrue(bodyText.contains(text), "Text not found!");
							}
					
						System.out.println("added to cart");
					

						}
					//Deals with different pop up which sometimes appears
					catch(Exception e) 
						{
							WebElement popUp = driver.findElement(By.xpath("//button[@id='attachSiNoCoverage-announce']"));
					  
							if (popUp.isDisplayed())
							{
								System.out.println("Pop up displayed");
								popUp.click();
								Thread.sleep(2000);
								String text = "Added to Basket";
								String bodyText = driver.findElement(By.tagName("Body")).getText();
								Assert.assertTrue(bodyText.contains(text), "Text not found!");
							}
				
							System.out.println("added to cart");
						}
				
					}	
				}
			//In event that Amazon offers multiple sellers of the item this method will select the cheapest
					catch(Exception e)
					{
						buyingOptions();	
					}
			
			
			
			
			//Brings user to checkout window	
			WebElement checkOut = driver.findElement(By.id("hlb-ptc-btn-native"));
			By locatorKey = By.id("hlb-ptc-btn-native");
				
			//isElementPresent(locatorKey);
			//Checks if button is visible
			if(isElementPresent(locatorKey))
			{
				System.out.println("Button is visible");
			}
				Thread.sleep(2000);
				checkOut.click();
				
				WebElement UseAddress = driver.findElement(By.className("a-button-inner"));
				UseAddress.click();
				
				
				WebElement Continue = driver.findElement(By.xpath("//span[@class='a-button-inner']"));
				Continue.click();
		}
	
	
	
	
	//Checks if button is visible
	public boolean isElementPresent(By locatorKey) {
	    try {
	        driver.findElement(locatorKey);
	        return true;
	    } catch (org.openqa.selenium.NoSuchElementException e) {
	        return false;
	    }
	}
	
	
	//Finds cheapest Item
	public void buyingOptions ()  throws Exception 
	{
		driver.findElement(By.id("buybox-see-all-buying-choices-announce")).click();
		System.out.println("In BuyingOptions!");
		
		java.util.List<WebElement> tabs = driver.findElements(By.xpath("//span[@class='a-price-whole']"));
		int lowestPrice= 10000;
		String amountName = null;
		int listPosition = 0;
		for( WebElement handle:tabs)
			{
			//Integer.parseInt(driver.findElement(By.WebElement).getText());
			
		//	System.out.println(Integer.parseInt(handle.getText()));
			//System.out.println(Integer.parseInt(handle.getText()));
			int Price = Integer.parseInt(handle.getText());
			if (Price < lowestPrice)
				{
					amountName = handle.getText();
					lowestPrice = Price;
					listPosition = tabs.indexOf(handle);
					System.out.println(listPosition);
				}
			
			//System.out.println(handle);	
			System.out.println("this is the list!");
			//WebElement textElement = driver.findElement(By.xpath("//span[@class='a-price-whole']"));
			//String text = textElement.getText();
			//System.out.println("Value: " + text);
			}
		System.out.println(lowestPrice);	
		
		
	
			System.out.println(amountName + " IS THE AMOUNT NAME");
			//WebElement findElement = driver.findElement(By.xpath("//*[contains(text(), amountName)]"));
			//System.out.println(findElement);
			//WebElement findElement2 = tabs.get(listPosition);
			//System.out.println(findElement2);
			
			//dynamically selecting Submit button depending on user preference of price
			System.out.println("list position is" +listPosition);
			System.out.println("this is answer " + listPosition + 1);
			driver.findElement(By.xpath("(//input[@name='submit.addToCart'])["+listPosition +1 +"]")).click();
			
			//submit.addToCart
			//(//input[@id="search_query"])[2]
	}
	
		
			@AfterMethod
			@AfterClass
			public void tearDown() throws Exception {
				Thread.sleep(4000);
				//driver.quit();
		}
}
