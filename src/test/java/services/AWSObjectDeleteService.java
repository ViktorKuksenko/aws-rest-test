package services;

import helpers.Sha256Utils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class AWSObjectDeleteService extends TopService{

  public AWSObjectDeleteService(RequestBuilder requestBuilder) {
    super(requestBuilder);
  }

  @Override
  String getEncryptedPayLoad(String header) {
    return Sha256Utils.getHexString(Sha256Utils.generateSHA256Hash(header, "SHA-256"));
  }

  public Response deleteObject() {
    RestAssured.baseURI = "https://" + baseUri + canonicalUri;
    RestAssured.urlEncodingEnabled = false;
    Map<String, Object> headerMap = new HashMap<>();
    headerMap.put("X-Amz-Content-Sha256", payload);
    headerMap.put("X-Amz-Date", timestamp);
    headerMap.put("Authorization", buildRequestData());
    headerMap.put("Host", baseUri);
    Response response = RestAssured.given()
        .headers(headerMap)
        .delete()
        .then()
        .extract()
        .response();
    response.then()
        .log()
        .all();
    return response;
  }
}
