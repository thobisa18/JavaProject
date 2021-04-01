package Call_Flow_Scenarios.Deep_Link_Call_Flows;
/*
 * Author: Monono Thobisa F52088882
 * <p>
 * Ensure that 'Debit order service is not availabe' prompt is played
 * when customer is not having access to his/her phone
 * The test case ID is TC_05
 */
import Call_Flow_Scenarios.Generic_Scenario;
import Libraries.Debit_Order_Library.Debit_Order;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Config.*;

public class Debit_Order_Prompt_No_Phone extends Generic_Scenario
{

  @BeforeClass
  public void start_Test()
  {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }

  @Test
  public void Testing_Debit_Order_Prompt_No_Phone () {
    try {

      //Get test data for the TC_02 from the data sheet and create collection of it
      test_data_tc_01 = test_data.stream()
          .filter(datad -> datad.getTest_CaSe_ID().equals("TC_19"))
          .collect(Collectors.toList());
      TC_ID = test_data_tc_01.get(0).getTest_CaSe_ID();
      //Create test for the report
      test = report.createTest(test_data_tc_01.get(0).getTest_Case_Name());
      //Add to the logs that the test case has started
      Log.startTestCase(test_data_tc_01.get(0).getTest_Case_Name());
      //Get Test case name from the collection
      test_Case_Name = test_data_tc_01.get(0).getTest_Case_Name();
      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order = new Debit_Order(test);
      //step 0 Make a calll
      dialogID = D_order.MakeCall();
      // Pass an utterance to the BOT
      D_order
          .pass_Data(test_data_tc_01.get(0).getUtterance(), dialogID, "Ask.IdNumber", "Ask.Intent");
      //Pass an ID number
      D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Confirm.DeepLinkDevice",
          "Ask.IdNumber");
      // Confirm the customer is not having a device
      D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Devive(), dialogID,
          "Announce.DeepLinkEducationalMessage",
          "Confirm.DeepLinkDevice");

      // Verify the educational msg
      D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
      D_order.pass_Data("", dialogID, "Announce.DODUnavailableNoDevice",
          "Announce.DeepLinkEducationalMessage");
      // Verify the DO service off prompt
      D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());


    } catch (Exception ex) {
      catch_Exeption(ex);
    } finally {
      updateResults();
      Log.endTestCase(test_Case_Name);
    }
  }
}
