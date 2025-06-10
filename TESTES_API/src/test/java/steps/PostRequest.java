// Classe responsável pelos testes de criação de usuário via método POST na API Reqres.in
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
import io.cucumber.java.Before;
import util.RestAssuredConfig;
import static org.junit.Assert.*;

/**
 * Classe de steps para o cenário de criação de usuário via POST.
 * Utiliza RestAssured para enviar requisições HTTP e Cucumber para BDD.
 */
public class PostRequest {
    // Armazena a resposta da requisição POST
    private static Response response;
    // Armazena o corpo (body) da requisição em formato JSON
    private static String jsonBody;
    // Armazena a URL completa do endpoint
    private static String url;
    // Armazena a especificação da requisição (headers, body, etc.)
    private static io.restassured.specification.RequestSpecification request;

    /**
     * Configuração global para todos os testes desta classe.
     * Define a base URI.
     * Usa configuração centralizada do RestAssured
     * via util.RestAssuredConfig para baseUri, headers e autenticação.
     * Basta inicializar o request com RestAssuredConfig.defaultRequestSpec().
     *
     */
    @Before
    public void setup() {
        request = RestAssuredConfig.defaultRequestSpec();
    }

    /**
     * Prepara a requisição POST com os dados fornecidos na tabela.
     * Exibe log organizado do request, mostrando método, endpoint e body enviado.
     * @param endpoint Endpoint da API (ex: /api/users)
     * @param dataTable Tabela de dados do Cucumber (name, job)
     */
    @Given("que envio uma requisição POST para {string} com os dados:")
    public void que_envio_uma_requisicao_post_para_com_os_dados(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        url = endpoint; // Usar apenas o endpoint relativo
        String name = dataTable.cell(1, 0);
        String job = dataTable.cell(1, 1);
        jsonBody = String.format("{\"name\":\"%s\",\"job\":\"%s\"}", name, job);
        System.out.println("\n========== REQUEST ==========");
        System.out.println("Método: POST");
        System.out.println("Endpoint: " + url);
        System.out.println("Body: " + jsonBody);
        request.body(jsonBody);
    }

    /**
     * Executa a requisição POST para criar o usuário.
     * Exibe logs organizados do response, incluindo status e body formatado (pretty print JSON).
     * Se o body não for JSON válido, exibe o body bruto.
     */
    @When("a requisição for processada")
    public void a_requisicao_for_processada() {
        response = request.post(url); // Usar apenas o endpoint relativo
        System.out.println("\n========== RESPONSE ==========");
        System.out.println("Status: " + response.getStatusCode());
        try {
            // Exibe o JSON de resposta de forma estruturada (pretty print)
            String prettyJson = new com.fasterxml.jackson.databind.ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(
                    new com.fasterxml.jackson.databind.ObjectMapper().readTree(response.getBody().asString())
                );
            System.out.println("Body (JSON):\n" + prettyJson);
        } catch (Exception e) {
            // Caso não seja possível formatar, exibe o body bruto
            System.out.println("Body: " + response.getBody().asString());
        }
    }

    /**
     * Valida se o status da resposta é 201 (Created), indicando sucesso na criação.
     * Exibe log organizado da asserção do status code.
     */
    @Then("o status da resposta deve ser 201")
    public void o_status_da_resposta_deve_ser_201() {
        System.out.println("\n========== STATUS CODE ASSERTION ==========");
        System.out.println("Expected: 201, Actual: " + response.getStatusCode());
        assertEquals(201, response.getStatusCode());
    }

    /**
     * Valida se a resposta contém os dados do usuário criado e os campos id e createdAt.
     * Garante que os campos obrigatórios estejam presentes e corretos.
     */
    @And("a resposta deve conter os dados do usuário criado")
    public void a_resposta_deve_conter_os_dados_do_usuario_criado() {
        assertEquals("morpheus", response.jsonPath().getString("name"));
        assertEquals("leader", response.jsonPath().getString("job"));
        assertNotNull(response.jsonPath().getString("id"));
        assertNotNull(response.jsonPath().getString("createdAt"));
    }
}
