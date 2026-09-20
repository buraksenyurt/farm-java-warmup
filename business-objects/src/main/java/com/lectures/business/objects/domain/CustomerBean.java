package com.lectures.business.objects.domain;

/**
This is a bad sample of a JavaBean class.
Stayed for educational purposes only.
 */
public class CustomerBean {

    private String customerId;
    private String companyName;
    private String contactName;
    private String country;

    public CustomerBean() {

    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
