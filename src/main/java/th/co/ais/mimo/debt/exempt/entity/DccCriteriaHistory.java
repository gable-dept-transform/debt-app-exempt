package th.co.ais.mimo.debt.exempt.entity; 
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor; 
 
@Data
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
@Entity 
@Table(name = "dcc_criteria_history")
public class DccCriteriaHistory implements Serializable{ 
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	DccCriteriaHistoryId id;

	@Column(name = "CRITERIA_ID")
	private Long criteriaId;

	@Column(name = "CRITERIA_TYPE")
	private String criteriaType;

	@Column(name = "PREASSIGN_DAT")
	private Date preassignDat;

	@Column(name = "ASSIGN_ID")
	private String assignId;

	@Column(name = "ASSIGN_DAT")
	private Date assignDat;

	@Column(name = "ASSIGN_STATUS")
	private String assignStatus;

	@Column(name = "UNASSIGN_DAT")
	private Date unassignDat;

	@Column(name = "COMPANY_CODE")
	private String companyCode;

	@Column(name = "COLLECTION_SEGMENT_LIST")
	private String collectionSegmentList;

	@Column(name = "PAYMENT_TYPE_LIST")
	private String paymentTypeList;

	@Column(name = "BILL_CYCLE_LIST")
	private String billCycleList;

	@Column(name = "REGION_LIST")
	private String regionList;

	@Column(name = "MOBILE_STATUS_LIST")
	private String mobileStatusList;

	@Column(name = "PRODUCT_GROUP_LIST")
	private String productGroupList;

	@Column(name = "CATE_SUBCATE_LIST")
	private String cateSubcateList;

	@Column(name = "BA_STATUS_LIST")
	private String baStatusList;

	@Column(name = "MIN_INVOICE_MNY")
	private BigDecimal minInvoiceMny;

	@Column(name = "INVOICE_COUNT")
	private Long invoiceCount;

	@Column(name = "DEBT_MNY_FROM")
	private BigDecimal debtMnyFrom;

	@Column(name = "DEBT_MNY_TO")
	private BigDecimal debtMnyTo;

	@Column(name = "AR_MNY_FROM")
	private BigDecimal arMnyFrom;

	@Column(name = "AR_MNY_TO")
	private BigDecimal arMnyTo;

	@Column(name = "DEBT_AGE_FROM")
	private Long debtAgeFrom;

	@Column(name = "DEBT_AGE_TO")
	private Long debtAgeTo;

	@Column(name = "STATUS_AGE_FROM")
	private Long statusAgeFrom;

	@Column(name = "STATUS_AGE_TO")
	private Long statusAgeTo;

	@Column(name = "SERVICE_AGE_FROM")
	private Long serviceAgeFrom;

	@Column(name = "SERVICE_AGE_TO")
	private Long serviceAgeTo;

	@Column(name = "FIRST_AR_DAT_FROM")
	private Date firstArDatFrom;

	@Column(name = "FIRST_AR_DAT_TO")
	private Date firstArDatTo;

	@Column(name = "BA_INACTIVE_DAT_FROM")
	private Date baInactiveDatFrom;

	@Column(name = "BA_INACTIVE_DAT_TO")
	private Date baInactiveDatTo;

	@Column(name = "STATUS_REASON_LIST")
	private String statusReasonList;

	@Column(name = "ACTION_REASON_LIST")
	private String actionReasonList;

	@Column(name = "NEGO_FLAG")
	private String negoFlag;

	@Column(name = "EXEMPT_FLAG")
	private String exemptFlag;

	@Column(name = "MAX_ACCOUNT")
	private Long maxAccount;

	@Column(name = "MAX_AMOUNT")
	private BigDecimal maxAmount;

	@Column(name = "ASSIGN_DURATION")
	private Long assignDuration;

	@Column(name = "ASSIGN_TYPE")
	private String assignType;

	@Column(name = "AGENT_TYPE")
	private String agentType;

	@Column(name = "CAMPAIGN_CODE")
	private String campaignCode;

	@Column(name = "DUE_DAY")
	private Long dueDay;

	@Column(name = "MESSAGE_ID")
	private String messageId;

