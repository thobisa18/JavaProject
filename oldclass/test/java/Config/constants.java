
package Config;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class constants {

  static Date date = new Date();
  static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy-HH_mm_ss");
  static HashMap<String, String> ui_ID_With_Text = new HashMap<>();
  static String dsa = formatter.format(date);
  static String[] ds = new String[2];
  static String PathExtent = "";
  static String PathExcel = "";
  private static constants constants_instance;
  String startTime;
  String endTime;
  static Properties prop;

  private constants() {
    prop=new Properties();
    ui_ID_With_Text.put("ui.card.statements.select.account.view", "Statement History");
    ui_ID_With_Text.put("ui.accounts.detailed", "Accounts");
    ui_ID_With_Text.put("ui.payments.history.controller", "Payment History");
    ui_ID_With_Text.put("ui.world.payment.view", "FNB Pay");
    ui_ID_With_Text.put("ui.payments.scheduled.payments.entry.controller", "Scheduled payment");
    ui_ID_With_Text.put("ui.payments.onceoff.entry.controller", "Public Recipient");
    ui_ID_With_Text.put("ui.cards.entry.without.account.controller", "Cards");
    ui_ID_With_Text.put("ui.messaging.set.selected.conversation.to.structured.chat.controller",
        "My Team of Bankers");
    ui_ID_With_Text.put("ui.insurance.life.is.ocep.controller", "Insurance");
    ui_ID_With_Text.put("ui.world.transfers.view", "Transfers");
    ui_ID_With_Text
        .put("ui.homeloans.pl.dvb.display.template.entry.10", "As the impact of COVID-19");
    ui_ID_With_Text.put("ui.debit.order.from.account.view", "Debit order");
    ui_ID_With_Text.put("ui.can.zbi.apply","What would you like to do");
    ui_ID_With_Text.put("ui.shares.ocep.entry.controller","Investment Detail");
    ui_ID_With_Text.put("ui.homeloans.pl.manage.entry.controller","My applications");
    ui_ID_With_Text.put("ui.navworld.forme.controller","home");
    ui_ID_With_Text.put("ui.settings.display.username.view","YOUR USERNAME");
    ui_ID_With_Text.put("ui.locators.branch","My Nearest ATM");
    ui_ID_With_Text.put("ui.locators.atm","Locator");
    ui_ID_With_Text.put("ui.locators.branch","My Nearest Branch");
    ui_ID_With_Text.put("ui.forex.ocep.gp.list.entry.controller","Global Payment");
    ui_ID_With_Text.put("ui.forex.ocep.rates.from.forex.controller","RAND PER FOREIGN CURRENCY");
    ui_ID_With_Text.put("ui.ebucks.inside.start","Earn");
    ui_ID_With_Text.put("ui.sim.cards","SIM CARD");
    ui_ID_With_Text.put("ui.mysim.load.mvno.account.controller","Get free FNB Connect");
    ui_ID_With_Text.put("ui.ebucks.travel.start","Book flight");
    ui_ID_With_Text.put("ui.forex.ocep.about.info.view","About Forex");
    ui_ID_With_Text.put("ui.homeloans.pl.home.application.entry.controller","For me");
    ui_ID_With_Text.put("ui.ebucks.pin.start.controller","View PIN");
    ui_ID_With_Text.put("ui.ebucks.inside.card.start.controller","Activate Card");
    ui_ID_With_Text.put("ui.ebucks.statement.entry.controller","PDF statement");
    ui_ID_With_Text.put("world.buy.ebucks.travel","Earn");
    ui_ID_With_Text.put("ui.ar.locators.landing.controller","Locator");
  }

  public static String GetEndPoint(String env) {
    try {
      if(env.toLowerCase().trim().equals(prop.get_Propertie_From_Config("Environment").toLowerCase()))
        return prop.get_Propertie_From_Config("QA_End_Point");
      else
        return prop.get_Propertie_From_Config("Dev_End_Point");
    }
    catch (Exception e)
    {
      return "null";
    }
  }
  public static boolean ViewDeepLink_flag(){
    return Boolean.parseBoolean( prop.get_Propertie_From_Config("Go_To_View_Deep_Link"));
  }

  public static String Smart_Call_Routing_GetEndPoint() {
    return "http://10.33.168.62:9018";
  }

  public static String DataLocation() {
    try {
      return prop.get_Propertie_From_Config("Local_Flag");
    }
    catch (Exception E)
    {
      Log.info("Error occurred when getting data location : "+E.getMessage());
      return "";
    }
  }

  public static String[] getExtentReportPath() {
    String endReport = "ExtentReportResults_" + dsa + ".html";

    //ds[0]="\\\\fds-rbgfs01\\\\CCITIS_QA\\\\Automated Testing\\\\LuanchQC\\"+endReport;
    //ds[1]="file://fds-rbgfs01/CCITIS_QA/Automated%20Testing/LuanchQC/"+endReport;

    ds[0] = PathExtent;
    ds[1] = "C:/curl/" + endReport;
    return ds;
  }

  public static String GetTestData(String Which_Data) {
    String dataPath;
    String PathExtentPath;
    String PathExcelPath;
    Properties  prop=new Properties();

    if (DataLocation().toLowerCase().trim().equals("local")) {
      PathExtentPath = prop.get_Propertie_From_Config("Extent_Result_Path");
      PathExcelPath = prop.get_Propertie_From_Config("Excel_Result_Path");
      dataPath=prop.get_Propertie_From_Config("Data_Path");
      Log.info("Data: "+ dataPath);
    }
    else
    {
      PathExtentPath ="";
      PathExcelPath="";
      dataPath="";

    }


    if (Which_Data.equals("intent")|| Which_Data.equals("intent_Smart_Routing")) {

      PathExtent =  PathExtentPath  + dsa + ".html";
      PathExcel =PathExcelPath+ "Intent_Smart_Call_Routing_API_Test_Results_";
      return dataPath+"Jakes1.xlsx";
    }
    else if (Which_Data.equals("intent_Exception")) {
      PathExtent = PathExtentPath + dsa + ".html";
      PathExcel = PathExcelPath+"Intent_Test_Results_Exception_";
      return dataPath+"High Risk Intents_Json.xlsx";
  }
    else if (Which_Data.equals("debit")) {
      PathExcel = PathExcelPath+ "Debit_Order_Automation_Test_Results_";
      PathExtent = PathExtentPath  +"Debit_Order_Automation_ExtentReportResults_"+ dsa + ".html";
      return dataPath+"Test_Data_Debit_Order.xlsx";
    }
    else if (Which_Data.equals("DeepLink")) {
      PathExcel = PathExcelPath+"Deep_Link_Automation_Test_Results_";
      PathExtent = PathExtentPath +"Deep_Link_Automation_ExtentReportResults_"+  dsa + ".html";
      return dataPath+"Test_Data_All_DeepLinks_All.xlsx";
    }
    else if (Which_Data.equals("DeepLink_Exception")) {
      PathExcel = PathExcelPath+"Deep Link_Other_Scenarios_Automation_Test_Results_";
      PathExtent = PathExtentPath+"Deep Link_Other_Scenarios_Automation_ExtentReportResults_"+ dsa + ".html";
      return dataPath+"Test_Data_Exception_Deep_Link.xlsx";
    }
    else if (Which_Data.equals("Chat")) {
      PathExcel = PathExcelPath+"Secure_Chat_Automation_Test_Results_";
      PathExtent = PathExtentPath +"Secure_Chat_Scenarios_Automation_ExtentReportResults_"+ dsa + ".html";
      return dataPath+"Test_Data_DeepLinks_All_Secured_Chat_Intents.xlsx";
    }
    else if (Which_Data.equals("Covid_19")) {
      PathExcel = PathExcelPath+"Covid_Automation_Test_Results_";
      PathExtent = PathExtentPath +"Covid_Scenarios_Automation_ExtentReportResults_"+ dsa + ".html";
      return dataPath+"Test_Data_DeepLinks_Covid_19.xlsx";
    }
    else {

      System. exit(1);
      return "";
    }
  }

  public static String getDnisBySegment(String segment) {
    if (segment.trim().toLowerCase().contains("future gold")) {
      return "0870302030";
    }
    else if (segment.trim().toLowerCase().contains("gold")) {
      return "0873124740";
    } else if (segment.trim().toLowerCase().contains("easy")) {
      return "0873124840";
    }
    else  if(segment.trim().toLowerCase().contains("future premier"))
    {
      return "0870302385";
    }
    else if (segment.trim().toLowerCase().contains("premier")) {
      return "0870301610";
    }
    else if(segment.trim().toLowerCase().contains("future private"))
    {
      return "0870302824";
    }
    else if (segment.trim().toLowerCase().contains("private client") || segment.trim().toLowerCase().contains("private")) {
      return "0870301677";
    }
    else if(segment.trim().toLowerCase().contains("ebucks"))
    {
      return "0870302255";
    }
  else{
      return "null";
    }
  }

  public static constants getInstance() {
    if (constants_instance == null) {
      constants_instance = new constants();
    }
    return constants_instance;
  }

  public static String GetTextToBeDisplayed(String ui_ID) {
    Log.info("Text to look for is '" + ui_ID_With_Text.get(ui_ID) + "'");
    return ui_ID_With_Text.get(ui_ID);
  }

  public String IntentReportPath() {
    //return "\\\\fds-rbgfs01\\\\CCITIS_QA\\\\Automated Testing\\\\LuanchQC\\Intent_Test_Results_";
    return PathExcel;
  }

  public String DebitOrderReportPath() {
    //return "\\\\fds-rbgfs01\\\\CCITIS_QA\\\\Automated Testing\\\\LuanchQC\\Debit_Order_Test_Results_";
    return PathExcel;

  }

  public String Intent_Report_Header() {
    return "NLP Intent Routing  Regression Report \n Environment : " + GetEnvironment()
        + "\n Time: " + startTime + " - " + endTime;
  }

  public String Debit_Order_Report_Header() {
    return "NLP Debit Order Regression Report \n Environment : " + GetEnvironment() + "\n Time: "
        + startTime + " - " + endTime;
  }

  public String GetEnvironment() {
    try {

      return prop.get_Propertie_From_Config("Environment");
    }
    catch (Exception e)
    {
      return "";
    }
  }
  public static String Get_Ask_ID_Indicator() {
    try {

      return prop.get_Propertie_From_Config("Ask_ID");
    }
    catch (Exception e)
    {
      return "";
    }
  }
  public void setStartTime() {
    startTime = getCurrentTime();
  }

  public void setEndTime() {
    endTime = getCurrentTime();
  }

  public String getCurrentTime() {
    final long CURRENT_TIME_MILLIS = System.currentTimeMillis();
    Date instant = new Date(CURRENT_TIME_MILLIS);
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
   return sdf.format(instant);
  }


}
