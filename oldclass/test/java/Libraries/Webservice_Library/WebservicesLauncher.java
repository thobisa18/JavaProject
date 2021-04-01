package Libraries.Webservice_Library;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import java.util.concurrent.TimeUnit;
import Config.*;
public class WebservicesLauncher {
  //private String Endpoint;

  private RequestSpecification httpRequest;
  private Response response;
  private String returnBody;
  public JsonPath jsonEvaluator;

  Properties  prop=new Properties();
  String dnis=prop.get_Propertie_From_Config("Defualt_DNIS");
  public WebservicesLauncher(String webServiceEndpoint)
  {
    RestAssured.baseURI = webServiceEndpoint;
    //Specify request data
    httpRequest = given().accept(ContentType.JSON);
  }

  public WebservicesLauncher(String webServiceEndpoint,String dnis)
  {
    if(!dnis.equals("null"))
    this.dnis=dnis;
    RestAssured.baseURI = webServiceEndpoint;
    //Specify request data
    httpRequest = given().accept(ContentType.JSON);
  }

  public String GetResponseAsString(String parameter1)
  {
    try
    {
      response = httpRequest.request(Method.GET, "/DiaManT/DialogServlet?app=EN-ZA.FNB&dtype=json&utt=" +parameter1);
      //response = httpRequest.request(Method.GET, "/7103240588306");

      returnBody = response.getBody().asString();
      //System.out.println( "Returned response: " + returnBody);
      return returnBody;
    }

    catch(Exception e)
    {
      return e.getMessage();
    }

  }


  public JsonPath GetResponseAsJson(String parameter1)
  {
    String st=parameter1;
    String app="EN-ZA";
    String dtype="json";

    //try
   // {
   // response=RestAssured.given().queryParam("Dnis",dnis).queryParam("app",app).queryParam("dtype",dtype).queryParam("utt",st).when().get("/DiaManT/DialogServlet");
      response = httpRequest.get("/DiaManT/DialogServlet?app=EN-ZA.FNB&dtype=json&utt=" +parameter1+"&DNIS="+dnis);
      try {
        TimeUnit.SECONDS.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    if(response.getStatusCode() != 200) {
      //System.out.println(" GetResponseAsJson Response was not OK");
      GetResponseAsJson(parameter1);
    }
      //Get Json representation from response body
      jsonEvaluator = response.jsonPath();
      //System.out.println("Returned response: " + jsonEvaluator.prettyPrint());
    // Log.info("Returned response: " + jsonEvaluator.prettyPrint());
      return jsonEvaluator;
   // }

    //catch(Exception e)
   // {
     // logger.log(Status.FATAL, "Error has been displayed: " + e.getMessage());
      //Log.info( "Error has been displayed: " + e.getMessage());
      //return jsonEvaluator;
    //}

  }

  public JsonPath GetResponseAsJson1(String parameter1,String parameter2)
  {
    String st=parameter1;
    String d=parameter2;
    String app="EN-ZA";
    String dtype="json";
    String dnis="0870301677";

    response=RestAssured.given().queryParam("Dnis",dnis).queryParam("app",app).queryParam("dtype",dtype).queryParam("utt",st).queryParam("dialogId",d).when().get("/DiaManT/DialogServlet");
    try {
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    if(response.getStatusCode() != 200) {
      //System.out.println("GetResponseAsJson1 Response was not OK");
      GetResponseAsJson1(parameter1,parameter2);
    }

    jsonEvaluator = response.jsonPath();
   //System.out.println("Returned response: " + jsonEvaluator.prettyPrint());
    //Log.info("Returned response: " + jsonEvaluator.prettyPrint());
    return jsonEvaluator;



  }

  public String getValueOfParameter(String JsonParameter)
  {
    String temp= "";
    if(jsonEvaluator!=null)
    {
      temp = jsonEvaluator.get(JsonParameter).toString().replace("[", "");
      temp = temp.replace("]", "");
      if(temp.contains("null"))
      {
        temp = "";
      }
    }
    return temp.trim();
  }




  public void checkWebserviceStatus()
  {


    if(response.getStatusCode() == 200)
    {

      //logger.log(Status.PASS, "The WebService Returned a respnse successfully as expected");
      //Assert.assertTrue(true);

    }
    else if(response.getStatusCode()  == 404)
    {
      //logger.log(Status.FAIL, "No results returned WebService Returned a respnse successfully as expected");
      //Assert.assertTrue(false);
    }

    else
    {
      //logger.log(Status.FAIL, "WebService has Failed");
      //Assert.assertTrue(false);
    }

  }
}
