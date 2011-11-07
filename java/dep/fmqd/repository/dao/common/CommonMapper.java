package dep.fmqd.repository.dao.common;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-27
 * Time: обнГ2:12
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface CommonMapper {

    @Select("select F_SM_GET_ROW_ID(0) from dual")
    long selectNewRowID();
}
