package models.aclresponsebucketmodels;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class GrantModel {
  @JacksonXmlProperty(localName = "ID")
  private String id;
  @JacksonXmlProperty(localName = "Permission")
  private String permission;

  public GrantModel() {
  }

  public GrantModel(String id, String permission) {
    this.id = id;
    this.permission = permission;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  @Override
  public String toString() {
    return "GrantModel{" +
        "id='" + id + '\'' +
        ", permission='" + permission + '\'' +
        '}';
  }
}
