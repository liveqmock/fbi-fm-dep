package monitor.service;

import monitor.repository.dao.DbStatusMngDao;
import monitor.repository.model.DbTableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-8
 * Time: ÉÏÎç10:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DbStatusMngService {

    @Autowired
    private DbStatusMngDao dbStatusMngDao;

    public List<DbTableBean> qrySyslogTblStatus() {
       return dbStatusMngDao.qrySyslogTblStatus();
    }

    public int deleteLogData(String tablename, String startDate, String endDate) {
        return dbStatusMngDao.deleteLogData(tablename, startDate, endDate);
    }
}
