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

public class Stop_Debit_Order extends Generic_Scenario
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
  public void Testing ()
  {
    try{
    TC_ID="TC_04";
   // List<Debit_Order_Converted_Data> test_data_tc_01;
    //Get test data for the TC_02 from the data sheet and create collection of it
    test_data_tc_01=test_data.stream()
        .filter(datad->datad.getTest_CaSe_ID().equals("TC_04"))
        .collect(Collectors.toList());
    //Get Test case name from the collection
    test_Case_Name=test_data_tc_01.get(0).getTest_Case_Name();
    //Create test for the report
    test=report.createTest(test_Case_Name);
    //Add to the logs that the test case has started
    Log.startTestCase(test_Case_Name);

    //create instance for debit order( it contains all the methods required to communicate with omillia)
    D_order=new Debit_Order(test);
    //step 0  create dailog ID
    dialogID = D_order.MakeCall();
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>when Adding a new test scenario start changing here<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>Rember the every steps depends on your data <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // D_order.getIntent(" Debit order");

      D_order.pass_Data(test_data_tc_01.get(0).getUtterance(),dialogID,"Confirm.DisputeTransaction","Ask.Intent");
      //Confirm dispute transaction
      D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Itent(),dialogID,"Ask.IdNumber","Confirm.DisputeTransaction");
      //Enter ID number
      D_order.pass_Data(test_data_tc_01.get(0).getID_Number(), dialogID, "Announce.DeepLinkEducationalMessage@Confirm.DeepLinkDevice",
          "Ask.IdNumber");

Authenticate();
      //Confirm if you wanna reverse
      D_order.pass_Data("", dialogID, "Ask.ReverseStop@Announce.DebitOrderOneAtTime@Ask.CardNumber", "Announce.DeepLinkEducationalMessage");
      Authenticate();
      //D_order.pass_Data(test_data_tc_01.get(0).getReverse_Stop(), dialogID, "Announce.DebitOrderOneAtTime", "Ask.ReverseStop");
    //Confirm if you wanna Stop
    D_order.pass_Data(test_data_tc_01.get(0).getReverse_Stop(), dialogID, "Announce.MoreThanOneDebitOrder", "Ask.ReverseStop");
    //Provide the marchent name
    D_order.pass_Data("", dialogID, "Ask.Merchant", "Announce.MoreThanOneDebitOrder");

    D_order.pass_Data(test_data_tc_01.get(0).getMarchent(), dialogID, "Ask.SkipTerms", "Ask.Merchant");

    //D_order.pass_Data("",dialogID,"Ask.Merchant","Announce.DebitOrdersToMenu");
   // D_order.pass_Data(test_data_tc_01.get(0).getMultiple(),dialogID,"Ask.SkipTerms","Ask.Merchant");
    //Skip terms and condictions
    D_order.pass_Data(test_data_tc_01.get(0).getSkip_terms(),dialogID,"Confirm.TermsAcceptance","Ask.SkipTerms");
    //Confirm terms and condictions
    D_order.pass_Data(test_data_tc_01.get(0).getConfirm_Terms(),dialogID,"Ask.DisputeReason","Confirm.TermsAcceptance");
    //provide dispute reason
    D_order.pass_Data(test_data_tc_01.get(0).getDisputeReason(),dialogID,"Confirm.DisputeOrder","Ask.DisputeReason");
    //confirm debit order
    D_order.pass_Data("yes",dialogID,"Announce.ReactionBeforeTransfer","Confirm.DisputeOrder");
    //D_order.No_InputDisputed(dialogID);
   /// D_order.pass_Data("No Thank you",dialogID,"Announce.DisputeDebitOrder","Confirm.DisputeOrder");
    }catch (Exception ex){
      D_order.setPreviousFalse();
      test.log(Status.FAIL, "Error happened: " + ex);
      Log.error("Error happened: " + ex);
    }
    finally {
      D_order.hug_Up(dialogID);
      updateResults();

    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>End changes here<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  }

  @AfterClass
  public void endTestCase()
  {
    Log.endTestCase(test_Case_Name);
  }
}
