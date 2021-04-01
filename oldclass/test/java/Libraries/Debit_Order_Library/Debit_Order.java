package Libraries.Debit_Order_Library;

import Libraries.Webservice_Library.WebservicesLauncher;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import java.util.concurrent.TimeUnit;
import Config.*;



/**
 * <h1>Debit Order!</h1>
 * The debit order program pass data to the webservice
 * following a test script flow
 * the final intent and number the call routed to.
 * <p>
 * <b>Note:</b> It generates an excel report and it write logs on Logs_File
 * @author  Thobisa Monono F5208882
 * @version 1.0
 * @since   2019-12-13
 */
public class Debit_Order {

  String dialogID;
  String NumberTransferedTo;
  String ExpectedNextStep;
  String ActualnextStep;
  ExtentTest test;
  WebservicesLauncher webservice;
  String PreviousStep;
  String endpoint = constants.GetEndPoint("qa");
  boolean PreviousStepResults = true;
  String result[][] = new String[1][2];
  String Comments = "";
  String prompt;
  String MatchPromp="";
  String promptPrevous;
public String getNextStep()
{
  return ActualnextStep;
}
  Properties  prop=new Properties();
  String dnis=prop.get_Propertie_From_Config("Defualt_DNIS");
  public Debit_Order(ExtentTest testP) {

    test = testP;
    webservice = new WebservicesLauncher(endpoint);
  }
  public Debit_Order(ExtentTest testP,String dnis) {
    test = testP;
    this.dnis=dnis;
    webservice = new WebservicesLauncher(endpoint,dnis);
  }

