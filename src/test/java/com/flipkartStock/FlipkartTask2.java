package com.flipkartStock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FlipkartTask2 {

	static WebDriver driver;
	static long startTime;

	@BeforeMethod(groups="don't execute")
	public  void beforeMethod() {
		System.out.println("start Time");
		long startTime = System.currentTimeMillis();
		System.out.println(startTime);
	}

	@AfterMethod(groups="don't execute")
	public  void afterMethod() {
		System.out.println("Emd Time");
		long EndTime = System.currentTimeMillis();
		System.out.println("Time Taken for Process:" + (EndTime - startTime));

	}

	@BeforeClass(groups="execute this")
	public void beforeClass() {
		System.out.println("browser launch");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://www.flipkart.com/");
	}

	@AfterClass(groups="execute this")
	public void afterClass() {
		System.out.println("browser close");
		// driver.quit();
	}

	@Test(priority=1,groups="execute this")
	public void login() {
		WebElement findElement = driver.findElement(By.xpath("//button[text()='✕']"));
		findElement.click();
	}

	@Test(priority=2,groups="execute this")
	public static void searchfield() throws Exception {
		driver.findElement(By.name("q")).sendKeys("shirt", Keys.ENTER);
		WebElement stock1 = driver.findElement(By.xpath("(//div[@class='_2B099V'])[1]"));
		String text1 = stock1.getText();
		System.out.println("stock text before clicking:" + text1);
		stock1.click();
		// excel read
		File f1 = new File("C:\\Users\\barga\\OneDrive\\Desktop\\Booktask1.xlsx");
		Workbook w1 = new XSSFWorkbook();
		Sheet sheet1 = w1.createSheet("excel1");
		Row row1 = sheet1.createRow(0);
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue(text1);
		FileOutputStream f0 = new FileOutputStream(f1);
		w1.write(f0);
	}

	@Test(retryAnalyzer=Retry.class,priority=3,groups="execute this")
	public static void windowsHandling() throws Exception {
		// windows handling

		Set<String> childwindowHandles = driver.getWindowHandles();
		List<String> parentwindow = new ArrayList<String>();
		parentwindow.addAll(childwindowHandles);
		driver.switchTo().window(parentwindow.get(1));
		WebElement stock2 = driver.findElement(By.xpath("//span[@class='B_NuCI']"));
		String text2 = stock2.getText();
		System.out.println("stock text after clicking:" + text2);

		// excel read
		File f = new File("C:\\Users\\barga\\OneDrive\\Desktop\\Booktask1.xlsx");
		FileInputStream fi = new FileInputStream(f);
		Workbook w2 = new XSSFWorkbook(fi);
		Sheet sheet2 = w2.getSheet("excel1");
		Row row2 = sheet2.createRow(1);
		Cell cell2 = row2.createCell(0);
		cell2.setCellValue(text2);
		FileOutputStream f02 = new FileOutputStream(f);
		w2.write(f02);
	}

	@Test(retryAnalyzer=Retry.class,priority=4,groups="execute this")
	public void excelRead() throws Exception {
	    // excel read
		File f2 = new File("C:\\Users\\barga\\OneDrive\\Desktop\\Booktask1.xlsx");
		FileInputStream fii = new FileInputStream(f2);
		Workbook w3 = new XSSFWorkbook(fii);
		Sheet sheet3 = w3.getSheet("excel1");
		Row row3 = sheet3.getRow(0);
		Cell cell3 = row3.createCell(0);
		System.out.println("text1:"+cell3);
		Row row4 = sheet3.getRow(1);
		Cell cell4 = row4.getCell(0);
		System.out.println(cell4);
		if(cell3.equals(cell4)) {
			System.out.println("text is pass");
		}else {
			System.out.println("text is fail");
		}
		
	}
	static int counter=0;
	@Test(priority=5,invocationCount=2,groups="execute this")
	public void screenShot() throws Exception {
		++counter;
		TakesScreenshot ts=(TakesScreenshot)driver;
		
		File screenshotAs = ts.getScreenshotAs(OutputType.FILE);
		File target= new File(".//target//report123"+counter+".png");
		FileUtils.copyFile(screenshotAs, target);
	}
	
	@BeforeTest(groups="execute this")
	public void beforeTest() {
		System.out.println("before test process");

	}

	@AfterTest(groups="execute this")
	public void afterTest() {
		System.out.println("after test process");
	
	}
}



