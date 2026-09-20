/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.lectures.business.objects;

import java.math.BigDecimal;

import com.lectures.business.objects.domain.Address;
import com.lectures.business.objects.domain.CustomerBean;
import com.lectures.business.objects.domain.Money;

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
        // var invalidAddress = new Address("", "Berlin", "10115", "Germany");
        //// should throw an exception due to empty street
        // System.out.println(invalidAddress);

        // Case 02: Creating a valid PostalAddress
        // use 
        // javap -p target/classes/com/lectures/business/objects/domain/PostalAddress.class 
        // to inspect the class content
        // Case 03: Money is not a double.
        var total = 0.1 + 0.2;
        System.out.println(total); // may not be exactly 0.3 due to floating-point precision issues
        float price = 9.14f;
        System.out.println(price * 3);
        var total2 = new BigDecimal("0.1").add(new BigDecimal("0.2"));
        System.out.println(total2); // should be exactly 0.3
        System.out.println(new BigDecimal(0.1)); // may not be exactly 0.1 due to floating-point precision issues

        var money = Money.tl("24.60");
        System.out.println(money);
        var money2 = Money.tl("24.6").times(3);
        System.out.println(money2);
        var money3 = Money.tl("1000").discountedBy(new BigDecimal("0.15"));
        System.out.println(money3);
        // // should throw an exception due to currency mismatch
        // var money4 = Money.tl("50").plus(new Money(new BigDecimal("15"), Currency.getInstance("USD")));
        // System.out.println(money4);
    }
}
