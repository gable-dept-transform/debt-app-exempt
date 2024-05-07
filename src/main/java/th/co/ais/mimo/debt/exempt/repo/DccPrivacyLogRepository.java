package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import th.co.ais.mimo.debt.exempt.entity.DccCalendarTransaction;
import th.co.ais.mimo.debt.exempt.entity.DccPrivacyLog;

public interface DccPrivacyLogRepository extends JpaRepository<DccPrivacyLog, String> {
    
}
