package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import th.co.ais.mimo.debt.exempt.dto.CommonDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.DistrictDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.ProvinceDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.SubDistrictDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.ZipCodeDropdownListDto;
import th.co.ais.mimo.debt.exempt.entity.DccGlobalParameter;
import th.co.ais.mimo.debt.exempt.model.AddReasonDto;
import th.co.ais.mimo.debt.exempt.model.CategoryDto;
import th.co.ais.mimo.debt.exempt.model.CollectionSegmentDto;
import th.co.ais.mimo.debt.exempt.model.CommonDropdownDto;
import th.co.ais.mimo.debt.exempt.model.SubCategoryDto;

import java.util.List;

@Repository
public interface DccGlobalParameterRepo extends JpaRepository<DccGlobalParameter, String> {

	@Query(value = " SELECT DISTINCT C.KEYWORD_VALUE AS val , C.KEYWORD_VALUE||' : '||C.KEYWORD_DESC AS label "
			+ "  FROM {h-schema}DCC_GLOBAL_PARAMETER C " + "  WHERE KEYWORD =:keyword "
			+ "  AND SECTION_NAME = :sectionName" + "  ORDER BY C.KEYWORD_VALUE  ", nativeQuery = true)
	List<CommonDropdownListDto> getInfoByKeyWordAndSectionName(@Param("keyword") String keyword,
			@Param("sectionName") String sectionName) throws Exception;

	@Query(value = "select keyword_value AS val ,keyword_desc AS label\n" + "from {h-schema}dcc_global_parameter\n"
			+ "where section_name = :sectionName and keyword = :keyword and keyword_value <> :keywordValue\n  ", nativeQuery = true)
	List<CommonDropdownListDto> getInfoByKeyWordAndSectionNameIgnoreKeyword(@Param("keyword") String keyword,
			@Param("sectionName") String sectionName, @Param("keywordValue") String keywordValue) throws Exception;

	@Query(value = "    select keyword_value AS val ,keyword_desc AS label\n" + "    from {h-schema}dcc_global_parameter\n"
			+ "    where section_name = :sectionName and keyword = :keyword\n"
			+ "    and ( :touchPointModeList  is null or instr(','||:touchPointModeList ||',',','||keyword_value||',' ) > 0 ) ", nativeQuery = true)
	List<CommonDropdownListDto> getInfoByKeyWordAndSectionNameIncludeKeyword(@Param("keyword") String keyword,
			@Param("sectionName") String sectionName, @Param("touchPointModeList") String touchPointModeList)
			throws Exception;

	@Query(value = " SELECT DISTINCT C.KEYWORD_VALUE AS val , C.KEYWORD_DESC AS LABEL "
			+ "  FROM {h-schema}DCC_GLOBAL_PARAMETER C " + "  WHERE  SECTION_NAME = :sectionName", nativeQuery = true)
	List<CommonDropdownListDto> getInfoBySectionName(@Param("sectionName") String sectionName) throws Exception;

	@Query(value = " SELECT DISTINCT C.KEYWORD_VALUE AS val , KEYWORD_VALUE||' : '||KEYWORD_DESC AS LABEL "
			+ "  FROM {h-schema}DCC_GLOBAL_PARAMETER C " + "  WHERE  SECTION_NAME = :sectionName", nativeQuery = true)
	List<CommonDropdownListDto> getInfoBySectionNameCaseMulti(@Param("sectionName") String sectionName)
			throws Exception;

	@Query(value = " SELECT DISTINCT C.KEYWORD_VALUE AS val , C.KEYWORD_DESC AS label "
			+ "  FROM {h-schema}DCC_GLOBAL_PARAMETER C " + "  WHERE KEYWORD =:keyword "
			+ "  AND SECTION_NAME = :sectionName" + "  ORDER BY C.KEYWORD_VALUE  ", nativeQuery = true)
	List<CommonDropdownListDto> getInfoByKeyWordAndSectionNameCaseDropdown(@Param("keyword") String keyword,
			@Param("sectionName") String sectionName) throws Exception;

