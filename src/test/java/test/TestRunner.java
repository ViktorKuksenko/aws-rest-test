package test;

import helpers.PropertiesUtils;
import java.util.Properties;

public class TestRunner {

  protected static final String HTTP_GET_METHOD = "GET";
  protected static final String HTTP_PUT_METHOD = "PUT";
  protected static final String HTTP_DELETE_METHOD = "DELETE";
  protected static final String SERVICE_NAME = "s3";
  protected static final String X_AMZ_CONTENT_SHA_256_HEADER = "x-amz-content-sha256";
  protected static final String X_AMZ_DATE_HEADER = "x-amz-date";
  protected static final String HOST_HEADER = "host";
  protected static final String X_AMZ_ACL_HEADER = "x-amz-acl";
  protected String awsAccessKeyId;
  protected String awsSecretAccessKeyId;
  protected String regionName;

  public TestRunner() {
    PropertiesUtils propertiesUtils = new PropertiesUtils();
    Properties credentialProperties = propertiesUtils.getProperties(
        "C:\\Users\\vkukse\\Desktop\\EducationProjects\\rest-onedrive\\src\\files\\"
            + "credentials.properties");
    awsAccessKeyId = credentialProperties.getProperty("aws.accessKeyId");
    awsSecretAccessKeyId = credentialProperties.getProperty("aws.secretAccessKeyId");
    Properties configProperties = propertiesUtils.getProperties(
        "C:\\Users\\vkukse\\Desktop\\EducationProjects\\rest-onedrive\\src\\files\\"
            + "config.properties");
    regionName = configProperties.getProperty("aws.regionName");
  }

}
