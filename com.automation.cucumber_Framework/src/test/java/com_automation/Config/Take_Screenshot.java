package com_automation.Config;

import com_automation.Properti_Files.Properties;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Take_Screenshot {
  public static String Capture(AppiumDriver<MobileElement> driver)
  {
    Properties prop=new Properties();
    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    File Dest = new File( prop.get_Propertie_From_Config("ScreenShot_Path") + System.currentTimeMillis()
        + ".png");
    String errflpath = Dest.getAbsolutePath();
    try {
      FileUtils.copyFile(scrFile, Dest);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return errflpath;

  }

}