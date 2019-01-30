package com.qa.ExcelTester;

import java.io.*;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Parameterized.class)
public class ParamTestExcell {

	public WebDriver driver;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Desktop\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Parameters
	public static Collection<Object[]> data() throws IOException {
		FileInputStream file = new FileInputStream("C:\\Users\\Admin\\Downloads\\DemoSiteDDT.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		Object[][] ob = new Object[sheet.getPhysicalNumberOfRows()-1][4];
		
		for (int rowNum = 1; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
			ob[rowNum-1][0] = sheet.getRow(rowNum).getCell(0).getStringCellValue();
			ob[rowNum-1][1] = sheet.getRow(rowNum).getCell(1).getStringCellValue();
			ob[rowNum-1][2] = sheet.getRow(rowNum).getCell(2).getStringCellValue();
			ob[rowNum-1][3] = rowNum;
		}
		file.close();
	return Arrays.asList(ob);
	}
	
	private String username;
	private String password;
	private String expected;
	private int row;
	
	public ParamTestExcell(String username, String password, String expected, int row) {
		this.username = username;
		this.password = password;
		this.expected = expected;
		this.row = row;
	}
	
	@Test
	public void testy() throws Exception {
		
		System.out.println(username+ "  " + password + "  " + expected +"  " + row);
		
		FileInputStream file = new FileInputStream("C:\\Users\\Admin\\Downloads\\DemoSiteDDT.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		driver.manage().window().maximize();
		driver.get("http://thedemosite.co.uk/addauser.php");
		
		driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[1]/td[2]/p/input")).sendKeys(username);
		driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[2]/td[2]/p/input")).sendKeys(password);
		
		driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[3]/td[2]/p/input")).click();
		driver.findElement(By.xpath("/html/body/div/center/table/tbody/tr[2]/td/div/center/table/tbody/tr/td[2]/p/small/a[4]")).click();
		
		driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[1]/td[2]/p/input")).sendKeys(username);
		driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/p/input")).sendKeys(password);
		
		driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[3]/td[2]/p/input")).click();
		
		WebElement status = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/big/blockquote/blockquote/font/center/b"));
		
		System.out.println(" actual: " + status.getText() + " expected: " + expected);
		
		XSSFRow rowNum = sheet.getRow(row);
		XSSFCell cellActual = rowNum.getCell(3);
		XSSFCell cellResult = rowNum.getCell(4);
		if(cellActual == null) {
			cellActual = rowNum.createCell(3);
		}
		if(cellResult == null) {
			cellResult = rowNum.createCell(4);
		}
		
		cellActual.setCellValue(status.getText());
		
		if(status.getText().equals(expected)) {
			
		
		cellResult.setCellValue("Pass");
		}
		else {
			cellResult.setCellValue("Fail");
		}
		
		FileOutputStream fileout = new FileOutputStream("C:\\Users\\Admin\\Downloads\\DemoSiteDDT.xlsx");
		
		workbook.write(fileout);
		fileout.flush();
		fileout.close();
		file.close();
		
	}
	
}
