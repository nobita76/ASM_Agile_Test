package selenium;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class registerTest {
	
	WebDriver driver;
	public UIMap uimap;
	public UIMap datafile;
	public String workingDir;
	public HSSFWorkbook workbook;
	public HSSFSheet sheet;
	Map<String, Object[]> TestNGResults;
	@BeforeTest
	public void init() {
		System.setProperty("webdriver.chrome.driver",
				"F:\\Kiểm thử\\Nâng cao\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		this.driver = new ChromeDriver(options);
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet("Register Selenium");
		TestNGResults = new LinkedHashMap<String, Object[]>();
		
		TestNGResults.put("1", new Object[] {"Input", "Expected Output", "Actual Output", "Result"});
		try {
			workingDir = System.getProperty("user.dir");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@AfterClass
	public void closeChrome() {
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
			FileOutputStream out = new FileOutputStream(new File("RegisterSeleniumTest.xls"));
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
  @Test(dataProvider="dataRegister")
  public void registerAccount(String username, String password, String email, int Excel, String expected) throws InterruptedException {
		
		
		String url = "http://localhost:8080/ASM_Java5_BanHang/login/index.html";
		
		driver.get(url);
		driver.findElement(By.xpath("/html/body/div/div/div/div/div/label[2]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div[2]/form/div[1]/input")).sendKeys(username);
		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div[2]/form/div[2]/input")).sendKeys(password);
		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div[2]/form/div[3]/input")).sendKeys(email);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"Users\"]/div[4]/input")).click();
		Thread.sleep(2000);
		String messageReg = driver.findElement(By.className("message")).getText();
		System.out.println(messageReg);
		if(messageReg.equals("The account already has an user")) {
			TestNGResults.put(""+Excel+"", new Object[] {""+username+"|"+password+"|"+email+"", expected, "FAIL - "+messageReg, "FAIL "+messageReg});

			//Đăng ký thất bại
			fail("Dang ky that bai");
		}else {
			
			TestNGResults.put(""+Excel+"", new Object[] {""+username+"|"+password+"|"+email+"", expected, "PASS", "PASS"});

		}
		

		
	}
  
  
  @DataProvider(name="dataRegister")
  public Object[][] getData()
  {
     Object [][] myData = {{"admin","admin", "admin@gmail.com", 1, "FAIL"},
                           {"admin123","admin123", "admin123@gmail.com", 2, "PASS"},
                           {"testbotrong", "", "test@gmail.com", 3, "FAIL"},
                           {"", "botrongpassword", "test@gmail.com", 4, "FAIL"}};
     return myData;
  }

}
