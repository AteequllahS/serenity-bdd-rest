package com.cydeo.rest;

import com.cydeo.utility.SpartanTestBase;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;


// How do we tell this is not a regular test but it's a serenity test that meant to generate some report
@ExtendWith(SerenityJUnit5Extension.class)
public class SpartanHelloTest extends SpartanTestBase {

    @Test
    public void testHello(){

       SerenityRest.given()
                            .log().all().
                    when()
                            .get("/hello").
                    then()
                            .log().all()
                            .statusCode(200);

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

    @DisplayName("Test Hello with detailed steps in report")
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
        Ensure.that("Status Code is 200", thenPart -> thenPart.statusCode(200)  ) ;

        Ensure.that("Content Type is Text Plain" ,
                            blaBla -> blaBla.contentType(ContentType.TEXT) ) ;

        Ensure.that("Body is Hello From Sparta" ,
                             thenPart -> thenPart.body( is("Hello from Sparta") )  ) ;




    }


}
