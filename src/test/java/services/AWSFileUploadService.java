package services;

import helpers.FileUtils;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AWSFileUploadService extends TopService{

  public AWSFileUploadService(RequestBuilder requestBuilder) {
    super(requestBuilder);
  }

  @Override
  String getEncryptedPayLoad(String filePath) {
    return FileUtils.getFileChecksum(new File(filePath));
  }

  public Response uploadObjects(String contentType) {
    RestAssured.baseURI = "https://" + baseUri + canonicalUri;
    RestAssured.urlEncodingEnabled = false;
    Map<String, Object> headerMap = new HashMap<>();
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
    response.then()
        .log()
        .all();
    return response;
  }
}
