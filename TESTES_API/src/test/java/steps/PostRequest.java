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
     * Monta o JSON e configura os headers necessários.
     * @param endpoint Endpoint da API (ex: /api/users)
     * @param dataTable Tabela de dados do Cucumber (name, job)
     */
    @Given("que envio uma requisição POST para {string} com os dados:")
    public void que_envio_uma_requisicao_post_para_com_os_dados(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        url = endpoint; // Usar apenas o endpoint relativo
        String name = dataTable.cell(1, 0);
        String job = dataTable.cell(1, 1);
        jsonBody = String.format("{\"name\":\"%s\",\"job\":\"%s\"}", name, job);
        request.body(jsonBody);
    }

    /**
     * Executa a requisição POST para criar o usuário.
     * O log do response é exibido para facilitar o debug.
     */
    @When("a requisição for processada")
    public void a_requisicao_for_processada() {
//        System.out.println("[DEBUG] baseURI atual: " + RestAssured.baseURI);
//        System.out.println("[DEBUG] Endpoint usado: " + url);
        response = request.post(url); // Usar apenas o endpoint relativo
        response.then().log().all(); // log do response
    }

    /**
     * Valida se o status da resposta é 201 (Created), indicando sucesso na criação.
     */
    @Then("o status da resposta deve ser 201")
    public void o_status_da_resposta_deve_ser_201() {
        assertEquals(201, response.getStatusCode());
    }

    /**
     * Valida se a resposta contém os dados do usuário criado e os campos id e createdAt.
     */
    @And("a resposta deve conter os dados do usuário criado")
    public void a_resposta_deve_conter_os_dados_do_usuario_criado() {
        assertEquals("morpheus", response.jsonPath().getString("name"));
        assertEquals("leader", response.jsonPath().getString("job"));
        assertNotNull(response.jsonPath().getString("id"));
        assertNotNull(response.jsonPath().getString("createdAt"));
    }
}
