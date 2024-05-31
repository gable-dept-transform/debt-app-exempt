package th.co.ais.mimo.debt.exempt.model;

import java.math.BigDecimal;

public interface DMSEM004CriteriaMasterDto {
	
	Long getCriteriaId();
	String getCriteriaType();
	String getCriteriaDescription();
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
	String getFirstArDatFrom();
	String getFirstArDatTo();
	String getBaInactiveDatFrom();
	String getBaInactiveDatTo();
	String getStatusReasonList();
	String getActionReasonList();
	String getNegoFlag();
	String getExemptFlag();
	Long getMaxAccount();
	BigDecimal getMaxAmount();
	Long getAssignDuration();
	String getCampaignCode();
	String getAgentType();
	String getAssignType();
	String getAssignJob();
	Long getDueDay();
	String getCallType();
	String getMessageId();
	Long getMaxCall();
	Long getMaxRedial();
	String getTemplateId();
	String getCheckDatFrom();
	String getCheckDatTo();
	String getInvoiceBackDat();
	Long getInvoiceInterval();
	String getRunType();
	String getRunAt();
	String getRunBillType();
	Long getRunBillDay();
	String getRunStartDat();
	String getRunEndDat();
	String getAutoAssignFlag();
	String getLastUpdateBy();
	String getLastUpdateDtm();
	String getOrderLevel();
	Long getPriorityNo();
	String getBlacklistType();
	String getBlacklistSubtype();
	String getLetterLevel();
	String getLetterAddressType();
	String getLetterDat();
	Long getLetterPaymentDue();
	String getProvinceList();
	String getTpDebtType();
	String getValueSegmentList();
	String getOrderType();
	String getReasonCodeList();
	String getDfDatFrom();
	String getDfDatTo();
	String getBlacklistDatFlag();
	String getBlacklistDatFrom();
	String getBlacklistDatTo();
	String getCaStatus();
	String getCaInactiveDatFrom();
	String getCaInactiveDatTo();
	Character getThaiLetterFlag();
	Character getTemplateIdCtype();
	String getSuperDealFlag();
	String getFixCompanyTypeFlag();
	BigDecimal getDeviceDiscountFrom();
	BigDecimal getDeviceDiscountTo();
	String getCreateBy();
	BigDecimal getCaDebtMnyFrom();
	BigDecimal getCaDebtMnyTo();
	Character getGenDetailRptFlag();
	String getBillSystemList();
	Character getAutoSmsFlag();
	BigDecimal getCaDeviceDiscountFrom();
	BigDecimal getCaDeviceDiscountTo();
	String getCpeBrandList();
	String getCpeFlag();
	String getIvrRobotFlag();
	String getCpePenaltyFlag();
	BigDecimal getCpePenaltyAmount();
	String getBundlingFlag();
	String getCaAmountFlag();
	BigDecimal getCaAmount();
	
}
