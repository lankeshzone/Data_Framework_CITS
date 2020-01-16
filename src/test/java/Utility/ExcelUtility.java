package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	
	public static XSSFWorkbook WB;
	public static XSSFSheet WS;
	public static XSSFRow row;
	public static XSSFCell cell;

	public void setExcelFile(String filePath, String workSheet) throws IOException
	{
		//opening the application(Excel)
		File file=new File(filePath);
		
		//Opening the file (Test1.xlsx)
		FileInputStream fis = new FileInputStream(file);
		WB = new XSSFWorkbook(fis);
	 
		// Setting the worksheet
		WS = WB.getSheet(workSheet);
	}
	
	
	public String getCellValue(int row, int col)
	{	
		String cellData="https://www.facebook.com";
		
		
		//reading the content by giving row and column numbers
		cellData=WS.getRow(row).getCell(col).getStringCellValue();
	
		return cellData;
		
	}
	
	 public static void setCellData(String Result,  int RowNum, int ColNum) throws Exception
	 {
		 try
		 {
			 WS.getRow(RowNum).createCell(ColNum).setCellValue(Result);
			 FileOutputStream fos = new FileOutputStream("D://Test/writecell.xlsx");
			 WB.write(fos);
			 fos.close();
		 }
		 catch(Exception e)
		 {
			 System.out.println("Exception occurred could not write data " + e.getMessage());	
		 }
	 }

}
