package th.co.ais.mimo.debt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class AccHasMobileModelId  implements Serializable {

    @Column(name = "BILL_ACCNT_NUM")
    private String billAccntNum;

    @Column(name = "SERVICE_NUM")
    private String serviceNum;
}
