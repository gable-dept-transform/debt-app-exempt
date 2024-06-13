package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import th.co.ais.mimo.debt.exempt.entity.AccHasMobileModel;
import th.co.ais.mimo.debt.exempt.entity.AccHasMobileModelId;

public interface AccountHasMobileRepo extends JpaRepository<AccHasMobileModel, AccHasMobileModelId> {

    @Query(value = "SELECT  COUNT(A.BILL_ACCNT_NUM)" +
            "FROM {h-schema}ACCOUNT_HAS_MOBILE A, {h-schema}ACCOUNT B " +
            "WHERE A.SERVICE_NUM = :mobileNo " +
            "AND A.BILL_ACCNT_NUM = B.OU_NUM ", nativeQuery = true)
    Integer checkMobileNo(@Param("mobileNo") String mobileNo);
}
