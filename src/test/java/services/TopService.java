package services;

import com.sun.istack.NotNull;
import helpers.Sha256Utils;
import helpers.TimeUtils;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public abstract class TopService {
  protected String awsAccessKeyId;
  protected String awsSecretAccessKeyId;
  protected String regionName;
  protected String httpMethod;
  protected String payload;
  protected String canonicalUri;
  protected String serviceName;
  protected String bucketName;
  protected String pathToFile;
  protected List<String> canonicalHeaders;
  protected List<String> signedHeaders;
  protected String timestamp;
  protected String date;
  protected String acl;
  protected String baseUri;
  protected String contentType;

  protected static final String REQUEST_ALGORITHM = "AWS4-HMAC-SHA256";

  public TopService(@NotNull RequestBuilder requestBuilder) {
    awsAccessKeyId = requestBuilder.getAwsAccessKeyId();
    awsSecretAccessKeyId = requestBuilder.getAwsSecretAccessKeyId();
    regionName = requestBuilder.getRegionName();
    httpMethod = requestBuilder.getHttpMethod();
    payload = getEncryptedPayLoad(requestBuilder.getPayload());
    canonicalUri = requestBuilder.getCanonicalUri();
    serviceName = requestBuilder.getServiceName();
    bucketName = requestBuilder.getBucketName();
    canonicalHeaders = requestBuilder.getCanonicalHeaders();
    signedHeaders = requestBuilder.getSignedHeaders();
    pathToFile = requestBuilder.getPathToFile();
    acl = requestBuilder.getAcl();
    baseUri = String.format("%s.%s.%s.amazonaws.com", bucketName, serviceName, regionName);
    contentType = requestBuilder.getContentType();

    OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
    timestamp = TimeUtils.getTimeStamp(now);
    date = TimeUtils.getDate(now);
  }

  abstract String getEncryptedPayLoad(String header);

  String getEncryptedCanonicalRequest(String canonicalRequest) {
    return Sha256Utils.getHexString(Sha256Utils.generateSHA256Hash(canonicalRequest
        , "SHA-256"));
  }

  byte [] getEncryptedSignInKey(String awsSecretAccessKey, String dateRegionKey
      , String region, String service) {
    byte [] signInKey = null;
    try {
      signInKey = Sha256Utils.getSigInKey(awsSecretAccessKey
          , dateRegionKey, region, service);
    } catch (NoSuchAlgorithmException | InvalidKeyException
        | UnsupportedEncodingException exception) {
      exception.printStackTrace();
      exception.getCause();
    }
    return signInKey;
  }

  String getAuthenticationSignature(String stringToSign, byte[] signInKey) {
    String signature = "";
    try {
      signature += Sha256Utils
          .getHexString(Sha256Utils.getHmacSHA256(stringToSign, signInKey));
    } catch (NoSuchAlgorithmException | InvalidKeyException
        | UnsupportedEncodingException exception) {
      exception.printStackTrace();
      exception.getCause();
    }
    return signature.trim();
  }

  String getSignedHeaders() {
    StringBuilder stringBuilder = new StringBuilder("");
    if (!signedHeaders.isEmpty()) {
      for (int i = 0; i < signedHeaders.size(); i++) {
        if (i < signedHeaders.size() - 1) {
          stringBuilder.append(signedHeaders.get(i)).append(";");
        } else {
          stringBuilder.append(signedHeaders.get(i));
        }
      }
    } else {
      throw new RuntimeException("Signed headers are empty!");
    }
    return stringBuilder.toString();
  }

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
        .append("host:" + baseUri)
        .append("\n");

    if (!canonicalHeaders.isEmpty()) {
      canonicalRequest.append(String.format("%s:%s", canonicalHeaders.get(0), payload))
          .append("\n");
      canonicalRequest.append(String.format("%s:%s", canonicalHeaders.get(1), timestamp))
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

  protected String getStringToSignIn() {
    String stringToSignIn = String.format("%s\n%s\n%s/%s/%s/aws4_request\n%s", REQUEST_ALGORITHM
        , timestamp, date, regionName, serviceName
        , getEncryptedCanonicalRequest(getCanonicalRequest()));
    return stringToSignIn;
  }

  protected String getSignature() {
    byte [] signInKey = getEncryptedSignInKey(awsSecretAccessKeyId, date, regionName, serviceName);
    return getAuthenticationSignature(getStringToSignIn(), signInKey);
  }

  protected String buildRequestData() {
    String credentialScope = String.format("%s/%s/%s/aws4_request", date, regionName, serviceName);
    String authorizationString = String.format("%s Credential=%s/%s, SignedHeaders=%s, Signature="
        + "%s", REQUEST_ALGORITHM, awsAccessKeyId, credentialScope, getSignedHeaders()
        , getSignature());
    return authorizationString;
  }
}
