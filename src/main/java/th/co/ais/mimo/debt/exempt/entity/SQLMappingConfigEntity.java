package th.co.ais.mimo.debt.exempt.entity;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Embeddable;
import jakarta.persistence.SqlResultSetMapping;
import th.co.ais.mimo.debt.exempt.dto.*;

import java.math.BigDecimal;
import java.util.Date;


@SqlResultSetMapping(
        name = "dcExemptCurrentDtoMapping",
        classes = @ConstructorResult(
                targetClass = DcExemptCurrentDto.class,
                columns = {
                        @ColumnResult(name = "custAccNum"),
                        @ColumnResult(name = "billingAccNum"),
                        @ColumnResult(name = "mobileNum"),
                        @ColumnResult(name = "moduleCode"),
                        @ColumnResult(name = "modeId"),
                        @ColumnResult(name = "exemptLevel"),
                        @ColumnResult(name = "billingAccName"),
                        @ColumnResult(name = "effectiveDate"),
                        @ColumnResult(name = "endDate"),
                        @ColumnResult(name = "expireDate"),
                        @ColumnResult(name = "cateCode"),
                        @ColumnResult(name = "addReason"),
                        @ColumnResult(name = "addLocation",type = Integer.class),
                        @ColumnResult(name = "updateReason"),
                        @ColumnResult(name = "updateLocation",type = Integer.class),
                        @ColumnResult(name = "lastUpdateBy"),
                        @ColumnResult(name = "lastUpdateDate"),
                        @ColumnResult(name = "noOfExempt",type = Integer.class),
                        @ColumnResult(name = "sentInterFaceFlag"),
                        @ColumnResult(name = "mobileStatus"),
                        @ColumnResult(name = "rownumber",type = Integer.class)
                }))

@SqlResultSetMapping(
        name = "dcExemptHistoryDtoMapping",
        classes = @ConstructorResult(
                targetClass = DcExempHistoryDto.class,
                columns = {
                        @ColumnResult(name = "cust_acc_num"),
                        @ColumnResult(name = "billing_acc_num"),
                        @ColumnResult(name = "mobile_num"),
                        @ColumnResult(name = "module_code"),
                        @ColumnResult(name = "mode_id"),
                        @ColumnResult(name = "exempt_level"),
                        @ColumnResult(name = "billing_acc_name"),
                        @ColumnResult(name = "effective_dat"),
                        @ColumnResult(name = "end_date"),
                        @ColumnResult(name = "expire_date"),
                        @ColumnResult(name = "add_reason"),
                        @ColumnResult(name = "add_location",type = Integer.class),
                        @ColumnResult(name = "update_reason"),
                        @ColumnResult(name = "update_location",type = Integer.class),
                        @ColumnResult(name = "last_update_by"),
                        @ColumnResult(name = "last_update_date"),
                        @ColumnResult(name = "no_of_exempt",type = Integer.class),
                        @ColumnResult(name = "exempt_seq",type = Integer.class),
                        @ColumnResult(name = "cate_code"),
                        @ColumnResult(name = "action_type"),
                        @ColumnResult(name = "mobile_status"),
                        @ColumnResult(name = "rownumber",type = Integer.class)
                }))


@SqlResultSetMapping(
        name = "billAccDtoMapping",
        classes = @ConstructorResult(
                targetClass = BillingAccDto.class,
                columns = {
                        @ColumnResult(name = "bill_acc_num"),
                        @ColumnResult(name = "bill_name"),
                        @ColumnResult(name = "mobile_status"),
                }))

