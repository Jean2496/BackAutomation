package runner;

import factoryRequest.FactoryRequest;
import factoryRequest.RequestInformation;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import util.Config;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class MyStepCleanAPI {
    Response response;
    Map<String,String> variables = new HashMap<>();
    RequestInformation info;

    @Given("tengo acceso a to serverest.dev")
    public void iHaveAccessToServerest() {
        info = new RequestInformation();
        info.setUrl(Config.host + "/login");
        info.setBody("{ \"email\": \"" + Config.user + "\", \"password\": \"" + Config.password + "\" }");

        response = FactoryRequest.make("post").send(info);
        String token = response.then().extract().path("authorization");
        info.setHeader("Authorization", token);
    }

    @When("envio un {word} request a {string} con body")
    public void iSendAPOSTRequestToWithBody(String method, String url, String body) {
        info.setUrl(Config.host+replaceVar(url)).setBody(body);
        response = FactoryRequest.make(method.toLowerCase()).send(info);
    }

    @Then("el codigo de respuesta es {int}")
    public void responseCodeIs(int expectedResponseCode) {
        response.then()
                .statusCode(expectedResponseCode);
    }

    @And("el atributo {word} {string} es {string}")
    public void theAttributeStringIs(String type,String attribute, String expectedResult) {
        System.out.println("Expected Result: "+expectedResult);
        if (type.toLowerCase().equals("int"))
            response.then().body(attribute,equalTo(Integer.parseInt(expectedResult)));
        if (type.toLowerCase().equals("boolean"))
            response.then().body(attribute,equalTo(Boolean.parseBoolean(expectedResult)));
        if (type.toLowerCase().equals("string"))
            response.then().body(attribute,equalTo(expectedResult));
    }

    @And("guardo el {string} de item en la variable {string}")
    public void iSaveTheValueOfInTheVariable(String attribute, String nameVariable) {
        variables.put(nameVariable,response.then().extract().path(attribute).toString());
        System.out.println("Variable "+nameVariable+" = "+variables.get(nameVariable));
    }

    private String replaceVar(String value){
        for (String key: variables.keySet())
            value=value.replace(key,variables.get(key));
        return value;
    }

    @And("la respuesta cumple con el esquema {string}")
    public void validateJsonSchema(String schemaFile) {
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/" + schemaFile));
    }



}
