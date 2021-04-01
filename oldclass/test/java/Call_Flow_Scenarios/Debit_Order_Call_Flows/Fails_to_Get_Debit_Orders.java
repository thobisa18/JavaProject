
package Call_Flow_Scenarios.Debit_Order_Call_Flows;
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

public class Fails_to_Get_Debit_Orders extends Generic_Scenario
{

  // Debit_Order_Excel_Data data_R= new Debit_Order_Excel_Data();
  @BeforeClass
  public void start_Test()
  {
    datasheet = new Read_Excel_File();
    test_data=convertData(datasheet.GetTestData("debit", "Test_Data"));
    re= Report_Generator.getInstance();
    report=re.getReport();
    test= re.getTest();
    data_R= getInstanceOfDebitOrder();
  }

  @Test
  public void Testing () {
    try{
    TC_ID="TC_13";
    //List<Debit_Order_Converted_Data> test_data_tc_01;
    //Get test data for the TC_02 from the data sheet and create collection of it
    test_data_tc_01=test_data.stream()
        .filter(datad->datad.getTest_CaSe_ID().equals("TC_13"))
        .collect(Collectors.toList());
    //Get Test case name from the collection
    test_Case_Name=test_data_tc_01.get(0).getTest_Case_Name();
    //Create test for the report
    test=report.createTest( test_Case_Name);
    //Add to the logs that the test case has started
    Log.startTestCase( test_Case_Name);

    //create instance for debit order( it contains all the methods required to communicate with omillia)
    D_order=new Debit_Order(test);
    //Authenticate();
    //step 0  create dailog ID
    dialogID = D_order.MakeCall();
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>when Adding a new test scenario start changing here<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>Rember the every steps depends on your data <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // D_order.getIntent(" Debit order");
    D_order.pass_Data(test_data_tc_01.get(0).getUtterance(), dialogID, "Confirm.DisputeTransaction", "Ask.Intent");
    //Confirm dispute transaction
    D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Itent(), dialogID, "Ask.IdNumber", "Confirm.DisputeTransaction");
    //Enter ID number8410032545470,0109244863089,7912075015270,8805028128274
    D_order.pass_Data("8707170646089", dialogID, "Confirm.DeepLinkDevice", "Ask.IdNumber");
    D_order.pass_Data("no", dialogID, "Announce.DeepLinkEducationalMessage", "Confirm.DeepLinkDevice");
    //Enter Card number
    //D_order.pass_Data(test_data_tc_01.get(0).getCard_Number(), dialogID, "Ask.Pin", "Ask.CardNumber");
    //Enter Pin
   // D_order.pass_Data(test_data_tc_01.get(0).getPin_Number(), dialogID, "Ask.ReverseStop", "Ask.Pin");
    //Confirm if you wanna Stop
      D_order.pass_Data("",dialogID,"Ask.ReverseStop@Ask.CardNumber","Announce.DeepLinkEducationalMessage");
      Authenticate();
    D_order.pass_Data(test_data_tc_01.get(0).getReverse_Stop(), dialogID, "Announce.ReactionBeforeTransfer", "Ask.ReverseStop");
    //Provide the marchent name
    D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());

    }catch (Exception ex){
      D_order.setPreviousFalse();
      test.log(Status.FAIL, "Error happened: " + ex);
      Log.error("Error happened: " + ex);
    }
    finally {
      D_order.hug_Up(dialogID);
      updateResults();

    }

    //D_order.getNumberTransferedTo(test_data_tc_01.get(0).getNumberTransferedTo());
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>End changes here<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  }

  @AfterClass
  public void endTestCase()
  {
    Log.endTestCase(test_Case_Name);
  }
}
