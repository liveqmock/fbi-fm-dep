package dep.repository.dao;

import dep.common.SQLConst;
import dep.repository.model.BiNotifyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-6
 * Time: ÉÏÎç11:01
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class BiNotifyMsgDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public int insertRecord(BiNotifyMessage record) {
        //row_id , opcode , bankcode, notifydate, get_flag, created_date, last_upd_date
         Object[] values = new Object[] {record.getRowId(), record.getOpcode(), record.getBankcode(),
                                         record.getNotifydate(), record.getGetFlag(), record.getCreatedDate(),
                                         record.getLastUpdDate()};
         return jdbcTemplate.update(SQLConst.INSERT_BINOTIFY_MSG, values);
    }

    @Transactional
    public int updateGetflagByPkid(String pkid) {
        return jdbcTemplate.update(SQLConst.UPDATE_BINOTIFY_MSG_GETFLAG, pkid);
    }
}
