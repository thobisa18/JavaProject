package com_automation.Page_Object;

import com_automation.Config.Log;
import com_automation.Properti_Files.Properties;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class All_Driver {


  public static AppiumDriver<MobileElement> get_Andriod_Driver()
  {
    Log.info("Get appium driver");
    //Set the Desired Capabilities

    AppiumDriver<MobileElement> driver;
    org.openqa.selenium.remote.DesiredCapabilities caps = new org.openqa.selenium.remote.DesiredCapabilities();
    caps.setCapability("deviceName", "HUAWEIY560-L01");
    caps.setCapability("udid", "Q6VBBBB5C1702948"); //Give Device ID of your mobile phone
    caps.setCapability("platformName", "Android");
    caps.setCapability("platformVersion", "5.1.1");
    caps.setCapability("appPackage", "za.co.fnb.connect.itt");
    caps.setCapability("appActivity", "za.co.fnb.connect.itt.gui.ZoneWelcomeActivity");
    caps.setCapability("noReset", "true");
    //Instantiate Appium Driver
    try
    {
      //This is Appium server
      driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);
      Log.info("Appium driver created successfully");
    }
    catch (MalformedURLException e)
    {
      Log.error(e.getMessage());
      driver=null;
      //System.out.println(e.getMessage());
    }
    return  driver;
  }
  public WebDriver web_Driver()
  {
     WebDriver driver=null;
    String browserName= Properties.get_Propertie_From_Config("browser");
    if(browserName.equals("ie"))
    {
      System.setProperty("webdriver.ie.driver","src/test/Drivers/IEDriverServer.exe");
      driver= new InternetExplorerDriver();
    }
    else if (browserName.equals("chrome"))
    {
      System.setProperty("webdriver.chrome.driver","src/test/Drivers/chromedriver.exe");
      driver= new ChromeDriver();
    }
    driver.manage().window().maximize();
    driver.manage().deleteAllCookies();
    driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
    driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
    driver.get(Properties.get_Propertie_From_Config("url"));
    return driver;
  }



}

