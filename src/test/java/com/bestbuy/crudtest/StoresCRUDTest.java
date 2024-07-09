package com.bestbuy.crudtest;

import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class StoresCRUDTest extends TestBase {
    static String storeName = TestUtils.getRandomValue() + "store";
    static String storeType = "BigBox";
    static String address1 = "13513 Ridgedale Dr";
    static String address2 = "";
    static String city = "Hopkins";
    static String state = "MN";
    static String zip = "55305";
    static double lat = 44.969658;
    static double lng = -93.449539;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";



    @Test
    public void getStoresTest(){
        ValidatableResponse response = given().log().ifValidationFails()
                .header("Accept","application/json")
                .when()
                .get("/stores")
                .then().statusCode(200);
        System.out.println(response.extract().body().asPrettyString());
    }
    @Test
    public void postStoresTest(){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(storeName);
        storePojo.setType(storeType);
        storePojo.setAddress(address1);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);
        Response response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .when()
                .body(storePojo)
                .post("/stores");
        response.then().log().ifValidationFails().statusCode(201);
        response.prettyPrint();
    }
    @Test
    public void getStoresByIdTest(){

    }
    @Test
    public void patchStoresByIdTest(){
        String name = StoresCRUDTest.storeName + "Updated";
        String type = StoresCRUDTest.storeType + "Updated";
        String address = StoresCRUDTest.address1 + "Updated";
        String address2 = StoresCRUDTest.address2;
        String city = StoresCRUDTest.city + "Updated";
        String state = StoresCRUDTest.state + "Updated";
        String zip = TestUtils.getRandomValue();
        double lat = 44.898855;
        double lng = -36.556651;
        String hours = "Mon: 9-10; Tue: 9-10; Wed: 9-10; Thurs: 9-10; Fri: 9-10; Sat: 11-6; Sun: 12-5";

        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);

        Response response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .pathParam("id", 18)
                .when()
                .body(storePojo)
                .put("/stores/{id}");
        response.prettyPrint();
        response.then().log().ifValidationFails().statusCode(200);

    }
    @Test
    public void deleteStoresByIdTest(){
        given().log().ifValidationFails()
                .pathParam("id", 8923)
                .when()
                .delete("/stores/{id}")
                .then().log().ifValidationFails().statusCode(200);
        given()
                .pathParam("id", 8923)
                .when()
                .get("/stores/{id}")
                .then()
                .statusCode(404);
    }
}
