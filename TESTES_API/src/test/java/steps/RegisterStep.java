package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.E;
import io.restassured.response.Response;
import io.cucumber.java.Before;
import util.RestAssuredConfig;
import static org.junit.Assert.*;

public class RegisterStep {
    private static Response response;
    private static io.restassured.specification.RequestSpecification request;
    private static String url;
    private static String jsonBody;

    @Before
    public void setup() {
        request = RestAssuredConfig.defaultRequestSpec();
    }

    @Given("I send a POST request to {string} with the registration data")
    public void envio_uma_requisicao_post_para_register_com_os_dados(String endpoint) {
        url = endpoint;
        jsonBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
        System.out.println("\n========== REQUEST ==========");
        System.out.println("Endpoint: " + url);
        System.out.println("Body: " + jsonBody);
        request.body(jsonBody);
    }

    /**
     * Executa a requisição de cadastro (POST) para o endpoint informado.
     * Exibe logs organizados do response, incluindo status e body formatado (pretty print JSON).
     * Se o body não for JSON válido, exibe o body bruto.
     */
    @When("the registration request is processed")
    public void a_requisicao_de_cadastro_e_processada() {
        response = request.post(url);
        System.out.println("\n========== RESPONSE ==========");
        System.out.println("Status: " + response.getStatusCode());
        try {
            String prettyJson = new com.fasterxml.jackson.databind.ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(
                    new com.fasterxml.jackson.databind.ObjectMapper().readTree(response.getBody().asString())
                );
            System.out.println("Body (JSON):\n" + prettyJson);
        } catch (Exception e) {
            System.out.println("Body: " + response.getBody().asString());
        }
    }

    /**
     * Valida se o status da resposta do cadastro é o esperado.
     * Exibe log organizado da asserção do status code.
     * @param statusCode Status esperado
     */
    @Then("the registration response status code should be {int}")
    public void the_registration_response_status_code_should_be(Integer statusCode) {
        System.out.println("\n========== STATUS CODE ASSERTION ==========");
        System.out.println("Expected: " + statusCode + ", Actual: " + response.getStatusCode());
        assertEquals(statusCode.intValue(), response.getStatusCode());
    }

    /**
     * Valida se a resposta contém os campos obrigatórios id e token do usuário.
     * Garante que o cadastro retornou os dados esperados.
     */
    @And("the response should contain the user's id and token")
    public void a_resposta_deve_conter_id_e_token() {
        assertNotNull(response.jsonPath().getString("id"));
        assertNotNull(response.jsonPath().getString("token"));
    }

}
