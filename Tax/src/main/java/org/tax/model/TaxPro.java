package org.tax.model;

import lombok.Data;


public class TaxPro extends TaxProKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tax_pro.name
     *
     * @mbg.generated Sat Jul 07 16:09:30 CST 2018
     */
    private String name;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tax_pro.name
     *
     * @return the value of tax_pro.name
     *
     * @mbg.generated Sat Jul 07 16:09:30 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tax_pro.name
     *
     * @param name the value for tax_pro.name
     *
     * @mbg.generated Sat Jul 07 16:09:30 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}