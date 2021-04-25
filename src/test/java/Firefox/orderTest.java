package Firefox;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class orderTest {

	WebDriver driver;

	@BeforeTest
	public void init() {
		System.setProperty("webdriver.gecko.driver",
				"F:\\Kiểm thử\\Nâng cao\\firefox\\geckodriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		this.driver = new ChromeDriver(options);
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	@Test(dataProvider = "dataLogin", priority = 1)
	public void loginAccount(String username, String password) throws InterruptedException {

		String url = "http://localhost:8080/ASM_Java5_BanHang/login/index.html";

		driver.get(url);

		driver.findElement(By.name("username")).sendKeys(username);
		Thread.sleep(1000);
		driver.findElement(By.name("password")).sendKeys(password);
		Thread.sleep(1000);
		driver.findElement(By.name("Action")).click();
		Thread.sleep(2000);
		String curUrl = driver.getCurrentUrl();
		if (curUrl.equals("http://localhost:8080/ASM_Java5_BanHang/login/login.html")) {
			// Login sai
			Assert.fail("Dang nhap sai");
		} else {
			// Login đúng => click Shop Now
			Thread.sleep(3000);
			driver.findElement(
					By.xpath("/html/body/div[2]/div[3]/div/div[1]/div[1]/div/div[3]/div/div[2]/div/div[3]/a")).click();
		}
	}

	@Test(priority = 3)
	public void addProductHetHangTest() throws InterruptedException {
		// driver.get("http://localhost:8080/ASM_Java5_BanHang/categories.html");
		// Thread.sleep(2000);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,1000)");
		Thread.sleep(1000);
		driver.findElement(By.cssSelector(
				"body > div.super_container > div.products > div > div > div > div > div > div:nth-child(1) > div > div.product_content.text-center > div.product_title > a"))
				.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div[3]/div[4]/div[2]/div/div/div/div/div[2]/form/div[6]/a")).click();
		String actual = driver.findElement(By.xpath("/html/body/div[3]/div[4]/div/div/div/div/div/font/b")).getText();

		String expected = "Sản phẩm đã hết hàng";
		assertEquals(actual, expected);

	}

	@Test(priority = 4)

	public void addProductOKIndexTest() throws InterruptedException {
		Thread.sleep(500);
		driver.get("http://localhost:8080/ASM_Java5_BanHang/categories.html");
		// Click continue shopping
		// driver.findElement(By.cssSelector("body > div.super_container >
		// div.cart_section > div:nth-child(1) > div > div > div > div >
		// div.cart_buttons.d-flex.flex-row.align-items-start.justify-content-start >
		// div > div.button.button_continue.trans_200 > a"));

		Thread.sleep(2000);

		// Click button thêm
		driver.findElement(
				By.xpath("/html/body/div[3]/div[4]/div[2]/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/a"))
				.click();
		Thread.sleep(2000);
		String actual = driver
				.findElement(By.xpath("/html/body/div[3]/div[4]/div[1]/div/div/div/div/div[2]/ul/li/div[1]/div[2]/a"))
				.getText();
		String expected = "Koko";
		assertEquals(actual, expected);

	}

	@Test(priority = 5)

	public void tangSPTest() throws Exception {
		driver.findElement(
				By.xpath("/html/body/div[3]/div[4]/div[1]/div/div/div/div/div[2]/ul/li/div[5]/div/a[2]/div/span"))
				.click();
		Thread.sleep(1000);
		String actual = driver
				.findElement(By.xpath("/html/body/div[3]/div[4]/div[1]/div/div/div/div/div[2]/ul/li/div[5]/div/span"))
				.getText().trim();

		String expected = "2";
		assertEquals(actual, expected);
	}

	@Test(priority = 6)

	public void giamSPTest() throws InterruptedException {
		driver.findElement(
				By.xpath("/html/body/div[3]/div[4]/div[1]/div/div/div/div/div[2]/ul/li/div[5]/div/a[1]/div/span"))
				.click();
		Thread.sleep(1000);
		String actual = driver
				.findElement(By.xpath("/html/body/div[3]/div[4]/div[1]/div/div/div/div/div[2]/ul/li/div[5]/div/span"))
				.getText();

		String expected = "1";
		assertEquals(actual, expected);
	}

	@Test(priority = 7)

	public void clearCartTest() throws InterruptedException {
		// Thiếu verify
		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div[3]/div[4]/div[1]/div/div/div/div/div[3]/div/div[2]/a")).click();
	}

	@Test(priority = 8)
	public void addProductOKViewTest() throws InterruptedException {
		Thread.sleep(1000);
		driver.get("http://localhost:8080/ASM_Java5_BanHang/product.html?maSP=5");
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div[3]/div[4]/div[2]/div/div/div/div/div[2]/form/div[6]/a")).click();
		String actual = driver
				.findElement(By.xpath("/html/body/div[3]/div[4]/div[1]/div/div/div/div/div[2]/ul/li/div[1]/div[2]/a"))
				.getText();
		String expected = "Koko";
		assertEquals(actual, expected);

	}

	@Test(priority = 9)
	public void checkoutOrderTest() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div[3]/div[4]/div[2]/div/div/div/div/div/div[2]/a")).click();

		Thread.sleep(2000);
		String actual = driver.findElement(By.cssSelector(
				"body > div.super_container > div.checkout > div > div > div > div > div > div > div > div.checkout_title > font"))
				.getAttribute("color");
		System.out.println(actual);
		String expected = "green";

		assertEquals(actual, expected);
	}

	@Test(priority = 10)

	public void addAddressToCheckoutTest() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div[3]/div[4]/div/div/div/div/div/div/div/form/input[2]"))
				.sendKeys("PS10511_Bùi Ngọc Thành...");

		Thread.sleep(1000);

		driver.findElement(By.xpath("/html/body/div[3]/div[4]/div/div/div/div/div/div/div/form/input[3]")).click();

		Thread.sleep(1000);

		String currentUrl = driver.getCurrentUrl();
		String expected = "http://localhost:8080/ASM_Java5_BanHang/index.html";

		assertEquals(currentUrl, expected);

	}

	@DataProvider(name = "dataLogin")
	public Object[][] getData() {
		Object[][] myData = { { "admin", "admin" } };
		return myData;
	}

}
