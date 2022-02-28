package com.flipkart;

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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Flipkart2 {
	static long startTime;
    static WebDriver driver;
  
    @BeforeClass
	public  static void browserlaunch() {
		System.out.println("launch method from before class");
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://www.flipkart.com/");
		
	}
	@AfterClass
	public  static void browserquit() {
		System.out.println("quit the browser");
		//driver.close();
		//driver.quit();
	}
	@BeforeMethod
	public void beforeMethod() {
		System.out.println("before method");
		long startTime = System.currentTimeMillis();
		
	}
	@AfterMethod
	public void afterMethod() {
		System.out.println("after method");
		long endTime = System.currentTimeMillis();
		System.out.println("Time Taked for process:"+(endTime-startTime));
		
	}
	@Test(priority=1)
	public void login() {
		//login page
		System.out.println(" method1 ");
		WebElement crossicon = driver.findElement(By.xpath("//button[text()='✕']"));
		crossicon.click();
	}
	@Test(priority=2)
	public void Validation1() throws Exception {
		System.out.println(" method2 ");
		WebElement seachfield = driver.findElement(By.xpath("//input[@type='text']"));
		seachfield.sendKeys("redmi",Keys.ENTER);
		WebElement mobilename = driver.findElement(By.xpath("(//div[@class='_4rR01T'])[1]"));
		String text = mobilename.getText();
		System.out.println("expected Mobilepage1:"+text);
		//write data from excel
		File f=new File("C:\\Users\\barga\\OneDrive\\Desktop\\Book11.xlsx");
		Workbook w1=new XSSFWorkbook();
		Sheet s1 = w1.createSheet("redmi");
		Row r1 = s1.createRow(0);
		Cell c1 = r1.createCell(0);
		c1.setCellValue(text);
		FileOutputStream f1= new FileOutputStream(f);
		w1.write(f1);
	
	}
	@Test(priority=3)
	public void Validation2() throws Exception {
		System.out.println(" method3 ");
		WebElement stock1 = driver.findElement(By.xpath("(//div[@class='_3pLy-c row'])[1]"));
		stock1.click();
		String text1 = stock1.getText();
		System.out.println("expected Mobilepage2:"+text1);
		//windows handling
		Set<String> childwindows = driver.getWindowHandles();
		List<String> parentwindow=new ArrayList<String>();
		parentwindow.addAll(childwindows);
		WebDriver window = driver.switchTo().window(parentwindow.get(1));
		//excel data 
		File f=new File("C:\\Users\\barga\\OneDrive\\Desktop\\Book11.xlsx");
		FileInputStream f3=new FileInputStream(f);
		Workbook w2=new XSSFWorkbook(f3);
		Sheet sheet = w2.getSheet("redmi");
		Row row = sheet.createRow(1);	
		Cell cell = row.createCell(0);
		cell.setCellValue(text1);
		FileOutputStream f1= new FileOutputStream(f);
		w2.write(f1);
		
	}
	@Test(priority=4)
	public void Excelread() throws Exception {
		System.out.println(" method4 ");
		//excel read data for two rows
		File f5=new File("C:\\Users\\barga\\OneDrive\\Desktop\\Book11.xlsx");
		FileInputStream f6=new FileInputStream(f5);
		Workbook wr=new XSSFWorkbook(f6);
		Sheet sheet = wr.getSheet("redmi");
		Row row = sheet.getRow(0);
		Cell cell4 = row.getCell(0);
		System.out.println("row1 mobile name:"+cell4);
		Cell cell2 = sheet.getRow(1).getCell(0);
		System.out.println("row2 mobile name:"+cell2);
		if(cell4.equals(cell2)) {
			System.out.println("Result is pass");
		}else {
			System.out.println("Result is fail");
		}
	} 
	@Test(priority=5)
	public void Screenshot() throws Exception  {
		System.out.println(" method5 ");
		TakesScreenshot ts=(TakesScreenshot)driver;
		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl);
		File source=ts.getScreenshotAs(OutputType.FILE);
	//	File target =new File("C:\\Users\\barga\\eclipse-workspace\\Junit-project\\target\\flipkart.png");
		File target =new File(".//target//report.png");
	    FileUtils.copyFile(source, target);
	    
	}
	

}
