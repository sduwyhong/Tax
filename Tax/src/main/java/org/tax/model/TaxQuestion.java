package org.tax.model;

import java.util.Date;

import lombok.ToString;
@ToString
public class TaxQuestion extends TaxQuestionKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_question.author_id
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    private String authorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_question.type
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    //=pro_list
    private String type = "-";

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_question.prize
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    private Integer prize = 0;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_question.title
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_question.content
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_question.publish_date
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    private Date publishDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_question.click
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    private Integer click = 0;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_question.likes
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    private Integer likes = 0;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_question.favourite
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    private Integer favourite = 0;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_question.status
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    private Integer status = 0;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_question.author_id
     *
     * @return the value of tax_question.author_id
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public String getAuthorId() {
        return authorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_question.author_id
     *
     * @param authorId the value for tax_question.author_id
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public void setAuthorId(String authorId) {
        this.authorId = authorId == null ? null : authorId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_question.type
     *
     * @return the value of tax_question.type
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_question.type
     *
     * @param type the value for tax_question.type
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_question.prize
     *
     * @return the value of tax_question.prize
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public Integer getPrize() {
        return prize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_question.prize
     *
     * @param prize the value for tax_question.prize
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_question.title
     *
     * @return the value of tax_question.title
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_question.title
     *
     * @param title the value for tax_question.title
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_question.content
     *
     * @return the value of tax_question.content
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_question.content
     *
     * @param content the value for tax_question.content
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_question.publish_date
     *
     * @return the value of tax_question.publish_date
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_question.publish_date
     *
     * @param publishDate the value for tax_question.publish_date
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_question.click
     *
     * @return the value of tax_question.click
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public Integer getClick() {
        return click;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_question.click
     *
     * @param click the value for tax_question.click
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public void setClick(Integer click) {
        this.click = click;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_question.likes
     *
     * @return the value of tax_question.likes
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_question.likes
     *
     * @param likes the value for tax_question.likes
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_question.favourite
     *
     * @return the value of tax_question.favourite
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public Integer getFavourite() {
        return favourite;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_question.favourite
     *
     * @param favourite the value for tax_question.favourite
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_question.status
     *
     * @return the value of tax_question.status
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_question.status
     *
     * @param status the value for tax_question.status
     *
     * @mbg.generated Tue Jul 10 18:08:12 CST 2018
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}