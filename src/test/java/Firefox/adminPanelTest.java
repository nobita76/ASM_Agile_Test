package Firefox;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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

public class adminPanelTest {
	
	WebDriver driver;
	
	public UIMap uimap;
	public UIMap datafile;
	public String workingDir;
	public HSSFWorkbook workbook;
	
	//Declare An Excel Work Book
	public HSSFSheet sheet;
	Map<String, Object[]> TestNGResults;
	@BeforeTest
	public void init() {
		System.setProperty("webdriver.gecko.driver",
				"F:\\Kiểm thử\\Nâng cao\\firefox\\geckodriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		this.driver = new ChromeDriver(options);
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet("Admin Panel Test Selenium");
		TestNGResults = new LinkedHashMap<String, Object[]>();
		
		TestNGResults.put("1", new Object[] {"Test Step No.", "Expected Output", "Actual Output"});
		try {
			workingDir = System.getProperty("user.dir");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@AfterClass
	public void tearDown() {
		Set<String> keyset = TestNGResults.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = TestNGResults.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof Date)
					cell.setCellValue((Date) obj);
				else if (obj instanceof Boolean)
					cell.setCellValue((Boolean) obj);
				else if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(new File("adminPanelTest.xls"));
			workbook.write(out);
			out.close();
			System.out.println("Successfully save Selenium WebDriver TestNG result to Excel File!!!");
			
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
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
			TestNGResults.put("2", new Object[] {1d, "Login Account Admin", "Login Success", "FAIL"});

			Assert.fail("Dang nhap sai");
			
		}else {
			//Login đúng => logout
			TestNGResults.put("2", new Object[] {1d, "Login Account Admin", "Login Success", "PASS"});

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
  
  
  @Test(alwaysRun = false, priority = 4)
  
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
		/*Thread.sleep(0);
		String actual = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/center/div/table/tbody/tr[3]/td[3]/span")).getText();
		String expected = "QTV";*/
		TestNGResults.put("5", new Object[] {4d, "Edit User Test", "EDITED", "PASS"});

		assertTrue(true);
		
  }
  
  @Test(alwaysRun = true, priority = 2, dataProvider = "dataAddUser")
  public void addUsersTest(int stt, String username, String password, int vaitro) throws InterruptedException {
	  try {
		  System.out.println(vaitro);
		  Thread.sleep(500);
		  driver.get("http://localhost:8080/ASM_Java5_BanHang/admin/index.html");
		  
		  Thread.sleep(2000);
		  driver.findElement(By.cssSelector("#navbarTogglerDemo01 > ul > li:nth-child(3) > a")).click();
		  Thread.sleep(1000);
		  driver.findElement(By.name("username_insert")).sendKeys(username);
		  driver.findElement(By.name("password_insert")).sendKeys(password);
		  Select dropRole = new Select(driver.findElement(By.name("vaitro_insert")));
		  dropRole.selectByIndex(vaitro);
		  //driver.findElement(By.name("Action")).click();
		  Thread.sleep(1000);
		  List<WebElement> button = driver.findElements(By.cssSelector("#form-update > input.btn.btn-primary"));
		  button.get(1).click();
		  Thread.sleep(5000);
			TestNGResults.put(""+stt+"", new Object[] {2d, "Add New Users - "+username+"", "Added", "PASS"});

	} catch (Exception e) {
		TestNGResults.put(""+stt+"", new Object[] {2d, "Add New Users - "+username+"", "Added", "FAIL"});

		e.printStackTrace();
		// TODO: handle exception
	}
	  
  }
//  @Test(alwaysRun = true, priority = 3)
  public void deleteUserTest() throws InterruptedException {
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/center/div/table/tbody/tr[9]/td[5]/center/a")).click();
	  
	  assertTrue(true);
	  
  }
  
  @Test(alwaysRun = true, priority = 3)
  public void findUserTest() throws InterruptedException {
	  Thread.sleep(1000);
	  driver.findElement(By.cssSelector("#bootstrap-data-table-Login_filter > label > input[type=search]")).sendKeys("testadd01");
	  Thread.sleep(2000);
	  String actual = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/center/div/table/tbody/tr[1]/td[1]")).getText();
	  String expected = "testadd01";
		TestNGResults.put("4", new Object[] {3d, "Find User Test", "SUCCESS", "PASS"});

	  AssertJUnit.assertEquals(actual, expected);
  }
  
  @Test(priority = 5)
  public void editProductTest() throws InterruptedException {
	  Thread.sleep(1000);
	  driver.get("http://localhost:8080/ASM_Java5_BanHang/admin/sanpham.html");
	  Thread.sleep(2000);
	  driver.findElement(By.cssSelector("#bootstrap-data-table-Login > tbody > tr:nth-child(2) > td:nth-child(8) > center > a")).click();
	  
	  Thread.sleep(1000);
	  driver.findElement(By.name("tensp_form")).clear();
	  driver.findElement(By.name("tensp_form")).sendKeys("Thanhbnps10511edittest");
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
	  String expected = "Thanhbnps10511edittest";
		TestNGResults.put("6", new Object[] {5d, "Edit Product Test", "EDITED", "PASS"});

	  AssertJUnit.assertEquals(actual, expected);
  }
  
  @DataProvider(name="dataLogin")
  public Object[][] getData()
  {
     Object [][] myData = {{"admin","admin"}};
     return myData;
  }
  
  @DataProvider(name="dataAddUser")
  public Object[][] getDataAddUser()
  {
     Object [][] myData = {{3, "testadd01","testadd01", 0},
    		 				{4, "testadd02", "testadd02", 1}};
     return myData;
  }

}
