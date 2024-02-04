package com.qa.ExtentReportListener;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportSingleton {

    private static ExtentReports report;
    private static ExtentTest test;

    public static void startReport() throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String timestampString = String.valueOf(timestamp.getTime());
        String reportPath = System.getProperty("user.dir") + "/OurExtentReport" + timestampString + ".html";

        // Specify the location of the report file
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);

        // Create an ExtentReports instance
        report = new ExtentReports();

        // Attach the HTML reporter
        report.attachReporter(htmlReporter);
    }

    public static void startTest(String testName) {
        // Create a new test in the report
        test = report.createTest(testName);
    }

    public static void logPass(String message) {
        // Log a pass status with the given message
        test.log(Status.PASS, message);
    }

    public static void logFail(String message) {
        // Log a fail status with the given message
        test.log(Status.FAIL, message);
    }

    public static void endTest() {
        // End the current test
        report.flush();
    }

    public static void captureScreenshot(WebDriver driver) throws IOException {
        // Capture screenshot and save it to the specified location
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(System.getProperty("user.dir") + "/QKTRIPImages/" + System.currentTimeMillis() + ".png");
        FileUtils.copyFile(srcFile, destFile);

        // Attach the screenshot to the ExtentReport
        test.addScreenCaptureFromPath(destFile.getAbsolutePath());
    }
}
