package com.cydeo.rest;

import com.cydeo.utility.SpartanTestBase;
import io.restassured.http.ContentType;
import lombok.Value;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static net.serenitybdd.rest.SerenityRest.*;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.is;


@ExtendWith(SerenityJUnit5Extension.class)
public class SpartanDataTest extends SpartanTestBase {


    @DisplayName("Test user able to get single Spartan Data")
    @Test
    public void test1Spartan(){

        // get last valid ID from /spartans
        SerenityRest.given()
                        .log().uri().
                     when().get("/spartans") ;
        int lastId = lastResponse().path("id[-1]") ;

        SerenityRest.given()
                        .pathParam("id", lastId).
                     when()
                        .get("/spartans/{id}");
        // check status code is 200 , content type is json , id match the lastId
        Ensure.that("Status code is 200", v -> v.statusCode(200)    ) ;
        Ensure.that("Content Type is JSON", v-> v.contentType(ContentType.JSON) ) ;
        // or you can chain then in this way : @Shazia @Yuliia
        Ensure.that("Status code is 200", v -> v.statusCode(200)    )
            .andThat("Content Type is JSON", v-> v.contentType(ContentType.JSON) )
            .andThat("The Id value is "+lastId
                            , v -> v.body("id" , is(lastId)   )      );


    }

    // Repeat above tests for 5 valid id :
    @ParameterizedTest(name = "Testing Spartan Data with ID of {0}")
    @ValueSource(ints = { 54, 26, 37, 15, 95}   )
    public void testOneSpartanDDT(int idParam){

        System.out.println("idParam = " + idParam);

        SerenityRest.given()
                        .pathParam("id", idParam)
                        .log().uri().
                        when()
                        .get("/spartans/{id}");

        Ensure.that("Status code is 200", v -> v.statusCode(200)    )
                .andThat("Content Type is JSON", v-> v.contentType(ContentType.JSON) )
                .andThat("The Id value is "+idParam
                        , v -> v.body("id" , is(idParam)   )      );
    }


}
