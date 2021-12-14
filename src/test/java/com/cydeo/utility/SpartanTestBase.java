package com.cydeo.utility;

import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class SpartanTestBase {

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://54.174.254.49:8000";
        RestAssured.basePath = "/api";
    }
    @AfterAll
    public static void teardown() {
        RestAssured.reset();
        // there is also a method to ensure to reset all rest assured static fields
        // that serenity use SerenityRest.reset() method
        SerenityRest.reset();
    }

}
