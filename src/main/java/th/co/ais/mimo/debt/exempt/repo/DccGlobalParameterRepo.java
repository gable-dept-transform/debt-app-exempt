package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import th.co.ais.mimo.debt.exempt.dto.CommonDropdownListDto;
import th.co.ais.mimo.debt.exempt.entity.DccGlobalParameter;

import java.util.List;

@Repository
public interface DccGlobalParameterRepo extends JpaRepository<DccGlobalParameter, String>{

    @Query(value =" SELECT DISTINCT C.KEYWORD_VALUE AS val , C.KEYWORD_VALUE||' : '||C.KEYWORD_DESC AS label " +
            "  FROM {h-schema}DCC_GLOBAL_PARAMETER C " +
            "  WHERE KEYWORD =:keyword " +
            "  AND SECTION_NAME = :sectionName" +
            "  ORDER BY C.KEYWORD_VALUE  ",nativeQuery = true)
    List<CommonDropdownListDto> getInfoByKeyWordAndSectionName(@Param("keyword") String keyword, @Param("sectionName")String sectionName) throws Exception;

    @Query(value ="select keyword_value AS val ,keyword_desc AS label\n" +
            "from {h-schema}dcc_global_parameter\n" +
            "where section_name = :sectionName and keyword = :keyword and keyword_value <> :keywordValue\n  ",nativeQuery = true)
    List<CommonDropdownListDto> getInfoByKeyWordAndSectionNameIgnoreKeyword(@Param("keyword") String keyword,@Param("sectionName")String sectionName,@Param("keywordValue")String keywordValue) throws Exception;

    @Query(value ="    select keyword_value AS val ,keyword_desc AS label\n" +
            "    from {h-schema}dcc_global_parameter\n" +
            "    where section_name = :sectionName and keyword = :keyword\n" +
            "    and ( :touchPointModeList  is null or instr(','||:touchPointModeList ||',',','||keyword_value||',' ) > 0 ) ",nativeQuery = true)
    List<CommonDropdownListDto> getInfoByKeyWordAndSectionNameIncludeKeyword(@Param("keyword") String keyword,@Param("sectionName")String sectionName,@Param("touchPointModeList")String touchPointModeList) throws Exception;





}


    

