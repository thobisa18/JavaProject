package Call_Flow_Scenarios.Deep_Link_Call_Flows;
/*
 * Author: Monono Thobisa F52088882
 * <p>
 * This class/scenario verify if NLP will play educational msg when customer doesnt have
 * a verified device
 * The test case ID is TC_05
 */
    import Call_Flow_Scenarios.Generic_Scenario;
    import Libraries.Debit_Order_Library.Debit_Order;
    import com.aventstack.extentreports.Status;
    import java.util.stream.Collectors;
    import org.testng.annotations.BeforeClass;
    import org.testng.annotations.Test;
    import Config.*;

public class Customer_No_Trusted_Device extends Generic_Scenario
{

  @BeforeClass
  public void start_Test()
  {
      getData_And_Reports("DeepLink_Exception", "Test_Data");
  }

  @Test
  public void Testing_Customer_No_Trusted_Device ()
  {
    try {
      //Get test data for the TC_05 from the data sheet and create collection of it
      test_data_tc_01=test_data.stream()
          .filter(datad->datad.getTest_CaSe_ID().equals("TC_05"))
          .collect(Collectors.toList());
      //Get test case ID
      TC_ID=test_data_tc_01.get(0).getTest_CaSe_ID();
      //Create test for the report
      test=report.createTest(test_data_tc_01.get(0).getTest_Case_Name());
      //Add to the logs that the test case has started
      Log.startTestCase(test_data_tc_01.get(0).getTest_Case_Name());
      //Get Test case name from the collection
      test_Case_Name=test_data_tc_01.get(0).getTest_Case_Name();
      //create instance for debit order( it contains all the methods required to communicate with omillia)
      test.log(Status.PASS,"This scenario was executed on '"+test_data_tc_01.get(0).getSegment()+"' Dnis");
      D_order = new Debit_Order(test,cons.getDnisBySegment(test_data_tc_01.get(0).getSegment()));
      //step 0 Make a calll
      dialogID=D_order.MakeCall();
      // Pass an utterance to the BOT
      D_order.pass_Data("Reverse debit order",dialogID,"Ask.IdNumber","Ask.Intent");
      //Pass an ID number 6001015523683
      D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Announce.DeepLinkEducationalMessage",
          "Ask.IdNumber") ;
      // Verify the educational msg
      D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());

    }
    catch (Exception ex)
    {
      catch_Exeption(ex);
    }
    finally {
      D_order.hug_Up(dialogID);
      updateResults();
      Log.endTestCase(test_Case_Name);
    }


  }

}