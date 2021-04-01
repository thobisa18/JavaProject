package com_automation.Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestListenerReport implements ITestListener {

  public void onTestStart(ITestResult iTestResult) {

  }

  public void onTestSuccess(ITestResult iTestResult) {

  }

  public void onTestFailure(ITestResult iTestResult) {

  }

  public void onTestSkipped(ITestResult iTestResult) {
    System.out.println("Skipped ");
  }

  public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
      System.out.println("....................");
  }

  public void onStart(ITestContext iTestContext) {
     System.out.println("Test execution started");
  }

  public void onFinish(ITestContext iTestContext) {
    System.out.println("Test execution ended");
  }
}
