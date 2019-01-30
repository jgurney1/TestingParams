package com.qa.ExcelTester;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestRunner {

	public WebDriver driver = null;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Desktop\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void loginTest() throws Exception {
		FileInputStream file = new FileInputStream("C:\\Users\\Admin\\Downloads\\DemoSiteDDT.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheetAt(0);
		
		
		
		for (int rowNum = 1; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
			List<String> entries = new ArrayList<String>();
			for (int colNum = 0; colNum < sheet.getRow(rowNum).getPhysicalNumberOfCells(); colNum++) {
				XSSFCell cell = sheet.getRow(rowNum).getCell(colNum);
				String userCell = cell.getStringCellValue();
				//System.out.println(userCell);
				entries.add(userCell);
			}
			System.out.println(entries);
			driver.manage().window().maximize();
			driver.get("http://thedemosite.co.uk/addauser.php");
			
			driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[1]/td[2]/p/input")).sendKeys(entries.get(0));
			driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[2]/td[2]/p/input")).sendKeys(entries.get(1));
			
			driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[3]/td[2]/p/input")).click();
			driver.findElement(By.xpath("/html/body/div/center/table/tbody/tr[2]/td/div/center/table/tbody/tr/td[2]/p/small/a[4]")).click();
			
			driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[1]/td[2]/p/input")).sendKeys(entries.get(0));
			driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/p/input")).sendKeys(entries.get(1));
			
			driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[3]/td[2]/p/input")).click();
			
			WebElement status = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/big/blockquote/blockquote/font/center/b"));
			
			System.out.println(" actual: " + status.getText() + " expected: " + entries.get(2));
			
			XSSFRow row = sheet.getRow(rowNum);
			XSSFCell cellActual = row.getCell(3);
			XSSFCell cellResult = row.getCell(4);
			if(cellActual == null) {
				cellActual = row.createCell(3);
			}
			if(cellResult == null) {
				cellResult = row.createCell(4);
			}
			
			cellActual.setCellValue(status.getText());
			
			if(status.getText().equals(entries.get(2))) {
				
			
			cellResult.setCellValue("Pass");
			}
			else {
				cellResult.setCellValue("Fail");
			}
		}
		
		FileOutputStream fileout = new FileOutputStream("C:\\Users\\Admin\\Downloads\\DemoSiteDDT.xlsx");
		
		wb.write(fileout);
		fileout.flush();
		fileout.close();
		file.close();
	}

}
