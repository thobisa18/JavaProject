
package Call_Flow_Scenarios.Deep_Link_Call_Flows;
/*
 * Author: Monono Thobisa F52088882
 * <p>
 * This class is to verify if NLP is able to send all the deep link provided
 * in the spreadsheeet. Currently there's quite a number of deep link intents
 * so these deep link intents are divided into batches
 * each batch is having a separate data sheet
 * To ensure all these deep links are executed please change them on
 * Constant class under the option 'DeepLink'
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
public class Deep_Link_Driver extends Generic_Scenario implements IAnnotationTransformer   {
  int i = 0;

  public Deep_Link_Driver() {
    getData_And_Reports("DeepLink", "Test_Data");
  }

  @Override
  public void transform(ITestAnnotation annotation, Class testclass, Constructor testConstructor, Method testMethod)
  {
    if (testMethod.getName().equals("Verify_If_Deep_Link_Will_Be_Sent_To_Customer_Mobile_App_for_Each_Intent"))
    {
      int size=test_data_tc_01.size();
      annotation.setInvocationCount(test_data_tc_01.size());
    }
  }
  @Test
  public void Verify_If_Deep_Link_Will_Be_Sent_To_Customer_Mobile_App_for_Each_Intent() {

    TC_ID=test_data_tc_01.get(i).getTest_CaSe_ID();
    if(i>130)
    try {
      test = report.createTest(test_data_tc_01.get(i).getTest_Case_Name());
      //Add to the logs that the test case has started
      Log.startTestCase(test_data_tc_01.get(i).getTest_Case_Name());
      //Get Test case name from the collection
      test_Case_Name = test_data_tc_01.get(i).getTest_Case_Name();
      test.log(Status.PASS,"This scenario was executed on '"+test_data_tc_01.get(0).getSegment()+"' Dnis");
      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order = new Debit_Order(test, constants.getDnisBySegment(test_data_tc_01.get(i).getSegment()));

      //step 0  create dailog ID
      dialogID = D_order.MakeCall();
      // Provide intent
      D_order
          .pass_Data(test_data_tc_01.get(i).getUtterance(), dialogID, "Confirm.DisputeTransaction@Ask.IdNumber", "Ask.Intent");
      if(D_order.getNextStep().contains("Confirm.DisputeTransaction"))
        D_order.pass_Data("yes", dialogID,
            "Ask.IdNumber", "Confirm.DisputeTransaction");
      //Check if intent are matching
      D_order.check_Intent(test_data_tc_01.get(i).getIntent());
      //Capture ID Number
      D_order.pass_Data(test_data_tc_01.get(i).getID_Number(), dialogID, "Confirm.DeepLinkDevice",
          "Ask.IdNumber");
      //Confirm device availability
      D_order.pass_Data(test_data_tc_01.get(i).getConfirm_Devive(), dialogID, "Announce.DeepLinkDOUnavailableSent@Announce.HasDeepLinkCompleted", "Confirm.DeepLinkDevice");
      //Listen to the prompt
      D_order.verify_Prompt(test_data_tc_01.get(i).getPrompt());
      D_order.pass_Data("", dialogID,"Ask.Intent","Announce.HasDeepLinkCompleted");
      //Pass After intent
      D_order.pass_Data(test_data_tc_01.get(i).getAfterIntent(), dialogID, "Exit", "Ask.Intent");
      //Go to check the deepLink
      viewDeepLink(test_data_tc_01.get(i).getDeepLnkUI_ID());

    }
      catch (Exception ex)
    {
      i++;
      catch_Exeption(ex);
    }
    finally {

      finaly_Exeption();
      updateResults();
      Log.endTestCase(test_Case_Name);
    }
    i++;

  }

}