  public String no_input(String nextStepP, String expectedNextStep, int seconds, String dialogIDP) {
    while (nextStepP.contains("Wait") && !nextStepP.contains(expectedNextStep)) {
      try {
        TimeUnit.SECONDS.sleep(seconds);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      webservice.GetResponseAsJson("" + "&dialogId=" + dialogIDP);
      nextStepP = webservice.getValueOfParameter("actionName");
      prompt=webservice.getValueOfParameter("prompt");
      Log.info("No input passed to omillia");
      Log.info("The next step is " + nextStepP);
      Log.error("The prompt1: "+webservice.getValueOfParameter("prompt"));

    }
    return nextStepP;

  }

/**
 * <h1>CaptureInput_to_the_Board!</h1>
 * The CaptureInput_to_the_Board program pass data to the webservice
 * following a test script flow
 * the final intent and number the call routed to.
 * <p>
 *   */
  public boolean CaptureInput_to_the_Board(String dataP, String dialogIDP, String ExpectedNextStepP,
      String PreviousStepP) {
    int count = 0;
    boolean results = false;
    boolean exitIndicator = false;
    ExpectedNextStep = ExpectedNextStepP;
    webservice.GetResponseAsJson1(dataP, dialogIDP);
    //prompt = webservice.getValueOfParameter("prompt");
    ActualnextStep = webservice.getValueOfParameter("actionName");
    // prompt= webservice.getValueOfParameter("prompt");
    if (!webservice.getValueOfParameter("prompt").isEmpty()) {
      prompt = webservice.getValueOfParameter("prompt");
    } else {
      no_input("wait", ExpectedNextStepP, 0, dialogIDP);
    }
    while (exitIndicator == false) {
      if (ActualnextStep.contains(ExpectedNextStep) ||ExpectedNextStepP.contains(ActualnextStep)) {

        results = true;
        exitIndicator = true;
        Log.info("The step " + PreviousStepP + " was successful.....");
      } else if (ActualnextStep.contains("Smart")) {
        Log.info("No input for " + ActualnextStep + ", wait for 10 seconds ");
        ActualnextStep = no_input(ActualnextStep, ExpectedNextStepP, 10, dialogIDP);
      } else if (ActualnextStep.contains("Wait")) {
        Log.info("No input for " + ActualnextStep + ", wait for 10 seconds ");
        ActualnextStep = no_input(ActualnextStep, ExpectedNextStepP, 4, dialogIDP);

      } else if (ActualnextStep.contains(PreviousStepP)) {
        if (!PreviousStepP.equals("Ask.Pin") && !PreviousStepP.equals("Ask.Intent")) {
          CaptureInput_to_the_Board(dataP, dialogIDP, ExpectedNextStep, PreviousStepP);
        } else {
          //I made change still need to test
          Log.warn("The step " + PreviousStepP
              + " was not successful,System couldn't validate pin,Please verify if the pin is correct");

          exitIndicator = true;
        }

      } else {
        Log.info("The current step " + ActualnextStep);
        Log.warn("The step " + PreviousStepP
            + " was not successful, User captured three attemps of the same input. Call was tranfered");
        exitIndicator = true;
      }
    }
    return results;
  }

  public void getIntent(String utterance) {

    Log.info(" Omilia prompted for " + webservice.getValueOfParameter("actionName")
        + ", Data passed to Omilia was ****" + utterance + "***** for dialog id : " + dialogID);
    webservice.GetResponseAsJson1(utterance, dialogID);
    ActualnextStep = webservice.getValueOfParameter("actionName");

    ActualnextStep = webservice.getValueOfParameter("actionName");
    webservice.GetResponseAsJson1("Yes", dialogID);
    PreviousStep = ActualnextStep;
    ActualnextStep = webservice.getValueOfParameter("actionName");
    test.log(Status.PASS, "The intent is " + webservice.getValueOfParameter("intent"));
    Log.info("The intent is " + webservice.getValueOfParameter("intent"));

    while (!ActualnextStep.equals("Ask.IdNumber")) {
      webservice.GetResponseAsJson("" + "&dialogId=" + dialogID);
      //Get the next action required from the Users
      ActualnextStep = webservice.getValueOfParameter("actionName");
      //System.out.println(ActualnextStep);
    }
    test.log(Status.INFO, "The BOT is  prompting for an ID(" + ActualnextStep + ")");
    Log.info("The BOT is  prompting for an ID(" + ActualnextStep + ")");
  }

  public String MakeCall() {
    promptPrevous="";
    prompt="";
    Log.info("start a phone call");
    webservice.GetResponseAsJson("");
    //Welcome message no input
    dialogID = webservice.getValueOfParameter("dialogId");
    //Step 3 Provide no input if the board is not asking for intent
    ActualnextStep = webservice.getValueOfParameter("actionName");
    if (!ActualnextStep.equals("Ask.Intent")) {
      webservice.GetResponseAsJson("" + "&dialogId=" + dialogID);
      //Get the next action required from the Users
      ActualnextStep = webservice.getValueOfParameter("actionName");
      //System.out.println(ActualnextStep);
    }
    if (!ActualnextStep.equals("Ask.Intent")) {
      webservice.GetResponseAsJson("" + "&dialogId=" + dialogID);
      //Get the next action required from the Users
      ActualnextStep = webservice.getValueOfParameter("actionName");
      test.log(Status.PASS, "The call has been generated, and the dialog id is " + dialogID);
      Log.info("The call has been generated, and the dialog id is " + dialogID);
    }
    promptPrevous = webservice.getValueOfParameter("prompt");
    return dialogID;
  }

  public void pass_Data(String data, String dialogIdP, String ExpectedStepP, String PreviousStepP) {

    if (PreviousStepResults == true) {

      ActualnextStep = webservice.getValueOfParameter("actionName");
      prompt = webservice.getValueOfParameter("prompt");
      Log.info("The current step is " + ActualnextStep + " and data passed is " + data);

      Log.info(" Omilia prompted for " + PreviousStepP + ", Data passed to Omilia was ****" + data
          + "***** for dialog id : " + dialogID);
      if (CaptureInput_to_the_Board(data, dialogIdP, ExpectedStepP, PreviousStepP)) {

        PreviousStepResults = true;
        //test.log(Status.PASS,  "The "+ PreviousStepP +" step has been executed successfull, data passed was  '" + data+"'");

        test.log(Status.PASS, promptPrevous + " data passed: '" + data + "'");

        Log.info(promptPrevous + " data passed: '" + data);
        Log.info("The " + PreviousStepP + " step has been executed successfull, data passed was  '"
            + data + "'");
        test.log(Status.INFO, "The next step is " + ExpectedStepP);
        Log.NextStep("The next step: " + ExpectedStepP);
        Comments = "";
        //System.out.println("Continue to the next step");
      } else {

        PreviousStepResults = false;
        if (!prompt.contains("Wait")) {
          test.log(Status.FAIL, promptPrevous + " data passed: '" + data + "'");
        }
       // no_input("Transfer", ExpectedStepP, 10, dialogIdP);
        test.log(Status.FAIL,
            "The " + PreviousStepP + " step has not been executed successful, The expected step: "+ExpectedStepP+" Actual Step: "+ ActualnextStep +" data passed was  '"
                + data + "', The bot throw this prompt '" + prompt + "'");
        Log.warn(
            "The " + PreviousStepP + " step has not been executed successful, data passed was  '"
                + data + "', The bot throw this prompt '" + prompt + "'");
       // Comments =
          //  "The " + PreviousStepP + " step has not been executed successful, data passed was  '"
            //    + data +  "', The bot throw this prompt '" + prompt + "'" + ;
        if(!prompt.trim().equals(""))
          Comments=SummaryResults(ExpectedStepP)+"  "+ ", The bot throw this prompt '" + prompt + "'";
        else
          Comments=SummaryResults(ExpectedStepP);


      }
      promptPrevous=prompt;
    }

    result[0][0] = String.valueOf(PreviousStepResults);
    result[0][1] = Comments;


  }


  public String[][] getEndResults() {
    return result;
  }
  public void setEndResults(String[][] result)
  {
    this.result=result;
  }

  public void hug_Up(String dialogIdP) {
    String nextStepP;
    boolean flag = false;
    while (!flag && !ActualnextStep.equals("Exit")) {
      webservice.GetResponseAsJson("" + "&dialogId=" + dialogIdP);
      nextStepP = webservice.getValueOfParameter("actionName");
      //System.out.println(nextStepP);
      Log.info("The next step is " + nextStepP);

      if (nextStepP.equals("readIncomingData") || nextStepP.equals("Transfer")) {
        flag = true;
      }

      if (nextStepP.equals("Transfer")) {
        getNumberTransfer();
      }
    }
    if (flag || ActualnextStep.equals("Exit")) {
      Log.info("Call has succesfully ended");
    }
  }

  private void getNumberTransfer() {

    NumberTransferedTo = webservice.getValueOfParameter("dialogEvents");
    NumberTransferedTo = NumberTransferedTo.substring(NumberTransferedTo.indexOf(":") + 1);
    Log.info("Call is transfered to " + NumberTransferedTo);
    //return NumberTransferedTo;
  }

  public void getNumberTransferedTo(String ExpectedTranferedToNumber) {
    if (PreviousStepResults == true) {
      if (NumberTransferedTo.equals(ExpectedTranferedToNumber)) {
        PreviousStepResults = true;
        Comments = "Call was transfered to a correct number, Expected number : "
            + ExpectedTranferedToNumber
            + " and Actual number was " + NumberTransferedTo;
        Log.info(
            "Call was transfered to a correct number, Expected number : "
                + ExpectedTranferedToNumber
                + " and Actual number was " + NumberTransferedTo);
        test.log(Status.PASS,
            "Call was transfered to a correct number, Expected number : "
                + ExpectedTranferedToNumber
                + " and Actual number was " + NumberTransferedTo);
      } else {
        PreviousStepResults = false;
        Log.info(
            "Call was not transfered to a correct number, Expected number : "
                + ExpectedTranferedToNumber
                + " and Actual number was " + NumberTransferedTo);
        test.log(Status.FAIL,
            "Call was not transfered to a correct number, Expected number : "
                + ExpectedTranferedToNumber
                + " and Actual number was " + NumberTransferedTo);
        Comments = "Call was not transfered to a correct number, Expected number : "
            + ExpectedTranferedToNumber
            + " and Actual number was " + NumberTransferedTo;
      }
      result[0][0] = String.valueOf(PreviousStepResults);
      result[0][1] = Comments;


    }
  }
public boolean searchString(String SearchInto,String Search)
{
  String[] tempArray;
  boolean flag=false;
    tempArray = Search.split("@");
    for(int i=0;i<tempArray.length;i++)
    {
      if(SearchInto.trim().toLowerCase().contains(tempArray[i].trim().toLowerCase())) {
        MatchPromp=tempArray[i];
        flag = true;
        break;
      }
    }
    return flag;
}
  public void verify_Prompt(String promptP) {

    if (PreviousStepResults == true) {

      if (searchString(prompt,promptP)) {
        if(!MatchPromp.equals(""))
          promptP= MatchPromp;
        PreviousStepResults = true;
        Log.info(
            "The prompt were equal, The expected prompt : " + promptP
                + " and Actual prompt was '" + promptPrevous+"'");
        test.log(Status.PASS,

            "The prompt were equal, The expected prompt : " + promptP
                + " and Actual prompt was '" + promptPrevous+"'");
        //Comments = "The prompt were equal, The expected prompt : " + promptP
           // + " and Actual prompt was " + prompt;
      }
      else {

        PreviousStepResults = false;
        Log.info(
            "The prompt were not equal, The expected prompt : " + promptP
                + " and Actual prompt was '" + prompt+"'");
        test.log(Status.FAIL,

            "The prompt were not equal, The expected prompt : " + promptP
                + " and Actual number was '" + prompt+"'");
        Comments = "The prompt were  not equal, The expected prompt : " + promptP
            + " and Actual prompt was '" + prompt+"'";

      }
      result[0][0] = String.valueOf(PreviousStepResults);
      result[0][1] = Comments;
    }
  }
public void setPreviousFalse()
{
  PreviousStepResults=false;
  result[0][0] = String.valueOf(PreviousStepResults);
  result[0][1] = "Error occurred";
}
  public void setPrevious()
  {
    PreviousStepResults=true;
  }
public String getPrompt()
{
  if (promptPrevous.isEmpty() || prompt.contains("Please hold while I transfer you for assistance"))
  return promptPrevous;
  else
    return prompt;
}
  public void No_InputDisputed(String dialogIDP) {
    if (PreviousStepResults == true) {
      webservice.GetResponseAsJson("" + "&dialogId=" + dialogIDP);
      prompt = webservice.getValueOfParameter("prompt");
    }
  }
  public void setEndResults(Boolean resultsP,String comment)
  {
    result[0][0] = String.valueOf(resultsP);
    result[0][1] = comment;
  }
  public void check_Intent(String intentP)
  {
    if (PreviousStepResults == true) {
   String intent= webservice.getValueOfParameter("intent");
   if(intent.toLowerCase().trim().contains(intentP.toLowerCase().trim()))
   {
     test.log(Status.PASS,

         "The intent were equal, The expected intent : '" + intentP+"'"
             + " and Actual intent: '" + intent +"'");
   }
   else {
     PreviousStepResults = false;
     test.log(Status.FAIL,

         "The intent were  not equal, The expected intent : '" + intentP+"'"
             + " and Actual intent: '" + intent+"'");
   }

    result[0][0] = String.valueOf(PreviousStepResults);
    result[0][1] = Comments;
   }

  }
public String SummaryResults(String ExpectedStep)
{
  String results="";
  String[] tempArray;
  boolean flag=false;
  tempArray = ExpectedStep.split("@");
  for(int i=0;i<tempArray.length;i++) {
    switch (tempArray[i]) {
      case "Ask.Intent"://Ask intent
        results = "The call was not generated successfully";
        i=tempArray.length;
        break;
      case "Ask.IdNumber":// ID number
        results = "The intent was incorrect";
        i=tempArray.length;
        break;
      case "Confirm.DisputeTransaction":// ID Validation
        results = "The intent was incorrect";
        i=tempArray.length;
        break;
      case "Confirm.CovidApplyOrStatus":// ID Validation
        results = "Intent didnt require confirmation step for covid-19";
        i=tempArray.length;
        break;
      case "Confirm.DeepLinkDevice"://Verify trusted device
        results = "The ID number captured is not correct or does not have a trusted device";
        i=tempArray.length;
        break;
      case "Announce.HasCovidDeepLinkCompleted"://confirm intent
        results = "Covid-19 deep link was not sent to the customer";
        break;
      case "Announce.DeepLinkEducationalMessage"://Send deep link
        results = "Customer has a trusted device";
        i=tempArray.length;
        break;
      case "Announce.DODUnavailableNoDevice":// No
        results = "Debit order BOT is available";
        i=tempArray.length;
        break;
      case "Announce.HasDeepLinkCompleted":// No
      case "Announce.DeepLinkDOUnavailableSent":
        results = "Sending deep link has failed";
        i=tempArray.length;
        break;

      default:
        results = "Failure has occured in one of the steps";
    }
  }
  return results;
}
}
