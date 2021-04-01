package test;

import static constants.AssertConstants.*;

import helpers.TimeUtils;
import helpers.XmlUtils;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import models.BucketCreationModel;
import models.aclresponsebucketmodels.AccessControlModel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.AWSBucketCreatorService;
import services.AWSBucketDeleteService;
import services.AwsListAclBucketService;
import services.RequestBuilder;

public class BucketCreationAndDeletionTest extends TestRunner {

  @DataProvider
  public Object[][] bucketCreationData() {
    return new Object[][]{
        {new BucketCreationModel(String.format("testBucket%s", TimeUtils.getCurrentLocalTimestamp())
            ,
            "C:\\Users\\vkukse\\Desktop\\EducationProjects\\rest-onedrive\\src\\files\\data\\test.xml"
            , "application/xml", "private", Arrays.asList(FULL_CONTROL))},
//        {new BucketCreationModel(String.format("testBucket%s", TimeUtils.getCurrentLocalTimestamp())
//            , "C:\\Users\\vkukse\\Desktop\\EducationProjects\\rest-onedrive\\src\\files\\data\\test.xml"
//            , "application/xml", "public-read", Arrays.asList(FULL_CONTROL, READ))},
//        {new BucketCreationModel(String.format("testBucket%s", TimeUtils.getCurrentLocalTimestamp())
//            , "C:\\Users\\vkukse\\Desktop\\EducationProjects\\rest-onedrive\\src\\files\\data\\test.xml"
//            , "application/xml", "public-read-write", Arrays.asList(FULL_CONTROL, READ, WRITE))},
//        {new BucketCreationModel(String.format("testBucket%s", TimeUtils.getCurrentLocalTimestamp())
//            , "C:\\Users\\vkukse\\Desktop\\EducationProjects\\rest-onedrive\\src\\files\\data\\test.xml"
//            , "application/xml", "authenticated-read", Arrays.asList(FULL_CONTROL, READ))}
    };
  }

  @Test(dataProvider = "bucketCreationData")
  public void awsBucketCreationWithAclPermissionsAndDeletionTest(
      BucketCreationModel bucketCreationModel) {
    Response bucketCreationResponse = createBucket(bucketCreationModel);
    bucketCreationResponse.then().assertThat().statusCode(200);
    Response aclResponse = listBucketAcl(bucketCreationModel);
    aclResponse.then().assertThat().statusCode(200);
    String aclContents = aclResponse.getBody().asString();
    List<String> permissions = XmlUtils.getDeserializedXml(aclContents, AccessControlModel.class)
        .getGrantModels()
        .stream()
        .map(x -> x.getPermission())
        .collect(Collectors.toList());
    Assert.assertEquals(permissions, bucketCreationModel.getAccessControlList());
    deleteBucket(bucketCreationModel).then().assertThat().statusCode(204);
  }

  public Response createBucket(BucketCreationModel bucketCreationModel) {
    List<String> canonicalHeaders = Arrays
        .asList(X_AMZ_ACL_HEADER, X_AMZ_CONTENT_SHA_256_HEADER, X_AMZ_DATE_HEADER);
    List<String> signedHeaders = Arrays
        .asList(HOST_HEADER, X_AMZ_ACL_HEADER, X_AMZ_CONTENT_SHA_256_HEADER, X_AMZ_DATE_HEADER);
    AWSBucketCreatorService awsBucketCreatorService = RequestBuilder
        .getInstance(awsAccessKeyId, awsSecretAccessKeyId).setRegionName(regionName)
        .setHttpMethod(HTTP_PUT_METHOD)
        .setPayload(bucketCreationModel.getFilePath())
        .setCanonicalUri("")
        .setServiceName(SERVICE_NAME)
        .setBucketName(bucketCreationModel.getBucketName())
        .setCanonicalHeaders(canonicalHeaders)
        .setSignedHeaders(signedHeaders)
        .setAcl(bucketCreationModel.getAcl())
        .setPathToFile(bucketCreationModel.getFilePath())
        .setContentType(bucketCreationModel.getContentType())
        .buildBucketCreatorRequest();
    return awsBucketCreatorService.createBucketWithPermissions();
  }

  public Response listBucketAcl(BucketCreationModel bucketCreationModel) {
    List<String> canonicalHeaders = Arrays.asList(X_AMZ_CONTENT_SHA_256_HEADER, X_AMZ_DATE_HEADER);
    List<String> signedHeaders = Arrays
        .asList(HOST_HEADER, X_AMZ_CONTENT_SHA_256_HEADER, X_AMZ_DATE_HEADER);
    AwsListAclBucketService awsListAclBucketService = RequestBuilder.getInstance(awsAccessKeyId
        , awsSecretAccessKeyId).setRegionName(regionName)
        .setHttpMethod(HTTP_GET_METHOD)
        .setPayload("")
        .setCanonicalUri("acl=")
        .setServiceName(SERVICE_NAME)
        .setBucketName(bucketCreationModel.getBucketName())
        .setCanonicalHeaders(canonicalHeaders)
        .setSignedHeaders(signedHeaders)
        .buildAclBucketRequest();
    return awsListAclBucketService.getAwsBucketAcl();
  }

  public Response deleteBucket(BucketCreationModel bucketCreationModel) {
    List<String> canonicalHeaders = Arrays.asList(X_AMZ_CONTENT_SHA_256_HEADER, X_AMZ_DATE_HEADER);
    List<String> signedHeaders = Arrays
        .asList(HOST_HEADER, X_AMZ_CONTENT_SHA_256_HEADER, X_AMZ_DATE_HEADER);
    AWSBucketDeleteService awsBucketDeleteService = RequestBuilder.getInstance(awsAccessKeyId
        , awsSecretAccessKeyId).setRegionName(regionName)
        .setHttpMethod(HTTP_DELETE_METHOD)
        .setPayload("")
        .setCanonicalUri("")
        .setServiceName(SERVICE_NAME)
        .setBucketName(bucketCreationModel.getBucketName())
        .setCanonicalHeaders(canonicalHeaders)
        .setSignedHeaders(signedHeaders)
        .buildBucketDeleteRequest();
    return awsBucketDeleteService.deleteBucket();
  }

}
