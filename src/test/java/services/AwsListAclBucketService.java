package services;

import helpers.Sha256Utils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class AwsListAclBucketService extends TopService{

  public AwsListAclBucketService(RequestBuilder requestBuilder) {
    super(requestBuilder);
  }

  @Override
  protected String getEncryptedPayLoad(String header) {
    return Sha256Utils.getHexString(Sha256Utils.generateSHA256Hash(header, "SHA-256"));
  }

  @Override
  protected String getCanonicalRequest() {
    StringBuilder canonicalRequest = new StringBuilder("");

    canonicalRequest.append(httpMethod)
        .append("\n");

    if (canonicalUri.equals("") || canonicalUri.trim().equals("")) {
      canonicalRequest.append("/");
    } else {
      canonicalRequest.append("/")
          .append("\n")
          .append(canonicalUri);
    }
    canonicalRequest.append("\n")
        .append("host:")
        .append(baseUri)
        .append("\n");

    if (!canonicalHeaders.isEmpty()) {
      canonicalRequest.append(canonicalHeaders.get(0))
          .append(":")
          .append(payload)
          .append("\n");
      canonicalRequest.append(canonicalHeaders.get(1))
          .append(":")
          .append(timestamp)
          .append("\n");
    } else {
      throw new RuntimeException("Canonical headers are empty!");
    }

    canonicalRequest.append("\n")
        .append(getSignedHeaders())
        .append("\n")
        .append(payload);

    return canonicalRequest.toString();
  }

  public Response getAwsBucketAcl() {
    RestAssured.baseURI = "https://" + baseUri;
    Map<String, Object> headerMap = new HashMap<>();
    headerMap.put("X-Amz-Content-Sha256", payload);
    headerMap.put("X-Amz-Date", timestamp);
    headerMap.put("Authorization", buildRequestData());
    headerMap.put("Host", baseUri);
    Response response = RestAssured.given()
        .queryParam("acl")
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
