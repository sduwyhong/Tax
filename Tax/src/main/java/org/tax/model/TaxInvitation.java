package org.tax.model;

import lombok.Data;

@Data
public class TaxInvitation extends TaxInvitationKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_invitation.question_id
     *
     * @mbg.generated Sat Jul 07 16:09:44 CST 2018
     */
    private Integer questionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_invitation.user_id
     *
     * @mbg.generated Sat Jul 07 16:09:44 CST 2018
     */
    private String userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_invitation.question_id
     *
     * @return the value of tax_invitation.question_id
     *
     * @mbg.generated Sat Jul 07 16:09:44 CST 2018
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_invitation.question_id
     *
     * @param questionId the value for tax_invitation.question_id
     *
     * @mbg.generated Sat Jul 07 16:09:44 CST 2018
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_invitation.user_id
     *
     * @return the value of tax_invitation.user_id
     *
     * @mbg.generated Sat Jul 07 16:09:44 CST 2018
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_invitation.user_id
     *
     * @param userId the value for tax_invitation.user_id
     *
     * @mbg.generated Sat Jul 07 16:09:44 CST 2018
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}