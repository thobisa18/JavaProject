package Call_Flow_Scenarios.Intent_Routing_Call_Flows;

import All_Reports.Excel_Report.Excel_Report_Data;
import All_Reports.Excel_Report.Generate_Excel_Report;
import All_Reports.Excel_Report.Read_Excel_File;
import Libraries.Intent_Routing_Library.Smart_Call_Routing;
import Libraries.Intent_Routing_Library.intents;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.AfterClass;

import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;
import Config.*;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * <h1>Intents Driver!</h1>
 * The Intents Driver program read data from excel spreadsheet Simply pass the intent to a
 * webservice and provide the final intent and number the call routed to.
 * <p>
 * <b>Note:</b> It generates an excel report and it write logs on Logs_File
 *
 * @author Thobisa Monono F5208882
 * @version 1.0
 * @since 2019-12-13
 */
public class Smart_Call_Driver implements IAnnotationTransformer  {

  private static DecimalFormat df2 = new DecimalFormat("#.##");
  private final int invocation_times = 449;
  private int count = 0;
  private int passedNum = 0;
  private int failedNum = 0;
  private String[][] smart_call_routing_result = new String[1][2];
  private constants cons = constants.getInstance();
  private  Excel_Report_Data excel_report_data = new Excel_Report_Data();
  private boolean intentR, TrasferR;
  private  String comment = "";
  private String executionStatus = "Failed";
  //get intents

  private Map<String, Object[]> data;
  private Read_Excel_File datasheet = new Read_Excel_File();
  private int i = 0;
  private Set<String> keyset;
  private Object[] objArr;


  public Smart_Call_Driver() {

    DOMConfigurator.configure("./src/test/java/Log_Files/Intent_Routing_Logs/Logs_Intent.xml");
    cons.setStartTime();
    data = datasheet.GetTestData("intent_Smart_Routing", "Test_Data");
    keyset = data.keySet();
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
  public void test() {
    if(i>=0) {
      try {
        System.out.println("Rows :" + data.size());
        executionStatus = "Failed";
        comment = "";
        count++;
        intentR = false;
        TrasferR = false;
        objArr = data.get(String.valueOf(i));

        //Passing intent to get the number routed tp
        Log.info("Intent Driver, The utterance : " + objArr[0].toString());

          intentR = true;
          comment += "";
          boolean auth=false;
        if(objArr[5].toString().contains("Y"))
          auth=true;
          //Call smart call routing
          Smart_Call_Routing smart_call_routing = new Smart_Call_Routing();
          smart_call_routing_result = smart_call_routing
              .GetDestination_Of_Call(objArr[3].toString().trim(), objArr[2].toString(), objArr[1].toString(),String.valueOf(auth));

          if (!smart_call_routing_result[0][0].equals("null") && !smart_call_routing_result[0][0].equals("")) {
            if (objArr[4].toString().contains(
                smart_call_routing_result[0][0]))//smart_call_routing_result[0][0] returns number transferred to from smart routing
            {
              TrasferR = true;
              comment += "";
            } else {
              comment += "Numbers are not equal, " + "the actual number routed to "
                  + smart_call_routing_result[0][0] + " and the expected " + objArr[4].toString()
                  + ".";
            }
          }
        } catch (Exception x) {
        comment = x.getMessage();
        Log.info("Intent Driver Exception:  " + comment);


      } finally {

        //check if it fails/ passed& TrasferR==true
        if (intentR  && TrasferR ) {
          executionStatus = "Passed";
          passedNum++;
        } else {
          failedNum++;
        }

        //print in excel "Utterance        Expected Intent     Actual Intent   Expected Number              Number Transfered To       Dialog ID   Execution Status Commments
        excel_report_data
            .set_Data_Smart_Routing( objArr[3].toString(),
                "'" + objArr[4].toString(),
                "'" + smart_call_routing_result[0][0], executionStatus, comment,
                smart_call_routing_result[0][1], objArr[2].toString(), objArr[1].toString());

        if (executionStatus.equals("Failed")) {
          i ++;
          Assert.fail(comment);
        }
      }
    }
    i ++;

  }

  @AfterClass
  public void afterMethodd() {
    cons.setEndTime();
    Generate_Excel_Report test = new Generate_Excel_Report();
    test.Generate_Report(excel_report_data.get_Data(), "intent_Smart_Routing", getString());
  }



  public String getString() {
    double ave1 = (double) passedNum / (double) count;
    double ave2 = (double) failedNum / (double) count;
    return " \n Pass Rate: " + df2.format(ave1 * 100) + "% \n Failure Rate: " + df2
        .format(ave2 * 100) + "%";
  }

}