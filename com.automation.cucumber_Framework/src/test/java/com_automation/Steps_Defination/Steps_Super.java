package com_automation.Steps_Defination;

import com_automation.Config.Report_Generator;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class Steps_Super {
    public Report_Generator re;
    public ExtentTest test;
    public ExtentReports report;
    public Steps_Super() {
        re = Report_Generator.getInstance();
        report = re.getReport();
        test = re.getTest();
    }
}