	@Query(value = "  SELECT distinct keyword_desc AS label, keyword_value AS val "
			+ " FROM dcc_global_parameter WHERE SECTION_NAME  = :sectionName AND KEYWORD = :keyWord ", nativeQuery = true)
	List<CommonDropdownListDto> getRegionInfoCaseDropdown(@Param("sectionName")String sectionName, @Param("keyWord")String keyWord) throws Exception;

	@Query(value = "  SELECT DISTINCT(L.LOV_NAME) AS val, L.DISPLAY_VAL AS label , L.LOV_VAL1 AS lovVal1 "
			+ " FROM SFF_LOV_MASTER L " + " WHERE L.LOV_TYPE = 'PROVINCE' "
			+ " AND INSTR (:provinveCodeList , L.LOV_VAL5) > 0 " + " ORDER BY L.LOV_VAL1 DESC ", nativeQuery = true)
	List<ProvinceDropdownListDto> getProvinceInfoCaseDropdown(@Param("provinveCodeList")String provinveCodeList) throws Exception;

	@Query(value = "  SELECT DISTINCT(L.LOV_NAME) AS val, L.DISPLAY_VAL AS label , L.LOV_VAL1 AS lovVal1 "
			+ " FROM SFF_LOV_MASTER L " + " WHERE L.LOV_TYPE = 'PROVINCE' "
			+ " ORDER BY L.LOV_VAL1 DESC ", nativeQuery = true)
	List<ProvinceDropdownListDto> getProvinceAllInfoCaseDropdown() throws Exception;

	@Query(value = " SELECT DISTINCT(CC.CITY) AS city, L.DISPLAY_VAL AS dispVal ,CC.X_PROVINCE AS province"
			+ "  FROM ZIPCODE CC, SFF_LOV_MASTER L " + "  WHERE CC.X_PROVINCE = L.LOV_NAME "
			+ "  AND L.LOV_TYPE = 'PROVINCE' "
			+ "  AND DCCU_UTIL.FIND_LIKE_LIST(:provinveCodeList,CC.X_PROVINCE,'F' )> 0 "
			+ "  ORDER BY CC.X_PROVINCE, CC.CITY ", nativeQuery = true)
	List<DistrictDropdownListDto> getdistrictInfoCaseDropdown(@Param("provinveCodeList")String provinveCodeList) throws Exception;

	@Query(value = " SELECT DISTINCT(CC.CITY) AS city,L.DISPLAY_VAL AS dispVal ,CC.X_PROVINCE AS province"
			+ "  FROM ZIPCODE CC, SFF_LOV_MASTER L " + "  WHERE CC.X_PROVINCE = L.LOV_NAME "
			+ "  AND L.LOV_TYPE = 'PROVINCE' " + "  ORDER BY CC.X_PROVINCE, CC.CITY ", nativeQuery = true)
	List<DistrictDropdownListDto> getdistrictAllInfoCaseDropdown() throws Exception;

	@Query(value = " SELECT DISTINCT(CC.TUMBOL) AS tumbol,L.DISPLAY_VAL AS dispVal, CC.CITY AS city, CC.X_PROVINCE AS province"
			+ " FROM ZIPCODE CC, SFF_LOV_MASTER L " + " WHERE CC.X_PROVINCE = L.LOV_NAME "
			+ " AND L.LOV_TYPE = 'PROVINCE' " + " AND DCCU_UTIL.FIND_LIKE_LIST(:aumphurCodeList,CC.CITY ,'F' )> 0 "
			+ " ORDER BY CC.X_PROVINCE,CC.CITY,CC.TUMBOL ", nativeQuery = true)
	List<SubDistrictDropdownListDto> getSubDistrictInfoCaseDropdown(@Param("aumphurCodeList")String aumphurCodeList) throws Exception;

