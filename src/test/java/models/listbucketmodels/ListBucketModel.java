package models.listbucketmodels;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(localName = "ListBucketResult")
public class ListBucketModel {
  @JacksonXmlProperty(localName = "Name")
  private String name;
  @JacksonXmlProperty(localName = "Prefix")
  private String prefix;
  @JacksonXmlProperty(localName = "Marker")
  private String marker;
  @JacksonXmlProperty(localName = "MaxKeys")
  private String maxKeys;
  @JacksonXmlProperty(localName = "IsTruncated")
  private boolean isTruncated;
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "Contents")
  private List<ContentsModel> contentsModel;
  @JacksonXmlProperty(localName = "StorageClass")
  private String storageClass;

  public ListBucketModel(String name, String prefix, String marker, String maxKeys,
      boolean isTruncated, List<ContentsModel> contentsModel, String storageClass) {
    this.name = name;
    this.prefix = prefix;
    this.marker = marker;
    this.maxKeys = maxKeys;
    this.isTruncated = isTruncated;
    this.contentsModel = contentsModel;
    this.storageClass = storageClass;
  }

  public ListBucketModel() {
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMaxKeys(String maxKeys) {
    this.maxKeys = maxKeys;
  }

  public void setTruncated(boolean truncated) {
    isTruncated = truncated;
  }

  public void setContentsModel(List<ContentsModel> contentsModel) {
    this.contentsModel = contentsModel;
  }

  public void setStorageClass(String storageClass) {
    this.storageClass = storageClass;
  }

  public String getName() {
    return name;
  }

  public String getMaxKeys() {
    return maxKeys;
  }

  public boolean isTruncated() {
    return isTruncated;
  }

  public List<ContentsModel> getContentsModel() {
    return contentsModel;
  }

  public String getStorageClass() {
    return storageClass;
  }

  @Override
  public String toString() {
    return "AclResponseModel{" +
        "name='" + name + '\'' +
        ", maxKeys='" + maxKeys + '\'' +
        ", isTruncated=" + isTruncated +
        ", contentsModel=" + contentsModel +
        '}';
  }
}