@SqlResultSetMapping(
        name = "searchTreatmentDtoMapping",
        classes = @ConstructorResult(
                targetClass = SearchTreatmentDto.class,
                columns = {
                        @ColumnResult(name = "cust_acc_num",type = String.class),
                        @ColumnResult(name = "billing_acc_num",type = String.class),
                        @ColumnResult(name = "name",type = String.class),
                        @ColumnResult(name = "ba_status",type = String.class),
                        @ColumnResult(name = "service_num",type = String.class),
                        @ColumnResult(name = "mobile_status",type = String.class),
                        @ColumnResult(name = "x_status_date",type = Date.class),
                        @ColumnResult(name = "woc_flag",type = String.class),
                        @ColumnResult(name = "limit_mny",type = BigDecimal.class),
                        @ColumnResult(name = "company_code",type = String.class),
                        @ColumnResult(name = "customer_id",type = String.class),
                        @ColumnResult(name = "x_id_type",type = String.class),
                        @ColumnResult(name = "bill_style",type = String.class),
                        @ColumnResult(name = "wht_req",type = String.class),
                        @ColumnResult(name = "payment_method",type = String.class),
                        @ColumnResult(name = "paymt_term",type = String.class),
                        @ColumnResult(name = "bill_cycle",type = String.class),
                        @ColumnResult(name = "credit_term",type = String.class),
                        @ColumnResult(name = "debt_mny",type = BigDecimal.class),
                        @ColumnResult(name = "account_name",type = String.class),
                        @ColumnResult(name = "sa_num",type = String.class),
                        @ColumnResult(name = "sa_status",type = String.class),
                        @ColumnResult(name = "company_type",type = String.class),
                        @ColumnResult(name = "ca_name",type = String.class),
                        @ColumnResult(name = "main_promo",type = String.class),
                        @ColumnResult(name = "no_print_bill_flag",type = String.class),
                        @ColumnResult(name = "sales_rep",type = String.class),
                        @ColumnResult(name = "sales_rep_mst",type = String.class),
                        @ColumnResult(name = "sales_rep_mobile",type = String.class),
                        @ColumnResult(name = "collection_segment",type = String.class)
                }))

@SqlResultSetMapping(
        name = "searchTreatmentMobileDtoMapping",
        classes = @ConstructorResult(
                targetClass = SearchTreatmentDto.class,
                columns = {
                        @ColumnResult(name = "cust_acc_num",type = String.class),
                        @ColumnResult(name = "billing_acc_num",type = String.class),
                        @ColumnResult(name = "name",type = String.class),
                        @ColumnResult(name = "ba_status",type = String.class),
                        @ColumnResult(name = "service_num",type = String.class),
                        @ColumnResult(name = "mobile_status",type = String.class),
                }))

@SqlResultSetMapping(
        name = "searchExemptDetailDtoMapping",
        classes = @ConstructorResult(
                targetClass = ExemptDetailDto.class,
                columns = {
                        @ColumnResult(name = "cust_acc_num",type = String.class),
                        @ColumnResult(name = "billing_acc_num",type = String.class),
                        @ColumnResult(name = "mobile_num",type = String.class),
                        @ColumnResult(name = "module_code",type = String.class),
                        @ColumnResult(name = "mode_id",type = String.class),
                        @ColumnResult(name = "exempt_level",type = String.class),
                        @ColumnResult(name = "billing_acc_name",type = String.class),
                        @ColumnResult(name = "effective_date",type = String.class),
                        @ColumnResult(name = "end_date",type = String.class),
                        @ColumnResult(name = "expire_date",type = String.class),
                        @ColumnResult(name = "cate_code",type = String.class),
                        @ColumnResult(name = "add_reason",type = String.class),
                        @ColumnResult(name = "add_location",type = String.class),
                        @ColumnResult(name = "update_reason",type = String.class),
                        @ColumnResult(name = "update_location",type = String.class),
                        @ColumnResult(name = "last_update_by",type = String.class),
                        @ColumnResult(name = "last_update_date",type = String.class),
                        @ColumnResult(name = "no_of_exempt",type = Long.class),
                        @ColumnResult(name = "sent_interface_flag",type = String.class),
                        @ColumnResult(name = "rownumber",type = Long.class)
                }))


@SqlResultSetMapping(
        name = "searchExemptCateMasterDtoMapping",
        classes = @ConstructorResult(
                targetClass = DccExemptCateMaster.class,
                columns = {
                        @ColumnResult(name = "cate_code",type = String.class),
                        @ColumnResult(name = "cate_description",type = String.class),
                        @ColumnResult(name = "exempt_reason",type = String.class),
                        @ColumnResult(name = "active_flag",type = String.class),
                        @ColumnResult(name = "last_update_by",type = String.class),
                        @ColumnResult(name = "last_update_dtm",type = String.class)
                }))

@SqlResultSetMapping(
        name = "searchExemptCateDetailDtoMapping",
        classes = @ConstructorResult(
                targetClass = DccExemptCateDetail.class,
                columns = {
                        @ColumnResult(name = "mode_id",type = String.class),
                        @ColumnResult(name = "module_code",type = String.class),
                        @ColumnResult(name = "exempt_duration",type = Long.class),
                        @ColumnResult(name = "expire_date",type = String.class),
                        @ColumnResult(name = "last_update_by",type = String.class),
                        @ColumnResult(name = "last_update_dtm",type = String.class),
                        @ColumnResult(name = "exempt_level",type = String.class)
                }))





@Embeddable
public class SQLMappingConfigEntity {
}