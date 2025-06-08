// Classe responsável pelos testes de atualização de usuário via método PUT na API Reqres.in
package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.response.Response;
import io.restassured.RestAssured;
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
     * Prepara a requisição PUT com os dados fornecidos na tabela.
     * Monta o JSON e configura os headers necessários.
     * @param endpoint Endpoint da API (ex: /api/users/2)
     * @param dataTable Tabela de dados do Cucumber (name, job)
     */
    @Dado("que envio uma requisição PUT para {string} com os dados:")
    public void que_envio_uma_requisicao_put_para_com_os_dados(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        url = "https://reqres.in" + endpoint;
        String name = dataTable.cell(1, 0);
        String job = dataTable.cell(1, 1);
        // Monta o JSON do body
        jsonBody = String.format("{\"name\":\"%s\",\"job\":\"%s\"}", name, job);
        // Prepara a requisição com headers e body
        request = RestAssured.given()
                .header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(jsonBody)
                .log().all();
    }

    /**
     * Executa a requisição PUT para atualizar o usuário.
     * O log do response é exibido para facilitar o debug.
     */
    @Quando("a requisição PUT for processada")
    public void a_requisicao_put_for_processada() {
        response = request.put(url);
        response.then().log().all();
    }

    /**
     * Valida se o status da resposta é 200 (OK), indicando sucesso na atualização.
     */
    @Entao("o status da resposta deve ser 200")
    public void o_status_da_resposta_deve_ser_200() {
        assertEquals(200, response.getStatusCode());
    }

    /**
     * Valida se a resposta contém os dados atualizados do usuário e o campo updatedAt.
     */
    @Entao("a resposta deve conter os dados atualizados do usuário")
    public void a_resposta_deve_conter_os_dados_atualizados_do_usuario() {
        assertEquals("morpheus", response.jsonPath().getString("name"));
        assertEquals("zion resident", response.jsonPath().getString("job"));
        assertNotNull(response.jsonPath().getString("updatedAt"));
    }
}

