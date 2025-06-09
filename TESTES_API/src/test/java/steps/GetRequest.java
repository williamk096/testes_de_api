
/*
 * Alteração: agora esta classe utiliza a configuração centralizada do RestAssured
 * via util.RestAssuredConfig para baseUri, headers e autenticação.
 * Basta inicializar o request com RestAssuredConfig.defaultRequestSpec().
 */

// Classe responsável pelos testes de consulta (GET) na API Reqres.in
package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import util.RestAssuredConfig;
import java.util.Map;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

/**
 * Classe de steps para cenários de consulta de usuários via GET.
 * Utiliza RestAssured para enviar requisições HTTP e Cucumber para BDD.
 */
public class GetRequest {
    // Armazena a resposta da requisição GET
    private static Response response;
    // Armazena a especificação da requisição (headers, etc.)
    private static RequestSpecification request;

    /**
     * Configuração global para todos os testes desta classe.
     * Define a base URI e os headers padrão para as requisições GET.
     * Usa configuração centralizada do RestAssured
     * via util.RestAssuredConfig para baseUri, headers e autenticação.
     * Basta inicializar o request com RestAssuredConfig.defaultRequestSpec().
     */
    @BeforeClass
    public static void setup() {
        // Configuração centralizada
        RestAssured.requestSpecification = RestAssuredConfig.defaultRequestSpec();
    }

    /**
     * Prepara a requisição GET configurando os headers necessários.
     * Inicializa request com configuração centralizada
     */
    @Given("the API is running")
    public void the_api_is_running() {
        request = RestAssuredConfig.defaultRequestSpec();
    }

    /**
     * Executa a requisição GET para o endpoint informado.
     * @param url Endpoint completo da API
     */
    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String url) {
        response = request.get(url);
    }

    /**
     * Valida se o status da resposta é o esperado e exibe o log do response.
     * Também chama a função de captura de elemento para exemplo.
     * @param statusCode Status esperado
     */
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        assertEquals(statusCode.intValue(), response.getStatusCode());
        response.then().log().all();
        capturar_elemento();
    }

    /**
     * Exemplo de função utilitária para capturar e exibir dados de um usuário específico.
     * Neste caso, busca o usuário de id 7 na lista de dados retornada.
     */
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

