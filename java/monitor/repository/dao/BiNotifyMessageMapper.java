package monitor.repository.dao;

import monitor.repository.model.BiNotifyMessage;
import monitor.repository.model.BiNotifyMessageExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiNotifyMessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int countByExample(BiNotifyMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int deleteByExample(BiNotifyMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int deleteByPrimaryKey(String rowId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int insert(BiNotifyMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int insertSelective(BiNotifyMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    List<BiNotifyMessage> selectByExample(BiNotifyMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    BiNotifyMessage selectByPrimaryKey(String rowId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiNotifyMessage record, @Param("example") BiNotifyMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int updateByExample(@Param("record") BiNotifyMessage record, @Param("example") BiNotifyMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int updateByPrimaryKeySelective(BiNotifyMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEP.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Fri Nov 04 15:15:48 CST 2011
     */
    int updateByPrimaryKey(BiNotifyMessage record);
}