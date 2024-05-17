package th.co.ais.mimo.debt.exempt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import th.co.ais.mimo.debt.exempt.dto.CpLocationDto;
import th.co.ais.mimo.debt.exempt.entity.CpLocationMaster;

public interface CpLocationMasterRepository extends JpaRepository<CpLocationMaster, String> {

    @Query(value = " select c.location_id as locationId,c.name as name  " +
            " from {h-schema}cp_location_master c " +
            " where c.location_id IN (:ACSLocationList) " +
            " order by c.location_id ", nativeQuery = true)
    public List<CpLocationDto> getLocationListByLocationId(@Param("ACSLocationList") Integer locationIdList);

    @Query(value = " select c.location_id as locationId,c.name as name " +
            " from {h-schema}cp_location_master c " +
            " order by c.location_id ", nativeQuery = true)
    public List<CpLocationDto> getLocationList();

}
