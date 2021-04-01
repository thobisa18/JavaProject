package Libraries;
import io.restassured.response.Response;
import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;


import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class test {

 // private static final Log log = LogFactory.getLog(OAUTH2GrantTypeTestCase.class);

  public static void main(String[] args) {
    RestAssured.baseURI = "https://scc-rbonlp-q01.fnb.co.za:8443";
    RequestSpecification httpRequest = RestAssured.given();
    Response response = httpRequest.get("/DiaManT/DialogServlet?app=EN-ZA.FNB&dtype=json&utt=");

    test fd=new test();
   // fd.getRequestBody("https://scc-rbonlp-q01.fnb.co.za:8443/DiaManT/DialogServlet?app=EN-ZA.FNB&dtype=json&utt=");

  }

  private String getRequestBody(final HttpServletRequest request) {
    final StringBuilder builder = new StringBuilder();
    try  {
      BufferedReader reader = request.getReader();
      if (reader == null) {
        //logger.debug("Request body could not be read because it's empty.");
        //System.out.println("Request body could not be read because it's empty.");
        return null;
      }
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }
      return builder.toString();
    } catch (final Exception e) {
      //logger.trace("Could not obtain the saml request body from the http request", e);
     // System.out.println("Could not obtain the saml request body from the http request");
      return null;
    }
  }




}
