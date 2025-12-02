package factoryRequest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestPUT implements IRequest {
    @Override
    public Response send(RequestInformation info) {
        return given()
                .header("Content-Type", "application/json")
                .headers(info.getHeaders())
                .body(info.getBody())
        .when()
                .put(info.getUrl());
    }
}
