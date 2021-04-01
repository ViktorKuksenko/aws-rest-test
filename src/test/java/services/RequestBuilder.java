package services;

import java.util.List;

public class RequestBuilder {
  private String awsAccessKeyId;
  private String awsSecretAccessKeyId;
  private String regionName;
  private String httpMethod;
  private String payload;
  private String canonicalUri;
  private String serviceName;
  private String bucketName;
  private List<String> canonicalHeaders;
  private List<String> signedHeaders;
  private String pathToFile;
  private String acl;
  private String contentType;

  public static RequestBuilder getInstance(String awsAccessKeyId, String awsSecretAccessKeyId) {
    return new RequestBuilder(awsAccessKeyId, awsSecretAccessKeyId);
  }

  public RequestBuilder(String awsAccessKeyId, String awsSecretAccessKeyId) {
    this.awsAccessKeyId = awsAccessKeyId;
    this.awsSecretAccessKeyId = awsSecretAccessKeyId;
  }

  public RequestBuilder setRegionName(String regionName) {
    this.regionName = regionName;
    return this;
  }

  public RequestBuilder setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
    return this;
  }

  public RequestBuilder setPayload(String payload) {
    this.payload = payload;
    return this;
  }

  public RequestBuilder setCanonicalUri(String canonicalUri) {
    this.canonicalUri = canonicalUri;
    return this;
  }

  public RequestBuilder setServiceName(String serviceName) {
    this.serviceName = serviceName;
    return this;
  }

  public RequestBuilder setBucketName(String bucketName) {
    this.bucketName = bucketName;
    return this;
  }

  public RequestBuilder setCanonicalHeaders(List<String> canonicalHeaders) {
    this.canonicalHeaders = canonicalHeaders;
    return this;
  }

  public RequestBuilder setSignedHeaders(List<String> signedHeaders) {
    this.signedHeaders = signedHeaders;
    return this;
  }

  public RequestBuilder setPathToFile(String pathToFile) {
    this.pathToFile = pathToFile;
    return this;
  }

  public RequestBuilder setAcl(String acl) {
    this.acl = acl;
    return this;
  }

  public RequestBuilder setContentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  public String getAwsAccessKeyId() {
    return awsAccessKeyId;
  }

  public String getAwsSecretAccessKeyId() {
    return awsSecretAccessKeyId;
  }

  public String getRegionName() {
    return regionName;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public String getPayload() {
    return payload;
  }

  public String getCanonicalUri() {
    return canonicalUri;
  }

  public String getServiceName() {
    return serviceName;
  }

  public String getBucketName() {
    return bucketName;
  }

  public List<String> getCanonicalHeaders() {
    return canonicalHeaders;
  }

  public List<String> getSignedHeaders() {
    return signedHeaders;
  }

  public String getPathToFile() {
    return pathToFile;
  }

  public String getAcl() {
    return acl;
  }

  public String getContentType() {
    return contentType;
  }

  public AWSListBucketService buildAuthRequest() {
    return new AWSListBucketService(this);
  }

  public AWSFileUploadService buildFileUploadRequest() {
    return new AWSFileUploadService(this);
  }

  public AWSBucketCreatorService buildBucketCreatorRequest() {
    return new AWSBucketCreatorService(this);
  }

  public AWSBucketDeleteService buildBucketDeleteRequest() {
    return new AWSBucketDeleteService(this);
  }

  public AWSObjectDeleteService buildObjectDeleteRequest() {
    return new AWSObjectDeleteService(this);
  }

  public AwsListAclBucketService buildAclBucketRequest() {
    return new AwsListAclBucketService(this);
  }
}
