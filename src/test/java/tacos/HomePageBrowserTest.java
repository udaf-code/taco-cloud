package tacos;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.openqa.selenium.By;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HomePageBrowserTest {

@LocalServerPort
private int port;
private static WebDriver browser;

	@BeforeAll
	public static void setup() {
		ChromeOptions options = new ChromeOptions();
		// новый headless флаг:
		options.addArguments("--headless=new");
		options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");
		options.addArguments("--window-size=1920,1200");
		//Если chromedriver находится в PATH, дополнительных действий не нужно.
		browser = new ChromeDriver(options);
				
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
	}
	
	@AfterAll
		public static void teardown() {
			if (browser != null) {
			browser.quit();
		}
	}
	
	@Test
	public void testHomePage() {
	String homePage = "http://localhost:" + port;
	browser.get(homePage);
	String titleText = browser.getTitle();
	Assertions.assertEquals("Taco Cloud", titleText);
	
	String h1Text = browser.findElement(By.tagName("h1")).getText();
	Assertions.assertEquals("Welcome to...", h1Text);

	String imgSrc = browser.findElement(By.tagName("img")).getAttribute("src");
	Assertions.assertEquals(homePage + "/images/TacoCloud.png", imgSrc);
	}
}
