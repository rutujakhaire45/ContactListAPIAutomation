package com.api.testing;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class BaseTest {

    public static String token;
    public static ExtentReports extent;
    public static ExtentTest test;
    public static String baseURI = "https://thinking-tester-contact-list.herokuapp.com";

    @BeforeSuite
    public void setupSuite() {
        // Set base URI for all requests
        RestAssured.baseURI = baseURI;

        // Create report instance
        extent = ExtentReportManager.createInstance();
    }

    @AfterSuite
    public void tearDownSuite() {
        // Write all logs to the report
        extent.flush();
    }
}
