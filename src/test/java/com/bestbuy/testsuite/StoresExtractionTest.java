package com.bestbuy.testsuite;

import com.fasterxml.jackson.databind.util.TypeKey;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

public class StoresExtractionTest {
    static ValidatableResponse response;

    @BeforeClass
    public void inIt() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
    }

    //1. Extract the limit
    @Test
    public void test001() {
        System.out.println("The limit is : " + response.extract().path("limit"));
    }

    //2. Extract the total
    @Test
    public void test002() {
        System.out.println("The total value is : " + response.extract().path("total"));
    }

    //3. Extract the name of 5th store
    @Test
    public void test003() {
        System.out.println("Name of 5th store is: " + response.extract().path("data[4].name"));
    }

    //4. Extract the names of all the store
    @Test
    public void test004() {
        System.out.println("Names of all stores: " + response.extract().path("data.name"));
    }

    //5. Extract the Ids of all the store
    @Test
    public void test005() {
        System.out.println("Store Ids of all stores are : " + response.extract().path("data.id"));
    }

    //6. Print the size of the data list
    @Test
    public void test006() {
        System.out.println("Size of the data list : " + response.extract().path("data.size()"));
    }

    //7. Get all the value of the store where store name = St Cloud
    @Test
    public void test007() {
        System.out.println("Getting All the values of the store with store name St Cloud: " + response.extract().path("data.findAll{it.name=='St Cloud'}"));
    }

    //8. Get the address of the store where store name = Rochester
    @Test
    public void test008() {
        System.out.println("Address of the store Rochester is : " + response.extract().path("data.findAll{it.name=='Rochester'}.address"));
    }

    //9. Get all the services of 8th store
    @Test
    public void test009() {
        System.out.println("Getting all services of 8th store : " + response.extract().path("data[7].services"));
    }

    //10. Get storeservices of the store where service name = Windows Store
    @Test
    public void test010() {
        List<Map<String, ?>> storeServices = response.extract().path("data.services*.findAll{it.name=='Windows Store'}.storeservices");
        System.out.println("Getting storeservices of the store where service name is Window Store: "+ storeServices);
//        for (HashMap<String, ?> storeServ : storeServices) {
//            for (Object key : storeServ.keySet()) {
//                System.out.println(key.toString() + " -> " + storeServ.get(key).toString());
//            }
//        }
    }
    //11. Get all the storeId of all the store
    @Test
    public void test011(){
        List<List<Integer>> listOfList = new ArrayList<>(response.extract().path("data.services.storeservices.storeId"));
        HashSet<Integer> uniqueStoreIds = new HashSet<>();
        for (List<Integer> list : listOfList){
            for(Integer abc: list) {
                uniqueStoreIds.add(abc);
            }
        }
        System.out.println("Getting all the store Ids of all store: "+response.extract().path("data.services.storeservices.storeId"));
        System.out.println("Unique store Ids are: "+uniqueStoreIds);
    }
    //12. Get id of all the store
    @Test
    public void test012(){
        System.out.println("Getting Ids of all the store are : " + response.extract().path("data.id"));
    }
    //13. Find the store names Where state = ND
    @Test
    public void test013(){
        System.out.println("Finding store names where state = ND are: "+response.extract().path("data.findAll{it.state=='ND'}.name"));
    }
    //14. Find the Total number of services for the store where store name = Rochester
    @Test
    public void test014(){
        System.out.println("Total number of services for the store Rochester are: "+response.extract().path("data.findAll{it.name=='Rochester'}.services.get(0).size()"));
    }
    //15. Find the createdAt for all services whose name = “Windows Store”
    @Test
    public void test015(){
        System.out.println("createdAt for all services whose name = “Windows Store” are :"+response.extract().path("data.services*.find{it.name=='Windows Store'}.createdAt"));
    }
    //16. Find the name of all services Where store name = “Fargo”
    @Test
    public void test016(){
        System.out.println("Name of all services where store name is \"Fargo\" :"+response.extract().path("data.findAll{it.name=='Fargo'}.services.name"));
    }
    //17. Find the zip of all the store
    @Test
    public void test017(){
        System.out.println("Zip of all the stores : "+response.extract().path("data.zip"));
    }
    //18. Find the zip of store name = Roseville
    @Test
    public void test018(){
        System.out.println("Zip of store name Roseville : "+response.extract().path("data.findAll{it.name=='Roseville'}.zip"));
    }
    //19. Find the storeservices details of the service name = Magnolia Home Theater
    @Test
    public void test019(){
        //List<HashMap<String, ?>> storeServices = response.extract().path("data.services.findAll{it.name=='Magnolia Home Theater'}.storeservices");
        List<HashMap<String, ?>> storeServices = response.extract().path("data.services*.find{it.name == 'Magnolia Home Theater'}.storeservices");
        System.out.println("Store services details of the service named Magnolia Home Theater are : "+storeServices);
    }
    //20. Find the lat of all the stores
    @Test
    public void test020(){
        System.out.println("Lat of all the stores: "+response.extract().path("data.lat"));
    }
}
