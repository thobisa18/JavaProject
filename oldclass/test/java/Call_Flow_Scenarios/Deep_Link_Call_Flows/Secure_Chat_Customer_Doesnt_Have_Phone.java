package Call_Flow_Scenarios.Deep_Link_Call_Flows;

import static All_Reports.Excel_Report.Debit_Order_Excel_Data.getInstanceOfDebitOrder;

import All_Reports.Extent_Report.Report_Generator;
import Call_Flow_Scenarios.Generic_Scenario;
import All_Reports.Excel_Report.Read_Excel_File;
import Libraries.Debit_Order_Library.Debit_Order;
import Libraries.Debit_Order_Library.Debit_Order_Converted_Data;
import com.aventstack.extentreports.Status;
import java.util.List;
import java.util.stream.Collectors;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Config.*;

public class Secure_Chat_Customer_Doesnt_Have_Phone extends Generic_Scenario
{

  @BeforeClass
  public void start_Test()
  {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }
  @Test
  public void Testing ()
  {
    try {
      TC_ID="TC_06";
      List<Debit_Order_Converted_Data> test_data_tc_01;
      //Get test data for the TC_02 from the data sheet and create collection of it
      test_data_tc_01=test_data.stream()
          .filter(datad->datad.getTest_CaSe_ID().equals("TC_09"))
          .collect(Collectors.toList());

      //Get Test case name from the collection
      test_Case_Name=test_data_tc_01.get(0).getTest_Case_Name();
      //Create test for the report
      test=report.createTest(test_Case_Name);
      //Add to the logs that the test case has started
      Log.startTestCase(test_Case_Name);

      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order=new Debit_Order(test);
      //step 0 Make a calll
      dialogID=D_order.MakeCall();
      // Pass an utterance to the BOT
      D_order.pass_Data(test_data_tc_01.get(0).getUtterance(),dialogID,"Ask.IdNumber","Ask.Intent");
      //Pass an ID number
      D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Confirm.DeepLinkDevice",
          "Ask.IdNumber") ;
      // Confirm the customer is not having a device
      D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Itent(), dialogID, "Announce.SecureChatEducationalMessage",
          "Confirm.DeepLinkDevice") ;
      // Verify the educational msg
      D_order.verify_Prompt("long queues");

    }
    catch (Exception ex)
    {
      // smart.setResult();
      test.log(Status.FAIL, "Error happened: "+ex);
      Log.error("Error happened: "+ex);
    }
    finally {
      D_order.hug_Up(dialogID);
      updateResults();
    }


  }



 // @AfterClass
  public void endTestCase()
  {
    Log.endTestCase(test_Case_Name);
  }
}