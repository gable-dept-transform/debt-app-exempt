package th.co.ais.mimo.debt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import th.co.ais.mimo.debt.dto.common.CommonDropdownListDto;
import th.co.ais.mimo.debt.entity.DccGlobalParameter;

import java.util.List;

@Repository
public interface DccGlobalParameterRepo extends JpaRepository<DccGlobalParameter, String>{

    @Query(value =" SELECT DISTINCT C.KEYWORD_VALUE AS val , C.KEYWORD_VALUE||' : '||C.KEYWORD_DESC AS label " +
            "  FROM {h-schema}DCC_GLOBAL_PARAMETER C " +
            "  WHERE KEYWORD =:keyword " +
            "  AND SECTION_NAME = :sectionName" +
            "  ORDER BY C.KEYWORD_VALUE  ",nativeQuery = true)
    List<CommonDropdownListDto> getInfoByKeyWordAndSectionName(@Param("keyword") String keyword,@Param("sectionName")String sectionName) throws Exception;


}


    

