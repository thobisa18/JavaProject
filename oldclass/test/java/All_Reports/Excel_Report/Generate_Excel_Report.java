package All_Reports.Excel_Report;

import java.text.DecimalFormat;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import  Config.*;
public class Generate_Excel_Report
{
  int rowResults=0;
  String Path_Test_Results="";
  private static DecimalFormat df2 = new DecimalFormat("#.##");
  public void Generate_Report( Map<String, Object[]> data,String which_Regression,String re)
  {


    constants cosn=constants.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy-HH_mm_ss");
    Date date = new Date();
    String build=null;
    int HeaderStartCell=1;//Default header start cell
    Generate_Excel_Report test=new Generate_Excel_Report();
    //Blank workbook
    XSSFWorkbook workbook = new XSSFWorkbook();
    //Create a blank sheet
    XSSFSheet sheet = workbook.createSheet("Test Results");
    //Create a rage and merge cells
    CellRangeAddress region;
    if(which_Regression.equals("intent")) {
      HeaderStartCell=4;
      region = new CellRangeAddress(0, 5, 4, 9);
    }
    else if(which_Regression.equals("intent_Smart_Routing")) {
      HeaderStartCell=2;
      region = new CellRangeAddress(0, 5, 2, 6);
    }
    else if(which_Regression.equals("intent_Exception")) {
      HeaderStartCell=2;
      region = new CellRangeAddress(0, 5, 2, 6);
    }
    else
      region = new CellRangeAddress(0, 5, 1, 2);

    RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
    RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
    RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
    sheet.addMergedRegion(region);

    //Report header
    Row row=sheet.createRow(0);

    Cell cell = row.createCell(HeaderStartCell);
    cell.setCellStyle(test.setReportHeader(workbook));
    build=cosn.GetEnvironment();


    //Provide intent report
    if(which_Regression.equals("intent")) {
      cell.setCellValue(cosn.Intent_Report_Header()+re);
      rowResults=8;// total and average summary
    }
    else if (which_Regression.equals("intent_Smart_Routing"))
    {
      cell.setCellValue(cosn.Intent_Report_Header()+re);
      rowResults=5;// total and average summary
    }
    else if(which_Regression.equals("intent_Exception")) {
      cell.setCellValue(cosn.Intent_Report_Header()+re);
      rowResults=6;// total and average summary
    }
    else if(which_Regression.equals("debit")) {
      cell.setCellValue(cosn.Debit_Order_Report_Header()+re+" \n click for more details" );

      rowResults=1;// total and average summary
      final Hyperlink href = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
      href.setAddress(constants.getExtentReportPath()[1]);
      cell.setHyperlink(href);
    }


    //Count number of failed and paases scenarios
    int failedSc=0;
    int passedSc=0;
    Set<String> keyset = data.keySet();
    int rownum = 6;
    // add a blank row
    row=sheet.createRow(rownum++);
    cell = row.createCell(1);
    for (String key : keyset)
    {

      row = sheet.createRow(rownum++);


      Object [] objArr = data.get(key);
      int cellnum = 0;
      for (Object obj : objArr)
      {
        cell = row.createCell(cellnum++);
        if(obj instanceof String)
        {
          cell.setCellStyle(test.setFontColor(workbook,(String)obj));
          cell.setCellValue((String) obj);

          if(rownum==8)// Row four is column header, it must be bold
          {
            cell.setCellStyle(test.setStyleHeader(workbook));
          }
        }
        else if(obj instanceof Integer)
        {
          cell.setCellValue((Integer)obj);
        }


        if ((String)obj=="Passed")//calculate passed test scenarios
          passedSc++;
        else if ((String)obj=="Failed")//calculate failed test scenarios
          failedSc++;
      }
    }
    // add a blank row
    row=sheet.createRow(rownum++);
    cell = row.createCell(1);

    //Add total of the passed scenarios
    row=sheet.createRow(rownum++);
    cell = row.createCell(rowResults);
    cell.setCellStyle(test.setStyleHeader(workbook));
    cell.setCellStyle(test.setTextToRight(workbook));
    cell.setCellValue("Total Passed");
    cell = row.createCell(rowResults+1);
    cell.setCellStyle(test.setStyleHeader(workbook));
    cell.setCellStyle(test.setFontColor(workbook,"Green"));
    cell.setCellValue(df2.format( passedSc));


    //Add total of the passed scenarios
    row=sheet.createRow(rownum++);
    cell = row.createCell(rowResults);
    cell.setCellStyle(test.setStyleHeader(workbook));
    cell.setCellStyle(test.setTextToRight(workbook));
    cell.setCellValue("Total Failed");
    cell = row.createCell(rowResults+1);
    cell.setCellStyle(test.setStyleHeader(workbook));
    cell.setCellStyle(test.setFontColor(workbook,"Red"));

    cell.setCellValue(df2.format( failedSc));


    double ave=0.0, ave1=0.0;
    //Add total of the passed scenarios
    row=sheet.createRow(rownum++);
    cell = row.createCell(rowResults);
    cell.setCellStyle(test.setStyleHeader(workbook));
    cell.setCellStyle(test.setTextToRight(workbook));
    cell.setCellValue("Average pass rate %");
    cell = row.createCell(rowResults+1);
    cell.setCellStyle(test.setStyleHeader(workbook));
    cell.setCellStyle(test.setFontColor(workbook,"Green"));
    ave=(double)passedSc/(double)(data.size()-1);
    cell.setCellValue(df2.format(ave *100));


    //Add total of the passed scenarios
    row=sheet.createRow(rownum++);
    cell = row.createCell(rowResults);
    cell.setCellStyle(test.setStyleHeader(workbook));
    cell.setCellStyle(test.setTextToRight(workbook));
    cell.setCellValue("Average failure rate %");
    cell = row.createCell(rowResults+1);
    cell.setCellStyle(test.setStyleHeader(workbook));
    cell.setCellStyle(test.setFontColor(workbook,"Red"));

    ave=(double)failedSc/(double)(data.size()-1);
    cell.setCellValue(df2.format(ave*100) );
    //Auto size all columns
    int number_Of_Columns=sheet.getRow(7).getPhysicalNumberOfCells();
    for (int i=0;i<number_Of_Columns;i++)
    {
      sheet.autoSizeColumn(i);
    }


    try
    {
      //Write the workbook in file system
      FileOutputStream out=null;
      if(which_Regression.contains("intent")) {
        Path_Test_Results = cosn.IntentReportPath() + formatter.format(date) + ".xlsx";
        out = new FileOutputStream(new File(Path_Test_Results));
      }
      else if(which_Regression.equals("debit")) {
        Path_Test_Results =cosn.DebitOrderReportPath() + formatter.format(date) + ".xlsx";
        out = new FileOutputStream(new File(Path_Test_Results));
      }

      workbook.write(out);
      out.close();
      Log.info("Test_Results written successfully on disk. The results Path " +Path_Test_Results);

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }


  public XSSFCellStyle setStyleHeader(XSSFWorkbook workbook)
  {
    XSSFCellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setFontName(HSSFFont.FONT_ARIAL);
    font.setFontHeightInPoints((short)10);
    font.setBold(true);

    style.setFont(font);
    style.setAlignment(CellStyle.ALIGN_LEFT);
    return style;
  }

  public  XSSFCellStyle setReportHeader(XSSFWorkbook workbook)
  {
    XSSFCellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setFontName(HSSFFont.FONT_ARIAL);
    font.setFontHeightInPoints((short)10);
    font.setBold(true);

    style.setFont(font);
    style.setAlignment(CellStyle.ALIGN_CENTER);
    style.setWrapText(true);
    return style;
  }

  public  XSSFCellStyle setStyleBody(XSSFWorkbook workbook)
  {
    XSSFCellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setFontName("Calibri");
    //font.setFontHeightInPoints((short)10);
    style.setFont(font);
    style.setAlignment(CellStyle.ALIGN_CENTER);
    return style;
  }


  /**+
   *
   * @param workbook Pass your current work book
   * @param results if you pass "Passed/Green" the text will be green, pass "Failed/Red" the text will be Red
   * @return
   */
  public  XSSFCellStyle setFontColor(XSSFWorkbook workbook,String results)
  {
    XSSFCellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setFontName("Calibri");
    if (results=="Failed" ||results=="Red" ) {
      font.setFontName(HSSFFont.FONT_ARIAL);
      font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
    }
    else if (results=="Passed"|| results=="Green")
    {
      font.setFontName(HSSFFont.FONT_ARIAL);
      font.setColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
    }

    style.setFont(font);
    style.setWrapText(true);
    return style;
  }

  public  XSSFCellStyle setTextToRight(XSSFWorkbook workbook)
  {
    XSSFCellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setFontName(HSSFFont.FONT_ARIAL);
    font.setFontHeightInPoints((short)10);
    font.setBold(true);

    style.setFont(font);
    style.setWrapText(true);
    style.setAlignment(CellStyle.ALIGN_RIGHT );
    return style;
  }

}
