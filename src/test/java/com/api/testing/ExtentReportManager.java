package com.api.testing;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    public static ExtentReports createInstance() {
        // Create Spark reporter
        ExtentSparkReporter spark = new ExtentSparkReporter("API_Test_Report.html");

        // Create ExtentReports and attach reporter
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);

        // Optional system info
        extent.setSystemInfo("Tester", "Rutuja");
        extent.setSystemInfo("Environment", "QA");

        return extent;
    }
}
