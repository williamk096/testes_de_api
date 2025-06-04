Feature: Criar usuário via POST

  Scenario: Criar usuário com sucesso
    Given que envio uma requisição POST para "/api/users" com os dados:
      | name     | job    |
      | morpheus | leader |
    When a requisição for processada
    Then o status da resposta deve ser 201
    And a resposta deve conter os dados do usuário criado
