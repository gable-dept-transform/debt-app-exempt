package th.co.ais.mimo.debt.exempt.dto;

import java.math.BigDecimal;
import java.util.Date;

public interface GetRefAssignDto {
	
	String getModeId();
	String getPreassignId();
	Long getCriteriaId();
	String getCriteriaType();
	String getAssignId();
	Date getAssignDat();
	String getAssignStatus();
	Date getUnassignDat();
	String getCompanyCode();
	String getCollectionSegmentList();
	String getPaymentTypeList();
	String getBillCycleList();
	String getRegionList();
	String getMobileStatusList();
	String getProductGroupList();
	String getCateSubcateList();
	String getBaStatusList();
	BigDecimal getMinInvoiceMny();
	Long getInvoiceCount();
	BigDecimal getDebtMnyFrom();
	BigDecimal getDebtMnyTo();
	BigDecimal getArMnyFrom();
	BigDecimal getArMnyTo();
	Long getDebtAgeFrom();
	Long getDebtAgeTo();
	Character getCreditTermFlag();
	Long getStatusAgeFrom();
	Long getStatusAgeTo();
	Long getServiceAgeFrom();
	Long getServiceAgeTo();
	Date getFirstArDatFrom();
	Date getFirstArDatTo();
	Date getBaInactiveDatFrom();
	Date getBaInactiveDatTo();
	String getStatusReasonList();
	String getActionReasonList();
	String getNegoFlag();
	String getExemptFlag();
	Long getMaxAccount();
	BigDecimal getMaxAmount();
	Long getAssignDuration();
	String getAssignType();
	String getAgentType();
	String getCampaignCode();
	Long getDueDay();
	String getMessageId();
	Long getMaxCall();
	Long getMaxRedial();
	String getTemplateId();
	Date getCheckDatFrom();
	Date getCheckDatTo();
	Date getInvoiceBackDat();
	Long getInvoiceInterval();
	String getAutoAssignFlag();
	String getLastUpdateBy();
	Date getLastUpdateDtm();
	String getOrderLevel();
	String getCollectionSegmentAllFlag();
	String getMobileStatusAllFlag();
	String getRegionAllFlag();
	String getProductGroupAllFlag();
	String getCateSubcateAllFlag();
	String getBaStatusAllFlag();
	String getActionReasonAllFlag();
	String getStatusReasonAllFlag();
	String getPaymentTypeAllFlag();
	String getBillCycleAllFlag();
	Date getCreateDtm();
	Long getRunDb();
	Character getCriteriaDescription();
	String getCancelAssignFlag();
	String getBlacklistType();
	String getBlacklistSubtype();
	String getLetterLevel();
	String getLetterAddressType();
	Date getLetterDat();
	Long getLetterPaymentDue();
	String getValueSegmentList();
	String getOrderType();
	String getReasonCodeList();
	Date getConfirmDat();
	String getProcessStatus();
	Date getDfDatFrom();
	Date getDfDatTo();
	Date getCaInactiveDatFrom();
	Date getCaInactiveDatTo();
	String getCallType();
	String getAssignJob();
	String getProvinceList();
	Character getCnFlag();
	String getCompanyTypeList();
	String getTemplateIdCtype();
	String getSuperDealFlag();
	String getFixCompanyTypeFlag();
	BigDecimal getDeviceDiscountFrom();
	BigDecimal getDeviceDiscountTo();
	String getBillSystemList();
	String getCreateBy();
	Character getGenDetailRptFlag();
	String getCpeBrandList();
	String getCpeFlag();
	String getExemptReason();
	String getBundlingFlag();
	
}