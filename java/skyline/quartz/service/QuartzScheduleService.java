package skyline.quartz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skyline.quartz.repository.dao.QuartzDBConfigDao;
import skyline.quartz.repository.model.QrtzTrigerJob;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-22
 * Time: ÏÂÎç1:46
 * To change this template use File | Settings | File Templates.
 */
@Service
public class QuartzScheduleService {

    @Autowired
    private QuartzDBConfigDao quartzDBConfigDao;

    public List<QrtzTrigerJob> qryAllTrigerJobs() {
        return quartzDBConfigDao.qryAllTrigerJobs();
    }

}
