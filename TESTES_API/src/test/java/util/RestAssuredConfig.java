package util;

/*
 * -----------------------------------------------------------------------------
 * Centralização da Configuração do RestAssured para Testes de API
 * -----------------------------------------------------------------------------
 * Objetivo:
 *   - Eliminar duplicidade de código.
 *   - Facilitar manutenção e futuras alterações de configuração.
 *   - Seguir boas práticas de organização e reutilização de código.
 *
 * O que faz esta classe:
 *   - Fornece um método estático defaultRequestSpec() que retorna uma configuração
 *     padrão do RestAssured com baseUri, headers comuns e autenticação.
 *   - Permite sobrescrever baseUri e api-key por variáveis de ambiente (API_BASE_URI, API_KEY).
 *   - Todas as classes de step dos testes devem utilizar este método para obter a configuração padrão.
 *
 * Benefícios:
 *   - Manutenção facilitada: qualquer alteração de baseUri, headers ou autenticação
 *     pode ser feita em um único lugar.
 *   - Padronização: todas as requisições seguem o mesmo padrão de configuração.
 *   - Flexibilidade: fácil integração com diferentes ambientes.
 *   - Código mais limpo: menos duplicidade e mais foco na lógica dos testes.
 * -----------------------------------------------------------------------------
 */

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class RestAssuredConfig {
    public static RequestSpecification defaultRequestSpec() {
        return RestAssured.given()
            .baseUri(System.getenv().getOrDefault("API_BASE_URI", "https://reqres.in"))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .header("x-api-key", System.getenv().getOrDefault("API_KEY", "reqres-free-v1"));
    }
}

