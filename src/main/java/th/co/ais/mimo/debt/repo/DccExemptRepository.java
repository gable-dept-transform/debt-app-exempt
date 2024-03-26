package th.co.ais.mimo.debt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import th.co.ais.mimo.debt.model.DccExemptModel;

@Repository
public interface DccExemptRepository extends JpaRepository<DccExemptModel,String> {
}
