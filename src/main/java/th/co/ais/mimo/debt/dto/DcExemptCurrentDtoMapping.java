package th.co.ais.mimo.debt.dto;

import lombok.Data;


 public interface DcExemptCurrentDtoMapping {

     String getCustAccNum();

     String getBillingAccNum();

     String getMobileNum();

     String getModuleCode();

     String getModeId();

     String getExemptLevel();

     String getBillingAccName();

     String getEffectiveDate();

     String getEndDate();

     String getExpireDate();

     String getCateCode();

     String getAddReason();

     Integer getAddLocation();

     String getUpdateReason();

     Integer getUpdateLocation();

     String getLastUpdateBy();

     String getLastUpdateDate();

     Integer getNoOfExempt();

     String getSentInterFaceFlag();

     String getMobileStatus();

     Integer getRowNumber();
}
