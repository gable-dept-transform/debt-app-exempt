package th.co.ais.mimo.debt.exempt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DMSEM004CriteriaMasterBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long criteriaId;
	private String criteriaType;
	private String criteriaDescription;
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
	private Date firstArDatFrom;
	private Date firstArDatTo;
	private Date baInactiveDatFrom;
	private Date baInactiveDatTo;
	private String statusReasonList;
	private String actionReasonList;
	private String negoFlag;
	private String exemptFlag;
	private Long maxAccount;
	private BigDecimal maxAmount;
	private Long assignDuration;
	private String campaignCode;
	private String agentType;
	private String assignType;
	private String assignJob;
	private Long dueDay;
	private String callType;
	private String messageId;
	private Long maxCall;
	private Long maxRedial;
	private String templateId;
	private Date checkDatFrom;
	private Date checkDatTo;
	private Date invoiceBackDat;
	private Long invoiceInterval;
	private String runType;
	private String runAt;
	private String runBillType;
	private Long runBillDay;
	private Date runStartDat;
	private Date runEndDat;
	private String autoAssignFlag;
	private String lastUpdateBy;
	private Date lastUpdateDtm;
	private String orderLevel;
	private Long priorityNo;
	private String blacklistType;
	private String blacklistSubtype;
	private String letterLevel;
	private String letterAddressType;
	private Date letterDat;
	private Long letterPaymentDue;
	private String provinceList;
	private String tpDebtType;
	private String valueSegmentList;
	private String orderType;
	private String reasonCodeList;
	private Date dfDatFrom;
	private Date dfDatTo;
	private String blacklistDatFlag;
	private Date blacklistDatFrom;
	private Date blacklistDatTo;
	private String caStatus;
	private Date caInactiveDatFrom;
	private Date caInactiveDatTo;
	private Character incEnvelopeFlag;
	private Character thaiLetterFlag;
	private Character templateIdCtype;
	private String superDealFlag;
	private String fixCompanyTypeFlag;
	private BigDecimal deviceDiscountFrom;
	private BigDecimal deviceDiscountTo;
	private String createBy;
	private BigDecimal caDebtMnyFrom;
	private BigDecimal caDebtMnyTo;
	private Character genDetailRptFlag;
	private String billSystemList;
	private Character autoSmsFlag;
	private BigDecimal caDeviceDiscountFrom;
	private BigDecimal caDeviceDiscountTo;
	private String cpeBrandList;
	private String cpeFlag;
	private String ivrRobotFlag;
	private String cpePenaltyFlag;
	private BigDecimal cpePenaltyAmount;
	private String bundlingFlag;
	private String caAmountFlag;
	private BigDecimal caAmount;

}