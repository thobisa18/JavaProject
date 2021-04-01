package Call_Flow_Scenarios.Deep_Link_Call_Flows;
/*
 * Author: Monono Thobisa F52088882
 * <p>
 * This class/scenario  Ensure a customer can request for a deep link and
 *  request for covid-19 deep link in one call in that order.
 * The test case ID is TC_11
 */
import Call_Flow_Scenarios.Generic_Scenario;
import Config.Log;
import Libraries.Debit_Order_Library.Debit_Order;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Deep_LInk_Covid_Deep_Link_Covid extends Generic_Scenario
{
  @BeforeClass
  public void start_Test()
  {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }
  @Test
  public void Testing_Flow_Deep_Link_Covid ()
  {
    try {
      //Get test data from deepLink test data returns all rows
      test_data_tc_01 = test_data.stream()
          .filter(datad->datad.getTest_CaSe_ID().equals("TC_11"))
          .collect(Collectors.toList());
      test_Case_Name=test_data_tc_01.get(0).getTest_Case_Name();
      //Create a test case
      test = report.createTest(test_Case_Name);
      //Add to the logs that the test case has started
      Log.startTestCase(test_Case_Name);
      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order = new Debit_Order(test);
      //step 0  create dailog ID
      dialogID = D_order.MakeCall();
      // Provide intent/ pass utterance
      D_order.pass_Data("Good morning how do I apply for cash flow relief", dialogID, "Announce.CovidNoNewApplicationsAccepted@Confirm.CovidCashflowReliefExtension", "Ask.Intent");
      //D_order.check_Intent(test_data_tc_01.get(0).getIntent());
      if(D_order.getNextStep().contains("Confirm.CovidCashflowReliefExtension"))
        D_order.pass_Data("No", dialogID, "Announce.CovidNoNewApplicationsAccepted", "Confirm.CovidCashflowReliefExtension");
      //D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
      D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.CovidNoNewApplicationsAccepted");
      D_order.pass_Data(test_data_tc_01.get(0).getUtterance() , dialogID, "Ask.IdNumber", "Ask.Intent");
      //Capture ID Number
      D_order.check_Intent(test_data_tc_01.get(0).getIntent());
      D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Confirm.DeepLinkDevice", "Ask.IdNumber");

      //Confirm customer has device
      D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Devive(), dialogID, "Announce.HasDeepLinkCompleted", "Confirm.DeepLinkDevice");
      //Verify prompt
      D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
      //Listen to the prompt
      D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.HasDeepLinkCompleted");
      //Pass chat intent
      D_order.pass_Data("Good morning how do I apply for cash flow relief", dialogID, "Announce.CovidNoNewApplicationsAccepted@Confirm.CovidCashflowReliefExtension", "Ask.Intent");
      //D_order.check_Intent(test_data_tc_01.get(0).getIntent());
      if(D_order.getNextStep().contains("Confirm.CovidCashflowReliefExtension"))
        D_order.pass_Data("No", dialogID, "Announce.CovidNoNewApplicationsAccepted", "Confirm.CovidCashflowReliefExtension");
      //D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
      D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.CovidNoNewApplicationsAccepted");
      //Listen to announcement
      D_order.pass_Data(test_data_tc_01.get(0).getUtterance(), dialogID, "Announce.HasDeepLinkCompleted", "Ask.Intent");
      D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.HasDeepLinkCompleted");
      D_order.pass_Data(test_data_tc_01.get(0).getAfterIntent(), dialogID, "Confirm.CovidCashflowReliefExtension", "Ask.Intent");
      if(D_order.getNextStep().contains("Confirm.CovidCashflowReliefExtension"))
        D_order.pass_Data("Yes", dialogID, "Announce.ReactionBeforeTransfer", "Confirm.CovidCashflowReliefExtension");


    }
    catch (Exception ex)
    {
      catch_Exeption(ex);
    }
    finally {
      updateResults();
      Log.endTestCase(test_Case_Name);
    }


  }

}