package com.bestbuy.ProducCRUDTest;

import com.bestbuy.model.ProductPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ProductCRUDTest extends TestBase {
    static ValidatableResponse response;

    static String name = "New Product" + TestUtils.getRandomValue();
    static String type = "Hard Good" + TestUtils.getRandomValue();
    static String upc = "10" + TestUtils.getRandomValue();
    static double price = 5.98;
    static double shipping = 0;
    static String description = "Compatible with select electronic devices" + TestUtils.getRandomValue();
    static String manufacturer = " Duracell";
    static String model = "MN9876TF" + TestUtils.getRandomValue();
    static String url = " http://www.bestway.com/" + TestUtils.getRandomValue() + "/" + TestUtils.getRandomValue();
    static String image = " http://img.bbystatic.com/" + TestUtils.getRandomValue() + "/" + TestUtils.getRandomValue();
    static Object productId;

    // Create product
    @Test
    public void postTest() {

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

        Response response = given()
                .contentType(ContentType.JSON)
                .body(productPojo)
                .when()
                .post("/products");
        response.then().log().all().statusCode(201);
        response.prettyPrint();
    }

    @Test
    public void getTheProduct() {

        Response response = given()
                .when()
                .get("/products");
        response.then().statusCode(200);
        response.prettyPrint();
    }


    @Test
    public void updateTheProduct() {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("Kodak");
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setShipping(shipping);
        productPojo.setDescription("Kodak Batteries");
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .put("/products/9999681");
        response.then().log().all().statusCode(200);
        response.prettyPrint();

    }

    @Test
    public void deleteTheProduct() {
        Response response = given()
                .pathParam("id", 9999681)
                .when()
                .delete("/products/{id}");
        response.then().log().all()
                .statusCode(200);
        Response response1 = given()
                .pathParam("id", 9999681)
                .when()
                .get("/products/{id}");
        response1.then().statusCode(404);
        response1.prettyPrint();
    }
}
