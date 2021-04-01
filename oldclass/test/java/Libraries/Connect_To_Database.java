package Libraries;
import static All_Reports.Excel_Report.Debit_Order_Excel_Data.getInstanceOfDebitOrder;

import Config.Log;
import All_Reports.Excel_Report.Debit_Order_Excel_Data;
import java.sql.*;

import java.text.SimpleDateFormat;

public class Connect_To_Database {

  Connection conn;
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  java.util.Date date = new java.util.Date();


  public  boolean updateDataToDatabase(String dailogID,String executionResults,String Comments, String TC_ID)
  {
    boolean results;
    try
    {
      // the mysql insert statement
      String query = " update Automation__Report set Dialog__ID=?, Execution_Results=?, Execution_Date=?, Results_Comments=?"
          + " where TC_ID=?";
      // create the mysql insert preparedstatement
      PreparedStatement preparedStmt = conn.prepareStatement(query);
     // preparedStmt.setString (1, testCaseDescription);
      preparedStmt.setString (1, dailogID);
      preparedStmt.setString   (2, executionResults);
      preparedStmt.setString(3,  formatter.format(date));
      preparedStmt.setString    (4, Comments);
      preparedStmt.setString    (5, TC_ID);
      //preparedStmt.setString    (6, TC_ID);
      // execute the preparedstatementresults= preparedStmt.execute();
      int rowUpdated=preparedStmt.executeUpdate();
      if(rowUpdated>0) {

        Log.info("updated"+ rowUpdated +" row(s)");
        results=true;
      }
      else {

        Log.info("no row  updated");
        results=false;
      }
    }
    catch (Exception e)
    {
      results=false;
      Log.info("Update methd Error occured :"+ e.getMessage());
    }
    return  results;
  }

  public  boolean get_reportByTestCaseID(String TC_ID)
  {
    Boolean results=false;
    try {
      //System.out.println(TC_ID);
      String query ="Select count(*) as RowCount  From Automation__Report where TC_ID=?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setString(1,TC_ID);
      ResultSet resultsSet=preparedStmt.executeQuery();
      //System.out.println("*********"+TC_ID);
      int rows=0;
      while(resultsSet.next())
      {
       rows=resultsSet.getInt("RowCount");
      }
      if(rows>0) {
        Log.info("Record exists");
        results=true;
      }
      else
        Log.info("Record doesnt exists");
    }
    catch (Exception e) {
      Log.info("Check if record exist, Error occured :"+ e.getMessage());
    }
    return results;
  }
  public  void get_All_report()
  {
    int countPassed=0;
    int countFailed=0;
    connectToDB();
    Debit_Order_Excel_Data data_R=getInstanceOfDebitOrder();
    Boolean results=false;
    try {
      String query ="Select *  From Automation__Report";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      ResultSet resultsSet=preparedStmt.executeQuery();

      while(resultsSet.next())
      {
        if(resultsSet.getString("Execution_Results").equals("Passed")) {
          data_R.setPassedNum(countPassed + 1);
          countPassed++;
        }else
        {
          data_R.setFailedNum(countFailed + 1);
         countFailed++;
        }
        //data_R.set_Data(test_Case_Name,dialogID,"Passed",results[0][1]);
        data_R.set_Data1(resultsSet.getString("Test_Case_Description"),resultsSet.getString("Dialog__ID"),resultsSet.getString("Execution_Results"),resultsSet.getString("Execution_Date"),resultsSet.getString("Results_Comments") );
       //System.out.println(resultsSet.getString("Test_Case_Description")+ " "+ resultsSet.getString("Dialog__ID"));
      }

    }
    catch (Exception e) {
      Log.info("Read all methot, Error occured :"+ e.getMessage());
    }

  }
public  boolean insertDataToDatabase(String testCaseDescription, String dailogID,String executionResults,String Comments, String TC_ID)
{
  boolean results;
  try
  {
    // the mysql insert statement
    String query = " insert Automation__Report (Test_Case_Description, Dialog__ID, Execution_Results, Execution_Date, Results_Comments,TC_ID)"
        + " values (?, ?, ?, ?,?,?)";
    // create the mysql insert preparedstatement
    PreparedStatement preparedStmt = conn.prepareStatement(query);
    preparedStmt.setString (1, testCaseDescription);
    preparedStmt.setString (2, dailogID);
    preparedStmt.setString   (3, executionResults);
    preparedStmt.setString(4,  formatter.format(date));
    preparedStmt.setString    (5, Comments);
    preparedStmt.setString    (6, TC_ID);
    // execute the preparedstatementresults= preparedStmt.execute();
   int rows= preparedStmt.executeUpdate();
    if(rows>0) {
      //System.out.println("Inserted "+ rows +" row(s)");
      Log.info("Inserted "+ rows +" row(s)");
      results=true;
    }
    else {

      Log.info("no row  inserted");
      results=false;
    }
  }
    catch (Exception e)
  {
    results=true;
    Log.info("Insert Method Error occured :"+ e.getMessage());
  }
  return  results;
}
public void connectToDB()
{
  try{
    String myDriver = "com.mysql.cj.jdbc.Driver";
    String myUrl = "jdbc:mysql://127.0.0.1:3306/db_NLP_Java_Automation?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    Class.forName(myDriver);
    conn = DriverManager.getConnection(myUrl, "root", "");
  }
  catch (Exception e)
  {

    Log.info("Connection Method Error occured :"+ e.getMessage());
  }

}

public void updateResults(String testCaseDescription, String dailogID,String executionResults,String Comments, String TC_ID)
{
  try {


  connectToDB();
  if(get_reportByTestCaseID(TC_ID))
  {
    Log.info("About to update a record");
    if (updateDataToDatabase(dailogID,executionResults,Comments,TC_ID)) {
      Log.info("Records updated");
    }else
    {
      Log.info("Records not updated");
    }

  }
  else{
    if(insertDataToDatabase(testCaseDescription,dailogID,executionResults,Comments,TC_ID))
    {
      Log.info("Record added succesfully");
    }
    else
    {
      Log.info("Record not added succesfully");
    }
  }
  }
  catch (Exception ex)
  {
    Log.error("Connection Method Error occured :"+ ex.getMessage());
  }
  finally {

    if (conn != null) try { conn.close(); } catch (SQLException logOrIgnore) {}
  }

}
}
