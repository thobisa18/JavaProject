
package Call_Flow_Scenarios.Deep_Link_Call_Flows;
import Call_Flow_Scenarios.Generic_Scenario;
import Config.Log;
import Libraries.Debit_Order_Library.Debit_Order;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;


public class Follow_Up_Covid_Intent extends Generic_Scenario  implements IAnnotationTransformer {
  int i = 0;

  public Follow_Up_Covid_Intent() {
    getData_And_Reports("Covid_19", "Test_Data");
  }
  @Override
  public void transform(ITestAnnotation annotation, Class testclass, Constructor testConstructor, Method testMethod)
  {
    if (testMethod.getName().equals("Testing_Follow_Up_Covid"))
    {
      annotation.setInvocationCount(test_data_tc_01.size());
    }
  }

  @Test
  public void Testing_Follow_Up_Covid() {
    TC_ID=test_data_tc_01.get(i).getTest_CaSe_ID();
   if(!test_data_tc_01.get(i).getIntent().equals("Covid.Application.New"))
    try {
      test_Case_Name = "Verify if the Covid-19 deep link intent ("+test_data_tc_01.get(i).getIntent()+ ") will be transferred to an agent when following up on Application ";
      test = report.createTest( test_Case_Name);
      //Add to the logs that the test case has started
      Log.startTestCase( test_Case_Name);
      //Get Test case name from the collection
      D_order = new Debit_Order(test);
      //step 0  create dailog ID
      dialogID = D_order.MakeCall();
      // Provide intent
      D_order
          .pass_Data(test_data_tc_01.get(i).getUtterance(), dialogID, "Confirm.CovidCashflowReliefExtension", "Ask.Intent");
      //Check if intent are matching
      D_order.check_Intent(test_data_tc_01.get(i).getIntent());
      //Pass yes to BOT
      if(D_order.getNextStep().contains("Confirm.CovidCashflowReliefExtension"))
      D_order.pass_Data("Yes", dialogID, "Announce.ReactionBeforeTransfer", "Confirm.CovidCashflowReliefExtension");
     //Transfer call
      D_order.hug_Up(dialogID);

    } catch (Exception ex) {

      catch_Exeption(ex);

    } finally {
      finaly_Exeption();
      updateResults();
      Log.endTestCase(test_Case_Name);
    }
    i++;
    }
  }