	@Query(value = " SELECT DISTINCT(CC.TUMBOL) AS tumbol,L.DISPLAY_VAL AS dispVal, CC.CITY AS city, CC.X_PROVINCE AS province"
			+ " FROM ZIPCODE CC, SFF_LOV_MASTER L " + " WHERE CC.X_PROVINCE = L.LOV_NAME "
			+ " AND L.LOV_TYPE = 'PROVINCE' " + " ORDER BY CC.X_PROVINCE,CC.CITY,CC.TUMBOL ", nativeQuery = true)
	List<SubDistrictDropdownListDto> getAllSubDistrictInfoCaseDropdown() throws Exception;

	@Query(value = " SELECT DISTINCT(Z.CONTINENT) AS zipCode, Z.CITY AS city" + " FROM ZIPCODE Z "
			+ " WHERE DCCU_UTIL.FIND_LIKE_LIST(:cityCodeList ,Z.CITY,'F') > 0 ", nativeQuery = true)
	List<ZipCodeDropdownListDto> getZipCodeInfoCaseDropdown(@Param("cityCodeList")String cityCodeList) throws Exception;

	@Query(value = " SELECT DISTINCT(Z.CONTINENT) AS zipCode, Z.CITY AS city" + " FROM ZIPCODE Z ", nativeQuery = true)
	List<ZipCodeDropdownListDto> getZipCodeAllInfoCaseDropdown() throws Exception;

	@Query(value = " select DISTINCT(keyword_desc) AS keywordDesc, keyword_value AS keywordValue, last_update_by AS lastUpdateBy, "
			+ " last_update_dtm AS lastUpdateDtm,SECTION_NAME AS sectionName ,KEYWORD AS keyword"
			+ " from dcc_global_parameter " + " where section_name = 'CRITERIA' "
			+ " and keyword like 'COLLECTION_SEGMENT%' " + " order by keyword_value ", nativeQuery = true)
	List<CollectionSegmentDto> getCollectionSegment() throws Exception;

	@Query(value = " select DISTINCT(keyword_desc) AS keywordDesc, keyword_value AS keywordValue, last_update_by AS lastUpdateBy, "
			+ " last_update_dtm AS lastUpdateDtm ,SECTION_NAME AS sectionName ,KEYWORD AS keyword"
			+ " from dcc_global_parameter " + " where section_name = 'CRITERIA' " + " and keyword = 'BA_STATUS' "
			+ " order by keyword_value ", nativeQuery = true)
	List<CommonDropdownDto> getBastatus() throws Exception;

	@Query(value = " select DISTINCT(keyword_desc) AS keywordDesc, keyword_value AS keywordValue, last_update_by AS lastUpdateBy, "
			+ " last_update_dtm AS lastUpdateDtm ,SECTION_NAME AS sectionName ,KEYWORD AS keyword"
			+ " from dcc_global_parameter " + " where section_name = 'CRITERIA' " + " and keyword = 'MOBILE_STATUS' "
			+ " order by keyword_value ", nativeQuery = true)
	List<CommonDropdownDto> getMobilestatus() throws Exception;

	@Query(value = " select DISTINCT(keyword_desc) AS keywordDesc, keyword_value AS keywordValue, last_update_by AS lastUpdateBy, "
			+ " last_update_dtm AS lastUpdateDtm ,SECTION_NAME AS sectionName ,KEYWORD AS keyword"
			+ " from dcc_global_parameter " + " where section_name = 'CRITERIA' " + " and keyword = 'MODULE_CODE' "
			+ " order by keyword_value ", nativeQuery = true)
	List<CommonDropdownDto> getModule() throws Exception;

