package dep.repository.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-6
 * Time: ионГ11:13
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CommonDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getSysGuid() {
        return jdbcTemplate.queryForObject("select sys_guid() from dual", String.class);
    }
}
