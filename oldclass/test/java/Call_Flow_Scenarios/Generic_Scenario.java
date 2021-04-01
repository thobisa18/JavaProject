/**
 * <h1>This is a parent class for all the scenarios!</h1>
 * @author  Thobisa Monono F5208882
 * @version 1.0
 * @since   2019-12-13
 */

package Call_Flow_Scenarios;

import static All_Reports.Excel_Report.Debit_Order_Excel_Data.getInstanceOfDebitOrder;

import All_Reports.Excel_Report.Debit_Order_Excel_Data;
import All_Reports.Excel_Report.Excel_Report_Data;
import All_Reports.Excel_Report.Generate_Excel_Report;
import All_Reports.Excel_Report.Read_Excel_File;
import Libraries.Debit_Order_Library.Debit_Order_Converted_Data;
import Config.Log;
import Config.constants;
import All_Reports.Extent_Report.Report_Generator;
import Libraries.Connect_To_Database;
import Libraries.Debit_Order_Library.Debit_Order;
import Mobile_App_Repository.Deep_Link;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import java.net.UnknownHostException;

public class Generic_Scenario
{
  public String TC_ID;
  public String segment="";
  public ExtentTest test;
  public ExtentReports report;
  public String test_Case_Name="";
  public boolean status_indicator=true;
  public Excel_Report_Data excel_report_data;
  public Read_Excel_File datasheet;
  static Logger logger = Logger.getLogger("info");
  public   Report_Generator re;
  public List<Debit_Order_Converted_Data> test_data;
  public Debit_Order_Excel_Data data_R;
  public String[][] results=new String[1][2];
  public String[][] smartresults=new String[1][2];
  public Connect_To_Database ds=new Connect_To_Database();
  public int  count=0;
  public int passedNum=0;
  public int failedNum=0;
  public int rowsS = 2;
  public int rows=0;
  public Debit_Order D_order;
  public String dialogID;
 public List<Debit_Order_Converted_Data> test_data_tc_01;
  public constants cons=constants.getInstance();
  private static DecimalFormat df2 = new DecimalFormat("#.##");
  @BeforeSuite
  public void testt()
  {

    DOMConfigurator.configure("./src/test/java/Log_Files/Self_Service_Logs/log4j_Self_Service.xml");
    excel_report_data = new Excel_Report_Data();
    cons.setStartTime();
  }


  public  String getString()
  {
    double ave1;
    double ave2;
    ave1=(double)data_R.getPassedNum()/(double)data_R.getCount1();
    ave2=(double)data_R.getFailedNum()/(double)data_R.getCount1();
    Log.error("Passed ave: "+ave1);
      Log.error("passed :"+data_R.getPassedNum() +" count: "+data_R.getCount1()+" failed:"+data_R.getFailedNum());
      Log.error(" \n Pass Rate: "+df2.format((ave1) *100) +"% || Failure Rate: "+df2.format((ave2) *100)+"%");
    return " \n Pass Rate: "+df2.format((ave1) *100) +"% || Failure Rate: "+df2.format((ave2) *100)+"%";
  }
  @BeforeClass
  public void beforeClass()
  {
    smartresults[0][0]="false";
    smartresults[0][1]="";
  }
  @AfterSuite
  public void close()
  {
    report.flush();
    Generate_Excel_Report test= new Generate_Excel_Report();
    List<String> list=data_R.get_Data().entrySet().stream().map(map->map.getValue().toString()).collect(
        Collectors.toList());
    cons.setEndTime();
    test.Generate_Report(data_R.get_Data(),"debit",getString());
  }



  public List<Debit_Order_Converted_Data> convertData(  Map<String, Object[]> data)
  {
    constants cons=constants.getInstance();
    List<Debit_Order_Converted_Data> list=new ArrayList<>();
    Debit_Order_Converted_Data ds;
    Set<String> keyset = data.keySet();

    for (String key : keyset) {
      Object[] objArr = data.get(key);

      ds = new Debit_Order_Converted_Data(objArr[0].toString(), objArr[1].toString(),
          objArr[2].toString(), objArr[3].toString(), objArr[4].toString(), objArr[5].toString(),
          objArr[6].toString(), objArr[7].toString(), objArr[8].toString(), objArr[9].toString(),
          objArr[10].toString(), objArr[11].toString(), objArr[12].toString(),objArr[14].toString(),objArr[15].toString(),objArr[13].toString());
      list.add(ds);
    }
    return list;
  }


