package th.co.ais.mimo.debt.exempt.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsertAssignIdReq {

	private String modeId;
	private String preassignId;
	private Long criteriaId;
	private String criteriaType;
	private String assignId;
	private String assignDat;
	private String assignStatus;
	private String unassignDat;
	private String companyCode;
	private String collectionSegmentList;
	private String paymentTypeList;
	private String billCycleList;
	private String regionList;
	private String mobileStatusList;
	private String productGroupList;
	private String cateSubcateList;
	private String baStatusList;
	private BigDecimal minInvoiceMny;
	private Long invoiceCount;
	private BigDecimal debtMnyFrom;
	private BigDecimal debtMnyTo;
	private BigDecimal arMnyFrom;
	private BigDecimal arMnyTo;
	private Long debtAgeFrom;
	private Long debtAgeTo;
	private Character creditTermFlag;
	private Long statusAgeFrom;
	private Long statusAgeTo;
	private Long serviceAgeFrom;
	private Long serviceAgeTo;
	private String firstArDatFrom;
	private String firstArDatTo;
		
	private String statusReasonList;
	private String actionReasonList;
	private String negoFlag;
	private String exemptFlag;
	private Long maxAccount;
	private BigDecimal maxAmount;
	private Long assignDuration;
	private String assignType;
	private String agentType;
	private String campaignCode;
	private Long dueDay;
	private String messageId;
	private Long maxCall;
	private Long maxRedial;
	private String templateId;

	private String invoiceBackDat;
	private Long invoiceInterval;
	private String autoAssignFlag;
	private String lastUpdateBy;
	private String lastUpdateDtm;
	private String orderLevel;
	private String collectionSegmentAllFlag;
	private String mobileStatusAllFlag;
	private String regionAllFlag;
	private String productGroupAllFlag;
	private String cateSubcateAllFlag;
	private String baStatusAllFlag;
	private String actionReasonAllFlag;
	private String statusReasonAllFlag;
	private String paymentTypeAllFlag;
	private String billCycleAllFlag;
	private String createDtm;
	private Long runDb;
	private String criteriaDescription;
	private String cancelAssignFlag;
	private String blacklistType;
	private String blacklistSubtype;
	private String letterLevel;
	private String letterAddressType;
	private String letterDat;
	private Long letterPaymentDue;
	private String valueSegmentList;
	private String orderType;
	private String reasonCodeList;
	private String confirmDat;
	private String processStatus;


	private String callType;
	private String assignJob;
	private String provinceList;
	private Character cnFlag;
	private String companyTypeList;
	private String templateIdCtype;
	private String superDealFlag;
	private String fixCompanyTypeFlag;
	private BigDecimal deviceDiscountFrom;
	private BigDecimal deviceDiscountTo;
	private String billSystemList;
	private String createBy;
	private Character genDetailRptFlag;
	private String cpeBrandList;
	private String cpeFlag;
	private String exemptReason;
	private String bundlingFlag;

	
	private Date caInactiveDatFrom;
	private Date caInactiveDatTo;
	private Date baInactiveDatFrom;
	private Date baInactiveDatTo;
	private Date checkDatFrom;
	private Date checkDatTo;
	private Date dfDatFrom;
	private Date dfDatTo;
	private Date processDate;

	private boolean selectEfficetiveDate;
	private boolean selectDuration;

	private String refAssignId;
	private String refReasonExempt;
	
	private List<String> billingAccNumList;
	private List<String> mobileNumList;
	
	private List<DMSEM004GetLoadTextDataDto> LoadTextData;
	private String typeLoadText;
}
