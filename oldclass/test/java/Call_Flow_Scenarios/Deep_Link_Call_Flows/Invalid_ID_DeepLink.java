
package Call_Flow_Scenarios.Deep_Link_Call_Flows;
/*
 * Author: Monono Thobisa F52088882
 * <p>
 * This class/scenario verify if NLP will not accept invalid ID
 * The test case ID is TC_04
 */
    import Call_Flow_Scenarios.Generic_Scenario;
    import Libraries.Debit_Order_Library.Debit_Order;
    import java.util.stream.Collectors;
    import org.testng.annotations.BeforeClass;
    import org.testng.annotations.Test;
    import Config.*;

public class Invalid_ID_DeepLink extends Generic_Scenario
{

  @BeforeClass
  public void start_Test()
  {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }
  @Test
  public void Invalid_ID_Deep_Link_Test ()
  {

    try{
          //Get test data for the TC_02 from the data sheet and create collection of it
          test_data_tc_01=test_data.stream()
            .filter(datad->datad.getTest_CaSe_ID().equals("TC_04"))
            .collect(Collectors.toList());
            //Create test for the report
            test=report.createTest(test_data_tc_01.get(0).getTest_Case_Name());
            //Add to the logs that the test case has started
            Log.startTestCase(test_data_tc_01.get(0).getTest_Case_Name());
            //Get Test case name from the collection
            test_Case_Name=test_data_tc_01.get(0).getTest_Case_Name();
            //create instance for debit order( it contains all the methods required to communicate with omillia)
            D_order=new Debit_Order(test);
            //step 0  create dailog ID
            dialogID=D_order.MakeCall();
            // Capture utterance
            D_order.pass_Data(test_data_tc_01.get(0).getUtterance(),dialogID,"Ask.IdNumber","Ask.Intent");
            //Capture invalid ID number
            D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Ask.IdNumber",
             "Ask.IdNumber") ;
             //Capture invalid ID number
            D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Ask.IdNumber",
              "Ask.IdNumber") ;
            //Capture invalid ID number
            D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Announce.ReactionBeforeTransfer",
            "Ask.IdNumber") ;
            //Check prompt is matching
            D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
            //hug up the call
    } catch (Exception ex) {

      catch_Exeption(ex);

    } finally {
      finaly_Exeption();
      updateResults();
      Log.endTestCase(test_Case_Name);
    }

  }

}