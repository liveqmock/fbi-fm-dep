package dep.fmqd.repository.dao;

import dep.fmqd.repository.model.BiNotifyInterest;
import dep.fmqd.repository.model.BiNotifyInterestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BiNotifyInterestMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_INTEREST
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int countByExample(BiNotifyInterestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_INTEREST
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int deleteByExample(BiNotifyInterestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_INTEREST
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int deleteByPrimaryKey(Long rowId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_INTEREST
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int insert(BiNotifyInterest record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_INTEREST
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int insertSelective(BiNotifyInterest record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_INTEREST
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    List<BiNotifyInterest> selectByExample(BiNotifyInterestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_INTEREST
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    BiNotifyInterest selectByPrimaryKey(Long rowId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_INTEREST
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiNotifyInterest record, @Param("example") BiNotifyInterestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_INTEREST
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int updateByExample(@Param("record") BiNotifyInterest record, @Param("example") BiNotifyInterestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_INTEREST
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int updateByPrimaryKeySelective(BiNotifyInterest record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_INTEREST
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int updateByPrimaryKey(BiNotifyInterest record);
}