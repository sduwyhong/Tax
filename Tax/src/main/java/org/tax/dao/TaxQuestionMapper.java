package org.tax.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.tax.VO.QuestionBrief;
import org.tax.model.TaxQuestion;
import org.tax.model.TaxQuestionExample;
import org.tax.model.TaxQuestionKey;

public interface TaxQuestionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_question
     *
     * @mbg.generated Sat Jul 07 16:08:58 CST 2018
     */
    long countByExample(TaxQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_question
     *
     * @mbg.generated Sat Jul 07 16:08:58 CST 2018
     */
    int deleteByExample(TaxQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_question
     *
     * @mbg.generated Sat Jul 07 16:08:58 CST 2018
     */
    @Delete({
        "delete from tax_question",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(TaxQuestionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_question
     *
     * @mbg.generated Sat Jul 07 16:08:58 CST 2018
     */
    @Insert({
        "insert into tax_question (id, author_id, ",
        "type, prize, title, ",
        "content, publish_date, ",
        "click, likes, favourite, ",
        "status)",
        "values (#{id,jdbcType=VARCHAR}, #{authorId,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=VARCHAR}, #{prize,jdbcType=INTEGER}, #{title,jdbcType=VARCHAR}, ",
        "#{content,jdbcType=VARCHAR}, #{publishDate,jdbcType=DATE}, ",
        "#{click,jdbcType=INTEGER}, #{likes,jdbcType=INTEGER}, #{favourite,jdbcType=INTEGER}, ",
        "#{status,jdbcType=INTEGER})"
    })
    int insert(TaxQuestion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_question
     *
     * @mbg.generated Sat Jul 07 16:08:58 CST 2018
     */
    int insertSelective(TaxQuestion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_question
     *
     * @mbg.generated Sat Jul 07 16:08:58 CST 2018
     */
    List<TaxQuestion> selectByExample(TaxQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_question
     *
     * @mbg.generated Sat Jul 07 16:08:58 CST 2018
     */
    @Select({
        "select",
        "id, author_id, type, prize, title, content, publish_date, click, likes, favourite, ",
        "status",
        "from tax_question",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    @ResultMap("org.tax.dao.TaxQuestionMapper.BaseResultMap")
    TaxQuestion selectByPrimaryKey(TaxQuestionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_question
     *
     * @mbg.generated Sat Jul 07 16:08:58 CST 2018
     */
    int updateByExampleSelective(@Param("record") TaxQuestion record, @Param("example") TaxQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_question
     *
     * @mbg.generated Sat Jul 07 16:08:58 CST 2018
     */
    int updateByExample(@Param("record") TaxQuestion record, @Param("example") TaxQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_question
     *
     * @mbg.generated Sat Jul 07 16:08:58 CST 2018
     */
    int updateByPrimaryKeySelective(TaxQuestion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_question
     *
     * @mbg.generated Sat Jul 07 16:08:58 CST 2018
     */
    @Update({
        "update tax_question",
        "set author_id = #{authorId,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=VARCHAR},",
          "prize = #{prize,jdbcType=INTEGER},",
          "title = #{title,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=VARCHAR},",
          "publish_date = #{publishDate,jdbcType=DATE},",
          "click = #{click,jdbcType=INTEGER},",
          "likes = #{likes,jdbcType=INTEGER},",
          "favourite = #{favourite,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(TaxQuestion record);

	int getLastInsertId();

	int click(int questionId);

	List<QuestionBrief> selectQuestionBriefByUser(@Param("userId")String userId, @Param("pagination")boolean pagination, @Param("offset")int offset, @Param("num")int num);

	List<TaxQuestion> selectByFavourite(@Param("userId")String userId, @Param("pagination")boolean pagination, @Param("offset")int offset, @Param("num")int num);

	int minusFavourite(int questionId);

}