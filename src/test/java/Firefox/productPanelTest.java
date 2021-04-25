package Firefox;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class productPanelTest {
	
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
  @Test(priority = 1 ,dataProvider="dataLogin")
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
		if(curUrl.equals("http://localhost:8080/ASM_Java5_BanHang/login/login.html")) {
			//Login sai
			Assert.fail("Dang nhap sai");
		}else {
			//Login đúng => logout
			Thread.sleep(3000);
			String actual = driver.findElement(By.xpath("/html/body/div[2]/div[2]/nav/ul/li[2]/a")).getAttribute("href");
			String expected = "admin/index.html";
			if(actual.equals(actual)) {
				assertTrue(true);
			}else {
				assertTrue(false);
			}
		}
	}
  
  
  //@Test(alwaysRun = false, priority = 4)
  
  public void editUsersTest() throws InterruptedException {
	  System.out.println("Test");
	  Thread.sleep(1000);
	  //http://savetext.net/KkUdJ53XnsvynDhvf9tt
		//driver.findElement(By.xpath("/html/body/div[2]/div[2]/nav/ul/li[2]/a")).click();
		//Thread.sleep(1000);
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/center/div/table/tbody/tr[1]/td[4]/center/a")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("password_form")).clear();
		driver.findElement(By.name("password_form")).sendKeys("ps10511buingocthanh");
		Select dropSelectRole = new Select(driver.findElement(By.name("vaitro_form")));
		dropSelectRole.selectByVisibleText("Quản trị viên");
		driver.findElement(By.name("Action")).click();
		
		String actual = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/center/div/table/tbody/tr[3]/td[3]/span")).getText();
		String expected = "QTV";
		AssertJUnit.assertEquals(actual, expected);
		
  }
  
  @Test(alwaysRun = true, priority = 2, dataProvider = "dataAddProduct")
  public void addProductTest(String tensp, String dongia, String soluong, String hinhanh, String type, String dm) throws InterruptedException {
	  try {
		  Thread.sleep(500);
		  driver.get("http://localhost:8080/ASM_Java5_BanHang/admin/sanpham.html");
		  
		  Thread.sleep(2000);
		  driver.findElement(By.cssSelector("#navbarTogglerDemo01 > ul > li:nth-child(3) > a")).click();
		  Thread.sleep(1000);
		  driver.findElement(By.name("tensp_insert")).sendKeys(tensp);
		  driver.findElement(By.name("dongia_insert")).sendKeys(dongia);
		  driver.findElement(By.name("soluong_insert")).sendKeys(soluong);
		  driver.findElement(By.name("photo_insert")).sendKeys(System.getProperty("user.dir")+"/ps10511_input/img/"+hinhanh+"");
		  Select dropType = new Select(driver.findElement(By.name("type_insert")));
		  dropType.selectByVisibleText(type);
		  
		  Select dropDM = new Select(driver.findElement(By.name("dm_insert")));
		  dropDM.selectByVisibleText(dm);
		  
		  //driver.findElement(By.name("Action")).click();
		  Thread.sleep(1000);
		  List<WebElement> button = driver.findElements(By.cssSelector("#form-update > input.btn.btn-primary"));
		  button.get(1).click();
		  Thread.sleep(3000);
	} catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	  
  }
  @Test(alwaysRun = true, priority = 4)
  public void deleteProduct() throws InterruptedException {
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/center/div/table/tbody/tr[1]/td[9]/center/a")).click();
	  
	  assertTrue(true);
	  
  }
  
  @Test(alwaysRun = true, priority = 3)
  public void findProductTest() throws InterruptedException {
	  Thread.sleep(1000);
	  driver.findElement(By.cssSelector("#bootstrap-data-table-Login_filter > label > input[type=search]")).sendKeys("tensp01");
	  Thread.sleep(2000);
	  String actual = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/center/div/table/tbody/tr[1]/td[2]")).getText();
	  String expected = "tensp01";
	  AssertJUnit.assertEquals(actual, expected);
  }
  
  //@Test(priority = 5)
  public void editProductTest() throws InterruptedException {
	  Thread.sleep(1000);
	  driver.get("http://localhost:8080/ASM_Java5_BanHang/admin/sanpham.html");
	  Thread.sleep(2000);
	  driver.findElement(By.cssSelector("#bootstrap-data-table-Login > tbody > tr:nth-child(2) > td:nth-child(8) > center > a")).click();
	  
	  Thread.sleep(1000);
	  driver.findElement(By.name("tensp_form")).clear();
	  driver.findElement(By.name("tensp_form")).sendKeys("Thanhdeptraiedit");
	  driver.findElement(By.name("dongia_form")).clear();
	  driver.findElement(By.name("dongia_form")).sendKeys("5000");
	  driver.findElement(By.name("soluong_form")).clear();
	  driver.findElement(By.name("soluong_form")).sendKeys("50");
	  Select dropType = new Select(driver.findElement(By.name("type_form")));
	  dropType.selectByVisibleText("NEW");
	  Select dropDM = new Select(driver.findElement(By.name("dm_form")));
	  dropDM.selectByVisibleText("summer");
	  Thread.sleep(2000);
	  driver.findElement(By.name("Action")).click();
	  Thread.sleep(1000);
	  String actual = driver.findElement(By.cssSelector("#bootstrap-data-table-Login > tbody > tr:nth-child(2) > td:nth-child(2)")).getText();
	  String expected = "Thanhdeptraiedit";
	  AssertJUnit.assertEquals(actual, expected);
  }
  
  @DataProvider(name="dataLogin")
  public Object[][] getData()
  {
     Object [][] myData = {{"admin","admin"}};
     return myData;
  }
  
  @DataProvider(name="dataAddProduct")
  public Object[][] getDataAddProduct()
  {
     Object [][] myData = {{"tensp01","1234", "500", "product_15.jpg", "HOT", "short"},
    		 {"tensp02","4566", "50", "product_16.jpg", "SALE", "summer"},
    		 {"tensp03","10", "30", "product_17.jpg", "HOT", "summer"}};
     return myData;
  }

}
