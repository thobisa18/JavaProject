package com_automation.Runnner;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com_automation.Config.Report_Generator;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;

//@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com_automation/Features",glue = {"com_automation.Steps_Defination"},monochrome = true, plugin ={"pretty","html:target/cucumber_reports/cucumber","json:target/cucumber_reports/cucumber.json","junit:target/cucumber_reports/cucumber.xml"})
public class Runnner extends AbstractTestNGCucumberTests {
    public Report_Generator re;
    public ExtentReports report;
    @AfterClass
    public void cleanUp()
    {
        re = Report_Generator.getInstance();
        report = re.getReport();
        report.flush();
    }
}
