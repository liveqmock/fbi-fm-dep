package monitor.service;

import monitor.repository.dao.DepBirouteLogMapper;
import monitor.repository.model.DepBirouteLog;
import monitor.repository.model.DepBirouteLogExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-28
 * Time: ÉÏÎç11:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BiRouteLogService {

    @Autowired
    private DepBirouteLogMapper mapper;

    public List<DepBirouteLog> qryRecords(String opcode, String bankcode, String startDate, String endDate) throws ParseException {

        DepBirouteLogExample example = new DepBirouteLogExample();
        DepBirouteLogExample.Criteria condition = example.createCriteria().andOpcodeEqualTo(opcode).andBankcodeEqualTo(bankcode);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(!StringUtils.isEmpty(startDate)) {
            condition.andLogdateGreaterThanOrEqualTo(sdf.parse(startDate));
        }
        if(!StringUtils.isEmpty(endDate)) {
            condition.andLogdateLessThanOrEqualTo(sdf.parse(endDate));
        }
        return mapper.selectByExample(example);
    }


}
