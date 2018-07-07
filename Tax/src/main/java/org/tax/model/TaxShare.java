package org.tax.model;

import java.util.Date;

import lombok.Data;
@Data
public class TaxShare extends TaxShareKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_share.author_id
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    private String authorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_share.title
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_share.content
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_share.publish_date
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    private Date publishDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_share.click
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    private Integer click;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_share.likes
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    private Integer likes;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_share.favourite
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    private Integer favourite;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_share.author_id
     *
     * @return the value of tax_share.author_id
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public String getAuthorId() {
        return authorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_share.author_id
     *
     * @param authorId the value for tax_share.author_id
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public void setAuthorId(String authorId) {
        this.authorId = authorId == null ? null : authorId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_share.title
     *
     * @return the value of tax_share.title
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_share.title
     *
     * @param title the value for tax_share.title
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_share.content
     *
     * @return the value of tax_share.content
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_share.content
     *
     * @param content the value for tax_share.content
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_share.publish_date
     *
     * @return the value of tax_share.publish_date
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_share.publish_date
     *
     * @param publishDate the value for tax_share.publish_date
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_share.click
     *
     * @return the value of tax_share.click
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public Integer getClick() {
        return click;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_share.click
     *
     * @param click the value for tax_share.click
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public void setClick(Integer click) {
        this.click = click;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_share.likes
     *
     * @return the value of tax_share.likes
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_share.likes
     *
     * @param likes the value for tax_share.likes
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_share.favourite
     *
     * @return the value of tax_share.favourite
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public Integer getFavourite() {
        return favourite;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_share.favourite
     *
     * @param favourite the value for tax_share.favourite
     *
     * @mbg.generated Sat Jul 07 16:10:16 CST 2018
     */
    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }
}