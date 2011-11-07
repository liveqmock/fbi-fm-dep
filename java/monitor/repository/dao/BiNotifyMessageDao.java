package monitor.repository.dao;

import monitor.repository.model.BiNotifyMessage;
import monitor.repository.sql.ConstSQL;
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
public class BiNotifyMessageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BiNotifyMessage> qryLastRecords() {
        return jdbcTemplate.query(ConstSQL.QRY_LAST_NOTIFY_MSG_LIST, new BiNotifyMessageMapper());
    }

    public List<BiNotifyMessage> qryRecords(String opCode, String bankCode, String startDate, String endDate) {
        StringBuilder sqlBuilder = new StringBuilder(ConstSQL.QRY_NOTIFY_MSG_LIST);
        sqlBuilder.append(" where opcode = '").append(opCode).append("'");
        sqlBuilder.append(" and bankcode = '").append(bankCode).append("'");
        sqlBuilder.append(" and notifydate >= to_date('").append(startDate).append("','yyyy-MM-dd')");
        sqlBuilder.append(" and notifydate <= to_date('").append(endDate).append("','yyyy-MM-dd')");
        return jdbcTemplate.query(sqlBuilder.toString(), new BiNotifyMessageMapper());
    }

    private final static class BiNotifyMessageMapper implements RowMapper<BiNotifyMessage> {

        @Override
        public BiNotifyMessage mapRow(ResultSet resultSet, int i) throws SQLException {

            BiNotifyMessage notifyMessage = new BiNotifyMessage();
            notifyMessage.setBankcode(resultSet.getString("bankcode"));
            notifyMessage.setOpcode(resultSet.getString("opcode"));
            notifyMessage.setGetFlag(resultSet.getString("get_flag"));
            notifyMessage.setNotifydate(resultSet.getTimestamp("notifydate"));
            return notifyMessage;
        }
    }
}
