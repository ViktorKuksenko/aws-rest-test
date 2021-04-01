package models.listbucketmodels;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ContentsModel {
  @JacksonXmlProperty(localName = "Key")
  private String key;
  @JacksonXmlProperty(localName = "LastModified")
  private String lastModified;
  @JacksonXmlProperty(localName = "ETag")
  private String eTag;
  @JacksonXmlProperty(localName = "Size")
  private String size;
  @JacksonXmlProperty(localName = "Owner")
  private OwnerModel owner;

  public ContentsModel(String key, String lastModified, String eTag, String size,
      OwnerModel owner) {
    this.key = key;
    this.lastModified = lastModified;
    this.eTag = eTag;
    this.size = size;
    this.owner = owner;
  }

  public ContentsModel() {
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setLastModified(String lastModified) {
    this.lastModified = lastModified;
  }

  public void seteTag(String eTag) {
    this.eTag = eTag;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public void setOwner(OwnerModel owner) {
    this.owner = owner;
  }

  public String getKey() {
    return key;
  }

  public String getLastModified() {
    return lastModified;
  }

  public String geteTag() {
    return eTag;
  }

  public String getSize() {
    return size;
  }

  public OwnerModel getOwner() {
    return owner;
  }

  @Override
  public String toString() {
    return "ContentsModel{" +
        "key='" + key + '\'' +
        ", lastModified='" + lastModified + '\'' +
        ", eTag='" + eTag + '\'' +
        ", size='" + size + '\'' +
        ", owner=" + owner +
        '}';
  }
}
