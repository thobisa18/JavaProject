
package Call_Flow_Scenarios.Deep_Link_Call_Flows;

    import static All_Reports.Excel_Report.Debit_Order_Excel_Data.getInstanceOfDebitOrder;

    import Call_Flow_Scenarios.Generic_Scenario;
    import Libraries.Debit_Order_Library.Debit_Order_Converted_Data;
    import Config.Log;
    import All_Reports.Excel_Report.Read_Excel_File;
    import All_Reports.Extent_Report.Report_Generator;
    import Libraries.Debit_Order_Library.Debit_Order;
    import Mobile_App_Repository.Deep_Link;
    import com.aventstack.extentreports.Status;
    import java.util.List;
    import java.util.concurrent.atomic.AtomicInteger;
    import java.util.stream.Collectors;
    import org.testng.Assert;
    import org.testng.annotations.Test;


public class TestInt extends Generic_Scenario {
  int i = 0;
  int rowsS = 2;
  List<Debit_Order_Converted_Data> test_data_tc_01;
  int rows=0;
  public final int invocation_times=56;//Please The 2 is the total number of records in the spreadsheet
  public TestInt() {
    getData_And_Reports("Chat", "Test_Data");
  }

  AtomicInteger sequence = new AtomicInteger(0);
  @Test(invocationCount = invocation_times)
  public void Testing() {
    TC_ID=test_data_tc_01.get(i).getTest_CaSe_ID();
    try {
      //Get Test case name from the collection
      test_Case_Name = test_data_tc_01.get(i).getTest_Case_Name();
      test = report.createTest( test_Case_Name);
      //Add to the logs that the test case has started
      Log.startTestCase( test_Case_Name);

      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order = new Debit_Order(test);
      //step 0  create dailog ID
      dialogID = D_order.MakeCall();
      // Provide intent
      D_order
          .pass_Data(test_data_tc_01.get(i).getUtterance(), dialogID, "Ask.IdNumber", "Ask.Intent");
      //Check if intent are matching
      D_order.check_Intent(test_data_tc_01.get(i).getIntent());
      //Capture ID Number
      D_order.pass_Data(test_data_tc_01.get(i).getID_Number(), dialogID, "Confirm.DeepLinkDevice",
          "Ask.IdNumber");
      //Confirm customer has device
      D_order.pass_Data(test_data_tc_01.get(i).getConfirm_Devive(), dialogID,
          "Announce.SecureChatDeepLinkHasCompleted", "Confirm.DeepLinkDevice");
      D_order.verify_Prompt(test_data_tc_01.get(i).getPrompt());
      D_order.pass_Data("", dialogID,"Exit","Announce.SecureChatDeepLinkHasCompleted");
      //D_order.pass_Data(test_data_tc_01.get(i).getAfterIntent(), dialogID, "Exit", "Ask.Intent");
      results = D_order.getEndResults();
      if (Boolean.parseBoolean(results[0][0])) {
       Deep_Link ds = new Deep_Link(test, D_order);
       ds.view_deepLink(test_data_tc_01.get(i).getDeepLnkUI_ID());

      } else {
        D_order.hug_Up(dialogID);

      }
    } catch (Exception ex) {

      catch_Exeption(ex);

    } finally {
      finaly_Exeption();
    }
    updateResults();
  }

  public void endTestCase()
  {
    Log.endTestCase(test_Case_Name);
  }
}



