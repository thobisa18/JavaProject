package com_automation.Properti_Files;

import com_automation.Config.Log;

import java.io.File;
import java.io.FileReader;

/**
 * <h1>Intents Driver!</h1>
 * This class read  data from config.properties
 * Pass property description and get value in return
 * <p>
 * <b>Note:</b> It gets value of a property
 * @author  Thobisa Monono F5208882
 * @version 1.0
 * @since   2019-1=07-27
 */
public class Properties {
  public static String get_Propertie_From_Config(String description)
  {
    java.util.Properties prop=new java.util.Properties();
    String propFileName="./src/test/java/com_automation/Properti_Files/config.properties";
    FileReader inputStream=null;
    String result="";
    try {

     inputStream= new FileReader(propFileName);
      File file=new File(propFileName);
      if(!file.exists())
        Log.info("File doesnt exist");
      if(inputStream!=null)
      {
        prop.load(inputStream);
        result=prop.getProperty(description);
        Log.info("The value of property '"+ description+"' is "+result);
        inputStream.close();
      }
      else {

        Log.error("Property File '"+propFileName+"' not found in the class path");
        Log.info("The value of property '"+ description+"' is not found");
        inputStream.close();
      }
    }
    catch (Exception e)
    {
      Log.error("An error occured : "+e.getMessage());
    }

    return result;
  }
}
