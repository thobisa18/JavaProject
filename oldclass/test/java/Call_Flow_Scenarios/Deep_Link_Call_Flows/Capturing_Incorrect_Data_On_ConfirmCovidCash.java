package Call_Flow_Scenarios.Deep_Link_Call_Flows;
/*
 * Author: Monono Thobisa F52088882
 * <p>
 * This class verify if a customer will be routed to an agent
 * when captured incorrect data on 'Confirm.CovidCashflowReliefExtension' steps
 * The test case ID is TC_16
 */
import Call_Flow_Scenarios.Generic_Scenario;
import Libraries.Debit_Order_Library.Debit_Order;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Config.*;

public class Capturing_Incorrect_Data_On_ConfirmCovidCash extends Generic_Scenario
{

  @BeforeClass
  public void start_Test()
  {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }
  @Test
  public void Testing_Deep_Drives ()
  {
    try {
      //Get test data for the TC_16 from the data sheet and create collection of it
      test_data_tc_01=test_data.stream()
          .filter(datad->datad.getTest_CaSe_ID().equals("TC_16"))
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
      D_order
          .pass_Data(test_data_tc_01.get(0).getUtterance(), dialogID, "Confirm.CovidCashflowReliefExtension", "Ask.Intent");
      //Check if intent are matching
      D_order.check_Intent(test_data_tc_01.get(0).getIntent());
      //Pass incorrect data
      D_order.pass_Data("Dudu", dialogID, "Confirm.CovidCashflowReliefExtension", "Confirm.CovidCashflowReliefExtension");
      D_order.pass_Data("That", dialogID, "Confirm.CovidCashflowReliefExtension", "Confirm.CovidCashflowReliefExtension");
      D_order.pass_Data("Dudu", dialogID, "Announce.ReactionBeforeTransfer", "Confirm.CovidCashflowReliefExtension");

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

