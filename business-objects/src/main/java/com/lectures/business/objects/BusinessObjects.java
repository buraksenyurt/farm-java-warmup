/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.lectures.business.objects;

import com.lectures.business.objects.domain.CustomerBean;

/**
 *
 * @author burak
 */
public class BusinessObjects {

    public static void main(String[] args) {
        CustomerBean customer=new CustomerBean(); // creates invalid customer with default constructor
        // example rule: Company name should not be empty or null
        System.out.println(customer.getCompanyName());
        // Every setter should be validated according to the business rules
        // Invalid customer ID examples according to the business rules
        customer.setCustomerId("");
        customer.setCustomerId("TOO LONG ID");
    }
}
