package Call_Flow_Scenarios.Deep_Link_Call_Flows;
import Call_Flow_Scenarios.Generic_Scenario;
import Libraries.Debit_Order_Library.Debit_Order;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Config.*;

public class TransactionUnAunthorized_Confirm_Dispute_Transaction extends Generic_Scenario
{

  @BeforeClass
  public void start_Test()
  {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }
  @Test
  public void Testing_Transaction_UnAuthorized ()
  {
    try {
      //Get test data for the TC_02 from the data sheet and create collection of it
      test_data_tc_01 =test_data.stream()
          .filter(datad->datad.getTest_CaSe_ID().equals("TC_18"))
          .collect(Collectors.toList());
      TC_ID= test_data_tc_01.get(0).getTest_CaSe_ID();
      //Create test for the report
      test=report.createTest(test_data_tc_01.get(0).getTest_Case_Name());
      //Add to the logs that the test case has started
      Log.startTestCase(test_data_tc_01.get(0).getTest_Case_Name());
      //Get Test case name from the collection
      test_Case_Name= test_data_tc_01.get(0).getTest_Case_Name();
      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order=new Debit_Order(test);
      //step 0 Make a calll
      dialogID=D_order.MakeCall();
      // Pass an utterance to the BOT
      D_order.pass_Data("debit order",dialogID,"Ask.IdNumber@Confirm.DisputeTransaction","Ask.Intent");
      D_order.check_Intent("Transactions.Inquiry");
      if(D_order.getNextStep().contains("Confirm.DisputeTransaction"))
        D_order.pass_Data(test_data_tc_01.get(0).getUtterance(), dialogID,
            "Announce.ReactionBeforeTransfer", "Confirm.DisputeTransaction");
      // Verify the transferring msg msg
      D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
    } catch (Exception ex) {

      catch_Exeption(ex);

    } finally {
      finaly_Exeption();
      updateResults();
      Log.endTestCase(test_Case_Name);
    }

  }

}
