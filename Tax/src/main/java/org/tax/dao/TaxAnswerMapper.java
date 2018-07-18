package org.tax.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.tax.model.TaxAnswer;
import org.tax.model.TaxAnswerExample;
import org.tax.model.TaxAnswerKey;

public interface TaxAnswerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_answer
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    long countByExample(TaxAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_answer
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    int deleteByExample(TaxAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_answer
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    @Delete({
        "delete from tax_answer",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(TaxAnswerKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_answer
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    @Insert({
        "insert into tax_answer (id, question_id, ",
        "author_id, publish_date, ",
        "content, favourite, ",
        "likes, status)",
        "values (#{id,jdbcType=INTEGER}, #{questionId,jdbcType=INTEGER}, ",
        "#{authorId,jdbcType=VARCHAR}, #{publishDate,jdbcType=DATE}, ",
        "#{content,jdbcType=VARCHAR}, #{favourite,jdbcType=INTEGER}, ",
        "#{likes,jdbcType=INTEGER}, #{status,jdbcType=TINYINT})"
    })
    int insert(TaxAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_answer
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    int insertSelective(TaxAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_answer
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    List<TaxAnswer> selectByExample(TaxAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_answer
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    @Select({
        "select",
        "id, question_id, author_id, publish_date, content, favourite, likes, status",
        "from tax_answer",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("org.tax.dao.TaxAnswerMapper.BaseResultMap")
    TaxAnswer selectByPrimaryKey(TaxAnswerKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_answer
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    int updateByExampleSelective(@Param("record") TaxAnswer record, @Param("example") TaxAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_answer
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    int updateByExample(@Param("record") TaxAnswer record, @Param("example") TaxAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_answer
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    int updateByPrimaryKeySelective(TaxAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_answer
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    @Update({
        "update tax_answer",
        "set question_id = #{questionId,jdbcType=INTEGER},",
          "author_id = #{authorId,jdbcType=VARCHAR},",
          "publish_date = #{publishDate,jdbcType=DATE},",
          "content = #{content,jdbcType=VARCHAR},",
          "favourite = #{favourite,jdbcType=INTEGER},",
          "likes = #{likes,jdbcType=INTEGER},",
          "status = #{status,jdbcType=TINYINT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TaxAnswer record);

	int updateStatus(@Param("status")int status, @Param("id")int id);

	int updateLike(int id);
}