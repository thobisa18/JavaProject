package Mobile_App_Repository.Repository_Elements;

import Libraries.Debit_Order_Library.Debit_Order;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Config.*;
import org.testng.annotations.Test;

public class Mobile_Elements {
  public ExtentTest test;
  public String[][] result = new String[1][2];
  public boolean PreviousStepResults=false;
  public String Comments;
  public Debit_Order debit;
  public AppiumDriver<MobileElement> driver=appium_capabilities.get_Driver();
  String xpath="";

  public WebElement Password()
  {
    WebElement elem=driver.findElement(By.xpath("//android.widget.EditText[@resource='za.co.fnb.connect.itt:id/field']"));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }
  public WebElement Logon_Button_1()
  {
    WebElement elem=driver.findElement(By.xpath("//android.widget.Button[@text='Login']"));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }
  public WebElement Approve_Button()
  {
    WebElement elem=driver.findElement(By.xpath("//android.widget.Button[@text='Approve']"));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }

  public WebElement Decline_Button()
  {
    xpath="//android.widget.Button[@text='No, decline']";
    WebElement elem=driver.findElement(By.xpath(xpath));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }

  public WebElement Thank_You_Msg()
  {
    String text="Your request has been successfully initiated";
    WebElement elem= ((AndroidDriver) driver).
        findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));


    return elem;
  }
  public WebElement finish_Button()
  {
    xpath="//android.widget.LinearLayout[@resource-id='za.co.fnb.connect.itt:id/imageButtonLayout'][1]/android.widget.RelativeLayout[1]";
    WebElement elem= driver.findElement(By.xpath(xpath));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }

  public WebElement cancel_Button()
  {
    xpath="//android.widget.LinearLayout[@resource-id='za.co.fnb.connect.itt:id/imageButtonLayout'][1]/android.widget.RelativeLayout[1]";
    WebElement elem= driver.findElement(By.xpath(xpath));
    WebDriverWait wait=new WebDriverWait(driver,2000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));

    return elem;
  }


  public WebElement cancel_Button1()
  {
    WebElement elem= driver.findElement(By.xpath("//android.widget.LinearLayout[@resource-id='za.co.fnb.connect.itt:id/imageButtonLayout'][1]/android.widget.RelativeLayout"));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }


  public WebElement Login_Button()
  {

    WebElement elem= driver.findElement(By.id("za.co.fnb.connect.itt:id/button"));
    driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
    return elem;
  }

  public WebElement Cancelled_Msg()
  {
    String text="This request has been cancelled";
    WebElement elem= ((AndroidDriver) driver).
        findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }
  public WebElement password()
  {
    WebElement elem= driver.findElement(By.id("za.co.fnb.connect.itt:id/field"));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }

  public WebElement messages_Button()
  {
    xpath="//android.widget.TextView[@text='Messages']";
    boolean d= Check_if_Element_Displayed(xpath);
    //WebElement elem= driver.findElement(By.xpath("//android.widget.RelativeLayout[@resource-id='za.co.fnb.connect.itt:id/button'][5]"));
    WebElement elem= driver.findElement(By.xpath(xpath));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }
  public WebElement messages_Button1()
  {
    WebElement elem= driver.findElement(By.xpath("//android.widget.RelativeLayout[@resource-id='za.co.fnb.connect.itt:id/button'][4]"));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }

  public int Team_Of_Bankers()
  {
    try {

      int i= driver.findElements(By.xpath("//android.widget.TextView[@text='My Team of Bankers']")).size();
      Log.info("Element size +" + i);
      return i;
    }
    catch (Exception e) {
      Log.info(" No element found "+e.getMessage());
      return 0;
    }
  }

  public int CardsServices()
  {
    try {
      String text="Cards";
      int i= (((AndroidDriver) driver).
          findElements(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"))).size();
      WebDriverWait wait=new WebDriverWait(driver,1000);
      Log.info("Element size +" + i);
      return i;
    }
    catch (Exception e) {
      Log.info(" No element found "+e.getMessage());
      return 0;
    }
  }

  public int Statements()
  {
    try {
      String text="Statement History";
      int i= (((AndroidDriver) driver).
          findElements(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"))).size();
      WebDriverWait wait=new WebDriverWait(driver,1000);
      Log.info("Element size +" + i);
      return i;
    }
    catch (Exception e) {
      Log.info(" No element found "+e.getMessage());
      return 0;
    }
  }

  public int Payments()
  {
    try {
      String text="Payments";
      int i= (((AndroidDriver) driver).
          findElements(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"))).size();
      WebDriverWait wait=new WebDriverWait(driver,1000);
      Log.info("Element size +" + i);
      return i;
    }
    catch (Exception e) {
      Log.info(" No element found "+e.getMessage());
      return 0;
    }
  }

  public int Scheduled_Payments()
  {
    try {
      String text="Scheduled payment";
      int i= (((AndroidDriver) driver).
          findElements(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"))).size();
      WebDriverWait wait=new WebDriverWait(driver,1000);
      Log.info("Element size +" + i);
      return i;
    }
    catch (Exception e) {
      Log.info(" No element found "+e.getMessage());
      return 0;
    }
  }
  public int Transfers()
  {
    try {
      String text="Transfers";
      int i= (((AndroidDriver) driver).
          findElements(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"))).size();
      WebDriverWait wait=new WebDriverWait(driver,1000);
      Log.info("Element size +" + i);
      return i;
    }
    catch (Exception e) {
      Log.info(" No element found "+e.getMessage());
      return 0;
    }
  }

  public int Payment_History()
  {
    try {
      String text="Payment History";
      int i= (((AndroidDriver) driver).
          findElements(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"))).size();
      WebDriverWait wait=new WebDriverWait(driver,1000);
      Log.info("Element size +" + i);
      return i;
    }
    catch (Exception e) {
      Log.info(" No element found "+e.getMessage());
      return 0;
    }
  }
  public int Balance()
  {
    try {
      String text="Accounts";
      int i= (((AndroidDriver) driver).
          findElements(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"))).size();
      WebDriverWait wait=new WebDriverWait(driver,1000);
      Log.info("Element size +" + i);
      return i;
    }
    catch (Exception e) {
      Log.info(" No element found "+e.getMessage());
      return 0;
    }
  }
  public int Verify_If_Screen_Has_Test(String text)
  {
    try {

      int i= (((AndroidDriver) driver).
          findElements(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"))).size();
      WebDriverWait wait=new WebDriverWait(driver,1000);
      Log.info("Element size +" + i);
      return i;
    }
    catch (Exception e) {
      Log.info(" No element found "+e.getMessage());
      return 0;
    }
  }
  public int forgoten_password()
  {
    try {

       int i= driver.findElements(By.xpath("//android.widget.Button[@text='Forgot Password']")).size();
       Log.info("Element size +" + i);
       return i;
    }
    catch (Exception e) {
      Log.info(" No element found "+e.getMessage());
           return 0;
    }

  }

  public WebElement Top_Just_For_Msg()
  {
    waitForSeconds(25);
    xpath="//android.widget.TextView[@text='Just for you']";
    boolean d= Check_if_Element_Displayed(xpath);
    WebElement elem= driver.findElement(By.xpath(xpath));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }


  public WebElement Preview_Msg()
  {
    WebElement elem= driver.findElement(By.xpath("//android.widget.TextView[@text='Just for you']/following::android.widget.TextView[2]"));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }

  public WebElement Msg_Heading()
  {
    int is=driver.findElements(By.id("za.co.fnb.connect.itt:id/message_title")).size()-1;
    WebElement elem= driver.findElements(By.id("za.co.fnb.connect.itt:id/message_title")).get(is);
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }

  public WebElement CLose_Image()
  {
    int index=getJst_For_Frame();
    WebElement elem= driver.findElement(By.className("//android.widget.ImageButton[@content-desc='Open']"));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }

  public WebElement CLose_Exit()
  {
    int index=getJst_For_Frame();
    WebElement elem= driver.findElement(By.xpath("//android.widget.ImageView[@resource-id='za.co.fnb.connect.itt:id/searchButton']"));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }
  public WebElement Msg_text()
  {
    int is=driver.findElements(By.id("za.co.fnb.connect.itt:id/message_body")).size()-1;
    WebElement elem= driver.findElements(By.id("za.co.fnb.connect.itt:id/message_body")).get(is);
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }

  public WebElement Button_text()
  {
    int is=driver.findElements(By.id("za.co.fnb.connect.itt:id/mmButton")).size()-1;
    WebElement elem= driver.findElements(By.id("za.co.fnb.connect.itt:id/mmButton")).get(is);
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }

  public  int getJst_For_Frame()
  {
    Log.info("The number of buttons: "+ driver.findElements(By.xpath("//android.view.View[@resource-id='za.co.fnb.connect.itt:id/recycler_view']/android.widget.RelativeLayout/android.widget.LinearLayout")).size()+1);
   System.out.println("The number of buttons= " + driver.findElements(By.id("za.co.fnb.connect.itt:id/mmButton")).size());
    return driver.findElements(By.xpath("//android.view.View[@resource-id='za.co.fnb.connect.itt:id/recycler_view']/android.widget.RelativeLayout/android.widget.LinearLayout")).size()+1;
  }

  public void setResult()
  {
    PreviousStepResults=false;
  }
  public String[][] getEndResults() {
    return result;
  }


  public boolean Check_if_Element_Displayed(String xpathP)
  {
    boolean flag=false;
    int count_Loop=0;

    while (!flag && count_Loop<=4) {

      if (driver.findElements(By.xpath(xpathP)).size()>0) {
        flag = true;
        System.out.println("Element is displayed");
      }
      else {
        System.out.println("Element is not displayed");
        waitForSeconds(15);
      }
        count_Loop++;
    }
   return flag;

  }

  public WebElement get_Element(String xpath)
  {
    boolean d= Check_if_Element_Displayed(xpath);
    WebElement elem= driver.findElement(By.xpath(xpath));
    WebDriverWait wait=new WebDriverWait(driver,1000);
    WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));
    return elem;
  }
  public  void waitForSeconds(int seconds)
  {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
