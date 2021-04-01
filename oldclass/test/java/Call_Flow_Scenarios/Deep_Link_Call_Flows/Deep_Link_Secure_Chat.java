
package Call_Flow_Scenarios.Deep_Link_Call_Flows;
import Call_Flow_Scenarios.Generic_Scenario;
import Config.Log;
import Libraries.Debit_Order_Library.Debit_Order;
import java.util.stream.Collectors;
import org.testng.annotations.Test;


public class Deep_Link_Secure_Chat extends Generic_Scenario
{


  @Test
  public void Testing ()
  {

    try {
      getData_And_Reports("DeepLink_Exception", "Test_Data");
      //Get test data from deepLink test data returns all rows
      test_data_tc_01 = test_data.stream()
          .filter(datad->datad.getTest_CaSe_ID().equals("TC_06"))
          .collect(Collectors.toList());
      //Loop through all records
      test = report.createTest(test_data_tc_01.get(0).getTest_Case_Name());
      test_Case_Name=test_data_tc_01.get(0).getTest_Case_Name();
      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order = new Debit_Order(test);
      //step 0  create dailog ID
      dialogID = D_order.MakeCall();
        //Add to the logs that the test case has started
        Log.startTestCase(test_data_tc_01.get(0).getTest_Case_Name());
        // Provide intent
          D_order.pass_Data(test_data_tc_01.get(0).getUtterance(), dialogID, "Ask.IdNumber", "Ask.Intent");
        //Check if intent are matching
        D_order.check_Intent(test_data_tc_01.get(0).getIntent());
        //Capture ID Number
        D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Confirm.DeepLinkDevice", "Ask.IdNumber");

        //Confirm customer has device
        D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Devive(), dialogID, "Announce.HasDeepLinkCompleted", "Confirm.DeepLinkDevice");
       //Verify prompt
        D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
        //Listen to the prompt
        D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.HasDeepLinkCompleted");
        //Pass chat intent
          D_order.pass_Data(test_data_tc_01.get(0).getAfterIntent(), dialogID, "Announce.SecureChatDeepLinkHasCompleted", "Ask.Intent.AfterFulfil");
         //Verify prompts
          D_order.verify_Prompt("chat");
          //Listen to announcement
          D_order.pass_Data("", dialogID, "Exit", "Announce.SecureChatDeepLinkHasCompleted'");
      //Go to check the deepLink
      viewDeepLink(test_data_tc_01.get(0).getDeepLnkUI_ID());

    } catch (Exception ex) {
      catch_Exeption(ex);
    } finally {

      finaly_Exeption();

    }
    updateResults();
  }

  public void endTestCase()
  {
    Log.endTestCase(test_Case_Name);
  }
}


