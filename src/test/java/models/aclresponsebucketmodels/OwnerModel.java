package models.aclresponsebucketmodels;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class OwnerModel {
  @JacksonXmlProperty(localName = "ID")
  private String id;

  public OwnerModel() {
  }

  public OwnerModel(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "OwnerModel{" +
        "id='" + id + '\'' +
        '}';
  }
}
