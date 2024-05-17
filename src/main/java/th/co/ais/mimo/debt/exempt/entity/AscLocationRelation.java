package th.co.ais.mimo.debt.exempt.entity;


import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ACS_LOCATION_RELATION")
public class AscLocationRelation implements Serializable {

    @Id
    @Column(name = "LOCATION_ID", nullable = false)
    private Integer locationId;

    @Column(name = "GROUP_ID", nullable = false)
    private String groupId;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    // Constructors, getters, and setters
}

