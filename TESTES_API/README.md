# TESTES_API

Projeto de automação de testes de API utilizando Java, Cucumber, RestAssured, Allure Report e ExtentReports.

## Alterações Recentes: Centralização da Configuração do RestAssured

- Agora todas as classes de step (GET, POST, PUT) utilizam a configuração centralizada do RestAssured, implementada em `util.RestAssuredConfig`.
- A configuração padrão inclui baseUri, headers comuns e autenticação (x-api-key), podendo ser sobrescrita por variáveis de ambiente (`API_BASE_URI`, `API_KEY`).
- Para usar, basta inicializar o objeto `request` com `RestAssuredConfig.defaultRequestSpec()` no método `@Before` ou `@BeforeClass` de cada classe de step.
- Isso elimina duplicidade, facilita manutenção e segue boas práticas.

### Exemplo de uso nas classes de step:
```java
@Before
public void setup() {
    request = RestAssuredConfig.defaultRequestSpec();
}
```

---

## Benefícios
- **Manutenção facilitada:** Alterações de baseUri, headers ou autenticação em um único lugar.
- **Padronização:** Todas as requisições seguem o mesmo padrão.
- **Flexibilidade:** Fácil integração com diferentes ambientes via variáveis de ambiente.
- **Código mais limpo:** Menos duplicidade e mais foco na lógica dos testes.

---

## Descrição
Este projeto tem como objetivo automatizar cenários de testes de APIs REST, utilizando boas práticas de BDD (Behavior Driven Development) com Cucumber e geração de relatórios dinâmicos e gráficos com Allure e ExtentReports.

## Tecnologias Utilizadas
- **Java 17**
- **Maven**
- **JUnit 4**
- **Cucumber 7**
- **RestAssured**
- **Allure Report**
- **ExtentReports**

## Estrutura do Projeto
- `src/test/java/util/RestAssuredConfig.java`: configuração centralizada do RestAssured.
- `src/test/java/steps/`: classes de step dos testes (GetRequest, PostRequest, PutRequest).
- `src/test/resources/features/` - Arquivos .feature com os cenários de teste.
- `pom.xml` - Gerenciamento de dependências e plugins.
- `target/extent-report/` - Relatórios ExtentReports.
- `target/allure-results/` - Resultados brutos do Allure.
- `target/site/allure-maven-plugin/` - Relatório HTML do Allure.

## Como Executar os Testes
1. Configure as variáveis de ambiente se desejar sobrescrever o padrão:
   - `API_BASE_URI` (opcional)
   - `API_KEY` (opcional)
2. **Executar os testes:**
   ```sh
   mvn clean test
   ```
3. **Gerar e visualizar o relatório Allure:**
   ```sh
   mvn allure:report
   # ou para abrir automaticamente no navegador
   mvn allure:serve
   ```
   O relatório estará em `target/site/allure-maven-plugin/index.html`.
4. **Visualizar o relatório Extent:**
   Abra o arquivo `target/extent-report/extent.html` no navegador.

## Exemplos de Cenários
- Criação de usuário (POST)
- Atualização de usuário (PUT)
- Consulta de usuários (GET)

## Configurações Especiais
- O arquivo `allure.properties` garante que os resultados do Allure sejam salvos em `target/allure-results`.
- O arquivo `extent.properties` personaliza o relatório ExtentReports.

## Contribuição
Pull requests são bem-vindos! Para grandes mudanças, abra uma issue primeiro para discutir o que você gostaria de modificar.

## Licença
Este projeto é open source e está sob a licença MIT.

