package org.srn.web.Recruiter.component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.entity.Bench;

public class ExcelHelper {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }

	@SuppressWarnings("deprecation")
	public static List<Bench> excelToBenchs(InputStream is) {
	    	      
	      List<Bench> benchSets = new ArrayList<Bench>();
	      
	          
	      try
			{
				XSSFWorkbook workbook = new XSSFWorkbook(is);
		        XSSFSheet sheet = workbook.getSheet("Benchs");
		        
		        XSSFRow row  =null;
		        XSSFCell cell = null;
		        DataFormatter df = new DataFormatter();
		        int totalrow = sheet.getLastRowNum();
		        System.out.println("Total Row " + totalrow);
		        for(int r = 1 ; r < totalrow ; r++)
		        {
		        	
		        	Bench hello  = new Bench();
		        	
	                 row = sheet.getRow(r);
		        	
		        	cell = row.getCell(0);
		        	hello.setBench_id((long) cell.getNumericCellValue());
		        	cell = row.getCell(1);
		        	hello.setPartner_id((long) cell.getNumericCellValue());
		        	cell = row.getCell(2);
		        	hello.setBench_type(df.formatCellValue(cell));
		        	cell = row.getCell(3);
		        	hello.setName(df.formatCellValue(cell));
		        	cell = row.getCell(4);
		         	hello.setContact(df.formatCellValue(cell));
		        	cell = row.getCell(5);
		        	hello.setAlternate_contact(df.formatCellValue(cell));
		        	cell = row.getCell(6);
		        	hello.setEmail(df.formatCellValue(cell));
		        	cell = row.getCell(7);
		        	hello.setAlternate_email(df.formatCellValue(cell));
		        	cell = row.getCell(8);
		        	hello.setExp((double)cell.getNumericCellValue());
		        	cell = row.getCell(9);
		        	hello.setDomain(df.formatCellValue(cell));
		        	cell = row.getCell(10);
		        	hello.setPrimary_skill(df.formatCellValue(cell));
		        	cell = row.getCell(11);
		        	hello.setSecondary_skill(df.formatCellValue(cell));
		        	cell = row.getCell(12);
		        	hello.setBudget((double)cell.getNumericCellValue());
		        	cell = row.getCell(13);
		        	hello.setSalary((double)cell.getNumericCellValue());
		        	cell = row.getCell(14);
		        	hello.setOrg_name(df.formatCellValue(cell));
		        	cell = row.getCell(15);
		        	hello.setOrg_url(df.formatCellValue(cell));
		        	cell = row.getCell(16);
		        	hello.setCurrent_role(df.formatCellValue(cell));
		        	cell = row.getCell(17);
		        	hello.setQualification(df.formatCellValue(cell));
		        	cell = row.getCell(18);
		        	hello.setResume(df.formatCellValue(cell));
		        	cell = row.getCell(19);
		        	hello.setResume(df.formatCellValue(cell));
		        	cell = row.getCell(20);
		        	//hello.setBench_start_dt(df.formatCellValue(cell));
		        	//cell = row.getCell(21);
		        	//hello.setBench_end_dt(df.formatCellValue(cell));
		        	cell = row.getCell(22);
		        	hello.setBench_status(df.formatCellValue(cell));
		        	//cell = row.getCell(23);
		        	//hello.setDt(df.formatCellValue(cell));
		        	cell = row.getCell(24);
		        	hello.setStatus((int)cell.getNumericCellValue());
		        	benchSets.add(hello);
		        }
		        is.close();
		        workbook.close();
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return benchSets;  			
			
		}
		
	      
	}
	


		   


