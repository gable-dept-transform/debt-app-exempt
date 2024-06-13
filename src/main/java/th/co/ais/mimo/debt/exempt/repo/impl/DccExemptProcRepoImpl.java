package th.co.ais.mimo.debt.exempt.repo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Date;

@Repository
public class DccExemptProcRepoImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public String callDGetNGExemSFF(String custAccNum, String billingAccNum, String mobileNum, String mode, Date startdate, Date enddate, String exemptLevel) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                //.withSchemaName("PLUGIN")
                .withFunctionName("DCCFNG_EXEMPT_SFF_ASYNC_WP")
                .withReturnValue()
                .useInParameterNames("P_PROCESS_NAME","P_CA","P_BA","P_MOBILE_NUM","P_MODE_ID","P_START_DATE","P_END_DATE","P_LEVEL")
                .declareParameters(
                        new SqlOutParameter("result", Types.VARCHAR)
                );
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("P_PROCESS_NAME", "D_GetNGExemSFF")
                .addValue("P_CA", custAccNum)
                .addValue("P_BA", billingAccNum)
                .addValue("P_MOBILE_NUM", mobileNum)
                .addValue("P_MODE_ID", mode)
                .addValue("P_START_DATE", startdate)
                .addValue("P_END_DATE", enddate)
                .addValue("P_LEVEL", exemptLevel);
        //First parameter is function output parameter type.
        return jdbcCall.executeFunction(String.class, paramMap);
    }
}
