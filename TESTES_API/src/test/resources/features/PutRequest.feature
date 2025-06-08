Feature: Atualizar usuário via PUT

  Scenario: Atualizar usuário com sucesso
    Given que envio uma requisição PUT para "/api/users/2" com os dados:
      | name     | job           |
      | morpheus | zion resident |
    When a requisição PUT for processada
    Then o status da resposta deve ser 200
    And a resposta deve conter os dados atualizados do usuário

