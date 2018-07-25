package org.tax.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.tax.VO.Candidate;
import org.tax.VO.UserQuery;
import org.tax.model.TaxUser;
import org.tax.model.TaxUserExample;
import org.tax.model.TaxUserKey;

public interface TaxUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_user
     *
     * @mbg.generated Sat Jul 07 16:56:15 CST 2018
     */
    long countByExample(TaxUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_user
     *
     * @mbg.generated Sat Jul 07 16:56:15 CST 2018
     */
    int deleteByExample(TaxUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_user
     *
     * @mbg.generated Sat Jul 07 16:56:15 CST 2018
     */
    @Delete({
        "delete from tax_user",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(TaxUserKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_user
     *
     * @mbg.generated Sat Jul 07 16:56:15 CST 2018
     */
    @Insert({
        "insert into tax_user (id, username, ",
        "password, email, ",
        "telephone, score, ",
        "last_visit, pro_list, ",
        "image, privilege)",
        "values (#{id,jdbcType=VARCHAR}, #{username,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, ",
        "#{telephone,jdbcType=VARCHAR}, #{score,jdbcType=INTEGER}, ",
        "#{lastVisit,jdbcType=DATE}, #{proList,jdbcType=VARCHAR}, ",
        "#{image,jdbcType=VARCHAR}, #{privilege,jdbcType=TINYINT})"
    })
    int insert(TaxUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_user
     *
     * @mbg.generated Sat Jul 07 16:56:15 CST 2018
     */
    int insertSelective(TaxUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_user
     *
     * @mbg.generated Sat Jul 07 16:56:15 CST 2018
     */
    List<TaxUser> selectByExample(TaxUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_user
     *
     * @mbg.generated Sat Jul 07 16:56:15 CST 2018
     */
    @Select({
        "select",
        "id, username, password, email, telephone, score, last_visit, pro_list, image, ",
        "privilege",
        "from tax_user",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    @ResultMap("org.tax.dao.TaxUserMapper.BaseResultMap")
    TaxUser selectByPrimaryKey(TaxUserKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_user
     *
     * @mbg.generated Sat Jul 07 16:56:15 CST 2018
     */
    int updateByExampleSelective(@Param("record") TaxUser record, @Param("example") TaxUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_user
     *
     * @mbg.generated Sat Jul 07 16:56:15 CST 2018
     */
    int updateByExample(@Param("record") TaxUser record, @Param("example") TaxUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_user
     *
     * @mbg.generated Sat Jul 07 16:56:15 CST 2018
     */
    int updateByPrimaryKeySelective(TaxUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tax_user
     *
     * @mbg.generated Sat Jul 07 16:56:15 CST 2018
     */
    @Update({
        "update tax_user",
        "set username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "telephone = #{telephone,jdbcType=VARCHAR},",
          "score = #{score,jdbcType=INTEGER},",
          "last_visit = #{lastVisit,jdbcType=DATE},",
          "pro_list = #{proList,jdbcType=VARCHAR},",
          "image = #{image,jdbcType=VARCHAR},",
          "privilege = #{privilege,jdbcType=TINYINT}",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(TaxUser record);

	List<Candidate> getUserByPro(String string);

	long selectScoresById(String id);

	int minusScores(@Param("score")int score, @Param("id")String id);

	int addScores(@Param("score")int score, @Param("id")String id);

	int updateAvatarAddress(@Param("filePath")String filePath, @Param("id")String userId);

	String getAvatar(String userId);

	List<TaxUser> selectList(@Param("offset")int i, @Param("num")int numPerPage);

	List<TaxUser> selectByCondition(UserQuery query);

}