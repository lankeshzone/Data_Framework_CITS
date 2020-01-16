package DB_Framework.Framework;

import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ClassOne {
	
	 public WebDriver driver;
	 public ExtentHtmlReporter htmlReporter;
	 public ExtentReports extent;
	 public ExtentTest logger;
	 
	 @BeforeTest
	 public void startReport() 
	 {
		 htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/CITS_Report.html");
		        	
		 // Create an object of Extent Reports
		 extent = new ExtentReports();  
		 extent.attachReporter(htmlReporter);
		 extent.setSystemInfo("Host Name", "Century IT Services");
		        	extent.setSystemInfo("Environment", "Testing");
		 extent.setSystemInfo("User Name", "Lankesh H");
		 htmlReporter.config().setDocumentTitle("Database Framework"); 
		                // Name of the report
		 htmlReporter.config().setReportName("Database Framework - Master Test"); 
		                // Dark Theme
		 htmlReporter.config().setTheme(Theme.STANDARD); 
	 }
	 
	 //This method is to capture the screenshot and return the path of the screenshot.
	 public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException 
	 {
		 String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		 TakesScreenshot ts = (TakesScreenshot) driver;
		 File source = ts.getScreenshotAs(OutputType.FILE);
		 // after execution, you could see a folder "FailedTestsScreenshots" under src folder
		 String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		 File finalDestination = new File(destination);
		 FileUtils.copyFile(source, finalDestination);
		 return destination;
	 }
	 
	 @BeforeMethod
	 public void setup()
	 {
		
	 }
	 
	 @Test
	 public void TC_01_DBRecords_Compare() throws SQLException
	 {
		logger = extent.createTest("To Verify DataBase Records");
		ResultSet resultSet1 = null;
		ResultSet resultSet2 = null;
        while (resultSet1.next())
        {
        	ArrayList<ResultSet> a=new ArrayList<ResultSet>();
            resultSet2.next();
            ResultSetMetaData resultSetMetaData = resultSet1.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= count; i++)
            {
                if (!resultSet1.getObject(i).equals(resultSet2.getObject(i))) 
                {
                   //write to file here 
                	writeListToExcel(a);
                	
                }
            }
        }
       

	 }
	 
	 @Test
	 public void TC_02_CompareTwoExcel_Multiplecolumn()
	 {
		 	logger = extent.createTest("To Verify Two Excel Files");
	        try 
	        {

	            ArrayList arr1 = new ArrayList();
	            ArrayList arr2 = new ArrayList();
	            ArrayList arr3 = new ArrayList();

	            FileInputStream file1 = new FileInputStream(new File("D:\\DB_Framework\\Excel\\ExcelOne.xlsx"));

	            FileInputStream file2 = new FileInputStream(new File("D:\\DB_Framework\\Excel\\ExcelTwo.xlsx"));

	            // Get the workbook instance for XLS file
	            XSSFWorkbook workbook1 = new XSSFWorkbook(file1);
	            XSSFWorkbook workbook2 = new XSSFWorkbook(file2);

	            // Get first sheet from the workbook
	            XSSFSheet sheet1 = workbook1.getSheetAt(0);
	            XSSFSheet sheet2 = workbook2.getSheetAt(0);
	            
	             // Compare sheets

	            // Get iterator to all the rows in current sheet1
	            Iterator<Row> rowIterator1 = sheet1.iterator();
	            Iterator<Row> rowIterator2 = sheet2.iterator();

	            while (rowIterator1.hasNext()) {
	                Row row = rowIterator1.next();
	                // For each row, iterate through all the columns
	                Iterator<Cell> cellIterator = row.cellIterator();
	                
	                while (cellIterator.hasNext()) {

	                    Cell cell = cellIterator.next();

	                             switch (cell.getCellType()) 
	                        {
		                        case NUMERIC:
		                            System.out.print(cell.getNumericCellValue());
		                            arr1.add(cell.getNumericCellValue());
		                            break;
		                        case STRING:
		                            arr1.add(cell.getStringCellValue());
		                            System.out.print(cell.getStringCellValue());
		                            break;
		                        case BOOLEAN:
		                            arr1.add(cell.getBooleanCellValue());
		                            System.out.print(cell.getBooleanCellValue());
		                            break;
	                        }

	              

	                }

	                System.out.println(" ");
	            }

	            file1.close();

	            System.out.println("-----------------------------------");
	            // For retrive the second excel data
	            while (rowIterator2.hasNext()) {
	                Row row1 = rowIterator2.next();
	                // For each row, iterate through all the columns
	                Iterator<Cell> cellIterator1 = row1.cellIterator();

	                while (cellIterator1.hasNext()) {

	                    Cell cell1 = cellIterator1.next();
	                        switch (cell1.getCellType()) 
	                      {
		                        case NUMERIC:
		                            arr2.add(cell1.getNumericCellValue());
		                            System.out.print(cell1.getNumericCellValue());
		                            break;
		                        case STRING:
		                            arr2.add(cell1.getStringCellValue());
		                            System.out.print(cell1.getStringCellValue());
		                            break;
		                        case BOOLEAN:
		                            arr2.add(cell1.getBooleanCellValue());
		                            System.out.print(cell1.getBooleanCellValue());
		                            break;
	                        }

	                }

	                System.out.println("");
	            }

	             // compare two arrays
	            for (Object value : arr1) 
	            {
	                if (!arr2.contains(value))
	                {
	                    arr3.add(value);
	                }
	            }
	            
	            writeListToExcel(arr3);

	            // closing the files
	            file1.close();
	            file2.close();

	        } 
	        catch (FileNotFoundException e) 
		    {
		        e.printStackTrace();
		    }
	        catch (IOException e) 
		    {
	            e.printStackTrace();
	        }

	    }

	 
	 @Test
	 public void TC_03_CompareTwoExcel_SingleColumn()
	 {
		 logger = extent.createTest("To Verify first column on two excel files");
        try 
        {

            ArrayList arr1 = new ArrayList();
            ArrayList arr2 = new ArrayList();
            ArrayList arr3 = new ArrayList();

            FileInputStream file1 = new FileInputStream(new File("D:\\DB_Framework\\Excel\\ExcelOne.xlsx"));

            FileInputStream file2 = new FileInputStream(new File("D:\\DB_Framework\\Excel\\ExcelTwo.xlsx"));

            // Get the workbook instance for XLS file
            XSSFWorkbook workbook1 = new XSSFWorkbook(file1);
            XSSFWorkbook workbook2 = new XSSFWorkbook(file2);

            // Get first sheet from the workbook
            XSSFSheet sheet1 = workbook1.getSheetAt(0);
            XSSFSheet sheet2 = workbook2.getSheetAt(0);

            // Compare sheets

            // Get iterator to all the rows in current sheet1
            Iterator<Row> rowIterator1 = sheet1.iterator();
            Iterator<Row> rowIterator2 = sheet2.iterator();

            while (rowIterator1.hasNext()) {
                Row row = rowIterator1.next();
                // For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    // This is for read only one column from excel
                    if (cell.getColumnIndex() == 0) {
                    	
                    	// Check the cell type and format accordingly
                        switch (cell.getCellType()) 
                        {
	                        case NUMERIC:
	                            System.out.print(cell.getNumericCellValue());
	                            arr1.add(cell.getNumericCellValue());
	                            break;
	                        case STRING:
	                            arr1.add(cell.getStringCellValue());
	                            System.out.print(cell.getStringCellValue());
	                            break;
	                        case BOOLEAN:
	                            arr1.add(cell.getBooleanCellValue());
	                            System.out.print(cell.getBooleanCellValue());
	                            break;
                        }

                    }

                }

                System.out.println(" ");
            }

            file1.close();

            System.out.println("-----------------------------------");
            // For retrive the second excel data
            while (rowIterator2.hasNext()) {
                Row row1 = rowIterator2.next();
                // For each row, iterate through all the columns
                Iterator<Cell> cellIterator1 = row1.cellIterator();

                while (cellIterator1.hasNext()) {

                    Cell cell1 = cellIterator1.next();
                    // Check the cell type and format accordingly


                    // This is for read only one column from excel
                    if (cell1.getColumnIndex() == 0) 
                    {
                      switch (cell1.getCellType()) 
                      {
	                        case NUMERIC:
	                            arr2.add(cell1.getNumericCellValue());
	                            System.out.print(cell1.getNumericCellValue());
	                            break;
	                        case STRING:
	                            arr2.add(cell1.getStringCellValue());
	                            System.out.print(cell1.getStringCellValue());
	                            break;
	                        case BOOLEAN:
	                            arr2.add(cell1.getBooleanCellValue());
	                            System.out.print(cell1.getBooleanCellValue());
	                            break;
                        }

                    }
                    // this continue is for
                    // continue;
                }

                System.out.println("");
            }

             // compare two arrays
            for (Object value : arr1) {
                if (!arr2.contains(value))
                {
                    arr3.add(value);
                }
            }
            writeListToExcel(arr3);

            // closing the files
            file1.close();
            file2.close();

        } 
        catch (FileNotFoundException e) 
	    {
	        e.printStackTrace();
	    }
        catch (IOException e) 
	    {
            e.printStackTrace();
        }

	}

	    // write into new file excel

    private static void writeListToExcel(ArrayList arr3) 
    {
    	
        FileOutputStream fos = null;
        try 
        {
            fos = new FileOutputStream("D:\\DB_Framework\\Excel\\Result.xlsx");

            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet spreadSheet = workBook.createSheet("email");
            XSSFRow row;
            XSSFCell cell;
            // System.out.println("array size is :: "+minusArray.size());
            int cellnumber = 0;
            for (int i1 = 0; i1 < arr3.size(); i1++) 
            {
                row = spreadSheet.createRow(i1);
                cell = row.createCell(cellnumber);
                // System.out.print(cell.getCellStyle());
                cell.setCellValue(arr3.get(i1).toString().trim());
            }
            workBook.write(fos);
        }
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

	 
	 @Test
	 public void TC_04_CompareText() throws FileNotFoundException
	 {
		 logger = extent.createTest("To Verify two Text Files");
	        String valSource;
	        String valDest;

	        File file1 = new File("D:\\DB_Framework\\Text\\TextOne.txt");
	        Scanner source = new Scanner(file1); 

	        File file2 = new File("D:\\DB_Framework\\Text\\TextTwo.txt");
	        Scanner dest = new Scanner(file2);  

	        while(source.hasNextLine())
	        {
	                   
	            while(dest.hasNextLine())
	            {
	            	valSource=source.next();
	                 
	                valDest=dest.next();
	               
	                if(valDest.equals(valSource)) 
	                	System.out.println(valDest);
	                else
	                {	System.out.println("else: " + valDest);
	                
		                try
		                {
		                    FileWriter writer=new FileWriter("D:\\DB_Framework\\Text\\Result.txt");
		                    writer.append(valSource + " " + valDest);
		                    writer.flush();
		                    writer.close();
		                }
		                catch(IOException e)
		                {
		                    e.printStackTrace();
		                }
	                }	
	            }
	        }
		
	 }
	 
	 @Test
	 public void TC_05_Compare_CSV() throws IOException
	 {
		logger = extent.createTest("To Verify Two CSV Files");
		String path="D:\\DB_Framework\\CSV\\";
	    String file1="File1.csv";
	    String file2="File2.csv";
	    String file3="Result.csv";
	    ArrayList al1=new ArrayList();
	    ArrayList al2=new ArrayList();
	    //ArrayList al3=new ArrayList();

	    BufferedReader CSVFile1 = new BufferedReader(new FileReader(path+file1));
	    String dataRow1 = CSVFile1.readLine();
	    while (dataRow1 != null)
	    {
	        String[] dataArray1 = dataRow1.split(",");
	        for (String item1:dataArray1)
	        { 
	           al1.add(item1);
	        }

	        dataRow1 = CSVFile1.readLine(); // Read next line of data.
	    }

	     CSVFile1.close();

	    BufferedReader CSVFile2 = new BufferedReader(new FileReader(path+file2));
	    String dataRow2 = CSVFile2.readLine();
	    while (dataRow2 != null)
	    {
	        String[] dataArray2 = dataRow2.split(",");
	        for (String item2:dataArray2)
	        { 
	           al2.add(item2);

	        }
	        dataRow2 = CSVFile2.readLine(); // Read next line of data.
	    }
	     CSVFile2.close();

	    for(Object bs:al2)
	    {
	        al1.remove(bs);
	    }
	
	    int size=al1.size();

	    try
        {
            FileWriter writer=new FileWriter(path+file3);
            while(size!=0)
            {
                size--;
                writer.append(""+al1.get(size));
                writer.append('\n');
            }
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
		
	 }
	 
	 @AfterMethod
	 public void getResult(ITestResult result) throws Exception
	 {
		 if(result.getStatus() == ITestResult.FAILURE)
		 {
			 //MarkupHelper is used to display the output in different colors
			 logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			 logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
		 }
		 else if(result.getStatus() == ITestResult.SKIP)
	 	 {
		 	logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE)); 
	 	 } 
		 else if(result.getStatus() == ITestResult.SUCCESS)
		 {
			 logger.log(Status.PASS, "Test Passed");
			 //logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
		 }
	 }
	 
	 @AfterTest
	 public void endReport() 
	 {
		 extent.flush();
	 }
}