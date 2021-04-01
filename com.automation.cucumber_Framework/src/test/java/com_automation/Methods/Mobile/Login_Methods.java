package com_automation.Methods.Mobile;

import com_automation.Page_Object.Mobile.Login_Objects;
import org.junit.Assert;

public class Login_Methods extends Login_Objects {
  public void go_To_Login(){
    Home_Page_Login().click();
    waitForSeconds(20);
  }
public void login_Enter_Password(String password)
{
  password().sendKeys(password);
  waitForSeconds(10);
}
public int verify_if_Login_has_Failed(String expected_error)
{
  return Verify_If_Screen_Has_Text(expected_error);
}
public void Click_Login()
{
  Logon_Button_1().click();
  waitForSeconds(20);
}
public void verify_If_Customer_Logged_In_Successful()
{
  if(forgoten_password()>0)
    Assert.fail();
}
}
