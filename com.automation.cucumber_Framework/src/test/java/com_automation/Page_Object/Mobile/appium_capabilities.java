package com_automation.Page_Object.Mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
public class appium_capabilities {
  private static appium_capabilities report_instance;
  AppiumDriver<MobileElement> driver;
  public static appium_capabilities getInstance()
  {
    if(report_instance==null)
    {
      report_instance = new appium_capabilities();
    }
    return report_instance;
  }
  public AppiumDriver<MobileElement> get_Driver() {
    if (driver == null) {

      //Set the Desired Capabilities

      org.openqa.selenium.remote.DesiredCapabilities caps = new org.openqa.selenium.remote.DesiredCapabilities();
      caps.setCapability("deviceName", "HUAWEIY560-L01");
      caps.setCapability("udid", "Q6VBBBB5C1702948"); //Give Device ID of your mobile phone
      caps.setCapability("platformName", "Android");
      caps.setCapability("platformVersion", "5.1.1");
      caps.setCapability("appPackage", "za.co.fnb.connect.itt");
      caps.setCapability("appActivity", "za.co.fnb.connect.itt.gui.ZoneWelcomeActivity");
      caps.setCapability("noReset", "true");
      //Instantiate Appium Driver
      try {
        //This is Appium server
        driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);

      } catch (MalformedURLException e) {

        driver = null;
        //System.out.println(e.getMessage());
      }

      return driver;
    }
    else
      return driver;
  }
  public void setDriver()
  {
    driver=null;
  }

}
