package com_automation.Config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {
    ExtentReport extent;
   public static ExtentTest scenarioDef;
    public static ExtentTest feature;
    public static String reportLocation="";
    String fileName=reportLocation+"/extent_Report.html";

    public void Html_Report()
    {
        ExtentReports report;
        ExtentHtmlReporter html_report;
        //System.out.println("Path : "+constants.getExtentReportPath()[0]);
        html_report=new ExtentHtmlReporter(fileName);
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
