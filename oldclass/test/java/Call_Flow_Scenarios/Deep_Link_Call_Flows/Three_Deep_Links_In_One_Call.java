package Call_Flow_Scenarios.Deep_Link_Call_Flows;
import Call_Flow_Scenarios.Generic_Scenario;
import Libraries.Debit_Order_Library.Debit_Order_Converted_Data;
import Config.Log;
import Libraries.Debit_Order_Library.Debit_Order;
import java.util.List;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Three_Deep_Links_In_One_Call extends Generic_Scenario {

  @BeforeClass
  public void start_Test()
  {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }
  @Test
  public void Testing_Send_Three_Deep_Link_One_Call ()
  {
    try {
      //Get test data from deepLink test data returns all rows
      test_data_tc_01 = test_data.stream()
          .filter(datad -> datad.getTest_CaSe_ID().equals("TC_08"))
          .collect(Collectors.toList());
      test_Case_Name = "Verify if a customer can send three deep links in one call";
      test = report.createTest(test_Case_Name);
      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order = new Debit_Order(test);
      dialogID = D_order.MakeCall();
      //Add to the logs that the test case has started
      Log.startTestCase(test_Case_Name);

      // First intent
      D_order.pass_Data(test_data_tc_01.get(0).getUtterance(), dialogID, "Ask.IdNumber", "Ask.Intent");
      //Check if intent are matching
      D_order.check_Intent(test_data_tc_01.get(0).getIntent());
      D_order.pass_Data(test_data_tc_01.get(0).getID_Number(),dialogID,"Confirm.DeepLinkDevice","Ask.IdNumber");
      D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Devive(),dialogID,"Announce.HasDeepLinkCompleted","Confirm.DeepLinkDevice");
      D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
      D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.HasDeepLinkCompleted");

      //Second Intent
      List<Debit_Order_Converted_Data> test_data_tc_02;
      test_data_tc_02 = test_data.stream()
          .filter(datad -> datad.getTest_CaSe_ID().equals("TC_11"))
          .collect(Collectors.toList());
      D_order.pass_Data(test_data_tc_02.get(0).getUtterance(),dialogID,"Announce.HasDeepLinkCompleted","Ask.Intent");
      D_order.verify_Prompt(test_data_tc_02.get(0).getPrompt());
      D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.HasDeepLinkCompleted");

      //Third intent
      List<Debit_Order_Converted_Data> test_data_tc_03;
      test_data_tc_03 = test_data.stream()
          .filter(datad -> datad.getTest_CaSe_ID().equals("TC_10"))
          .collect(Collectors.toList());
      D_order.pass_Data(test_data_tc_03.get(0).getUtterance(),dialogID,"Announce.HasDeepLinkCompleted","Ask.Intent");
      D_order.verify_Prompt(test_data_tc_02.get(0).getPrompt());
      D_order.pass_Data("", dialogID, "Ask.Intent", "Announce.HasDeepLinkCompleted");
      D_order.pass_Data(test_data_tc_03.get(0).getAfterIntent(), dialogID, "Exit", "Ask.Intent");
    } catch (Exception ex) {

      catch_Exeption(ex);

    } finally {
      finaly_Exeption();
      updateResults();
      Log.endTestCase(test_Case_Name);
    }

  }

}


