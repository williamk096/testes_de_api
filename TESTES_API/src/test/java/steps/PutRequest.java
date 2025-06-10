// Classe responsável pelos testes de atualização de usuário via método PUT na API Reqres.in
package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.cucumber.java.Before;
import util.RestAssuredConfig;
import static org.junit.Assert.*;

/**
 * Classe de steps para o cenário de atualização de usuário via PUT.
 * Utiliza RestAssured para enviar requisições HTTP e Cucumber para BDD.
 */
public class PutRequest {
    // Armazena a resposta da requisição PUT
    private static Response response;
    // Armazena o corpo (body) da requisição em formato JSON
    private static String jsonBody;
    // Armazena a URL completa do endpoint
    private static String url;
    // Armazena a especificação da requisição (headers, body, etc.)
    private static io.restassured.specification.RequestSpecification request;

    /**
     * Configuração global para todos os testes desta classe.
     * Define a base URI para as requisições.
     *  Usa configuração centralizada do RestAssured
     *  via util.RestAssuredConfig para baseUri, headers e autenticação.
     *  Basta inicializar o request com RestAssuredConfig.defaultRequestSpec().
     */
    @Before
    public void setup() {
        request = RestAssuredConfig.defaultRequestSpec();
    }

    /**
     * Prepara a requisição PUT com os dados fornecidos na tabela.
     * Monta o JSON e configura os headers necessários.
     * @param endpoint Endpoint da API (ex: /api/users/2)
     * @param dataTable Tabela de dados do Cucumber (name, job)
     */
    @Dado("que envio uma requisição PUT para {string} com os dados:")
    public void que_envio_uma_requisicao_put_para_com_os_dados(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        url = endpoint; // Usar apenas o endpoint relativo
        String name = dataTable.cell(1, 0);
        String job = dataTable.cell(1, 1);
        jsonBody = String.format("{\"name\":\"%s\",\"job\":\"%s\"}", name, job);
        System.out.println("\n========== REQUEST ==========");
        System.out.println("Método: PUT");
        System.out.println("Endpoint: " + url);
        System.out.println("Body: " + jsonBody);
        request.body(jsonBody);
    }

    /**
     * Executa a requisição PUT para atualizar o usuário.
     * Exibe logs organizados do response, incluindo status e body formatado (pretty print JSON).
     * Se o body não for JSON válido, exibe o body bruto.
     */
    @Quando("a requisição PUT for processada")
    public void a_requisicao_put_for_processada() {
        response = request.put(url);
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
     * Valida se o status da resposta é 200 (OK), indicando sucesso na atualização.
     * Exibe log organizado da asserção do status code.
     */
    @Entao("o status da resposta deve ser 200")
    public void o_status_da_resposta_deve_ser_200() {
        System.out.println("\n========== STATUS CODE ASSERTION ==========");
        System.out.println("Expected: 200, Actual: " + response.getStatusCode());
        assertEquals(200, response.getStatusCode());
    }

    /**
     * Valida se a resposta contém os dados atualizados do usuário e o campo updatedAt.
     * Garante que os campos obrigatórios estejam presentes e corretos.
     */
    @Entao("a resposta deve conter os dados atualizados do usuário")
    public void a_resposta_deve_conter_os_dados_atualizados_do_usuario() {
        assertEquals("morpheus", response.jsonPath().getString("name"));
        assertEquals("zion resident", response.jsonPath().getString("job"));
        assertNotNull(response.jsonPath().getString("updatedAt"));
    }
}

