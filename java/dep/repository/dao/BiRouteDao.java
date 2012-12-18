package dep.repository.dao;

import dep.common.SQLConst;
import dep.repository.model.DepBirouteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class BiRouteDao {

    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
            insert into DEP_BIROUTE_LOG(" +
            "  pkid, msg_id, msg_from, msg_to, current_addr, is_arrive," +
            "  msg_rtncode, corralation_id, msg_type, down_status," +
            "  bankcode, opcode, logdate) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
     */
    @Transactional
    public int insertRecord(DepBirouteLog log) {
        Object[] values = new Object[]{ log.getPkid(), log.getMsgId(), log.getMsgFrom(), log.getMsgTo(),
                                        log.getCurrentAddr(), log.getIsArrive(), log.getMsgRtncode(),
                                        log.getCorralationId(), log.getMsgType(), log.getDownStatus(),
                                        log.getBankcode(), log.getOpcode(), log.getLogdate()};
        return jdbcTemplate.update(SQLConst.INSERT_BIROUTE_LOG, values);
    }

    @Transactional
    public int updateRtncodeByPkid(String pkid, String rtnCode) {
       String[] values = new String[] {rtnCode, pkid};
        return jdbcTemplate.update(SQLConst.UPDATE_BIROUTE_RTNCODE, values);
    }
}
