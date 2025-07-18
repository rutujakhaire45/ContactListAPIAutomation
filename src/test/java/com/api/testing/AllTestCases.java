package com.api.testing;

import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AllTestCases extends BaseTest {

    private String contactId;

    @Test(priority = 1)
    public void testAddUser() {
        test = extent.createTest("TestCase01_AddUser - Add New User");

        String payload = """
        {
          "firstName": "Rutuja",
          "lastName": "User",
          "email": "rutujakhaire1@test.com",
          "password": "password123"
        }
        """;

        Response res = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/users");

        test.log(Status.INFO, "Response: " + res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 201);
        token = res.jsonPath().getString("token");
        test.log(Status.PASS, "User added successfully. Token: " + token);
    }

    @Test(priority = 2)
    public void testGetProfile() {
        test = extent.createTest("TestCase02_GetProfile - Get User Profile");

        Response res = given()
                .header("Authorization", "Bearer " + token)
                .get("/users/me");

        test.log(Status.INFO, "Response: " + res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 200);
        test.log(Status.PASS, "User profile fetched successfully.");
    }

    @Test(priority = 3)
    public void testUpdateUser() {
        test = extent.createTest("TestCase03_UpdateUser - Update User Info");

        String payload = "{ \"firstName\": \"UpdatedUser\" }";

        Response res = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(payload)
                .patch("/users/me");

        test.log(Status.INFO, "Response: " + res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 200);
        test.log(Status.PASS, "User updated successfully.");
    }

    @Test(priority = 4)
    public void testLoginUser() {
        test = extent.createTest("TestCase04_LoginUser - Login");

        String payload = """
        {
          "email": "rutujakhaire1@test.com",
          "password": "password123"
        }
        """;

        Response res = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/users/login");

        token = res.jsonPath().getString("token");
        test.log(Status.INFO, "Response: " + res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 200);
        test.log(Status.PASS, "User logged in successfully.");
    }

    @Test(priority = 5)
    public void testAddContact() {
        test = extent.createTest("TestCase05_AddContact - Add New Contact");

        String payload = """
        {
          "firstName": "Anvi",
          "lastName": "Kapoor",
          "birthdate": "1999-08-15",
          "email": "anvi@example.com",
          "phone": "9876543210",
          "street1": "123 Road",
          "city": "Mumbai",
          "stateProvince": "MH",
          "postalCode": "400001",
          "country": "India"
        }
        """;

        Response res = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/contacts");

        contactId = res.jsonPath().getString("_id");
        test.log(Status.INFO, "Response: " + res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 201);
        test.log(Status.PASS, "Contact added. ID: " + contactId);
    }

    @Test(priority = 6)
    public void testGetContacts() {
        test = extent.createTest("TestCase06_GetContacts - Get All Contacts");

        Response res = given()
                .header("Authorization", "Bearer " + token)
                .get("/contacts");

        test.log(Status.INFO, "Response: " + res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 200);
        test.log(Status.PASS, "Contact list retrieved.");
    }

    @Test(priority = 7)
    public void testGetContactById() {
        test = extent.createTest("TestCase07_GetContactById - Get Contact");

        Response res = given()
                .header("Authorization", "Bearer " + token)
                .get("/contacts/" + contactId);

        test.log(Status.INFO, "Response: " + res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 200);
        test.log(Status.PASS, "Contact retrieved by ID.");
    }

    @Test(priority = 8)
    public void testUpdateContact() {
        test = extent.createTest("TestCase08_UpdateContact - Update Full Contact");

        String payload = """
        {
          "firstName": "AnviUpdated",
          "lastName": "Kapoor",
          "birthdate": "1999-08-15",
          "email": "anviupdated@example.com",
          "phone": "9988776655",
          "street1": "456 Avenue",
          "city": "Pune",
          "stateProvince": "MH",
          "postalCode": "411001",
          "country": "India"
        }
        """;

        Response res = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(payload)
                .put("/contacts/" + contactId);

        test.log(Status.INFO, "Response: " + res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 200);
        test.log(Status.PASS, "Contact updated successfully.");
    }

    @Test(priority = 9)
    public void testPartialUpdateContact() {
        test = extent.createTest("TestCase09_PartialUpdate - Patch Contact");

        String payload = "{ \"firstName\": \"Rutuja\" }";

        Response res = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(payload)
                .patch("/contacts/" + contactId);

        test.log(Status.INFO, "Response: " + res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 200);
        test.log(Status.PASS, "Contact partially updated.");
    }

    @Test(priority = 10)
    public void testLogoutUser() {
        test = extent.createTest("TestCase10_Logout - Logout");

        Response res = given()
                .header("Authorization", "Bearer " + token)
                .post("/users/logout");

        test.log(Status.INFO, "Response: " + res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 200);
        test.log(Status.PASS, "User logged out successfully.");
    }
}
