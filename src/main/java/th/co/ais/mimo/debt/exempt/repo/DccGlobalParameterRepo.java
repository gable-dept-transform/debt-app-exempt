package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import th.co.ais.mimo.debt.exempt.dto.CommonDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.ProvinceDropdownListDto;
import th.co.ais.mimo.debt.exempt.entity.DccGlobalParameter;

import java.util.List;

@Repository
public interface DccGlobalParameterRepo extends JpaRepository<DccGlobalParameter, String> {

        @Query(value = " SELECT DISTINCT C.KEYWORD_VALUE AS val , C.KEYWORD_VALUE||' : '||C.KEYWORD_DESC AS label " +
                        "  FROM {h-schema}DCC_GLOBAL_PARAMETER C " +
                        "  WHERE KEYWORD =:keyword " +
                        "  AND SECTION_NAME = :sectionName" +
                        "  ORDER BY C.KEYWORD_VALUE  ", nativeQuery = true)
        List<CommonDropdownListDto> getInfoByKeyWordAndSectionName(@Param("keyword") String keyword,
                        @Param("sectionName") String sectionName) throws Exception;

        @Query(value = "select keyword_value AS val ,keyword_desc AS label\n" +
                        "from dcc_global_parameter\n" +
                        "where section_name = :sectionName and keyword = :keyword and keyword_value <> :keywordValue\n  ", nativeQuery = true)
        List<CommonDropdownListDto> getInfoByKeyWordAndSectionNameIgnoreKeyword(@Param("keyword") String keyword,
                        @Param("sectionName") String sectionName, @Param("keywordValue") String keywordValue)
                        throws Exception;

        @Query(value = "    select keyword_value AS val ,keyword_desc AS label\n" +
                        "    from dcc_global_parameter\n" +
                        "    where section_name = :sectionName and keyword = :keyword\n" +
                        "    and ( :touchPointModeList  is null or instr(','||:touchPointModeList ||',',','||keyword_value||',' ) > 0 ) ", nativeQuery = true)
        List<CommonDropdownListDto> getInfoByKeyWordAndSectionNameIncludeKeyword(@Param("keyword") String keyword,
                        @Param("sectionName") String sectionName,
                        @Param("touchPointModeList") String touchPointModeList) throws Exception;

        @Query(value = " SELECT DISTINCT C.KEYWORD_VALUE AS val , C.KEYWORD_DESC AS LABEL " +
                        "  FROM {h-schema}DCC_GLOBAL_PARAMETER C " +
                        "  WHERE  SECTION_NAME = :sectionName", nativeQuery = true)
        List<CommonDropdownListDto> getInfoBySectionName(@Param("sectionName") String sectionName) throws Exception;

        @Query(value = " SELECT DISTINCT C.KEYWORD_VALUE AS val , KEYWORD_VALUE||' : '||KEYWORD_DESC AS LABEL " +
                        "  FROM {h-schema}DCC_GLOBAL_PARAMETER C " +
                        "  WHERE  SECTION_NAME = :sectionName", nativeQuery = true)
        List<CommonDropdownListDto> getInfoBySectionNameCaseMulti(@Param("sectionName") String sectionName) throws Exception;

        @Query(value = " SELECT DISTINCT C.KEYWORD_VALUE AS val , C.KEYWORD_DESC AS label " +
                        "  FROM {h-schema}DCC_GLOBAL_PARAMETER C " +
                        "  WHERE KEYWORD =:keyword " +
                        "  AND SECTION_NAME = :sectionName" +
                        "  ORDER BY C.KEYWORD_VALUE  ", nativeQuery = true)
        List<CommonDropdownListDto> getInfoByKeyWordAndSectionNameCaseDropdown(@Param("keyword") String keyword,
                        @Param("sectionName") String sectionName) throws Exception;
        
        @Query(value = "  SELECT distinct keyword_desc AS label, keyword_value AS val "+
        			" FROM dcc_global_parameter WHERE SECTION_NAME  = :sectionName AND KEYWORD = :keyWord ",nativeQuery = true)
        List<CommonDropdownListDto> getRegionInfoCaseDropdown(String sectionName, String keyWord) throws Exception;
        
        @Query(value = "  SELECT DISTINCT(L.LOV_NAME) AS val, L.DISPLAY_VAL AS label , L.LOV_VAL1 AS lovVal1 "
        		+ " FROM SFF_LOV_MASTER L "
        		+ " WHERE L.LOV_TYPE = 'PROVINCE' "
        		+ " AND INSTR (:provinveCodeList , L.LOV_VAL5) > 0 "
        		+ " ORDER BY L.LOV_VAL1 DESC ",nativeQuery = true)
    List<ProvinceDropdownListDto> getProvinceInfoCaseDropdown(String provinveCodeList) throws Exception;
        
        @Query(value = "  SELECT DISTINCT(L.LOV_NAME) AS val, L.DISPLAY_VAL AS label , L.LOV_VAL1 AS lovVal1 "
        		+ " FROM SFF_LOV_MASTER L "
        		+ " WHERE L.LOV_TYPE = 'PROVINCE' "
        		+ " ORDER BY L.LOV_VAL1 DESC ",nativeQuery = true)
    List<ProvinceDropdownListDto> getProvinceAllInfoCaseDropdown() throws Exception;
        

}
