package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class ExemploSteps {
    private static Response response;

    @Given("the API is running")
    public void the_api_is_running() {

    }
    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String url) {
        response = get(url);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        assertEquals(statusCode.intValue(), response.getStatusCode());
        response.then().log().all();
    }

    public static Map<String, Object> capturar_elemento() {
        Map<String, Object> dados = response.jsonPath().getMap("data.find { it.id == 7 }");
        System.out.println("Dados do ID 7: " + dados);
        return dados;
    }
}

