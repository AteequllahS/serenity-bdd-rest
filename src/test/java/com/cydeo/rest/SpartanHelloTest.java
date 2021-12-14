package com.cydeo.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.extension.ExtendWith;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SerenityJUnit5Extension.class)
public class SpartanHelloTest {

    //send request to GET http://54.174.254.49:8000
    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = "http://54.174.254.49:8000";
        RestAssured.basePath="/api";
    }

    @AfterAll
    public static void teardown() {
      RestAssured.reset();
    }


    @Test
    public void testHello(){

        SerenityRest.given()
                .log().all().
        when()
                .get("/hello").
        then()
                .log().all()
                .statusCode(200)
                ;
// get response object from above request
//        .lastResponse() method from SerenityRest will return
        // the response object from previous request
        lastResponse().prettyPrint() ;


        // can you print out the Date header using the response object.
        //SerenityRest.lastResponse() return Response Object from previous request
        System.out.println("SerenityRest.lastResponse().header(\"Date\") = "
                + lastResponse().header("Date"));

        System.out.println("SerenityRest.lastResponse().contentType() = "
                + lastResponse().contentType());

    }


    @Test
    public void testHelloResponseStepByStep(){

        SerenityRest.given()
                .log().uri().
                when()
                .get("/hello") ;
        // verify status code is 200, content type is text
        // body is Hello From Sparta
        assertEquals(200, lastResponse().statusCode() ) ;

        // If you want to add the assertion of the response
        // in the report as a separate step,
        // Ensure class from Serenity provide the way
        // to let our response assertion show up as a step
        // here is how
        Ensure.that("Verify Status Code is 200" ,
                thenPart -> thenPart.statusCode(200)
        ) ;

        Ensure.that("Content Type is Text Plain" ,
                blaBla -> blaBla.contentType(ContentType.TEXT)
        ) ;

        Ensure.that("Body is Hello From Sparta" ,
                thenPart -> thenPart.body( is("Hello from Sparta") )
        ) ;




    }

}
