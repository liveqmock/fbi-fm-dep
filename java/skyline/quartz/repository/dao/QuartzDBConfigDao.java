package skyline.quartz.repository.dao;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import skyline.quartz.repository.model.QrtzTrigerJob;

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
public class QuartzDBConfigDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<QrtzTrigerJob> qryAllTrigerJobs() {
        String qrySql = "select trgr.trigger_name,trgr.trigger_group,trgr.job_name," +
                " jbdtl.job_class_name, jbdtl.description, trgr.prev_fire_time," +
                " trgr.next_fire_time,trgr.trigger_state " +
                " from QRTZ_TRIGGERS trgr " +
                " join QRTZ_JOB_DETAILS jbdtl" +
                " on trgr.job_name = jbdtl.job_name";


        return jdbcTemplate.query(qrySql, new QrtzTrigerJobMapper());
    }


    private final static class QrtzTrigerJobMapper implements RowMapper<QrtzTrigerJob> {

        @Override
        public QrtzTrigerJob mapRow(ResultSet resultSet, int i) throws SQLException {

            QrtzTrigerJob trigJob = new QrtzTrigerJob();
            trigJob.setTrigName(resultSet.getString("trigger_name"));
            trigJob.setTrigGroup(resultSet.getString("trigger_group"));
            trigJob.setJobName(resultSet.getString("job_name"));
            trigJob.setJobClassName(resultSet.getString("job_class_name"));
            trigJob.setJobDescription(resultSet.getString("description"));
            trigJob.setPrevFireTime(DateFormatUtils.format(resultSet.getLong("prev_fire_time"), "yyyy-MM-dd HH:mm:ss"));
            trigJob.setNextFireTime(DateFormatUtils.format(resultSet.getLong("next_fire_time"), "yyyy-MM-dd HH:mm:ss"));
            trigJob.setTrigState(resultSet.getString("trigger_state"));
            return trigJob;
        }
    }
}
