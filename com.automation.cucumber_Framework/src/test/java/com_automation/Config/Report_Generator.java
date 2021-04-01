package com_automation.Config;

import com_automation.Properti_Files.Properties;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Report_Generator {

     ExtentTest test;
     ExtentReports report;
    ExtentHtmlReporter html_report;
    private static Report_Generator report_instance;
    private  Report_Generator()
    {

    }
    public static Report_Generator getInstance()
    {
        if(report_instance==null)
        {
            report_instance = new Report_Generator();
        }
        return report_instance;
    }


    public  ExtentTest getTest()
    {
        return test;
    }

    public ExtentReports getReport()
    {
        if(report==null)
        {

            Html_Report();
        }

        return report;
    }




    public void Html_Report()
    {

      //System.out.println("Path : "+constants.getExtentReportPath()[0]);
      html_report=new ExtentHtmlReporter(Properties.get_Propertie_From_Config("Extent_Report_Path")+"/Extent_Report.html");
      html_report.config().setReportName("NLP Automation Report");
      html_report.config().setDocumentTitle("NLP Automation Report");
      html_report.config().setTheme(Theme.DARK);

      report=new ExtentReports();
      report.setSystemInfo("Environment", "QA");
      report.setSystemInfo("Host NAme", "QA");
      report.setSystemInfo("User", "Thobisa Monono");
      report.attachReporter(html_report);

    }

}
