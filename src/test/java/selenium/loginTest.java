package selenium;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class loginTest {
	
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
		System.setProperty("webdriver.chrome.driver",
				"F:\\Kiểm thử\\Nâng cao\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		//set headless
		options.setHeadless(false);
		options.addArguments("start-maximized");
		this.driver = new ChromeDriver(options);
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet("Login Selenium");
		TestNGResults = new LinkedHashMap<String, Object[]>();
		
		TestNGResults.put("1", new Object[] {"Input", "Expected Output", "Actual Output", "Result"});
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
			FileOutputStream out = new FileOutputStream(new File("LoginSeleniumTest.xls"));
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
  @Test(dataProvider="dataLogin")
  public void loginThanhDepTrai(String username, String password, int Excel, String expected) throws InterruptedException {
		
		
		String url = "http://localhost:8080/ASM_Java5_BanHang/login/index.html";
		
		driver.get(url);
		
		driver.findElement(By.name("username")).sendKeys(username);
		Thread.sleep(1000);
		driver.findElement(By.name("password")).sendKeys(password);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"Users\"]/div[3]/input")).click();
		Thread.sleep(2000);
		
		String curUrl = driver.getCurrentUrl();
		if(curUrl.equals("http://localhost:8080/ASM_Java5_BanHang/login/login.html")) {
			//Login sai
			TestNGResults.put(""+Excel+"", new Object[] {""+username+"|"+password+"", expected, "FAIL", "FAIL"});

			fail("Dang nhap sai");
		}else {
			//Login đúng => logout
			Thread.sleep(3000);
			TestNGResults.put(""+Excel+"", new Object[] {""+username+"|"+password+"", expected, "PASS", "PASS"});

			/*if(username.equals("admin")) {
				driver.findElement(By.xpath("/html/body/div[2]/div[2]/nav/ul/li[8]/a")).click();

			}*/
			String checkRole = driver.findElement(By.cssSelector("body > div.super_container > div.sidebar > div.info > div > div.info_currencies.has_children > div")).getText();
			if(checkRole.equals("QTV")) {
				//click logout
				driver.findElement(By.xpath("/html/body/div[2]/div[2]/nav/ul/li[8]/a")).click();

			}else {				
				//click logout
				driver.findElement(By.xpath("/html/body/div[2]/div[2]/nav/ul/li[4]/a")).click();

			}
			//Assert logout thành công
		}
	}
  
  
  @DataProvider(name="dataLogin")
  public Object[][] getData()
  {
     Object [][] myData = {{"admin","admin", 2, "PASS"},
                           {"admin123","admin123", 3, "PASS"},
                           {"tksai","", 4, "FAIL"},
                           {"","mksai", 5, "FAIL"}};
     return myData;
  }

}
