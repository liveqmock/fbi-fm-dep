package monitor.repository.dao;

import monitor.repository.model.BiNotifyMsgCnt;
import monitor.repository.sql.ConstSQL;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-28
 * Time: ÏÂÎç3:55
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class BiNotifyMsgCntDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BiNotifyMsgCnt> qryRecordsCnt(String opcode, String bankcode, String startDate, String endDate) {
        StringBuilder qrySqlBuilder = new StringBuilder(ConstSQL.QRY_BI_NOTIFYMSG_CNT);
        if(StringUtils.isEmpty(opcode)) {
            qrySqlBuilder.append(" where bankcode = '").append(bankcode).append("'");
        }else if(StringUtils.isEmpty(bankcode)){
            qrySqlBuilder.append(" where opcode = '").append(opcode).append("'");
        }
        qrySqlBuilder.append(" group by opcode,bankcode ");

        if(!StringUtils.isEmpty(startDate) ) {
            qrySqlBuilder.append(" having min(notifydate) >= to_date('").append(startDate).append("','yyyy-MM-dd')");
            if(!StringUtils.isEmpty(endDate)) {
                qrySqlBuilder.append(" and max(notifydate) <= to_date('").append(endDate).append("','yyyy-MM-dd')");
            }
        }else if(!StringUtils.isEmpty(endDate)) {
            qrySqlBuilder.append(" having max(notifydate) <= to_date('").append(endDate).append("','yyyy-MM-dd')");
        }
        qrySqlBuilder.append(" order by opcode,bankcode ");

        return jdbcTemplate.query(qrySqlBuilder.toString(), new BiNotifyMsgCntMapper());
    }


    private final static class BiNotifyMsgCntMapper implements RowMapper<BiNotifyMsgCnt> {

        @Override
        public BiNotifyMsgCnt mapRow(ResultSet resultSet, int i) throws SQLException {

            BiNotifyMsgCnt nmCnt = new BiNotifyMsgCnt();
            nmCnt.setBankcode(resultSet.getString("bankcode"));
            nmCnt.setOpcode(resultSet.getString("opcode"));
            nmCnt.setCount(resultSet.getLong("count"));
            return nmCnt;
        }
    }
}
