package models.listbucketmodels;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class OwnerModel {

  @JacksonXmlProperty(localName = "ID")
  private String id;

  public OwnerModel(String id) {
    this.id = id;
  }

  public OwnerModel() {
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
