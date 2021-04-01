package Config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
public class appium_capabilities {
  public static  AppiumDriver<MobileElement> get_Driver()
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
}
