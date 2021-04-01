package Libraries.Intent_Routing_Library;
import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

import Libraries.Webservice_Library.WebservicesLauncher;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SessionConfig;
import io.restassured.http.ContentType;
    import io.restassured.http.Method;
    import io.restassured.path.json.JsonPath;
    import io.restassured.response.Response;
    import io.restassured.response.ResponseBody;
    import io.restassured.specification.RequestSpecification;
    import java.util.concurrent.TimeUnit;
    import Config.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreConnectionPNames;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


/**
 * <h1>NLP Smart call routing!</h1>
 * <p>
 * <b>Note:</b> It get the response(Number and Destination) from smart call routing
 * @author  Thobisa Monono F5208882
 * @version 1.0
 * @since   2019-12-13
 */
public class Smart_Call_Routing {

  private RequestSpecification httpRequest;
  private Response response;
  private String returnBody;
  private JsonPath jsonEvaluator;

  public Smart_Call_Routing()
  {
    RestAssured.baseURI =constants.Smart_Call_Routing_GetEndPoint();

    httpRequest = given().accept(ContentType.JSON);
  }






  public String[][] GetDestination_Of_Call(String intent,String segment,String ucn,String authenticated)
  {

    String dnis=constants.getDnisBySegment(segment);
    Log.info("Details passed to GetDestination_Of_Call method :-UCN= "+ucn +"  Intent= "+intent +" dnis= "+dnis);
    String[][] result = new String[1][2];
      try {
            String callId = "sccrbcmxvsq01-1586418503.1057";

            //String authenticated = "false";
            System.setProperty("http.proxyHost", "proxyo.fnb.co.za");
            System.setProperty("http.proxyPort", "8080");
            Log.info("Request to smart call routing: "+intent);
            response = RestAssured.given().param("callId", callId).param("intent", intent)
                     .param("authenticated", authenticated).param("dnis", dnis).param("ucn", ucn).param("sourceSystem","OMILIA").when()
                      .get("/smart-routing/intent/destination");
        if(response.getStatusCode() != 200) {
          Log.info("GetDestination_Of_Call Response was not OK");
          //GetDestination_Of_Call(intent,segment,ucn);
        }
            jsonEvaluator = response.jsonPath();
            Log.info("Returned response: " + jsonEvaluator.prettyPrint());
            result[0][0]=getValueOfParameter("transferLine");
            Log.info("Number Transferred To: "+result[0][0]  );
            result[0][1] = getValueOfParameter("group");
            Log.info("Destination Group To: "+result[0][1]  );
          }
            catch (Exception x)
            {
              Log.info("Error occurred in method GetDestination_Of_Call, Error :"+x.getMessage());
              result[0][0]="Not found";
               result[0][1] ="00000000";
            }

      return result;
  }


  private  String getValueOfParameter(String JsonParameter)
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








}
