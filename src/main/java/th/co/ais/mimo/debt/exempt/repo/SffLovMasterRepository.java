package th.co.ais.mimo.debt.exempt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import th.co.ais.mimo.debt.exempt.dto.CommonCheckListDto;
import th.co.ais.mimo.debt.exempt.entity.SffLovMaster;

public interface SffLovMasterRepository extends JpaRepository<SffLovMaster, String> {

        @Query(value = " select s.lov_name name,s.display_val val, s.lov_name||' : '||s.display_val AS label" +
                        "  FROM {h-schema}SFF_LOV_MASTER s " +
                        "  where  s.LOV_TYPE = :lovType" +
                        " and s.active_flg = 'Y'  " +
                        " order by s.lov_name ", nativeQuery = true)
        List<CommonCheckListDto> getLovByLovType(@Param("lovType") String lovType) throws Exception;

        @Query(value = " select " +
                        "s.lov_name || ' / ' || sm.lov_name || ' : ' || sm.display_val AS label, " +
                        "s.lov_name || '/' || sm.lov_name as name, " +
                        "sm.display_val val " +
                        "  FROM {h-schema}SFF_LOV_MASTER s, {h-schema}sff_lov_master sm  " +
                        "  where s.row_id = sm.par_row_id  " +
                        " and s.lov_type = :lovType " +
                        " and sm.lov_type = :subLovType " +
                        " and s.active_flg = 'Y'  " +
                        " and sm.active_flg = 'Y' " +
                        " and s.lov_name = :category " +
                        " order by sm.lov_name ", nativeQuery = true)
        List<CommonCheckListDto> getSubLovByLovTypeandSublovTypeandCategory(@Param("lovType") String lovType,
                        @Param("subLovType") String subLovType, @Param("category") String category) throws Exception;
}