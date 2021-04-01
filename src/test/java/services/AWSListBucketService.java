package services;

import helpers.Sha256Utils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class AWSListBucketService extends TopService {

  public AWSListBucketService(RequestBuilder requestBuilder) {
    super(requestBuilder);
  }

  @Override
  String getEncryptedPayLoad(String header) {
    return Sha256Utils.getHexString(Sha256Utils.generateSHA256Hash(header, "SHA-256"));
  }

  public Response getAwsBucketContents() {
    RestAssured.baseURI = "https://" + baseUri;
    Map<String, Object> headerMap = new HashMap<>();
    headerMap.put("X-Amz-Content-Sha256", payload);
    headerMap.put("X-Amz-Date", timestamp);
    headerMap.put("Authorization", buildRequestData());
    headerMap.put("Host", baseUri);
    Response response = RestAssured.given()
        .headers(headerMap)
        .get()
        .then()
        .extract()
        .response();
    response.then()
        .log()
        .all();
    return response;
  }

}
