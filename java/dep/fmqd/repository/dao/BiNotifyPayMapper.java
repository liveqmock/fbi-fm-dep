package dep.fmqd.repository.dao;

import dep.fmqd.repository.model.BiNotifyPay;
import dep.fmqd.repository.model.BiNotifyPayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BiNotifyPayMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_PAY
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int countByExample(BiNotifyPayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_PAY
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int deleteByExample(BiNotifyPayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_PAY
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int deleteByPrimaryKey(Long rowId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_PAY
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int insert(BiNotifyPay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_PAY
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int insertSelective(BiNotifyPay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_PAY
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    List<BiNotifyPay> selectByExample(BiNotifyPayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_PAY
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    BiNotifyPay selectByPrimaryKey(Long rowId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_PAY
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiNotifyPay record, @Param("example") BiNotifyPayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_PAY
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int updateByExample(@Param("record") BiNotifyPay record, @Param("example") BiNotifyPayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_PAY
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int updateByPrimaryKeySelective(BiNotifyPay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FMDB.BI_NOTIFY_PAY
     *
     * @mbggenerated Thu Sep 29 14:37:20 CST 2011
     */
    int updateByPrimaryKey(BiNotifyPay record);
}