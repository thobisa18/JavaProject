package Mobile_App_Repository;

import Config.*;
import Mobile_App_Repository.Repository_Elements.Mobile_Elements;
import Libraries.Debit_Order_Library.Debit_Order;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.annotations.AfterClass;

public class Smart_Incontact extends Mobile_Elements
{

  public Smart_Incontact(ExtentTest testP,Debit_Order debitP) {

    test = testP;
    test.log(Status.INFO, "Launch Appium for Smart InCcontact ");
    debit=debitP;

  }

    public void Approve_Smart_Incontact()
    {
      //Click approve button

      Log.info("About to click 'Approve' button for smart incontact");
      try{
        test.log(Status.PASS, "Screenshot before Clicking 'Approve' button ", MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
      Approve_Button().click();
      Log.info("'Approve' button was clicked for smart incontact");
      if (Thank_You_Msg().isDisplayed())
      {

        PreviousStepResults=true;
        Comments="Smart in contact was approved  successfully";
        Log.info(Comments);
        test.log(Status.PASS, Comments);
        test.log(Status.PASS, "Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
        finish_Button().click();
      }
      else
      {
        PreviousStepResults=false;
        test.log(Status.FAIL, "Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
        Comments="Smart in contact was not approved  successfully";
        Log.info(Comments);
        test.log(Status.PASS, Comments);
      }

    }
    catch (Exception ex)
  {
    PreviousStepResults=false;
   // test.log(Status.ERROR, "Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
    Log.error("Error occurred: "+ex.getMessage());
    Comments="Error occurred: "+ex.getMessage();
    test.log(Status.ERROR,"Error occurred: "+ex.getMessage());
  }
      result[0][0] = String.valueOf(PreviousStepResults);
      result[0][1] = Comments;
    }

  public void Decline_Smart_Incontact()
  {
    //Click decline button
    Log.info("About to click 'Decline' button for smart incontact");
    try {
      test.log(Status.PASS, "Screenshot before Clicking 'Decline' button ", MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
      Decline_Button().click();
      Log.info("'Decline' button was clicked for smart incontact");
      if (Cancelled_Msg().isDisplayed()) {
        PreviousStepResults=true;
        test.log(Status.PASS, "Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
        cancel_Button().click();
        Comments="Smart in contact was declined  successfully";
        Log.info(Comments);
        test.log(Status.PASS, Comments);
      } else {
        PreviousStepResults=false;
        test.log(Status.FAIL, "Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
        Comments="Smart in contact was not declined  successfully";
        Log.info(Comments);
        test.log(Status.PASS, Comments);
      }
    }
    catch (Exception ex)
    {
      PreviousStepResults=false;
      Log.error("Error occurred: "+ex.getMessage());
      Comments="Error occurred: "+ex.getMessage();
      test.log(Status.ERROR,"Error occurred: "+ex.getMessage());
    }
    result[0][0] = String.valueOf(PreviousStepResults);
    result[0][1] = Comments;
  }
  public  void Cancel_Smart_Incontact_Request()
  {
    //Click decline button
    Log.info("About to click 'Cancel' button for smart incontact");
    try {

      cancel_Button1().click();
      Log.info("'Cancel' button was clicked for smart incontact");
      if (Login_Button().isDisplayed()) {
        PreviousStepResults=true;
        //cancel_But.click();
        test.log(Status.PASS, "Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
        Comments="Smart in contact was cancelled  successfully";
        Log.info(Comments);
        test.log(Status.PASS, Comments);
      } else {
        PreviousStepResults=false;
        test.log(Status.FAIL, "Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
        Comments="Smart in contact was not cancelled  successfully";
        Log.info(Comments);
        test.log(Status.PASS, Comments);
      }
    }
    catch (Exception ex)
    {
      PreviousStepResults=false;
      Comments=ex.getMessage();
      Log.error(ex.getMessage());
      test.log(Status.ERROR,ex.getMessage());
    }
    result[0][0] = String.valueOf(PreviousStepResults);
    result[0][1] = Comments;
  }
  @AfterClass
  public void test()
  {
    debit.setEndResults(result);
  }
}