	@Column(name = "MAX_CALL")
	private Long maxCall;

	@Column(name = "MAX_REDIAL")
	private Long maxRedial;

	@Column(name = "TEMPLATE_ID")
	private String templateId;

	@Column(name = "INVOICE_BACK_DAT")
	private Date invoiceBackDat;

	@Column(name = "AUTO_ASSIGN_FLAG")
	private String autoAssignFlag;

	@Column(name = "LAST_UPDATE_BY")
	private String lastUpdateBy;

	@Column(name = "LAST_UPDATE_DTM")
	private Date lastUpdateDtm;

	@Column(name = "CHECK_DAT_FROM")
	private Date checkDatFrom;

	@Column(name = "CHECK_DAT_TO")
	private Date checkDatTo;

	@Column(name = "INVOICE_INTERVAL")
	private Long invoiceInterval;

	@Column(name = "UNASSIGN_REASON")
	private String unassignReason;

	@Column(name = "CREATE_DTM")
	private Date createDtm;

	@Column(name = "RUN_DB")
	private Long runDb;

	@Column(name = "COLLECTION_SEGMENT_ALL_FLAG")
	private String collectionSegmentAllFlag;

	@Column(name = "PAYMENT_TYPE_ALL_FLAG")
	private String paymentTypeAllFlag;

	@Column(name = "BILL_CYCLE_ALL_FLAG")
	private String billCycleAllFlag;

	@Column(name = "REGION_ALL_FLAG")
	private String regionAllFlag;

	@Column(name = "MOBILE_STATUS_ALL_FLAG")
	private String mobileStatusAllFlag;

	@Column(name = "PRODUCT_GROUP_ALL_FLAG")
	private String productGroupAllFlag;

	@Column(name = "CATE_SUBCATE_ALL_FLAG")
	private String cateSubcateAllFlag;

	@Column(name = "BA_STATUS_ALL_FLAG")
	private String baStatusAllFlag;

	@Column(name = "STATUS_REASON_ALL_FLAG")
	private String statusReasonAllFlag;

	@Column(name = "ACTION_REASON_ALL_FLAG")
	private String actionReasonAllFlag;

	@Column(name = "ORDER_LEVEL")
	private String orderLevel;

	@Column(name = "CANCEL_ASSIGN_FLAG")
	private String cancelAssignFlag;

	@Column(name = "BLACKLIST_TYPE")
	private String blacklistType;

	@Column(name = "BLACKLIST_SUBTYPE")
	private String blacklistSubtype;

	@Column(name = "LETTER_LEVEL")
	private String letterLevel;

	@Column(name = "LETTER_ADDRESS_TYPE")
	private String letterAddressType;

	@Column(name = "LETTER_DAT")
	private Date letterDat;

	@Column(name = "LETTER_PAYMENT_DUE")
	private Long letterPaymentDue;

	@Column(name = "TP_DEBT_TYPE")
	private String tpDebtType;

	@Column(name = "PROVINCE_LIST")
	private String provinceList;

	@Column(name = "CONFIRM_DAT")
	private Date confirmDat;

	@Column(name = "PROCESS_STATUS")
	private String processStatus;

	@Column(name = "WO_YEAR")
	private String woYear;

	@Column(name = "WO_NO")
	private String woNo;

	@Column(name = "VALUE_SEGMENT_LIST")
	private String valueSegmentList;

	@Column(name = "VALUE_SEGMENT_ALL_FLAG")
	private String valueSegmentAllFlag;

	@Column(name = "TEXT_FILE_NAME")
	private String textFileName;

	@Column(name = "DF_DAT_TO")
	private Date dfDatTo;

	@Column(name = "DF_DAT_FROM")
	private Date dfDatFrom;

	@Column(name = "BLACKLIST_SEQ")
	private Long blacklistSeq;

	@Column(name = "ORDER_TYPE")
	private String orderType;

	@Column(name = "REASON_CODE_LIST")
	private String reasonCodeList;

	@Column(name = "ASSIGN_PROCESS_STATUS")
	private String assignProcessStatus;

