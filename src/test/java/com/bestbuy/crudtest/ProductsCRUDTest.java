package com.bestbuy.crudtest;

import com.bestbuy.model.ProductPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ProductsCRUDTest extends TestBase {
    static ValidatableResponse response;

    static String name = "Duracell - AAA Batteries (8-Pack)" + TestUtils.getRandomValue();
    static String type = "HardGood" + TestUtils.getRandomValue();
    static Double price = 5.49;
    static String upc = TestUtils.getRandomValue();
    static Double shipping = 4.99;
    static String description = "Compatible with select electronic devices; AAA size; DURALOCK Power Preserve technology; 4-pack";
    static String manufacturer = "Duracell";
    static String model = "MN2400B4Z";
    static String url = "http://www.bestbuy.com/site/duracell-aaa-batteries-4-pack/43900.p?id=1051384074145&skuId=43900&cmp=RMXCC";
    static String image = "http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";

    @Test
    public void getProductsTest() {
        Response response = (Response) given().log().ifValidationFails()
                .header("Accept", "application/json")
                .when()
                .get("/products")
                .then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void postProductsTest() {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setShipping(shipping);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        Response response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .when()
                .body(productPojo)
                .post("/products");
        response.prettyPrint();
        response.then().log().ifValidationFails().statusCode(201);
    }

    @Test
    public void getProductsByIdTest() {

    }

    @Test
    public void patchProductsByIdTest() {
        String name = ProductsCRUDTest.name + "Updated";
        String type = ProductsCRUDTest.type + "Updated";
        Double price = 1600.00;
        String upc = TestUtils.getRandomValue();
        Double shipping = 5.99;
        String description = ProductsCRUDTest.description + "Updated";
        String manufacturer = ProductsCRUDTest.manufacturer + "Updated";
        String model = ProductsCRUDTest.model + "Updated";
        String url = ProductsCRUDTest.url + "Updated";
        String image = ProductsCRUDTest.image + "Updated";

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setShipping(shipping);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        Response response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .pathParam("id", 9999680)
                .when()
                .body(productPojo)
                .put("/products/{id}");
        response.prettyPrint();
        response.then().log().ifValidationFails().statusCode(200);

    }

    @Test
    public void deleteProductsByIdTest() {
        given().log().ifValidationFails()
                .pathParam("id", 9999684)
                .when()
                .delete("/products/{id}")
                .then().log().ifValidationFails().statusCode(200);
        given()
                .pathParam("id", 9999684)
                .when()
                .get("/products/{id}")
                .then()
                .statusCode(404);
    }
}