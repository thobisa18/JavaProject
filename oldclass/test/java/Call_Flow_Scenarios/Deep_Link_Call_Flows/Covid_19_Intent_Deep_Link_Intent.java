package Call_Flow_Scenarios.Deep_Link_Call_Flows;
/*
 * Author: Monono Thobisa F52088882
 * <p>
 * This class/scenario verify if NLP can announce 'New Covid-19 applications'
 * are not accepted
 * The test case ID is TC_15
 */
import Call_Flow_Scenarios.Generic_Scenario;
import Config.Log;
import Libraries.Debit_Order_Library.Debit_Order;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Covid_19_Intent_Deep_Link_Intent extends Generic_Scenario {

  @BeforeClass
  public void start_Test()
  {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }
  @Test
  public void Send_Covid_19_And_Send_Deep_Link_In_One_Call() {
    try {

      //Get a row from the datasheet
      test_data_tc_01 = test_data.stream()
          .filter(datad -> datad.getTest_CaSe_ID().equals("TC_15"))
          .collect(Collectors.toList());
      //Get test case name
      test_Case_Name = test_data_tc_01.get(0).getTest_Case_Name();
      test = report.createTest(test_Case_Name);
      //Add to the logs that the test case has started
      Log.startTestCase(test_Case_Name);
      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order = new Debit_Order(test);
      //step 0  create dailog ID
      dialogID = D_order.MakeCall();

      // Provide utterance
      D_order
          .pass_Data("how do I apply for cash flow relief", dialogID, "Confirm.CovidCashflowReliefExtension",
              "Ask.Intent");
      if(D_order.getNextStep().contains("Confirm.CovidCashflowReliefExtension"))
        D_order.pass_Data("No", dialogID, "Announce.CovidNoNewApplicationsAccepted", "Confirm.CovidCashflowReliefExtension");

      //Verify prompts
      D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
      //Check if intent are matching
      D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.CovidNoNewApplicationsAccepted");
      D_order.pass_Data("I want my account Balance ", dialogID, "Ask.IdNumber",
          "Ask.Intent");
      //Capture ID Number
      D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Confirm.DeepLinkDevice",
          "Ask.IdNumber");
      //Confirm customer has device
      D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Devive(), dialogID,
          "Announce.HasDeepLinkCompleted", "Confirm.DeepLinkDevice");

      D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.HasDeepLinkCompleted");
      //Provide response for after fulfilment intent "No thank you"
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



  // @AfterClass
  public void endTestCase()
  {
    Log.endTestCase(test_Case_Name);
  }

}
