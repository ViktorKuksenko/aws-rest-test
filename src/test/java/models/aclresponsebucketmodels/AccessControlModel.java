package models.aclresponsebucketmodels;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(localName = "AccessControlPolicy")
public class AccessControlModel {
  @JacksonXmlProperty(localName = "Owner")
  private OwnerModel ownerModel;
  @JacksonXmlProperty(localName = "AccessControlList")
  private List<GrantModel> grantModels;

  public AccessControlModel() {
  }

  public AccessControlModel(OwnerModel ownerModel,
      List<GrantModel> grantModels) {
    this.ownerModel = ownerModel;
    this.grantModels = grantModels;
  }

  public OwnerModel getOwnerModel() {
    return ownerModel;
  }

  public void setOwnerModel(OwnerModel ownerModel) {
    this.ownerModel = ownerModel;
  }

  public List<GrantModel> getGrantModels() {
    return grantModels;
  }

  public void setGrantModels(List<GrantModel> grantModels) {
    this.grantModels = grantModels;
  }

  @Override
  public String toString() {
    return "AccessControlModel{" +
        "ownerModel=" + ownerModel +
        ", grantModels=" + grantModels +
        '}';
  }
}
