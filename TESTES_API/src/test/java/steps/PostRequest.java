package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import static org.junit.Assert.*;

public class PostRequest {
    private static Response response;
    private static String jsonBody;
    private static String url;
    private static io.restassured.specification.RequestSpecification request;

    @Given("que envio uma requisição POST para {string} com os dados:")
    public void que_envio_uma_requisicao_post_para_com_os_dados(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        url = "https://reqres.in" + endpoint;
        String name = dataTable.cell(1, 0);
        String job = dataTable.cell(1, 1);
        jsonBody = String.format("{\"name\":\"%s\",\"job\":\"%s\"}", name, job);
        request = RestAssured.given()
                .header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(jsonBody)
                .log().all(); // log do request
    }

    @When("a requisição for processada")
    public void a_requisicao_for_processada() {
        response = request.post(url);
        response.then().log().all(); // log do response
    }

    @Then("o status da resposta deve ser 201")
    public void o_status_da_resposta_deve_ser_201() {
        assertEquals(200, response.getStatusCode());
    }

    @And("a resposta deve conter os dados do usuário criado")
    public void a_resposta_deve_conter_os_dados_do_usuario_criado() {
        assertEquals("morpheus", response.jsonPath().getString("name"));
        assertEquals("leader", response.jsonPath().getString("job"));
        assertNotNull(response.jsonPath().getString("id"));
        assertNotNull(response.jsonPath().getString("createdAt"));
    }
}
