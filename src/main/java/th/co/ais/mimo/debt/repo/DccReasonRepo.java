package th.co.ais.mimo.debt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import th.co.ais.mimo.debt.dto.common.CommonDropdownListDto;
import th.co.ais.mimo.debt.entity.DccReason;

import java.util.List;

@Repository
public interface DccReasonRepo extends JpaRepository<DccReason,String> {

    @Query(value =" select reason_code val , reason_description label\n" +
            "from {h-schema}dcc_reason\n" +
            "where reason_type = :reason_type \n" +
            "order by reason_type, reason_code ",nativeQuery = true)
    public List<CommonDropdownListDto> findByReasonType(@Param("reason_type") String reasonType);

    @Query(value =" select  reason_code AS val, reason_description AS label\n" +
            "from dcc_reason\n" +
            "where reason_code = :reason_code and reason_type = :reason_type ",nativeQuery = true)
    public List<CommonDropdownListDto> findReasonByCodeAndType(@Param("reason_code") String reasonCode,@Param("reason_type") String reasonType);

}