  public List<Debit_Order_Converted_Data> convertDataDeepLink(  Map<String, Object[]> data)
  {
    constants cons=constants.getInstance();
    List<Debit_Order_Converted_Data> list=new ArrayList<>();
    Debit_Order_Converted_Data ds;
    Set<String> keyset = data.keySet();

    for (String key : keyset) {
      Object[] objArr = data.get(key);

      ds = new Debit_Order_Converted_Data(objArr[0].toString(), objArr[1].toString(),
          objArr[2].toString(), objArr[3].toString(), objArr[4].toString(), objArr[5].toString(),
          objArr[6].toString(), objArr[7].toString(), objArr[8].toString(),objArr[9].toString());
      list.add(ds);
    }
    return list;
  }
  //after successfully executing tests, this method will executed to check if results
  //@AfterMethod
  public void updateResults()
  {

    data_R.setCount1(data_R.getCount1()+1);
    count=data_R.getCount();
    //get final results
    results= D_order.getEndResults();
    //Check if its pass or fail
    Log.info("The debit order :"+results[0][0] +" The Smart results "+smartresults[0][0]);
    if(Boolean.parseBoolean(results[0][0]) || Boolean.parseBoolean(smartresults[0][0]))
    {

      ds.updateResults(test_Case_Name,dialogID,"Passed","",TC_ID);
     data_R.setPassedNum(data_R.getPassedNum()+1);
      //Set the data to write in excel spread sheet
      data_R.set_Data(test_Case_Name,dialogID,"Passed",results[0][1]);
      //Log the final results that will be written on excel
      System.out.println("Test Case No. "+count+": "+test_Case_Name +" " + dialogID+ " Passed  " +results[0][1] );
      Log.info("This infor will be written "+ test_Case_Name +" " + dialogID+ " Passed  " +results[0][1] );
      Log.info("Thobisa "+test_Case_Name+" "+dialogID+ " Passed  "+ "paassss"+ TC_ID);
      Log.endTestCase(test_Case_Name);


    }
    else
    {
     data_R.setFailedNum(data_R.getFailedNum()+1);
      System.out.println("Test Case No. "+count+": "+test_Case_Name +" " + dialogID+ " Failed  " +results[0][1] );
      //Set the data to write in excel spread sheet
      Log.info("This infor will be written "+ test_Case_Name +" " + dialogID+ " Failed  " +results[0][1] + " "+ smartresults[0][1]);
      //Log the final results that will be written on excel
      data_R.set_Data(test_Case_Name,dialogID,"Failed",results[0][1]);
      ds.updateResults(test_Case_Name,dialogID,"Failed",results[0][1],TC_ID);
      Log.endTestCase(test_Case_Name);
      Assert.fail(results[0][1]);

    }

  }
  public void Authenticate()
  {

    String step=D_order.getNextStep();
   if ( step.equals("Ask.CardNumber"))
   {
     //Enter Card number
     D_order.pass_Data("028379",dialogID,"Ask.Pin","Ask.CardNumber");
     D_order.pass_Data(test_data_tc_01.get(0).getPin_Number(),dialogID,"Ask.ReverseStop@Announce.DebitOrderOneAtTime","Ask.Pin");


   }

    if ( step.equals("Confirm.DeepLinkDevice")) {
      D_order.pass_Data("No", dialogID, "Announce.DeepLinkEducationalMessage",
          "Confirm.DeepLinkDevice") ;
    }
    }
   public void getData_And_Reports(){}

  public void getData_And_Reports(String which_Data,String test_Sheet){
    datasheet = new Read_Excel_File();
    test_data = convertDataDeepLink(datasheet.GetTestData(which_Data, test_Sheet));
    re= Report_Generator.getInstance();
    report=re.getReport();
    test= re.getTest();
    //Get test data from deepLink test data returns all rows
    rowsS = test_data.size();
    Log.info("Number of rows in Sheet " + rowsS);
    test_data_tc_01 = test_data.stream()
        .collect(Collectors.toList());
    //Loop through all rewcords
    rows = test_data_tc_01.size();
    Log.info("Rows :" + rows);
    data_R= getInstanceOfDebitOrder();
  }
  public void viewDeepLink( String ui_ID){
    results = D_order.getEndResults();
    if (Boolean.parseBoolean(results[0][0]) && constants.ViewDeepLink_flag()) {
      Deep_Link ds = new Deep_Link(test, D_order);
      ds.view_deepLink(ui_ID);

    } else {
      D_order.hug_Up(dialogID);

    }
  }

  public void catch_Exeption(Exception ex)
  {
    String exceptionClassName = ex.getClass().getName();
    //System.out.println("Get "+ex.getClass().getName());
    if(exceptionClassName.contains("UnknownHostException"))
    {
      test.log(Status.FAIL, "Error happened: Please connect to FNB intranet" );
      Log.error("Error happened: Please connect to FNB intranet");
    }
    else {
      D_order.hug_Up(dialogID);
      test.log(Status.FAIL, "Error happened: " + ex);
    }

    Log.error("Error happened: " + ex);
    D_order.setPreviousFalse();
    results= D_order.getEndResults();
  }
  public void finaly_Exeption()
  {
    D_order.hug_Up(dialogID);
  }
}