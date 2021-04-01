package com_automation.Page_Object.Mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.offset.PointOption.point;

public class Generic_Elements {
    AppiumDriver<MobileElement> driver;
    String xpath;
    public Generic_Elements()
    {
        driver=appium_capabilities.getInstance().get_Driver();
    }
    public boolean Check_if_Element_Displayed(String xpathP)
    {
        boolean flag=false;
        int count_Loop=0;

        while (!flag && count_Loop<=3) {

            if (driver.findElements(By.xpath(xpathP)).size()>0) {
                flag = true;
                System.out.println("Element is displayed");

            }
            else {
                System.out.println("Element is not displayed");
                waitForSeconds(10);
                if(count_Loop>=2)
                    scrollDown(xpathP);
            }

            if(count_Loop==3 && !flag)
                //Final_Result.getInstance().set_Current_Results(false,"Element("+xpathP+") not found");
            count_Loop++;
        }

        return flag;

    }
    public void scrollDown(String xpath)
    {
        int i=0;
        while(driver.findElements(By.xpath(xpath)).size()==0 && i<4)
        {
            System.out.println(xpath);
            Dimension dim = driver.manage().window().getSize();
            int height = dim.getHeight();
            int width = dim.getWidth();
            int x = width/2;
            int top_y = (int)(height*0.80);
            int bottom_y = (int)(height*0.20);
            System.out.println("coordinates :" + x + "  "+ top_y + " "+ bottom_y);
            TouchAction ts = new TouchAction(driver);
            ts.press(point(x,top_y)).moveTo(point(x, bottom_y)).release().perform();
            i++;

        }
    }
    public  void waitForSeconds(int seconds)
    {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public WebElement get_Element(String xpath)
    {
        boolean d= Check_if_Element_Displayed(xpath);
        WebElement elem= driver.findElement(By.xpath(xpath));
        WebDriverWait wait=new WebDriverWait(driver,1000);
        WebElement element = wait.until(ExpectedConditions.visibilityOf(elem));

        return elem;
    }


    //Method return zero when text is display
    public int Verify_If_Screen_Has_Text(String text)
    {
        try {

            int i= (driver).
                    findElements(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")")).size();
            WebDriverWait wait=new WebDriverWait(driver,1000);
            return i;
        }
        catch (Exception e) {
            return 0;
        }
    }
}
