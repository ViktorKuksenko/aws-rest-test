package services;

import helpers.FileUtils;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AWSBucketCreatorService extends TopService {

  public AWSBucketCreatorService(RequestBuilder requestBuilder) {
    super(requestBuilder);
  }

  @Override
  String getEncryptedPayLoad(String filePath) {
    return FileUtils.getFileChecksum(new File(filePath));
  }

  @Override
  protected String getCanonicalRequest() {
    StringBuilder canonicalRequest = new StringBuilder("");

    canonicalRequest.append(httpMethod)
        .append("\n");

    if (canonicalUri.equals("") || canonicalUri.trim().equals("")) {
      canonicalRequest.append("/");
    } else {
      canonicalRequest.append(canonicalUri);
    }
    canonicalRequest.append("\n\n")
        .append(String.format("host:%s", baseUri))
        .append("\n");

    if (!canonicalHeaders.isEmpty()) {
      canonicalRequest.append(String.format("%s:%s", canonicalHeaders.get(0), acl))
          .append("\n")
          .append(String.format("%s:%s", canonicalHeaders.get(1), payload))
          .append("\n")
          .append(String.format("%s:%s", canonicalHeaders.get(2), timestamp))
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

  public Response createBucketWithPermissions() {
    RestAssured.baseURI = "https://" + baseUri;
    RestAssured.urlEncodingEnabled = false;
    Map<String, Object> headerMap = new HashMap<>();
    headerMap.put("X-Amz-Acl", acl);
    headerMap.put("X-Amz-Content-Sha256", payload);
    headerMap.put("X-Amz-Date", timestamp);
    headerMap.put("Authorization", buildRequestData());
    headerMap.put("Host", baseUri);

    Response response = RestAssured.given()
        .contentType(contentType)
        .config(RestAssured.config()
            .encoderConfig(EncoderConfig
                .encoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
        .headers(headerMap)
        .body(new File(pathToFile))
        .put()
        .then()
        .extract()
        .response();
    response.then().log().all();
    return response;
  }

}
