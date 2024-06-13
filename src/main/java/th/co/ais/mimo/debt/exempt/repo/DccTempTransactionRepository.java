package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import th.co.ais.mimo.debt.exempt.entity.DccTempTransaction;
import th.co.ais.mimo.debt.exempt.entity.DccTempTransactionId;

@Repository
public interface DccTempTransactionRepository extends JpaRepository<DccTempTransaction, DccTempTransactionId> {

}
