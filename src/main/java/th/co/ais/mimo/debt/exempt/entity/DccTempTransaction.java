package th.co.ais.mimo.debt.exempt.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Column;
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
@Table(name = "dcc_temp_transaction")
public class DccTempTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CRITERIA_ID")
	private Long criteriaId;

	@Column(name = "CUST_ACC_NUM")
	private String custAccNum;

	@Column(name = "BILLING_ACC_NAME")
	private String billingAccName;

	@Column(name = "COMPANY_CODE")
	private String companyCode;

	@Column(name = "COLLECTION_SEGMENT")
	private String collectionSegment;

	@Column(name = "BILL_CYCLE")
	private String billCycle;

	@Column(name = "REGION_CODE")
	private String regionCode;

	@Column(name = "BA_STATUS")
	private String baStatus;

	@Column(name = "BA_STATUS_DAT")
	private Date baStatusDat;

	@Column(name = "MOBILE_STATUS_DAT")
	private Date mobileStatusDat;

	@Column(name = "CATE_TYPE")
	private String cateType;

	@Column(name = "SUBCATE_TYPE")
	private String subcateType;

	@Column(name = "INVOICE_COUNT")
	private Long invoiceCount;

	@Column(name = "DEBT_MNY")
	private BigDecimal debtMny;

	@Column(name = "FIRST_AR_DAT")
	private Date firstArDat;

	@Column(name = "CHECK_DAT")
	private Date checkDat;

	@Column(name = "NEGO_FLAG")
	private String negoFlag;

	@Column(name = "EXEMPT_FLAG")
	private String exemptFlag;

	@Column(name = "WO_TYPE")
	private String woType;

	@Column(name = "WO_TYPE_REASON")
	private String woTypeReason;

	@Column(name = "PRODUCT_GROUP")
	private String productGroup;

	@Column(name = "PAYMENT_TYPE")
	private String paymentType;

	@Column(name = "CUST_ACC_NAME")
	private String custAccName;

	@Column(name = "DEBT_AGE")
	private Long debtAge;

	@Column(name = "CHANGE_RX_FLAG")
	private String changeRxFlag;

	@Column(name = "REGISTER_DAT")
	private Date registerDat;

	@Column(name = "WAIVE_MNY")
	private BigDecimal waiveMny;

	@Column(name = "WAIVE_REASON")
	private String waiveReason;

	@Column(name = "APPROVE_FLAG")
	private String approveFlag;

	@Column(name = "TL_DAT")
	private Date tlDat;

	@Column(name = "NL_DAT")
	private Date nlDat;

	@Column(name = "WL")
	private Long wl;

	@Column(name = "TL")
	private Long tl;

	@Column(name = "NL")
	private Long nl;

	@Column(name = "EXEMPT_LEGAL_FLAG")
	private String exemptLegalFlag;

	@Column(name = "EXEMPT_REASON")
	private String exemptReason;

	@Column(name = "STAMP_COURT_DAT")
	private Date stampCourtDat;

	@Column(name = "NON_VAT")
	private BigDecimal nonVat;

	@Column(name = "NET_VAT")
	private BigDecimal netVat;

	@Column(name = "PENDING_VAT")
	private BigDecimal pendingVat;

	@Column(name = "VAT")
	private BigDecimal vat;

	@Column(name = "LAST_UPDATE_BY")
	private String lastUpdateBy;

	@Column(name = "LAST_UPDATE_DTM")
	private Date lastUpdateDtm;

	@Column(name = "FORCE_COURT_DAT")
	private Date forceCourtDat;

	@Column(name = "WIN_FLAG")
	private String winFlag;

	@Column(name = "PROVINCE_CODE")
	private String provinceCode;

	@Column(name = "NET_PENDING_VAT")
	private BigDecimal netPendingVat;

	@Column(name = "EXEMPT_TL_FLAG")
	private String exemptTlFlag;

	@Column(name = "EXEMPT_NL_FLAG")
	private String exemptNlFlag;

	@Column(name = "EXEMPT_WL_FLAG")
	private String exemptWlFlag;

	@Column(name = "STATUS_AGE")
	private Long statusAge;

	@Column(name = "ZIP_CODE")
	private String zipCode;

	@Column(name = "STATUS_REASON")
	private String statusReason;

	@Column(name = "MOBILE_STATUS")
	private String mobileStatus;

	@Column(name = "VALUE_SEGMENT")
	private String valueSegment;

	@Column(name = "DF_DAT")
	private Date dfDat;

	@Column(name = "STATUS_BEFORE")
	private String statusBefore;

	@Column(name = "BA_TITLE")
	private String baTitle;

	@Column(name = "EXCESS_MNY")
	private BigDecimal excessMny;

	@Column(name = "WAIVE_SELECTION_FLAG")
	private String waiveSelectionFlag;

	@Column(name = "INVOICE_FLAG")
	private String invoiceFlag;

	@Column(name = "GEN_DATA_DAT")
	private Date genDataDat;

	@Column(name = "CONTACT_NAME")
	private String contactName;

	@Column(name = "ADDRESS1")
	private String address1;

	@Column(name = "ADDRESS2")
	private String address2;

	@Column(name = "ORDER_TYPE")
	private String orderType;

	@Column(name = "REASON_CODE_LIST")
	private String reasonCodeList;

	@Column(name = "NEW_BILLING_ACC_NUM")
	private String newBillingAccNum;

	@Column(name = "NEW_BILLING_ACC_NAME")
	private String newBillingAccName;

	@Column(name = "OWNER_NAME")
	private String ownerName;

	@Column(name = "WL_DAT")
	private Date wlDat;

	@Column(name = "PROVINCE_NAME")
	private String provinceName;

	@Column(name = "DN_MNY")
	private BigDecimal dnMny;

	@Column(name = "CA_STATUS_DAT")
	private Date caStatusDat;

	@Column(name = "CA_MNY")
	private BigDecimal caMny;

	@Column(name = "PENALTY_MNY")
	private BigDecimal penaltyMny;

	@Column(name = "WOC_MNY")
	private BigDecimal wocMny;

	@Column(name = "CONTRACT_PHONE_FLAG")
	private String contractPhoneFlag;

	@Column(name = "CHANGE_WO_REASON_CODE")
	private String changeWoReasonCode;

	@Column(name = "CHANGE_WO_REASON_DESC")
	private String changeWoReasonDesc;

	@Column(name = "OLD_WO_TYPE")
	private String oldWoType;

	@Column(name = "OLD_WO_TYPE_REASON_CODE")
	private String oldWoTypeReasonCode;

	@Column(name = "LAST_AR_DAT")
	private Date lastArDat;

	@Column(name = "CA_STATUS")
	private String caStatus;

	@Column(name = "INV_CNT_NO_DUE")
	private Long invCntNoDue;

	@Column(name = "DEBT_MNY_NO_DUE")
	private BigDecimal debtMnyNoDue;

	@Column(name = "VIP_TYPE")
	private String vipType;

	@Column(name = "FIRST_AR_DUE_DAT")
	private Date firstArDueDat;

	@Column(name = "LAST_AR_DUE_DAT")
	private Date lastArDueDat;

	@Column(name = "ASSIGN_WL_DAT")
	private Date assignWlDat;

	@Column(name = "ASSIGN_TL_DAT")
	private Date assignTlDat;

	@Column(name = "ASSIGN_NL_DAT")
	private Date assignNlDat;

	@Column(name = "LAST_INVOICE_DAT")
	private Date lastInvoiceDat;

	@Column(name = "LAST_AR_OVER_DUE")
	private String lastArOverDue;

	@Column(name = "TERMINATE_MONTH")
	private String terminateMonth;

	@Column(name = "CREDIT_TERM")
	private Long creditTerm;

	@Column(name = "COMPANY_TYPE_ABBR")
	private String companyTypeAbbr;

	@Column(name = "SUPER_DEAL_FLAG")
	private String superDealFlag;

	@Column(name = "HANDSET_PROD_GRP")
	private String handsetProdGrp;

	@Column(name = "PAYMENT_TERM")
	private String paymentTerm;

	@Column(name = "FIX_COMPANY_TYPE_FLAG")
	private String fixCompanyTypeFlag;

	@Column(name = "NO_OF_CONTRACT")
	private Long noOfContract;

	@Column(name = "DEVICE_DISCOUNT_AMOUNT")
	private BigDecimal deviceDiscountAmount;

	@Column(name = "CONTRACT_MONTH")
	private Long contractMonth;

	@Column(name = "SERVICE_AGE")
	private Long serviceAge;

	@Column(name = "CA_DEVICE_DISCOUNT_AMOUNT")
	private BigDecimal caDeviceDiscountAmount;

	@Column(name = "CPE_FLAG")
	private String cpeFlag;

	@Column(name = "COUNT_CPE")
	private Long countCpe;

	@Column(name = "CPE_PENALTY_CHARGE")
	private BigDecimal cpePenaltyCharge;

	@Column(name = "CPE_DEVICE_PENALTY")
	private BigDecimal cpeDevicePenalty;

	@Column(name = "CA_AMOUNT")
	private BigDecimal caAmount;

	@Column(name = "DEBT_AIR_TIME_MNY")
	private BigDecimal debtAirTimeMny;

	@Column(name = "DEFAULT_INSTALLATION_PENALTY")
	private BigDecimal defaultInstallationPenalty;

	@Column(name = "DEFAULT_COUNT_CPE")
	private Long defaultCountCpe;

	@Column(name = "DEFAULT_CPE_DEVICE_PENALTY")
	private BigDecimal defaultCpeDevicePenalty;

	@Column(name = "DEFAULT_DEVICE_PENALTY")
	private BigDecimal defaultDevicePenalty;

	@Column(name = "DEVICE_PENALTY_SYSTEM")
	private String devicePenaltySystem;

	@Column(name = "CPE_DETAIL")
	private String cpeDetail;

	@Column(name = "FBB_DEVICE_PENALTY")
	private BigDecimal fbbDevicePenalty;

	@Column(name = "DEFAULT_FBB_DEVICE_PENALTY")
	private BigDecimal defaultFbbDevicePenalty;

	@Column(name = "FBB_DEVICE_PENALTY_T1")
	private BigDecimal fbbDevicePenaltyT1;

	@Column(name = "FBB_DEVICE_PENALTY_T2")
	private BigDecimal fbbDevicePenaltyT2;

	@Column(name = "DEFAULT_FBB_DEVICE_PENALTY_T1")
	private BigDecimal defaultFbbDevicePenaltyT1;

	@Column(name = "DEFAULT_FBB_DEVICE_PENALTY_T2")
	private BigDecimal defaultFbbDevicePenaltyT2;

	@Column(name = "DEBT_MNY_CASH_BASIS")
	private BigDecimal debtMnyCashBasis;

	@Column(name = "CA_MNY_CASH_BASIS")
	private BigDecimal caMnyCashBasis;
}
