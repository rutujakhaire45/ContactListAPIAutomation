package com.api.testing;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestCase01_AddUser extends BaseTest {

    @Test
    public void addUser() {
        // Start reporting for this test
        test = extent.createTest("TestCase01 - Add New User");

        // JSON payload for new user
        String payload = """
        {
          "firstName": "Test",
          "lastName": "User",
          "email": "rutujak@test.com",
          "password": "myNewPassword"
        }
        """;

        test.info("Sending POST request to create a new user");

        // API request
        Response res = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/users");

        // Log status code in report
        test.info("Response status code: " + res.getStatusCode());

        // Assertion
        try {
            Assert.assertEquals(res.getStatusCode(), 201);
            test.pass("User created successfully with status code 201");
        } catch (AssertionError e) {
            test.fail("Expected status code 201 but got " + res.getStatusCode());
            throw e;
        }

        // Extract token and log
        token = res.jsonPath().getString("token");
        test.info("Token generated: " + token);

        System.out.println("Token: " + token);
    }
}
