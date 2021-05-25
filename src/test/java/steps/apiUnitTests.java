package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class apiUnitTests {
    // City Bikes API Documentation link: https://api.citybik.es/v2/

    RequestSpecification requestSpec;
    Response response;

    @Given("I am using CityBikes API")
    public void iAmUsingCityBikesAPI() {
        // Create request
        requestSpec = new RequestSpecBuilder().build();
        requestSpec.baseUri("https://api.citybik.es/v2");
    }

    @When("I call retrieve networks list")
    public void iCallRetrieveNetworksList() {
        // Define path for call
        requestSpec.basePath("networks");

        // Get response for the API call
        response = given()
                .spec(requestSpec)
                .get();
    }

    @Then("some entries are returned")
    public void someEntriesAreReturned() {
        // Assert response is 200
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        // Print response to screen
//        System.out.println(response.getBody().asString());
    }

    @When("I call network_id api for {string}")
    public void iCallNetworkIdApiFor(String network_id) {
        // Please use API http://api.citybik.es/v2/networks/network_id

        // Define path for call
        requestSpec.basePath("networks/" + network_id);

        // Get response for the API call
        response = given()
                .spec(requestSpec)
                .get();
    }

    @Then("{string} is in {string}")
    public void isIn(String city, String country) {
        String countryCode;

        countryCode = countryToCode(country);

        // assert that response message object is not empty
        Assert.assertNotNull(response);

        Assert.assertEquals(city, response.getBody().jsonPath().getJsonObject("network.location.city"));
        Assert.assertEquals(countryCode, response.getBody().jsonPath().getJsonObject("network.location.country"));
    }

    @When("I call network_id api for visa-frankfurt")
    public void iCallNetworkIdApiForVisaFrankfurt() {
        // Please use API http://api.citybik.es/v2/networks/visa-frankfurt

        // Define path for call
        requestSpec.basePath("networks/visa-frankfurt");

        // Get response for the API call
        response = given()
                .spec(requestSpec)
                .get();
    }

    @Then("Frankfurt is in Germany")
    public void FrankfurtIsInGermany() {
        String countryCode;

        countryCode = countryToCode("Germany");

        // assert that response message object is not empty
        Assert.assertNotNull(response);

        Assert.assertEquals("Frankfurt", response.getBody().jsonPath().getJsonObject("network.location.city"));
        Assert.assertEquals(countryCode, response.getBody().jsonPath().getJsonObject("network.location.country"));
    }

    @When("I call network_id api for nextbike-bath")
    public void iCallNetworkIdApiForNextbikeBath() {
        // Please use API http://api.citybik.es/v2/networks/nextbike-bath

        // Define path for call
        requestSpec.basePath("networks/nextbike-bath");

        // Get response for the API call
        response = given()
                .spec(requestSpec)
                .get();
    }

    @Then("Bath is in United Kingdom")
    public void BathIsInUnitedKingdom() {
        String countryCode;

        countryCode = countryToCode("United Kingdom");

        // assert that response message object is not empty
        Assert.assertNotNull(response);

        Assert.assertEquals("Bath", response.getBody().jsonPath().getJsonObject("network.location.city"));
        Assert.assertEquals(countryCode, response.getBody().jsonPath().getJsonObject("network.location.country"));
    }

    @Then("location is returned")
    public void locationIsReturned() {
        // assert that response message object is not empty
        Assert.assertNotNull(response);

        // Print location to screen
        System.out.println(response.getBody().jsonPath().getJsonObject("network.location").toString());
    }

    @And("country is returned")
    public void countryIsReturned() {
        // assert that response message object is not empty
        Assert.assertNotNull(response);

        // assert that country returned is 'Germany'
        Assert.assertEquals("DE", response.getBody().jsonPath().getJsonObject("network.location.country"));
    }

    @And("city is returned")
    public void successMessageIsReturned() {
        // assert that city returned is 'Frankfurt'
        Assert.assertEquals("Frankfurt", response.getBody().jsonPath().getJsonObject("network.location.city"));
    }

    private String countryToCode(String country) {
        String countryCode = "";

        switch(country.toUpperCase()) {
            case "GERMANY":
                countryCode = "DE";
                break;

            case "UNITED KINGDOM":
                countryCode = "GB";
                break;
        }

        return countryCode;
    }
}
