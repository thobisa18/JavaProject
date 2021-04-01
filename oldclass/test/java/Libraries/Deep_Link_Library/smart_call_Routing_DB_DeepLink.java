package Libraries.Deep_Link_Library;

import Config.Log;
import Config.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.testng.annotations.Test;

public class smart_call_Routing_DB_DeepLink {

  public smart_call_Routing_DB_DeepLink()
  {
    connectToDB();
  }
  Connection conn=null;
  public void connectToDB()
  {

    Properties prop=new Properties();
      try {
        String myDriver = "com.mysql.jdbc.Driver";

        if(true)
        {
          String myUrl = "jdbc:mysql://10.33.244.16:3607/smart_routing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
          Class.forName(myDriver).newInstance();
          conn = DriverManager.getConnection(myUrl, prop.get_Propertie_From_Config("Smart_Routing_Username"), prop.get_Propertie_From_Config("Smart_Routing_Password"));
        }
       else {
          String myUrl = "jdbc:mysql://127.0.0.1:3306/db_nlp_java_automation?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
          Class.forName(myDriver).newInstance();
          conn = DriverManager.getConnection(myUrl, "root", "");
        }

      }
      catch (Exception e) {
        //System.out.println(e.getMessage());
        Log.info("Connection Method Error occurred :" + e.getMessage());
      }

  }
  @Test
public void test()
{
  connectToDB();
  get_lookup_deeplink_By_deep_link_uiid("ui.accounts.detailed");
}

  public  Lookup_DeepLink get_lookup_deeplink_By_deep_link_uiid(String deep_link_uiid)
  {
    Lookup_DeepLink deepLink=null;

    try {
      //System.out.println(deep_link_uiid);
      String query ="Select * From lookup_deeplink where deep_link_uiid=?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setString(1,deep_link_uiid);
      ResultSet resultsSet=preparedStmt.executeQuery();
      //System.out.println("*********"+deep_link_uiid);
      int rows=0;
    while(resultsSet.next())
      {
        //System.out.println("Data "+ resultsSet.getString(5));
        //Create a new object of DeepLink row
        deepLink=new  Lookup_DeepLink(deep_link_uiid,resultsSet.getString(4),resultsSet.getString(5), resultsSet.getString(6),resultsSet.getString(3),resultsSet.getString(6));
        rows=1;
      }
    conn.close();
      if(rows<0)
        Log.info("get_lookup_deeplink_By_deep_link_uiid:: Record doesnt exists");
    }
    catch (Exception e) {
      Log.info("get_lookup_deeplink_By_deep_link_uiid:: Check if record exist, Error occured :"+ e.getMessage());
    }

    return deepLink;
  }

}
