package Call_Flow_Scenarios.Deep_Link_Call_Flows;
    import Call_Flow_Scenarios.Generic_Scenario;
    import Libraries.Debit_Order_Library.Debit_Order;
    import java.util.stream.Collectors;
    import org.testng.annotations.BeforeClass;
    import org.testng.annotations.Test;
    import Config.*;

public class Three_Attemps_Failed_Device_Comfirmation extends Generic_Scenario
{
  @BeforeClass
  public void start_Test()
  {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }
  @Test
  public void Testing_Last_Attempt()
  {
try {
  //Get test data for the TC_02 from the data sheet and create collection of it
  test_data_tc_01 = test_data.stream()
      .filter(datad -> datad.getTest_CaSe_ID().equals("TC_03"))
      .collect(Collectors.toList());
  TC_ID = test_data_tc_01.get(0).getTest_CaSe_ID();
  //Get Test case name from the collection
  test_Case_Name = test_data_tc_01.get(0).getTest_Case_Name();
  //Create test for the report
  test = report.createTest(test_Case_Name);
  //Add to the logs that the test case has started
  Log.startTestCase(test_Case_Name);

  //create instance for debit order( it contains all the methods required to communicate with omillia)
  D_order = new Debit_Order(test);
  //step 0  create dailog ID
  dialogID = D_order.MakeCall();
  // D_order.getIntent(" Debit order");
  D_order.pass_Data(test_data_tc_01.get(0).getUtterance(), dialogID, "Ask.IdNumber", "Ask.Intent");

  //Pass ID to the BOT
  D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Confirm.DeepLinkDevice",
      "Ask.IdNumber");
  //do not Confrim the customer has deep link
  D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Devive(), dialogID, "Confirm.DeepLinkDevice",
      "Confirm.DeepLinkDevice");

  //do not Confrim the customer has deep link
  D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Devive(), dialogID, "Confirm.DeepLinkDevice",
      "Confirm.DeepLinkDevice");
  //do not Confrim the customer has deep link
  D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Devive(), dialogID,
      "Announce.ReactionBeforeTransfer",
      "Confirm.DeepLinkDevice");
  //Verify prompts
  D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
  //Deep_Link dsa=new Deep_Link(test,D_order);da
  D_order.hug_Up(dialogID);

} catch (Exception ex) {

  catch_Exeption(ex);

} finally {
  finaly_Exeption();
  updateResults();

}

  }
}