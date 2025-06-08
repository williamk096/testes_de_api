# TESTES_API

Projeto de automação de testes de API utilizando Java, Cucumber, RestAssured, Allure Report e ExtentReports.

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
- `src/test/java/steps/` - Classes de step definitions dos cenários BDD.
- `src/test/resources/features/` - Arquivos .feature com os cenários de teste.
- `pom.xml` - Gerenciamento de dependências e plugins.
- `target/extent-report/` - Relatórios ExtentReports.
- `target/allure-results/` - Resultados brutos do Allure.
- `target/site/allure-maven-plugin/` - Relatório HTML do Allure.

## Como Executar os Testes
1. **Executar os testes:**
   ```
   mvn clean test
   ```
2. **Gerar e visualizar o relatório Allure:**
   ```
   mvn allure:report
   # ou para abrir automaticamente no navegador
   mvn allure:serve
   ```
   O relatório estará em `target/site/allure-maven-plugin/index.html`.
3. **Visualizar o relatório Extent:**
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

