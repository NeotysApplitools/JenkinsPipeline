package com.neotys.applitool.sampleproject;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.*;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.neotys.selenium.proxies.NLRemoteWebDriver;
import com.neotys.selenium.proxies.NLWebDriverFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import io.appium.java_client.AppiumDriver;

public class ApplitoolAppium {
	ChromeDriver wd;
	NLRemoteWebDriver driver;
	String openSearch = "//*[@id=\'open-search\']";
	String searchField = "//*[@id=\'search-input-mobile\']";
	String searchSubmit = "//*[@id=\'search-button-mobile\']";
	EyesRunner runner;
	String applicationURL="";
	String apikey="";
	Eyes eyes;
	//{
	//    "deviceName": "Nexus 5X",
	//    "udid": "00ac5958dd8d9aed",
	//    "autoAcceptAlerts": true,
	//    "automationName": "UiAutomator2",
	//    "appPackage": "com.android.settings",
	//    "platformName": "Android",
	//    "appActivity": "com.android.settings.Settings"
	//}

	@BeforeMethod @Before
	public void beforeMethod() throws Exception {
		runner = new VisualGridRunner(1);


		//Replace <<cloud name>> with your perfecto cloud name (e.g. demo) or pass it as maven properties: -DcloudName=<<cloud name>>

		//Replace <<security token>> with your perfecto security token or pass it as maven properties: -DsecurityToken=<<SECURITY TOKEN>>  More info: https://developers.perfectomobile.com/display/PD/Generate+security+tokens
		String projectPath="";
		applicationURL="<<applicationURL>>";
		applicationURL=Utils.fetchApplicationURL(applicationURL);
		//A sample perfecto connect appium script to connect with a perfecto android device and perform addition validation in calculator app.
		String browserName = "mobileOS";

		apikey="<<apiKey>>";
		apikey=Utils.fetchApitoken(apikey);
		/*DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("sessionDescription", "NeoLoad test");
		capabilities.setCapability("deviceOrientation", "portrait");
		capabilities.setCapability("captureScreenshots", true);
		capabilities.setCapability("browserName", "firefox");
// The given group is used for finding devices and the created session will be visible for all members within the group.
		capabilities.setCapability("platformName", "Android");

		capabilities.setCapability("deviceName", "HD1907");
        capabilities.setCapability("udid", "cd3aed29");
        capabilities.setCapability("headspin:capture", "true");

*/


		//{
        //    "deviceName": "HD1907",
        //    "udid": "cd3aed29",
        //    "autoAcceptAlerts": true,
        //    "automationName": "UiAutomator2",
        //    "appPackage": "com.android.settings",
        //    "platformName": "Android",
        //    "appActivity": "com.android.settings.Settings"
        //}

		boolean session=false;
		for(int i=0;i<5;i++)
		{
			try {
				session=createSession(projectPath);
				if(session)
					break;
			}
			catch (SessionNotCreatedException e)
			{
				e.printStackTrace();
			}
		}
		if(!session)
			throw new RuntimeException("Driver not created wb");



	}

	public boolean createSession(String projectname) throws Exception,SessionNotCreatedException {
		boolean result=false;
		eyes = new Eyes(runner);
		// create a new batch info instance and set it to the configuration

		Configuration suiteConfig = (Configuration) new Configuration()
				// Checkpoint configurations
				.setForceFullPageScreenshot(true)
				.setStitchMode(StitchMode.CSS)
				.setHideScrollbars(true)
				.setHideCaret(true)
				// Test suite configurations
				.setApiKey(apikey)

				.setAppName("NeoloadTest");

		suiteConfig.setBatch(new BatchInfo("Ultrafast Batch"));

		// Add browsers with different viewports
		suiteConfig.addBrowser(800, 600, BrowserType.CHROME);
		suiteConfig.addBrowser(700, 500, BrowserType.FIREFOX);
		suiteConfig.addBrowser(1600, 1200, BrowserType.IE_11);
		suiteConfig.addBrowser(1024, 768, BrowserType.EDGE_CHROMIUM);
		suiteConfig.addBrowser(800, 600, BrowserType.SAFARI);

		// Add mobile emulation devices in Portrait mode
		suiteConfig.addDeviceEmulation(DeviceName.iPhone_X);

		eyes.setConfiguration(suiteConfig);
		wd  = new ChromeDriver();
		driver = (NLRemoteWebDriver) NLWebDriverFactory.newNLWebDriver(wd, "KonaKart Android", projectname);

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		eyes.open(driver, "Demo App", "Ultrafast grid demo", new RectangleSize(800, 600));
		System.out.println("Session created, session id :"+driver.getSessionId());
		result=true;
		return result;
	}

	@Test @org.junit.Test
	public void appiumTest() throws Exception {

		//----enable vitals metrics---
		driver.startTransaction("Accessibility Assertion");


		driver.startTransaction("Navigate to Konakart.com");
		driver.get("http://"+applicationURL);
		driver.stopTransaction();
		eyes.checkWindow("Home Page");

		driver.startTransaction("Search for Comptuer");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSearch)));
		WebElement element=driver.findElement(By.xpath((openSearch)));
		element.click();
		eyes.checkWindow("Search computer");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchField)));
		element=driver.findElement(By.xpath((searchField)));
		element.clear();
		element.sendKeys("book");


		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchSubmit)));
		element=driver.findElement(By.xpath((searchSubmit)));
		element.click();
		driver.stopTransaction();

		eyes.checkWindow("Search Book");


		driver.startTransaction("Search for phone");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSearch)));
		element=driver.findElement(By.xpath((openSearch)));
		element.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchField)));
		element=driver.findElement(By.xpath((searchField)));
		element.clear();
		element.sendKeys("phone");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchSubmit)));
		element=driver.findElement(By.xpath((searchSubmit)));
		element.click();
		driver.stopTransaction();

		eyes.checkWindow("Search Phone");


		driver.startTransaction("Search for game");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSearch)));
		element=driver.findElement(By.xpath((openSearch)));
		element.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchField)));
		element=driver.findElement(By.xpath((searchField)));
		element.clear();
		element.sendKeys("game");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchSubmit)));
		element=driver.findElement(By.xpath((searchSubmit)));
		element.click();
		driver.stopTransaction();

		eyes.checkWindow("Search Game");


		driver.close();
		eyes.closeAsync();
		driver.quit();

		TestResultsSummary allTestResults = runner.getAllTestResults(false);
		System.out.println(allTestResults);
	}




}

