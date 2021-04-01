package All_Reports.Excel_Report;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import Config.*;
public class Read_Excel_File {

  public Map<String, Object[]> GetTestData(String which_Data,String SheetName) {
    int rowNum=0;

    List<String> dataFromExcel=new ArrayList<>();
    Map<String, Object[]> data= new TreeMap<>();
    String FILE_NAME=constants.GetTestData(which_Data);


    try {
      FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
      Workbook workbook = new XSSFWorkbook(excelFile);
      //Sheet datatypeSheet = workbook.getSheetAt("Sheet1");

      Sheet datatypeSheet = workbook.getSheet(SheetName);
      Iterator<Row> iterator = datatypeSheet.iterator();

      while (iterator.hasNext()) {

        Row currentRow = iterator.next();
        Iterator<Cell> cellIterator = currentRow.iterator();
        if (rowNum!=0) {
          //for (int i = 0; i <= 2; i++)
          while(cellIterator.hasNext())
          {
            Cell currentCell = cellIterator.next();
            dataFromExcel.add(currentCell.getStringCellValue());

          }
         // System.out.println(dataFromExcel.get(0)+ " " + dataFromExcel.get(1)+ "   "+dataFromExcel.get(2)+"  " + dataFromExcel.get(3) +"   "+dataFromExcel.get(4));
          if(which_Data.equals("intent")|| which_Data.equals("intent_Smart_Routing"))

              data.put(String.valueOf(rowNum-1), new Object[]{dataFromExcel.get(0), dataFromExcel.get(1), dataFromExcel.get(2),dataFromExcel.get(3),dataFromExcel.get(4),dataFromExcel.get(5)});
          else if(which_Data.equals("intent_Exception"))
            data.put(String.valueOf(rowNum-1), new Object[]{dataFromExcel.get(0), dataFromExcel.get(1), dataFromExcel.get(2),dataFromExcel.get(3)});
         else if(which_Data.equals("debit"))
              data.put(String.valueOf(rowNum-1), new Object[]{dataFromExcel.get(0), dataFromExcel.get(1), dataFromExcel.get(2),dataFromExcel.get(3),dataFromExcel.get(4),dataFromExcel.get(5),dataFromExcel.get(6),dataFromExcel.get(7),dataFromExcel.get(8),dataFromExcel.get(9),dataFromExcel.get(10),dataFromExcel.get(11),dataFromExcel.get(12),dataFromExcel.get(13),dataFromExcel.get(14),dataFromExcel.get(15)});
         else if(which_Data.equals("DeepLink") || which_Data.equals("Chat")|| which_Data.equals("DeepLink_Exception") || which_Data.equals("Covid_19"))
              data.put(String.valueOf(rowNum-1),new Object[]{dataFromExcel.get(0), dataFromExcel.get(1), dataFromExcel.get(2),dataFromExcel.get(3),dataFromExcel.get(4),dataFromExcel.get(5),dataFromExcel.get(6),dataFromExcel.get(7),dataFromExcel.get(8),dataFromExcel.get(9)});

        dataFromExcel.clear();
        }
        rowNum++;
        //System.out.println("Row "+ rowNum);

      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return  data;
  }

}
