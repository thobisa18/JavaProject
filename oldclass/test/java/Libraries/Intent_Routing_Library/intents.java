package Libraries.Intent_Routing_Library;
import static org.jsoup.helper.StringUtil.isNumeric;

import Libraries.Webservice_Library.WebservicesLauncher;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import Config.*;
import java.util.concurrent.TimeUnit;


public class intents {
  //private String Endpoint;

  private RequestSpecification httpRequest;
  private JsonPath response;
  private String returnBody;
  public JsonPath jsonEvaluator;
int count=0;
  String dialogID;
  String nextStep;
  String NumberTransferedTo=null;
  String utterance;
  String intent;
  String[][] results=new String[1][5];
  WebservicesLauncher webservice;
  boolean flag=true;

  public  void waitForSeconds(int seconds)
  {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  public String[][] get_Final_Intent(String utteranceP,String dnis){
    Properties prop=new Properties();
    try {
          //waitForSeconds(20);
          Log.info("Test Case Number :"+count+"  Utterance:  "+utteranceP);
          Log.info("Inside get_Final_Intent");
          String endpoint=constants.GetEndPoint(prop.get_Propertie_From_Config("Environment"));
          webservice = new WebservicesLauncher(endpoint,dnis);
          Log.info("Endpoint : "+endpoint );
          //Create a
          Log.info("About to start call");
         //System.out.println("About to start call");
         response= webservice.GetResponseAsJson("");
          //response= webservice.GetResponseAsJson("");
          //Log.info("Jas:  "+response.prettyPrint());
          Log.info("Call Started");
          dialogID=webservice.getValueOfParameter("dialogId");
          Log.info("Test Case Number :"+count+" Dialog ID: "+dialogID);
          nextStep=webservice.getValueOfParameter("actionName");
          //wait for the board to ask for intent
           while(!nextStep.equals("Ask.Intent")) {
             response= webservice.GetResponseAsJson("" + "&dialogId=" + dialogID);
            // Log.info("Jas:  "+response.prettyPrint());
                nextStep=webservice.getValueOfParameter("actionName");
                Log.info("Next Step: "+nextStep);
            }
            //Get Json response
            response= webservice.GetResponseAsJson1(utteranceP,dialogID);
     // Log.info("Jas:  "+response.prettyPrint());
            //wait for the BOT to transfer the call

           while (!nextStep.equals("Transfer"))
           {
             if(nextStep.equals("Ask.IdNumber") )
             {
               results[0][4]="true";
             }
             response= webservice.GetResponseAsJson("" + "&dialogId=" + dialogID);
             //Log.info("Jas:  "+response.prettyPrint());
             //get next action
             nextStep=webservice.getValueOfParameter("actionName");

             Log.info("Next Step: "+nextStep);
           }

            if( !webservice.getValueOfParameter("dialogEvents").equals("")) {
              NumberTransferedTo = webservice.getValueOfParameter("dialogEvents");
              if(!isNumeric(NumberTransferedTo.substring(NumberTransferedTo.indexOf("transfer-extension:")+19)))
                NumberTransferedTo=null;
            }
            //get intent final intent
            intent=webservice.getValueOfParameter("intent");
            Log.info("Intent: "+intent);
            utterance=utteranceP;
            // assign final "Utterance            Final Intent                Number Transfered To");
            results[0][0]=utterance;
            results[0][1]=intent;
            results[0][2]="000000000";
            if(NumberTransferedTo!=null)
            results[0][2]=NumberTransferedTo.substring(NumberTransferedTo.indexOf("transfer-extension:")+19);
            results[0][3]=dialogID;


           // System.out.println("Next Step: ");
           // System.out.println( "Test Case Number :"+count+" "+utterance+"************"+intent+"*****************"+ NumberTransferedTo.substring(NumberTransferedTo.indexOf(":")+1));
            Log.info("Test Case Number :"+count+" "+utterance+"************"+intent+"*****************"+ NumberTransferedTo.substring(NumberTransferedTo.indexOf(":")+1));
            Log.info("Test Case Number :"+count+"ended well");
    }
    catch (Exception x)
    {
      flag=false;
      Log.info("Returned response on error:"+response.prettyPrint());
      Log.info("Error :"+ x.getMessage());
      results[0][0]=utteranceP;
      results[0][1]="Error occoured";
      results[0][2]="0000000000";
      results[0][3]=dialogID;
      //Log.info("Test Case Number :"+count+" "+utterance +" "+dialogID);
      Log.info("Test Case Number :"+count+" Ended on error");
      return results;
    }
    finally {
      count++;
      if(!flag)
      hug_Up(dialogID);
    }
            return results;
  }
  public void hug_Up(String dialogIdP) {
    String nextStepP;
    nextStepP = webservice.getValueOfParameter("actionName");
    boolean flag = false;
    while (!nextStepP.equals("readIncomingData")) {
      //System.out.println("Call termination : "+nextStepP);
      webservice.GetResponseAsJson("" + "&dialogId=" + dialogIdP);
      nextStepP = webservice.getValueOfParameter("actionName");
      Log.info("The next step is " + nextStepP);
    }

      Log.info("Call has succesfully ended");

  }


}
