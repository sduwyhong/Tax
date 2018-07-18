package org.tax.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.tax.VO.MessageVO;
import org.tax.model.TaxMessage;
import org.tax.model.TaxMessageExample;
import org.tax.model.TaxMessageKey;

public interface TaxMessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_message
     *
     * @mbg.generated Sat Jul 07 16:10:55 CST 2018
     */
    long countByExample(TaxMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_message
     *
     * @mbg.generated Sat Jul 07 16:10:55 CST 2018
     */
    int deleteByExample(TaxMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_message
     *
     * @mbg.generated Sat Jul 07 16:10:55 CST 2018
     */
    @Delete({
        "delete from tax_message",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(TaxMessageKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_message
     *
     * @mbg.generated Sat Jul 07 16:10:55 CST 2018
     */
    @Insert({
        "insert into tax_message (id, sender_id, ",
        "receiver_id, content, ",
        "status)",
        "values (#{id,jdbcType=INTEGER}, #{senderId,jdbcType=VARCHAR}, ",
        "#{receiverId,jdbcType=VARCHAR}, #{content,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=TINYINT})"
    })
    int insert(TaxMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_message
     *
     * @mbg.generated Sat Jul 07 16:10:55 CST 2018
     */
    int insertSelective(TaxMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_message
     *
     * @mbg.generated Sat Jul 07 16:10:55 CST 2018
     */
    List<TaxMessage> selectByExample(TaxMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_message
     *
     * @mbg.generated Sat Jul 07 16:10:55 CST 2018
     */
    @Select({
        "select",
        "id, sender_id, receiver_id, content, status",
        "from tax_message",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("org.tax.dao.TaxMessageMapper.BaseResultMap")
    TaxMessage selectByPrimaryKey(TaxMessageKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_message
     *
     * @mbg.generated Sat Jul 07 16:10:55 CST 2018
     */
    int updateByExampleSelective(@Param("record") TaxMessage record, @Param("example") TaxMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_message
     *
     * @mbg.generated Sat Jul 07 16:10:55 CST 2018
     */
    int updateByExample(@Param("record") TaxMessage record, @Param("example") TaxMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_message
     *
     * @mbg.generated Sat Jul 07 16:10:55 CST 2018
     */
    int updateByPrimaryKeySelective(TaxMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_message
     *
     * @mbg.generated Sat Jul 07 16:10:55 CST 2018
     */
    @Update({
        "update tax_message",
        "set sender_id = #{senderId,jdbcType=VARCHAR},",
          "receiver_id = #{receiverId,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TaxMessage record);

	List<MessageVO> selectMessageVOReceived(@Param("userId")String userId, @Param("pagination")boolean pagination, @Param("offset")int offset, @Param("num")int num);

	List<MessageVO> selectMessageVOSent(@Param("userId")String userId, @Param("pagination")boolean pagination, @Param("offset")int offset, @Param("num")int num);
}