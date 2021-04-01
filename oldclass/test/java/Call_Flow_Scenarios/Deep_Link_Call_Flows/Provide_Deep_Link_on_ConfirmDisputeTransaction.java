package Call_Flow_Scenarios.Deep_Link_Call_Flows;
import Call_Flow_Scenarios.Generic_Scenario;
import Libraries.Debit_Order_Library.Debit_Order;
import Mobile_App_Repository.Deep_Link;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Config.*;

public class Provide_Deep_Link_on_ConfirmDisputeTransaction extends Generic_Scenario
{

  @BeforeClass
  public void start_Test() {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }
  @Test
  public void Testing_Confirm_Reverse ()
  {
    try {
      //Get test data for the TC_02 from the data sheet and create collection of it
      test_data_tc_01=test_data.stream()
          .filter(datad->datad.getTest_CaSe_ID().equals("TC_17"))
          .collect(Collectors.toList());
      TC_ID=test_data_tc_01.get(0).getTest_CaSe_ID();
      //Create test for the report
      test=report.createTest(test_data_tc_01.get(0).getTest_Case_Name());
      //Add to the logs that the test case has started
      Log.startTestCase(test_data_tc_01.get(0).getTest_Case_Name());
      //Get Test case name from the collection
      test_Case_Name=test_data_tc_01.get(0).getTest_Case_Name();
      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order=new Debit_Order(test);
      //step 0 Make a calll
      dialogID=D_order.MakeCall();
      // Pass an utterance to the BOT
      D_order.pass_Data("debit order",dialogID,"Ask.IdNumber@Confirm.DisputeTransaction","Ask.Intent");
      D_order.check_Intent("Transactions.Inquiry");
      if(D_order.getNextStep().contains("Confirm.DisputeTransaction"))
        D_order.pass_Data("I wanna view my statement", dialogID,
            "Ask.IdNumber", "Confirm.DisputeTransaction");
     // D_order.check_Intent(test_data_tc_01.get(0).getIntent());
      //Capture ID Number
      D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Confirm.DeepLinkDevice",
          "Ask.IdNumber");
      //Confirm device availability
      D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Devive(), dialogID, "Announce.DeepLinkDOUnavailableSent@Announce.HasDeepLinkCompleted", "Confirm.DeepLinkDevice");
      //Listen to the prompt
      D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
      D_order.pass_Data("", dialogID,"Ask.Intent","Announce.HasDeepLinkCompleted");
      //Pass After intent
      D_order.pass_Data(test_data_tc_01.get(0).getAfterIntent(), dialogID, "Exit", "Ask.Intent");
      //Go to check the deepLink
      results = D_order.getEndResults();
      if (Boolean.parseBoolean(results[0][0]) && cons.ViewDeepLink_flag()) {
        Deep_Link ds = new Deep_Link(test, D_order);
        ds.view_deepLink(test_data_tc_01.get(0).getDeepLnkUI_ID());

      }

    } catch (Exception ex) {

      catch_Exeption(ex);

    } finally {
      finaly_Exeption();
      updateResults();
      Log.endTestCase(test_Case_Name);
    }

  }

}

