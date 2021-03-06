package org.tax.model;

import java.util.Date;

public class TaxAnswer extends TaxAnswerKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_answer.question_id
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    private Integer questionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_answer.author_id
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    private String authorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_answer.publish_date
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    private Date publishDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_answer.content
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_answer.favourite
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    private Integer favourite = 0;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_answer.likes
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    private Integer likes = 0;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_answer.status
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    private Byte status = 0;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_answer.question_id
     *
     * @return the value of tax_answer.question_id
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_answer.question_id
     *
     * @param questionId the value for tax_answer.question_id
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_answer.author_id
     *
     * @return the value of tax_answer.author_id
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public String getAuthorId() {
        return authorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_answer.author_id
     *
     * @param authorId the value for tax_answer.author_id
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public void setAuthorId(String authorId) {
        this.authorId = authorId == null ? null : authorId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_answer.publish_date
     *
     * @return the value of tax_answer.publish_date
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_answer.publish_date
     *
     * @param publishDate the value for tax_answer.publish_date
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_answer.content
     *
     * @return the value of tax_answer.content
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_answer.content
     *
     * @param content the value for tax_answer.content
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_answer.favourite
     *
     * @return the value of tax_answer.favourite
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public Integer getFavourite() {
        return favourite;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_answer.favourite
     *
     * @param favourite the value for tax_answer.favourite
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_answer.likes
     *
     * @return the value of tax_answer.likes
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_answer.likes
     *
     * @param likes the value for tax_answer.likes
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_answer.status
     *
     * @return the value of tax_answer.status
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_answer.status
     *
     * @param status the value for tax_answer.status
     *
     * @mbg.generated Wed Jul 18 08:58:27 CST 2018
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}