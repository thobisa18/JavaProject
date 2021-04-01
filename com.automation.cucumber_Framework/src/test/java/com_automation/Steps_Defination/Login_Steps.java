package com_automation.Steps_Defination;

import com_automation.Config.Take_Screenshot;
import com_automation.Methods.Mobile.Login_Methods;
import com_automation.Page_Object.Mobile.appium_capabilities;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;

import io.cucumber.java.Before;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
/*import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.core.gherkin.Step;
import io.cucumber.java.AfterStep;
import io.cucumber.plugin.event.TestCase;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import io.cucumber.java.BeforeStep;*/

public class Login_Steps extends Steps_Super {
    Login_Methods methods;
    ExtentTest extent_Test;

    io.cucumber.java.Scenario scenario;
    public Login_Steps()
    {
        methods=new Login_Methods();
    }
    @Before
    public void before(io.cucumber.java.Scenario scenario) {
       this.scenario = scenario;
    }

    @Given("The user is on login screen")
    public void the_user_is_on_login_screen() throws Throwable{

        test = report.createTest(Feature.class,scenario.getName());
        test=test.createNode(Scenario.class,scenario.getName());
        extent_Test=test.createNode(new GherkinKeyword("Given"),"User on Login screen");
        methods.go_To_Login();
        extent_Test.addScreenCaptureFromPath(Take_Screenshot.Capture(appium_capabilities.getInstance().get_Driver()));
    }
    @When("The user enters correct login details: {string}")
    public void the_user_enters_correct_login_details(String password) throws Throwable {
        extent_Test=null;
        extent_Test=test.createNode(new GherkinKeyword("When"),"The user enters correct login details");
        methods.login_Enter_Password(password);
        extent_Test.addScreenCaptureFromPath(Take_Screenshot.Capture(appium_capabilities.getInstance().get_Driver()));
    }

    @When("The user clicks the login button")
    public void the_user_clicks_the_login_button() throws Throwable {

        extent_Test=null;
        extent_Test=test.createNode(new GherkinKeyword("When"),"The user clicks the login button");
        methods.Click_Login();
        extent_Test.addScreenCaptureFromPath(Take_Screenshot.Capture(appium_capabilities.getInstance().get_Driver()));
    }

    @Then("Verify if the user has logged in successful")
    public void verify_if_the_user_has_logged_in_successful() throws Throwable {
        extent_Test=null;
        extent_Test=test.createNode(new GherkinKeyword("Then"),"Verify if the user has logged in successful");
        methods.verify_If_Customer_Logged_In_Successful();
        extent_Test.addScreenCaptureFromPath(Take_Screenshot.Capture(appium_capabilities.getInstance().get_Driver()));
        appium_capabilities.getInstance().setDriver();

    }

    @When("The user enters incorrect login password: {string}")
    public void theUserEntersIncorrectLoginPassword(String password) throws Throwable {
        extent_Test=null;
        extent_Test=test.createNode(new GherkinKeyword("When"),"The user enters incorrect login password");
        methods.login_Enter_Password(password);
        extent_Test.addScreenCaptureFromPath(Take_Screenshot.Capture(appium_capabilities.getInstance().get_Driver()));
    }
    @Then("Verify if the app is throwing the error: {string}")
    public void verify_if_the_app_is_throwing_the_error(String expected_error) throws Throwable{
        extent_Test=null;
        extent_Test=test.createNode(new GherkinKeyword("Then"),"Verify if the app is throwing the error");
       if (methods.verify_if_Login_has_Failed(expected_error)<=0)
       {
           extent_Test=test.fail("The app didn't throw an error: '"+expected_error +"' ");
       }
        extent_Test.addScreenCaptureFromPath(Take_Screenshot.Capture(appium_capabilities.getInstance().get_Driver()));
        appium_capabilities.getInstance().setDriver();
    }



   /* @BeforeStep
    public void getStepName(io.cucumber.java.Scenario scenario) throws Exception {

        Field f = scenario.getClass().getDeclaredField("testCase");
        f.setAccessible(true);
        TestCase r = (TestCase) f.get(scenario);

        List<PickleStepTestStep> stepDefs = r.getTestSteps()
                .stream()
                .filter(x -> x instanceof PickleStepTestStep)
                .map(x -> (PickleStepTestStep) x)
                .collect(Collectors.toList());

        currentStep = stepDefs.get(counter);

        System.out.println(currentStep.getStep());

    }
   // @AfterStep
    public void afterStep(io.cucumber.java.Scenario scenario) {
        counter += 1;
    }*/
}
