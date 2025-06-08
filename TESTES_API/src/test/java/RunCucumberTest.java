// Classe responsável por configurar e executar os testes BDD com Cucumber, Allure e ExtentReports
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Runner principal dos testes automatizados.
 *
 * - Utiliza o JUnit para execução dos testes.
 * - Configura o Cucumber para buscar features e steps.
 * - Gera relatórios dinâmicos com Allure e ExtentReports.
 *
 * Plugins:
 *   - pretty: saída legível no console
 *   - io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm: resultados para o Allure Report
 *   - com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:: resultados para o Extent Report
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features", // Caminho das features
    glue = {"steps"}, // Pacote dos steps definitions
    plugin = {
        "pretty",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    }
)
public class RunCucumberTest {
    // Classe de runner não precisa de métodos, apenas configurações via anotações
}
