package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import th.co.ais.mimo.debt.exempt.entity.DccBosExempt;
import th.co.ais.mimo.debt.exempt.entity.DccBosExemptId;

public interface DccExemptBosRepo extends JpaRepository<DccBosExempt, DccBosExemptId> {
}
