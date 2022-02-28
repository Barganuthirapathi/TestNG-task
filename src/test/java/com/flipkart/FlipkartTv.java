package com.flipkart;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Driver;
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
import org.openqa.selenium.interactions.SendKeysAction;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FlipkartTv {
	static long startTime;
	static WebDriver driver;

	@BeforeClass
	public void browserlaunch() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://www.flipkart.com/");
	}

	@AfterClass
	public static void browserquit() {
		System.out.println("quit browser");
		// driver.close();
	}

	@BeforeMethod
	public static void beforeMethod() {
		System.out.println("beforemethod");
		long startTime = System.currentTimeMillis();

	}

	@AfterMethod
	public void afterMethod() {
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken for process:" + (endTime - startTime));

	}

	@Test(priority=1)
	public void loginpage() {
		// login page
		System.out.println(" method1 ");
		try {
		WebElement crossicon = driver.findElement(By.xpath("//button[text()='✕']"));
		Assert.assertTrue(crossicon.isDisplayed());
		crossicon.click();}
		catch (Exception e) {
			System.out.println("cross symbol not visible");
		}
	
	}

	@Test(priority=2)
	public void stockValidation1() throws Exception {
		System.out.println("method2");
		WebElement searchfield = driver.findElement(By.xpath("//input[@type='text']"));
		searchfield.sendKeys("television",Keys.ENTER);
		WebElement stock1 = driver.findElement(By.xpath("(//div[@class='_4rR01T'])[2]"));
		String text1 = stock1.getText();
		System.out.println("first output text:"+text1);
		stock1.click();
	   //excel write data
		File f=new File("C:\\Users\\barga\\OneDrive\\Desktop\\testng.xlsx");
		Workbook w=new XSSFWorkbook();
		Sheet sheet = w.createSheet("sample");
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(text1);
		FileOutputStream f1=new FileOutputStream(f);
		w.write(f1);
	}

	@Test(priority=3)
	public void windowhandling() throws Exception {
		System.out.println("method3");
		 //windows handling
		 Set<String> childtab = driver.getWindowHandles();
		 List<String> parentTab=new ArrayList<String>();
		 parentTab.addAll(childtab);
		 WebDriver window = driver.switchTo().window(parentTab.get(1));
		 //
		 WebElement stock2 = driver.findElement(By.xpath("//span[@class='B_NuCI']"));
		 String text2 = stock2.getText();
		 System.out.println("second output text:"+text2);
		  //excel data
		 File f=new File("C:\\Users\\barga\\OneDrive\\Desktop\\testng.xlsx");
		 FileInputStream f3=new FileInputStream(f);
		 Workbook w1=new XSSFWorkbook(f3);
		 Sheet sheet1 = w1.getSheet("sample");
		 Row row1 = sheet1.createRow(1);
		 Cell cell2 = row1.createCell(0);
		 cell2.setCellValue(text2);
		 FileOutputStream f4=new FileOutputStream(f);
		 w1.write(f4);  
		 
	}

	@Test(priority=4)
	public void excelread() throws Exception {
		System.out.println("method4");
		File f5=new File("C:\\Users\\barga\\OneDrive\\Desktop\\testng.xlsx");
		FileInputStream f6=new FileInputStream(f5);
		Workbook w2=new XSSFWorkbook(f6);
		Sheet sheet2 = w2.getSheet("sample");
		Row row2 = sheet2.getRow(0);
		Cell cell2 = row2.getCell(0);
		System.out.println("first output:"+cell2);
		Row row3 = sheet2.getRow(1);
		Cell cell3 = row3.getCell(0);
		System.out.println("first output:"+cell3);
		
		//Assert.assertEquals(cell2, cell3);
		if (cell2.equals(cell3)) {
			System.out.println("result is pass");
		}else {
			System.out.println("result is fail");
		}
		
	}
     static int counter=0;
	@Test(priority=5,invocationCount = 3)
	public void screenshot() throws Exception {
		System.out.println("method5");
		++counter;
		TakesScreenshot ts=(TakesScreenshot)driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		File target=new File("C:\\Users\\barga\\eclipse-workspace\\TestNG-Flipkart\\target\\flipkart"+counter+".png");
        FileUtils.copyFile(source, target);
        
        
	}

}
