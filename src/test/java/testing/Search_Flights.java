package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Search_Flights {
	private static WebDriver driver;
	private static JavascriptExecutor js;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "F:/IBM Training/Jars/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.close();
		driver.quit();
	}

	@Test
	public void searchFlights() {
		String baseURL = "https://www.air.irctc.co.in/";
		driver.get(baseURL);

		// Verifying URL
		String actual = driver.getCurrentUrl();
		String expected = "https://www.air.irctc.co.in/";
		assertEquals(expected, actual);

		// Maximizing Window
		driver.manage().window().maximize();

		// Selecting From and To Cities
		driver.findElement(By.name("From")).sendKeys("Hyd");
		WebElement From = driver.findElement(By.xpath("//div[text() = 'Hyderabad (HYD)']"));
		js.executeScript("arguments[0].click();", From);

		driver.findElement(By.name("To")).sendKeys("Pune");
		driver.findElement(By.xpath("//ul[@id='ui-id-2']/li/div[starts-with(@id, 'ui-id-') and text() = 'Pune (PNQ)']"))
		.click();

		// Selecting Origin Date
		WebElement date = new WebDriverWait(driver, Duration.ofSeconds(40))
				.until(ExpectedConditions.elementToBeClickable(By.id("originDate")));
		date.click();
		driver.findElement(By.xpath("//span[@class = 'act active-red']")).click();
		String TodayDate = driver.findElement(By.id("originDate")).getAttribute("value");

		// Selecting Business Class
		driver.findElement(By.id("noOfpaxEtc")).click();
		Select sc = new Select(driver.findElement(By.id("travelClass")));
		sc.selectByIndex(1);

		// Clicking Search Button
		driver.findElement(By.xpath(
				"//body/app-root[1]/app-index[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/form[1]/div[6]/button[1]"))
		.click();

		// Verifying Cities and Date From Previous Page
		String FromCity = driver.findElement(By.xpath("//input[@id='stationFrom']")).getAttribute("value");
		assertEquals(FromCity, "Hyderabad (HYD)");

		String ToCity = driver.findElement(By.xpath("//input[@id='stationTo']")).getAttribute("value");
		assertEquals(ToCity, "Pune (PNQ)");

		String OriginDate = driver.findElement(By.xpath("//input[@id='originDate']")).getAttribute("value");
		assertEquals(OriginDate, TodayDate);

		// Printing Number of Available Flights
		List<WebElement> flights = driver.findElements(By.xpath("//div[@class='right-searchbarbtm']"));
		System.out.println("Number of Available Flights: " + flights.size());
		System.out.print("\n");

		// Printing Flight Names on Console
		System.out.println("Available Flights");
		for (WebElement flight : flights) {
			System.out.print("Flight Name: " + flight.getText().split("\n")[0] + "-" + flight.getText().split("\n")[1]);
			System.out.println("");
		}

		// Taking Screenshot and Saving in the folder
		TakesScreenshot ts = (TakesScreenshot) driver;
		File sourceFile = ts.getScreenshotAs(OutputType.FILE);
		String FileSuffix = new SimpleDateFormat("ddMMyyyyhhmmss").format(new Date());
		File destinationFile = new File(
				"F:\\IBM Training\\Java Programs\\IRCTC-AIR\\screenshots\\screenshots" + FileSuffix + ".png");

		try {
			FileUtils.copyFile(sourceFile, destinationFile);
		} catch (IOException e) {
			System.out.println("Exception" + e.getMessage());
		}

	}

}
