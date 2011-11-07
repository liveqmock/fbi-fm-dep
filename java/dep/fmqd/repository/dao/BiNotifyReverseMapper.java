package dep.fmqd.repository.dao;

import dep.fmqd.repository.model.BiNotifyReverse;
import dep.fmqd.repository.model.BiNotifyReverseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BiNotifyReverseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_REVERSE
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int countByExample(BiNotifyReverseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_REVERSE
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int deleteByExample(BiNotifyReverseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_REVERSE
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int deleteByPrimaryKey(Long rowId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_REVERSE
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int insert(BiNotifyReverse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_REVERSE
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int insertSelective(BiNotifyReverse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_REVERSE
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    List<BiNotifyReverse> selectByExample(BiNotifyReverseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_REVERSE
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    BiNotifyReverse selectByPrimaryKey(Long rowId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_REVERSE
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiNotifyReverse record, @Param("example") BiNotifyReverseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_REVERSE
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int updateByExample(@Param("record") BiNotifyReverse record, @Param("example") BiNotifyReverseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_REVERSE
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int updateByPrimaryKeySelective(BiNotifyReverse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_REVERSE
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int updateByPrimaryKey(BiNotifyReverse record);
}