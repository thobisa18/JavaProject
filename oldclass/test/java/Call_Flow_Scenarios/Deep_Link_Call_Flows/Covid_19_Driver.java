
package Call_Flow_Scenarios.Deep_Link_Call_Flows;
/*
 * Author: Monono Thobisa F52088882
 * <p>
 * This class or scenario verify if NLP will play that Covid-19 new application
 * are no longer accepted
 * It loops through the data specified in a spreadsheet
 * Variable 'invocation_times' represent the number of rows in the spreadsheet
 */
import Call_Flow_Scenarios.Generic_Scenario;
import Config.Log;
import Config.constants;
import Libraries.Debit_Order_Library.Debit_Order;
import com.aventstack.extentreports.Status;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;


public class Covid_19_Driver extends Generic_Scenario implements IAnnotationTransformer {

  int i = 0;
  public Covid_19_Driver() {
    getData_And_Reports("Covid_19", "Test_Data");

  }

  @Override
  public void transform(ITestAnnotation annotation, Class testclass, Constructor testConstructor, Method testMethod)
  {
    if (testMethod.getName().equals("Verify_If_The_BOT_Can_Send_Covid_19_Deep_Link"))
    {
      annotation.setInvocationCount(test_data_tc_01.size());
    }
  }
  @Test
  public void Verify_If_The_BOT_Can_Send_Covid_19_Deep_Link() {
    //Get test case ID from the spreadsheet
    TC_ID = test_data_tc_01.get(i).getTest_CaSe_ID();
    if(!test_data_tc_01.get(i).getIntent().equals("Covid.Application.New"))
    try {
      test = report.createTest(test_data_tc_01.get(i).getTest_Case_Name());

      //Get Test case name from the collection
      test_Case_Name = test_data_tc_01.get(i).getTest_Case_Name();
      //Add to the logs that the test case has started
      Log.startTestCase(test_Case_Name);
      test.log(Status.PASS,"This scenario was executed on '"+test_data_tc_01.get(i).getSegment()+"' Dnis");
      segment= constants.getDnisBySegment(test_data_tc_01.get(i).getSegment());
      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order = new Debit_Order(test,segment);
      //step 0  create dailog ID
      dialogID = D_order.MakeCall();
      // Provide intent
      D_order
          .pass_Data(test_data_tc_01.get(i).getUtterance(), dialogID, "Announce.CovidNoNewApplicationsAccepted@Confirm.CovidCashflowReliefExtension",
              "Ask.Intent");
      //Check if intent are matching
      D_order.check_Intent(test_data_tc_01.get(i).getIntent());
      //Confirm customer is applying for new covid-19 application
      if(D_order.getNextStep().contains("Confirm.CovidCashflowReliefExtension"))
        D_order.pass_Data("No", dialogID, "Announce.CovidNoNewApplicationsAccepted", "Confirm.CovidCashflowReliefExtension");
      //Verify if NPL played the announcement "No new application for covid 19"
      D_order.verify_Prompt(test_data_tc_01.get(i).getPrompt());
      //Pass no input
      D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.CovidNoNewApplicationsAccepted");
     //Provide response for after fulfilment intent "No thank you"
      D_order.pass_Data(test_data_tc_01.get(i).getAfterIntent(), dialogID, "Exit", "Ask.Intent");

    } catch (Exception ex) {
      catch_Exeption(ex);
    } finally {
      i++;
      finaly_Exeption();
      updateResults();
      Log.endTestCase(test_Case_Name);
    }

  }
  }


