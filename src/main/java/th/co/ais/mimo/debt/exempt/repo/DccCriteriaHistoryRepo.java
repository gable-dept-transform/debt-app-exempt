package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import th.co.ais.mimo.debt.exempt.entity.DccCriteriaHistory;
import th.co.ais.mimo.debt.exempt.entity.DccCriteriaHistoryId;

@Repository
public interface DccCriteriaHistoryRepo extends JpaRepository<DccCriteriaHistory, DccCriteriaHistoryId> {


}
