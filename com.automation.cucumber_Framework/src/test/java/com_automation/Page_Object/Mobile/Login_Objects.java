package com_automation.Page_Object.Mobile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login_Objects extends Generic_Elements {

  public WebElement Home_Page_Login() {
    xpath = "//android.widget.TextView[@text='Login']";
    if(Check_if_Element_Displayed(xpath))
      return get_Element(xpath);
    else {
      //Log.error("Secure chat button not displayed");
      return null;
    }
  }
  public WebElement Logon_Button_1()
  {
    WebElement elem=driver.findElement(By.xpath("//android.widget.Button[@text='Login']"));
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

  public int forgoten_password()
  {
    try {
      int i= driver.findElements(By.xpath("//android.widget.Button[@text='Forgot Password']")).size();
      return i;
    }
    catch (Exception e) {
      return 0;
    }

  }
}
