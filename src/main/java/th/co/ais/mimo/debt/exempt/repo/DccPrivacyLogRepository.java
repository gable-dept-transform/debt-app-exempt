package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import th.co.ais.mimo.debt.exempt.entity.DccPrivacyLog;
import th.co.ais.mimo.debt.exempt.entity.DccPrivacyLogId;

@Repository
public interface DccPrivacyLogRepository extends JpaRepository<DccPrivacyLog, DccPrivacyLogId> {
    
}
