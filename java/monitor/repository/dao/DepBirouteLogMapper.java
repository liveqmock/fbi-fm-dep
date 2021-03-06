package monitor.repository.dao;

import monitor.repository.model.DepBirouteLog;
import monitor.repository.model.DepBirouteLogExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepBirouteLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.DEP_BIROUTE_LOG
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int countByExample(DepBirouteLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.DEP_BIROUTE_LOG
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int deleteByExample(DepBirouteLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.DEP_BIROUTE_LOG
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int deleteByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.DEP_BIROUTE_LOG
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int insert(DepBirouteLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.DEP_BIROUTE_LOG
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int insertSelective(DepBirouteLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.DEP_BIROUTE_LOG
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    List<DepBirouteLog> selectByExample(DepBirouteLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.DEP_BIROUTE_LOG
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    DepBirouteLog selectByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.DEP_BIROUTE_LOG
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int updateByExampleSelective(@Param("record") DepBirouteLog record, @Param("example") DepBirouteLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.DEP_BIROUTE_LOG
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int updateByExample(@Param("record") DepBirouteLog record, @Param("example") DepBirouteLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.DEP_BIROUTE_LOG
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int updateByPrimaryKeySelective(DepBirouteLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.DEP_BIROUTE_LOG
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int updateByPrimaryKey(DepBirouteLog record);
}