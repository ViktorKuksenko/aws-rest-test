package test;

import helpers.XmlUtils;
import models.listbucketmodels.ListBucketModel;
import helpers.TimeUtils;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import models.UploadObjectModel;
import models.listbucketmodels.ContentsModel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.AWSFileUploadService;
import services.AWSListBucketService;
import services.AWSObjectDeleteService;
import services.RequestBuilder;

public class ObjectCreationAndDeletionTest extends TestRunner {

  @DataProvider
  public Object[][] uploadObjectsData() {
    return new Object[][]{
        {new UploadObjectModel("restassured", "C:\\Users\\vkukse\\Desktop\\EducationProjects\\"
            + "rest-onedrive\\src\\files\\data\\12mbtest.txt", String.format("/test/test%s",
            TimeUtils.getCurrentLocalTimestamp()), "text/txt")},
        {new UploadObjectModel("restassured", "C:\\Users\\vkukse\\Desktop\\EducationProjects\\"
            + "rest-onedrive\\src\\files\\data\\test.txt", String.format("/test/test%s",
            TimeUtils.getCurrentLocalTimestamp()), "text/txt")},
        {new UploadObjectModel("restassured", "C:\\Users\\vkukse\\Desktop\\EducationProjects\\"
            + "rest-onedrive\\src\\files\\data\\100mbtest.txt", String.format("/test/test%s",
            TimeUtils.getCurrentLocalTimestamp()), "text/txt")},
        {new UploadObjectModel("restassured", "C:\\Users\\vkukse\\Desktop\\EducationProjects\\"
            + "rest-onedrive\\src\\files\\data\\CatJPEG.jpg", String.format("/test/test%s",
            TimeUtils.getCurrentLocalTimestamp()), "image/jpeg")},
    };
  }

  @Test(dataProvider = "uploadObjectsData")
  public void verifyObjectCreationAndDeletionTest(UploadObjectModel uploadObjectModel) {
    Response awsFileUploadServiceResponse = createObject(uploadObjectModel);
    awsFileUploadServiceResponse.then().assertThat().statusCode(200);

    Response awsBucketContents = getBucketContents(uploadObjectModel);
    awsBucketContents.then().assertThat().statusCode(200);

    String bucketContents = awsBucketContents.getBody().asString();
    List<ContentsModel> contentsModel = XmlUtils
        .getDeserializedXml(bucketContents, ListBucketModel.class)
        .getContentsModel();

    Assert.assertTrue(contentsModel
        .stream()
        .anyMatch(file -> uploadObjectModel.getUri().substring(1)
            .equals(file.getKey())));

    Response deleteBucketResponse = deleteBucketContents(uploadObjectModel);
    deleteBucketResponse.then().statusCode(204);
  }

  public Response createObject(UploadObjectModel uploadObjectModel) {
    List<String> canonicalHeaders = Arrays.asList(X_AMZ_CONTENT_SHA_256_HEADER, X_AMZ_DATE_HEADER);
    List<String> signedHeaders = Arrays.asList(HOST_HEADER, X_AMZ_CONTENT_SHA_256_HEADER
        , X_AMZ_DATE_HEADER);
    AWSFileUploadService awsFileUploadService = RequestBuilder.getInstance(awsAccessKeyId
        , awsSecretAccessKeyId).setRegionName(regionName)
        .setHttpMethod(HTTP_PUT_METHOD)
        .setPayload(uploadObjectModel.getFilePath())
        .setPathToFile(uploadObjectModel.getFilePath())
        .setCanonicalUri(uploadObjectModel.getUri())
        .setServiceName(SERVICE_NAME)
        .setBucketName(uploadObjectModel.getBucketName())
        .setCanonicalHeaders(canonicalHeaders)
        .setSignedHeaders(signedHeaders)
        .buildFileUploadRequest();
    return awsFileUploadService.uploadObjects(uploadObjectModel.getContentType());
  }

  public Response getBucketContents(UploadObjectModel uploadObjectModel) {
    List<String> canonicalHeaders = Arrays.asList(X_AMZ_CONTENT_SHA_256_HEADER, X_AMZ_DATE_HEADER);
    List<String> signedHeaders = Arrays.asList(HOST_HEADER, X_AMZ_CONTENT_SHA_256_HEADER
        , X_AMZ_DATE_HEADER);
    AWSListBucketService awsListBucketService = RequestBuilder.getInstance(awsAccessKeyId
        , awsSecretAccessKeyId).setRegionName(regionName)
        .setHttpMethod(HTTP_GET_METHOD)
        .setPayload("")
        .setCanonicalUri("")
        .setServiceName(SERVICE_NAME)
        .setBucketName(uploadObjectModel.getBucketName())
        .setCanonicalHeaders(canonicalHeaders)
        .setSignedHeaders(signedHeaders)
        .buildAuthRequest();
    return awsListBucketService.getAwsBucketContents();
  }

  public Response deleteBucketContents(UploadObjectModel uploadObjectModel) {
    List<String> canonicalHeaders = Arrays.asList(X_AMZ_CONTENT_SHA_256_HEADER, X_AMZ_DATE_HEADER);
    List<String> signedHeaders = Arrays.asList(HOST_HEADER, X_AMZ_CONTENT_SHA_256_HEADER
        , X_AMZ_DATE_HEADER);
    AWSObjectDeleteService awsObjectDeleteService = RequestBuilder.getInstance(awsAccessKeyId
        , awsSecretAccessKeyId).setRegionName(regionName)
        .setHttpMethod(HTTP_DELETE_METHOD)
        .setPayload("")
        .setCanonicalUri(uploadObjectModel.getUri())
        .setServiceName(SERVICE_NAME)
        .setBucketName(uploadObjectModel.getBucketName())
        .setCanonicalHeaders(canonicalHeaders)
        .setSignedHeaders(signedHeaders)
        .buildObjectDeleteRequest();
    return awsObjectDeleteService.deleteObject();
  }

}
