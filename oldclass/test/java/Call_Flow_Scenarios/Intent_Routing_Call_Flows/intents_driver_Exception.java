package Call_Flow_Scenarios.Intent_Routing_Call_Flows;

import All_Reports.Excel_Report.Excel_Report_Data;
import All_Reports.Excel_Report.Generate_Excel_Report;
import All_Reports.Excel_Report.Read_Excel_File;
import Config.Log;
import Config.constants;
import Libraries.Intent_Routing_Library.Smart_Call_Routing;
import Libraries.Intent_Routing_Library.intents;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;

/**
 * <h1>Intents Driver!</h1>
 * The Intents Driver program read data from excel spreadsheet
 * Simply pass the intent to a webservice and provide
 * the final intent and number the call routed to.
 * <p>
 * <b>Note:</b> It generates an excel report and it write logs on Logs_File
 * @author  Thobisa Monono F5208882
 * @version 1.0
 * @since   2019-12-13
 */
public class intents_driver_Exception  implements IAnnotationTransformer
{

  int  count=0;
  int passedNum=0;
  int failedNum=0;
  String[][] smart_call_routing_result = new String[1][2];
  constants cons=constants.getInstance();

  Excel_Report_Data excel_report_data=new  Excel_Report_Data();
  String[][] results;
  boolean intentR,TrasferR;

  String comment="";
  String executionStatus="Failed";
  //get intents
  intents ts=new intents();
  int row=0;
  Map<String, Object[]> data;
  Read_Excel_File datasheet=new Read_Excel_File();
  private static DecimalFormat df2 = new DecimalFormat("#.##");
  Set<String> keyset;

  int i = 0;

  public intents_driver_Exception()
  {
    DOMConfigurator.configure("./src/test/java/Log_Files/Intent_Routing_Logs/Logs_Intent.xml");
    cons.setStartTime();
    Read_Excel_File datasheet=new Read_Excel_File();
    data=datasheet.GetTestData("intent_Exception","Test_Data");
    keyset = data.keySet();
    System.out.println("Size:"+ keyset.size());
  }
  @Override
  public void transform(ITestAnnotation annotation, Class testclass, Constructor testConstructor, Method testMethod)
  {
    if (testMethod.getName().equals("test"))
    {
      annotation.setInvocationCount(keyset.size());
    }
  }
  @Test()
  public  void test()
  {
    String comment;
    String executionStatus;

    int row=0;
   // System.out.println("Rows :" + data.size());
      executionStatus="Failed";
      comment="";
      count++;
      intentR=false;
      TrasferR=false;
      Object [] objArr = data.get(String.valueOf(i));
      //Passing intent to get the number routed tp
      Log.info("The utterance : "+objArr[0].toString());
      results= ts.get_Final_Intent(objArr[0].toString(),constants.getDnisBySegment(objArr[3].toString() ));
      //Compare the actual intent with the expected intent

    if (Boolean.parseBoolean(constants.Get_Ask_ID_Indicator()))
    {
          comment="It ask for an ID from a customer";
    }else{
      if (objArr[1].toString().equals(results[0][1])) {
        intentR = true;
        comment += "";
        //Compare the actual number routed to with expected number
        if (objArr[2].toString()
            .equals(results[0][2]))// returns number transferred to from smart routing
        {
          TrasferR = true;
          comment += "";
        } else {
          comment += "Numbers are not equal, " + "the actual number routed to " + results[0][2]
              + " and the expected " + objArr[2].toString() + ".";
        }
      } else {
        comment +=
            "Intents are not equal, " + "the actual intent " + results[0][1] + " and the expected "
                + objArr[1].toString();
      }
    }
      //check if it fails/ passed& TrasferR==true
      if(intentR && TrasferR)
      {
        executionStatus="Passed";
        passedNum++;
      }
      else
        failedNum++;
      //print in excel "Utterance        Expected Intent     Actual Intent   Expected Number              Number Transfered To       Dialog ID   Execution Status Commments
      excel_report_data.set_Data_exception(results[0][0],objArr[1].toString(),results[0][1],"'"+objArr[2].toString(),"'"+results[0][2], results[0][3],executionStatus,comment,objArr[3].toString());
    //}
    i++;
    if(executionStatus.equals("Failed"))
      Assert.fail(comment);
    }
  @AfterClass
  public void afterMethodd()
  {
    cons.setEndTime();
    Generate_Excel_Report test= new Generate_Excel_Report();
    test.Generate_Report(excel_report_data.get_Data(),"intent_Exception",getString());
  }

public  String getString()
{
  double ave1=(double)passedNum/(double) count;
  double ave2=(double)failedNum/(double) count;
  return " \n Pass Rate: "+df2.format( ave1*100) +"% \n Failure Rate: "+df2.format(ave2 *100)+"%";
}

}
