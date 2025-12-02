Feature: PruebasAPI
@ProjectAPI
  Scenario: Como usuario quiero hacer el CRUD del API

    Given tengo acceso a to serverest.dev
    # create usuarios
    When envio un POST request a "/usuarios" con body
    """
    {
      "nome": "PruebasParaProjectAPI",
      "email": "PruebasParaProjectAPI@pruebamultiple.com",
      "password": "123456",
      "administrador": "true"
    }
    """
    Then el codigo de respuesta es 201
    And el atributo String "message" es "Cadastro realizado com sucesso"
    And guardo el "_id" de item en la variable "ID"
    And la respuesta cumple con el esquema "usuario_schemaPOST.json"


    # update usuarios
    When envio un PUT request a "/usuarios/ID" con body
    """
    {
      "nome": "PruebasParaProjectAPIUpdate",
      "email": "PruebasParaProjectAPIUpdate@pruebamultiple.com",
      "password": "654321",
      "administrador": "false"
    }
    """
    Then el codigo de respuesta es 200
    And el atributo String "message" es "Registro alterado com sucesso"
    And la respuesta cumple con el esquema "usuario_schemaPUT.json"


    # read usuarios
    When envio un GET request a "/usuarios/ID" con body
    """
    """
    Then el codigo de respuesta es 200
    And el atributo String "nome" es "PruebasParaProjectAPIUpdate"
    And el atributo String "email" es "PruebasParaProjectAPIUpdate@pruebamultiple.com"
    And la respuesta cumple con el esquema "usuario_schemaGET.json"


    # delete usuarios
    When envio un DELETE request a "/usuarios/ID" con body
    """
    """
    Then el codigo de respuesta es 200
    And el atributo String "message" es "Registro excluído com sucesso"
    And la respuesta cumple con el esquema "usuario_schemaDELETE.json"


     # read lista usuarios
    When envio un GET request a "/usuarios" con body
      """
      """
    Then el codigo de respuesta es 200






