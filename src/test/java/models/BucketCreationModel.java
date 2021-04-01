package models;

import java.util.List;

public class BucketCreationModel {

  private String bucketName;
  private String filePath;
  private String contentType;
  private String acl;
  private List<String> accessControlList;

  public BucketCreationModel(String bucketName, String filePath, String contentType,
      String acl, List<String> accessControlList) {
    this.bucketName = bucketName;
    this.filePath = filePath;
    this.contentType = contentType;
    this.acl = acl;
    this.accessControlList = accessControlList;
  }

  public String getBucketName() {
    return bucketName;
  }

  public String getFilePath() {
    return filePath;
  }

  public String getContentType() {
    return contentType;
  }

  public String getAcl() {
    return acl;
  }

  public List<String> getAccessControlList() {
    return accessControlList;
  }
}
