package th.co.ais.mimo.debt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import th.co.ais.mimo.debt.dto.DcExemptCurrentDtoMapping;
import th.co.ais.mimo.debt.entity.DccExemptModel;

import java.util.List;

@Repository
public interface DccExemptRepository extends JpaRepository<DccExemptModel,String> {
}
