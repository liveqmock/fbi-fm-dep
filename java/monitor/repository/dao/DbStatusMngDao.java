package monitor.repository.dao;

import dep.common.BoolType;
import monitor.repository.model.DbTableBean;
import monitor.repository.sql.ConstSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.advance.utils.PropertyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-28
 * Time: ÏÂÎç3:55
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class DbStatusMngDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DbTableBean> qrySyslogTblStatus() {
        String[] tables = PropertyManager.getProperty("pub.platform.db.warn.tables").split(",");
        String[] tblChNames = PropertyManager.getProperty("pub.platform.db.warn.tblnames").split(",");
        // pub.platform.db.warn.limitCount
        long LIMIT_COUNT = PropertyManager.getLongProperty("pub.platform.db.warn.limitCount");

        List<DbTableBean> tableBeanList = null;
        if (tables != null && tables.length >= 1) {
            tableBeanList = new ArrayList<DbTableBean>();
            DbTableBean tableBean = null;
            int index = 0;
            for (String tableName : tables) {
                tableBean = new DbTableBean();
                tableBean.setTblname(tableName);
                tableBean.setChName(tblChNames[index++]);
                String sql = ConstSQL.QRY_TBL_COUNT + tableName;
                long count = jdbcTemplate.queryForLong(sql);
                tableBean.setRecordCount(count);
                if(count >= LIMIT_COUNT) {
                    tableBean.setHuge(BoolType.TRUE);
                }else tableBean.setHuge(BoolType.FALSE);
                tableBeanList.add(tableBean);
            }
        }
        return tableBeanList;
    }

    @Transactional
    public int deleteLogData(String tablename, String startDate, String endDate) {
        String sql = ConstSQL.DELETE_TBL + tablename + " where 1=1 and ";
       // BI_NOTIFY_MESSAGE,DEP_BIROUTE_LOG,DEP_MQ_LOG
        // NOTIFYDATE, LOGDATE, OPERDAT
        String dateField = null;
        if("BI_NOTIFY_MESSAGE".equalsIgnoreCase(tablename)) {
            dateField = "NOTIFYDATE";
        }
         if("DEP_BIROUTE_LOG".equalsIgnoreCase(tablename)) {
            dateField = "LOGDATE";
        }
         if("DEP_MQ_LOG".equalsIgnoreCase(tablename)) {
            dateField = "OPERDAT";
        }
        //sqlBuilder.append(" and notifydate >= to_date('").append(startDate).append("','yyyy-MM-dd')");
        sql += dateField + " >= to_date('" + startDate + "','yyyy-MM-dd') and ";
        sql += dateField + " <= to_date('" + endDate + "','yyyy-MM-dd')";
        return jdbcTemplate.update(sql);
    }
}
