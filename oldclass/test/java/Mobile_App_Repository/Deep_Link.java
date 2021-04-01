package Mobile_App_Repository;

import Config.Log;
import Config.constants;
import Libraries.Deep_Link_Library.Lookup_DeepLink;
import Config.Take_Screenshot;
import Mobile_App_Repository.Repository_Elements.Mobile_Elements;
import Libraries.Debit_Order_Library.Debit_Order;
import Libraries.Deep_Link_Library.smart_call_Routing_DB_DeepLink;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import java.util.concurrent.TimeUnit;
import javax.xml.bind.Element;
import org.testng.annotations.AfterClass;

public class Deep_Link extends Mobile_Elements {

  String st1_heading;
  String st2_Label;
  String str3_Msg_Text;
  String str4_msg_Preview;
  Lookup_DeepLink deepLink;
  String endResults;
  boolean flag=true;

  public Deep_Link(ExtentTest testP,Debit_Order debitP) {

    test = testP;
    test.log(Status.INFO, "Launch Appium for Smart InCcontact ");
    debit=debitP;
    debit.setPrevious();

  }

  public void Login_Method_DeepLink()
  {
    if(PreviousStepResults) {
      Log.info("About to capture password");
      try {
        if (forgoten_password() == 0) {
          PreviousStepResults = true;
          Log.info("Login was not executed, customer was already logged successful");
          test.log(Status.PASS, "Login was not executed, customer was already logged successful");
          test.log(Status.PASS, "View deep link destination",
              MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver))
                  .build());
        } else {
          Log.info("About to capture password");
          password().sendKeys("asdfgh");
          test.log(Status.PASS, "Capture password",
              MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver))
                  .build());
          Log.info("Password is captured");
          test.log(Status.PASS, "Password is captured");
          Logon_Button_1().click();
          waitForSeconds(15);
          if (forgoten_password() == 0) {
            PreviousStepResults = true;
            test.log(Status.PASS, "Login was successful");
            test.log(Status.PASS, "View deep link destination",
                MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver))
                    .build());
          } else {
            test.log(Status.FAIL, "Login was not successful");
            test.log(Status.FAIL, "Check screenshot capture",
                MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver))
                    .build());
          }
        }
      } catch (Exception ex) {
        Log.error("Login Method Error occurred: " + ex.getMessage());
        Comments = "Login Method Error occurred: " + ex.getMessage();
        test.log(Status.ERROR, "Login Method Error occurred: " + ex.getMessage());
      }
      result[0][0] = String.valueOf(PreviousStepResults);
      result[0][1] = Comments;
    }
  }

  public void Login_Method()
  {
    //Click decline button
    Log.info("About to click 'Login' button on FNB App");
    try {

      Login_Button().click();
      Log.info("'Login' button was clicked for deep link");
      password().sendKeys("asdfgh");
      test.log(Status.INFO,"Password was captured");
      Login_Button().click();
      test.log(Status.INFO,"Login button clicked");

      if (cancel_Button().isDisplayed()) {
        Comments="Login was successfully";
        Log.info(Comments);
        PreviousStepResults=true;
        test.log(Status.PASS, "Screenshot", MediaEntityBuilder
           .createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
        test.log(Status.PASS, Comments);

      } else {
        test.log(Status.FAIL, "Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
        Comments="Login was not declined  successfully";
        Log.info(Comments);
        test.log(Status.PASS, Comments);
      }


    }
    catch (Exception ex)
    {
      Log.error("Login Method Error occurred: "+ex.getMessage());
      Comments="Login Method Error occurred: "+ex.getMessage();
      test.log(Status.ERROR,"Login Method Error occurred: "+ex.getMessage());
    }
    result[0][0] = String.valueOf(PreviousStepResults);
    result[0][1] = Comments;
  }


public void view_deepLink(String deep_link_uiid)
{
  waitForSeconds(20);
  PreviousStepResults=true;
  try {
    smart_call_Routing_DB_DeepLink smt=new smart_call_Routing_DB_DeepLink();
    deepLink=smt.get_lookup_deeplink_By_deep_link_uiid(deep_link_uiid);
    Log.info("About to click 'Messages' button");
    //Capture screenshot before clicking 'Messages' button
    test.log(Status.INFO, "About to click 'Messages' button", MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
   //Click 'Messages' button

      messages_Button().click();
    //Wait for the "Messages" to be displayed
    waitForSeconds(10);
    //Verify is 'Preview' messege is displayed
    if(Preview_Msg().isDisplayed())
    {
      Log.info("Clicked 'Messages' button successful");
      test.log(Status.PASS, "Clicked 'Messages' button successful", MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
      //Get expected preview messege from the Database
      str4_msg_Preview=Preview_Msg().getText();
      Log.info("Msg Preview :"+str4_msg_Preview);
      String sd=deepLink.getMsg_Preview();
      //Compare actual Preview msg with the expected
      compareToString(deepLink.getMsg_Preview(),str4_msg_Preview,"Message Preview are matching, Expected Preview Message : ' ");

    }
    else
      {
        PreviousStepResults=false;
        debit.setEndResults(false,"Not Clicked 'Messages' button successful");
      Log.info("Not Clicked 'Messages' button successful");
      test.log(Status.FAIL, "Not Clicked 'Messages' button successful",
          MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());

    }



    if(PreviousStepResults) {
      Log.info("About to open 'Just for You' message");
    //Click "Jst for you" message
      Top_Just_For_Msg().click();
    //Wiat for it to display messages
    waitForSeconds(20);
    //Verify if heading is displayed
    if(Msg_Heading().isDisplayed()) {
      Log.info("Clicked 'Messages' button successful");
      test.log(Status.PASS, "Clicked 'Messages' button successful",
          MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());

      //Get Message Title from Database and compare with the one displayed on Jst For messages
      st1_heading = Msg_Heading().getText();
      compareToString(deepLink.getMsg_heading(), st1_heading,
          "Verify if the 'Message Title' is per as expected: Expected heading: '");
      //Get Message body from Database and compare with the one displayed on Jst For messages
      str3_Msg_Text = Msg_text().getText();
      compareToString(deepLink.getMsg_Text(), str3_Msg_Text,
          "Verify if the 'Message body' is per as expected:  Expected heading: '");
      //Get Button text from Database and compare with the one displayed on Jst For messages
      st2_Label = Button_text().getText();
      compareToString(deepLink.getMsg_label(), st2_Label,
          "Verify if the 'Button text' is as per as expected: Expected heading: '");
      if (PreviousStepResults) {
        Button_text().click();
        waitForSeconds(10);
        Login_Method_DeepLink();
        verifyItem_Displayed(constants.GetTextToBeDisplayed(deep_link_uiid));
      }

    }
    else
      {
        PreviousStepResults=false;
        debit.setEndResults(false,"Not Clicked 'Just for You Messages' button successful");
         Log.info("Not Clicked 'Just for You Messages' button successful");
         test.log(Status.FAIL, "Not Clicked 'Just for You Messages' button successful",
             MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver)).build());
      }
    }

   }
  catch (Exception ex)
  {
    PreviousStepResults=false;
    debit.setEndResults(false,"Error occurred when viewing DeepLink Msg, error: "+ex.getMessage());
    Log.info("Error occurred when viewing DeepLink Msg, error: "+ex.getMessage());
    test.log(Status.FAIL,"Error occurred when viewing DeepLink Msg, error: "+ex.getMessage());
  }


}

  public void compareToString(String ExpectedString,String ActualString, String comment)
  {
  //  if(PreviousStepResults)
    if(flag) {
      Log.info(
          "String to compare : Expected =" + ExpectedString + ", Actual string =+" + ActualString
              + "Comment =" + comment);
      try {

        String ExpectedString1 = ExpectedString.replaceAll("\\s", "");
        String ActualString1 = ActualString.replaceAll("\\s", "");
        //System.out.println(ExpectedString + "" + ActualString);
        //Check if two strings are equal
        if (ExpectedString1.trim().toLowerCase().equals(ActualString1.trim().toLowerCase())) {
          Log.info(comment + ExpectedString + "' Actual Heading : '" + ActualString + "'");
          test.log(Status.PASS,
              comment + ExpectedString + "' Actual Heading : '" + ActualString + "'");
        } else {
          PreviousStepResults=false;
          debit.setEndResults(false,
              comment + ExpectedString + "' Actual Heading : '" + ActualString + "'");
          Log.info(comment + ExpectedString + "' Actual Heading : '" + ActualString + "'");
          test.log(Status.FAIL,
              comment + ExpectedString + "' Actual Heading : '" + ActualString + "'");
          flag=false;
        }
      } catch (Exception ex) {
        flag=false;
        PreviousStepResults=false;
        debit.setEndResults(false,"ComparingTwoString Error occurred :" + ex.getMessage());
        Log.info("ComparingTwoString Error occurred :" + ex.getMessage());
      }
    }
  }
public void verifyItem_Displayed(String text)
{
  if(PreviousStepResults) {
    try {
      if (Verify_If_Screen_Has_Test(text) > 0) {
        Log.info("The text (" + text + ") is displayed on the screen");
        test.log(Status.PASS, "The text (" + text + ") is displayed on the screen");
        test.log(Status.PASS, "Screenshot",
            MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver))
                .build());
      } else {
        PreviousStepResults=false;
        debit.setEndResults(false,"The text (" + text + ") is not displayed on the screen");
        Log.info("The text (" + text + ") is not displayed on the screen");
        test.log(Status.FAIL, "The text (" + text + ") is not displayed on the screen");
        test.log(Status.FAIL, "Screenshot",
            MediaEntityBuilder.createScreenCaptureFromPath(Take_Screenshot.Capture(driver))
                .build());
      }
    } catch (Exception ex) {
      PreviousStepResults=false;
      debit.setEndResults(false,"Verify Item Displayed Method, error occured " + ex.getMessage());
      Log.info("Verify Item Displayed Method, error occured " + ex.getMessage());
      test.log(Status.FAIL, "Verify Item Displayed Method, error occured " + ex.getMessage());
    }
  }
}


  @AfterClass
  public void test()
  {
    debit.setEndResults(result);
  }
}