	@Query(value = " select DISTINCT(keyword_desc) AS keywordDesc, keyword_value AS keywordValue, last_update_by AS lastUpdateBy, "
			+ " last_update_dtm AS lastUpdateDtm ,SECTION_NAME AS sectionName ,KEYWORD AS keyword"
			+ " from dcc_global_parameter " + " where section_name = 'CRITERIA' " + " and keyword = 'EXEMPT_LEVEL' "
			+ " order by keyword_value ", nativeQuery = true)
	List<CommonDropdownDto> getExemptLevel() throws Exception;

	@Query(value = " select DISTINCT(keyword_desc) AS keywordDesc, keyword_value AS keywordValue, last_update_by AS lastUpdateBy, "
			+ " last_update_dtm AS lastUpdateDtm ,SECTION_NAME AS sectionName ,KEYWORD AS keyword"
			+ " from dcc_global_parameter "
			+ " where section_name = 'EXEMPT_MODE' and keyword = :module " , nativeQuery = true)
	List<CommonDropdownDto> getMode(@Param("module")String module) throws Exception;

	@Query(value = " select DISTINCT(keyword_desc) AS keywordDesc, keyword_value AS keywordValue, last_update_by AS lastUpdateBy, "
			+ "	last_update_dtm AS lastUpdateDtm ,SECTION_NAME AS sectionName ,KEYWORD AS keyword "
			+ " from dcc_global_parameter "
			+ " where section_name = 'CRITERIA' "
			+ " and keyword = 'BILLCYCLE' "
			+ " and ('' IS NULL OR instr('',KEYWORD_VALUE) >0 ) "
			+ " order by keyword_value " , nativeQuery = true)
	List<CommonDropdownDto> getBillCycle() throws Exception;

	@Query(value = " select reason_code AS reasonCode, reason_type AS reasonType, reason_subtype AS reasonSubtype, reason_description AS reasonDescription, last_update_by AS lastUpdateBy, "
			+ " last_update_dtm AS lastUpdateDtm from dcc_reason "
			+ " where reason_type = 'EXEMPT_ADD' and reason_subtype like 'EXEMPT_ADD' order by reason_type, reason_code " , nativeQuery = true)
	List<AddReasonDto> getReason() throws Exception;

	@Query(value = " select lov_name AS name, s.display_val AS val "
			+ " from SFF_LOV_MASTER s "
			+ " where  s.LOV_TYPE = 'ACCOUNT_CATEGORY' "
			+ " and s.active_flg = 'Y' "
			+ " order by lov_name " , nativeQuery = true)
	List<CategoryDto> getCategory() throws Exception;

	@Query(value = " select sm.lov_name AS name,sm.display_val AS display, s.lov_name || '/' || sm.lov_name AS val "
			+ " from sff_lov_master s,sff_lov_master sm  "
			+ " where s.row_id = sm.par_row_id  "
			+ " and s.lov_type = 'ACCOUNT_CATEGORY' "
			+ " and sm.lov_type = 'ACCOUNT_SUBCATEGORY' "
			+ " and sm.active_flg = 'Y' "
			+ " and s.active_flg = 'Y' "
			+ " and s.lov_name = :category "
			+ " order by sm.lov_name ", nativeQuery = true)
	List<SubCategoryDto> getSubCategory(@Param("category")String category) throws Exception;

	@Query(value = " select DISTINCT(keyword_desc) AS keywordDesc, keyword_value AS keywordValue, last_update_by AS lastUpdateBy, "
			+ "	last_update_dtm AS lastUpdateDtm ,SECTION_NAME AS sectionName ,KEYWORD AS keyword "
			+ " from dcc_global_parameter "
			+ " where section_name = 'CRITERIA' and keyword = 'COMPANY_CODE'  "
			+ " and instr((select company_code from dcc_criteria_history "
			+ " where mode_id = 'EM' and criteria_id = 8 and criteria_type = 'LOADTEXT' ), :companyCode) > 0 "
			+ " ORDER BY KEYWORD_VALUE ", nativeQuery = true)
	List<CommonDropdownDto> getCompanyByCode(@Param("companyCode")String companyCode) throws Exception;
}
