/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.lectures.business.objects;

import com.lectures.business.objects.domain.Address;
import com.lectures.business.objects.domain.CustomerBean;

/**
 *
 * @author burak
 */
public class BusinessObjects {

    public static void main(String[] args) {
        // Case 00: Creating an invalid customer with the default constructor

        CustomerBean customer = new CustomerBean(); // creates invalid customer with default constructor
        // example rule: Company name should not be empty or null
        System.out.println(customer.getCompanyName());
        // Every setter should be validated according to the business rules
        // Invalid customer ID examples according to the business rules
        customer.setCustomerId("");
        customer.setCustomerId("TOO LONG ID");

        // Case 01: Creating a valid Address
        var workAddress = new Address(
                "1234 Main St",
                "Washington",
                "12345",
                "USA"
        );
        var workAddressAgain = new Address(
                "1234 Main St",
                "Washington",
                "12345",
                "USA"
        );
        System.out.println(workAddress.equals(workAddressAgain)); // true
        System.out.println(workAddress.hashCode() == workAddressAgain.hashCode()); // true
        System.out.println(workAddress == workAddressAgain); // false
        var invalidAddress = new Address("", "Berlin", "10115", "Germany"); 
        // should throw an exception due to empty street
        System.out.println(invalidAddress);
    }
}
