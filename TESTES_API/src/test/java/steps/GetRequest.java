package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class GetRequest {
    private static Response response;
    private static RequestSpecification request;

    @Given("the API is running")
    public void the_api_is_running() {
        request = given()
                .header("x-api-key", "reqres-free-v1");

    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String url) {
        response = request.get(url);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        assertEquals(statusCode.intValue(), response.getStatusCode());
        response.then().log().all();
        capturar_elemento();
    }


    public static void capturar_elemento() {
        List<Map<String, Object>> dataList = response.jsonPath().getList("data");
        Map<String, Object> dados = dataList.stream()
                .filter(item -> item.get("id") instanceof Integer && (Integer) item.get("id") == 7)
                .findFirst()
                .orElse(null);

        if (dados != null) {
            System.out.println("Dados do ID 7: " + dados);
        } else {
            System.out.println("Nenhum dado encontrado para o ID 7.");
        }
    }
}




