package th.co.ais.mimo.debt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.dto.DcExempHistoryDto;
import th.co.ais.mimo.debt.dto.DcExemptDto;

import javax.persistence.*;
import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DCC_EXEMPT")
@SqlResultSetMapping(
        name = "dcExemptDtoMapping",
        classes = @ConstructorResult(
                targetClass = DcExemptDto.class,
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
                        @ColumnResult(name = "cate_code"),
                        @ColumnResult(name = "add_reason"),
                        @ColumnResult(name = "add_location",type = Integer.class),
                        @ColumnResult(name = "update_reason"),
                        @ColumnResult(name = "update_location",type = Integer.class),
                        @ColumnResult(name = "last_update_by"),
                        @ColumnResult(name = "last_update_date"),
                        @ColumnResult(name = "no_of_exempt",type = Integer.class),
                        @ColumnResult(name = "sent_interface_flag")
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
public class DccExemptModel {

    @Id
    private DccExemptModelId id;

    @Column(name = "MODULE_CODE")
    private String moduleCode;


    @Column(name = "EFFECTIVE_DAT")
    private Calendar effectiveDate;

    @Column(name = "END_DAT")
    private Calendar endDate;

    @Column(name = "EXPIRE_DAT")
    private Calendar expireDate;

    @Column(name = "CATE_CODE")
    private String cateCode;

    @Column(name = "ADD_LOCATION")
    private Integer addLocation;


    @Column(name = "UPDATE_LOCATION")
    private Integer updateLocation;


    @Column(name = "ADD_REASON")
    private String addReason;


    @Column(name = "UPDATE_REASON")
    private String updateReason;


    @Column(name = "NO_OF_EXEMPT")
    private Integer noOfExempt;

    @Column(name = "SENT_INTERFACE_FLAG")
    private String sentIntefaceFlag;


    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;

    @Column(name = "LAST_UPDATE_DTM")
    private Calendar lastUpdateDtm;

    @Column(name = "SENT_BOS_FLAG")
    private String sentBosFlag;

    @Column(name = "SO_NBR")
    private String soNbr;
}
