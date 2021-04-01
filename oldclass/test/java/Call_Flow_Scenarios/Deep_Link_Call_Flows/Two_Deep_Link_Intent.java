package Call_Flow_Scenarios.Deep_Link_Call_Flows;
import Call_Flow_Scenarios.Generic_Scenario;
import Config.Log;
import Libraries.Debit_Order_Library.Debit_Order;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Two_Deep_Link_Intent extends Generic_Scenario
{

  @BeforeClass
  public void start_Test()
  {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }
  @Test
  public void Testing_Two_Deep_Link_One_Call ()
  {
    try {
      //Get test data from deepLink test data returns all rows
      test_data_tc_01 = test_data.stream()
          .filter(datad->datad.getTest_CaSe_ID().equals("TC_08"))
          .collect(Collectors.toList());
      test_Case_Name=test_data_tc_01.get(0).getTest_Case_Name();
      test = report.createTest(test_Case_Name);
      //Add to the logs that the test case has started
      Log.startTestCase(test_Case_Name);
      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order = new Debit_Order(test);
      //step 0  create dailog ID
      dialogID = D_order.MakeCall();
        // Provide intent
        D_order.pass_Data(test_data_tc_01.get(0).getUtterance(), dialogID, "Ask.IdNumber", "Ask.Intent");
        //Check if intent are matching
        D_order.check_Intent(test_data_tc_01.get(0).getIntent());
        //Capture ID Number
        D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Confirm.DeepLinkDevice", "Ask.IdNumber");

        //Confirm customer has device
        D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Devive(), dialogID, "Announce.HasDeepLinkCompleted", "Confirm.DeepLinkDevice");
        D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
        //Listen to the prompt
        D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.HasDeepLinkCompleted");
        //Pass another utterance
        D_order.pass_Data("I want to view my statement", dialogID, "Announce.HasDeepLinkCompleted", "Ask.Intent.AfterFulfil");
        D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
        D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.HasDeepLinkCompleted");
        D_order.pass_Data("No Thank you", dialogID, "Exit", "Ask.Intent");
        //Go to check the deepLink
          viewDeepLink(test_data_tc_01.get(0).getDeepLnkUI_ID());
    } catch (Exception ex) {

      catch_Exeption(ex);

    } finally {
      finaly_Exeption();
      updateResults();
      Log.endTestCase(test_Case_Name);
    }

  }

}