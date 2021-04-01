package Call_Flow_Scenarios.Deep_Link_Call_Flows;
import Call_Flow_Scenarios.Generic_Scenario;
import Config.Log;
import Libraries.Debit_Order_Library.Debit_Order;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Not_Providing_Correct_Answer_on_Covid_Appy_Or_FollowUp extends Generic_Scenario {

  @BeforeClass
  public void start_Test()
  {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }
  @Test
  public void Testing_Not_Providing_Correct_Answer ()
  {

    try {
      //Get test data from deepLink test data returns all rows
      test_data_tc_01 = test_data.stream()
          .filter(datad -> datad.getTest_CaSe_ID().equals("TC_12"))
          .collect(Collectors.toList());
      test_Case_Name = "Verify if the BOT will transfer the customer on third attempt when customer is not providing a correct answer to 'Confirm.CovidCashflowReliefExtension' step ";
      test = report.createTest(test_Case_Name);

      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order = new Debit_Order(test);
      dialogID = D_order.MakeCall();
      //Add to the logs that the test case has started
      Log.startTestCase(test_Case_Name);
      // First intent
      D_order.pass_Data(test_data_tc_01.get(0).getUtterance(), dialogID, "Confirm.CovidCashflowReliefExtension", "Ask.Intent");
      //Check if intent are matching
      D_order.check_Intent(test_data_tc_01.get(0).getIntent());
      D_order.pass_Data(" Dolo Dolo", dialogID,"Confirm.CovidCashflowReliefExtension","Confirm.CovidCashflowReliefExtension" );
      D_order.pass_Data("Do Lo Do Lo", dialogID,"Confirm.CovidCashflowReliefExtension","Confirm.CovidCashflowReliefExtension" );
      D_order.pass_Data(" Apply", dialogID,"Announce.ReactionBeforeTransfer","Confirm.CovidCashflowReliefExtension" );
    } catch (Exception ex) {

      catch_Exeption(ex);

    } finally {
      finaly_Exeption();
      updateResults();
      Log.endTestCase(test_Case_Name);
    }

  }


}


