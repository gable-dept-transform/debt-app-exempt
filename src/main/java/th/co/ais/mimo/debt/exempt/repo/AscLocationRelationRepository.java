package th.co.ais.mimo.debt.exempt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import th.co.ais.mimo.debt.exempt.entity.AscLocationRelation;

public interface AscLocationRelationRepository extends JpaRepository<AscLocationRelation, Integer> {

    @Query(value = " SELECT DISTINCT D.LOCATION_ID" +
            " from {h-schema}ACS_USER A, {h-schema}ACS_USER_GROUP_LOCATION B, {h-schema}ACS_GROUP_LOCATION C, " +
            " {h-schema}ACS_LOCATION_RELATION D, {h-schema}CP_LOCATION_MASTER E" +
            " where A.USER_ID = B.USER_ID AND B.GROUP_ID = C.GROUP_ID " +
            " AND C.GROUP_ID = D.GROUP_ID AND D.LOCATION_ID = E.LOCATION_ID AND UPPER(A.USER_ID) = UPPER(:userName) ", nativeQuery = true)
    public Integer getAscLocationList(@Param("userName") String userName);
}