	@Column(name = "BLACKLIST_DAT_FLAG")
	private String blacklistDatFlag;

	@Column(name = "BLACKLIST_DAT_FROM")
	private Date blacklistDatFrom;

	@Column(name = "BLACKLIST_DAT_TO")
	private Date blacklistDatTo;

	@Column(name = "SEND_ADA_DTM")
	private Date sendAdaDtm;

	@Column(name = "CA_STATUS")
	private String caStatus;

	@Column(name = "CA_INACTIVE_DAT_FROM")
	private Date caInactiveDatFrom;

	@Column(name = "CA_INACTIVE_DAT_TO")
	private Date caInactiveDatTo;

	@Column(name = "CRITERIA_DESCRIPTION")
	private String criteriaDescription;

	@Column(name = "ASSIGN_JOB")
	private String assignJob;

	@Column(name = "MODULE_NAME")
	private String moduleName;

	@Column(name = "INITIAL_FLAG")
	private String initialFlag;

	@Column(name = "AUTO_CRITERIA_FLAG")
	private String autoCriteriaFlag;

	@Column(name = "TOTAL_ACCOUNT_BA")
	private Long totalAccountBa;

	@Column(name = "TOTAL_AMOUNT")
	private BigDecimal totalAmount;

	@Column(name = "TOTAL_ACCOUNT_CA")
	private Long totalAccountCa;

	@Column(name = "CALL_FLAG")
	private String callFlag;

	@Column(name = "CREDIT_TERM_FLAG")
	private Character creditTermFlag;

	@Column(name = "THAI_LETTER_FLAG")
	private Character thaiLetterFlag;

	@Column(name = "INC_ENVELOPE_FLAG")
	private Character incEnvelopeFlag;

	@Column(name = "UNLOCK_MODE_FLAG")
	private Character unlockModeFlag;

	@Column(name = "COMPANY_TYPE_LIST")
	private String companyTypeList;

	@Column(name = "MESSAGE_ID_FBB")
	private String messageIdFbb;

	@Column(name = "TEMPLATE_ID_CTYPE")
	private String templateIdCtype;

	@Column(name = "SUPER_DEAL_FLAG")
	private String superDealFlag;

	@Column(name = "FIX_COMPANY_TYPE_FLAG")
	private String fixCompanyTypeFlag;

	@Column(name = "DEVICE_DISCOUNT_FROM")
	private BigDecimal deviceDiscountFrom;

	@Column(name = "DEVICE_DISCOUNT_TO")
	private BigDecimal deviceDiscountTo;

	@Column(name = "CREATE_BY")
	private String createBy;

	@Column(name = "CA_DEBT_MNY_FROM")
	private BigDecimal caDebtMnyFrom;

	@Column(name = "CA_DEBT_MNY_TO")
	private BigDecimal caDebtMnyTo;

	@Column(name = "GEN_DETAIL_RPT_FLAG")
	private Character genDetailRptFlag;

	@Column(name = "BILL_SYSTEM_LIST")
	private String billSystemList;

	@Column(name = "AUTO_SMS_FLAG")
	private Character autoSmsFlag;

	@Column(name = "CA_DEVICE_DISCOUNT_FROM")
	private BigDecimal caDeviceDiscountFrom;

	@Column(name = "CA_DEVICE_DISCOUNT_TO")
	private BigDecimal caDeviceDiscountTo;

	@Column(name = "CPE_FLAG")
	private String cpeFlag;

	@Column(name = "CPE_BRAND_LIST")
	private String cpeBrandList;

	@Column(name = "IVR_ROBOT_FLAG")
	private String ivrRobotFlag;

	@Column(name = "CPE_PENALTY_FLAG")
	private String cpePenaltyFlag;

	@Column(name = "CPE_PENALTY_AMOUNT")
	private BigDecimal cpePenaltyAmount;

	@Column(name = "BUNDLING_FLAG")
	private String bundlingFlag;

	@Column(name = "EXEMPT_REASON")
	private String exemptReason;

	@Column(name = "CA_AMOUNT_FLAG")
	private String caAmountFlag;

	@Column(name = "CA_AMOUNT")
	private BigDecimal caAmount;
}
