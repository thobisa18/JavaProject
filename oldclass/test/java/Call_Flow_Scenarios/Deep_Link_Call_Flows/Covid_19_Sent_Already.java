package Call_Flow_Scenarios.Deep_Link_Call_Flows;
/*
 * Author: Monono Thobisa F52088882
 * <p>
 * This class/scenario verify if NLP can announce 'New Covid-19 applications'
 * are not accepted
 * The test case ID is TC_15
 */
import Call_Flow_Scenarios.Generic_Scenario;
import Config.Log;
import Libraries.Debit_Order_Library.Debit_Order;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Covid_19_Sent_Already extends Generic_Scenario
{
  @BeforeClass
  public void start_Test()
  {
    getData_And_Reports("DeepLink_Exception", "Test_Data");
  }
  @Test
  public void Testing_Covid_19_Sent_Already ()
  {
    try {

      //Get test data from deepLink test data returns all rows
      test_data_tc_01 = test_data.stream()
          .filter(datad->datad.getTest_CaSe_ID().equals("TC_15"))
          .collect(Collectors.toList());
      test_Case_Name="Verify if NLP will play 'No new application for covid-19' for two times when a customer has request for two covid-19 application in one call";
      test = report.createTest(test_Case_Name);
      //Add to the logs that the test case has started
      Log.startTestCase(test_Case_Name);
      //create instance for debit order( it contains all the methods required to communicate with omillia)
      D_order = new Debit_Order(test);
      //step 0  create dailog ID
      dialogID = D_order.MakeCall();
      // Provide intent/ pass utterance
      D_order.pass_Data("how do I apply for cash flow relief", dialogID, "Confirm.CovidCashflowReliefExtension", "Ask.Intent");
      //Confirm the customer is applying for new covid-19 application
      D_order.pass_Data("No", dialogID,"Announce.CovidNoNewApplicationsAccepted","Confirm.CovidCashflowReliefExtension" );
      //Verify if NPL played the announcement "No new application for covid 19"
      D_order.verify_Prompt(test_data_tc_01.get(0).getPrompt());
      D_order.pass_Data("", dialogID,"Ask.Intent","Announce.CovidNoNewApplicationsAccepted");
      //Provide covid-19 utterance for the second time
      D_order.pass_Data(test_data_tc_01.get(0).getAfterIntent(), dialogID, "Confirm.CovidCashflowReliefExtension", "Ask.Intent");
      //Confirm the customer is applying for new covid-19 application
      D_order.pass_Data("No", dialogID,"Announce.CovidNoNewApplicationsAccepted","Confirm.CovidCashflowReliefExtension" );
      D_order.pass_Data("", dialogID,"Ask.Intent","Announce.CovidNoNewApplicationsAccepted");
      D_order.pass_Data("No Thank you", dialogID, "Exit", "Ask.Intent");

    } catch (Exception ex) {
      catch_Exeption(ex);
    } finally {
      finaly_Exeption();
      updateResults();
      Log.endTestCase(test_Case_Name);
    }

  }


}

