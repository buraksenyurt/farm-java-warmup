/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.lectures.business.objects;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.lectures.business.objects.domain.Address;
import com.lectures.business.objects.domain.AnemicOrder;
import com.lectures.business.objects.domain.Money;
import com.lectures.business.objects.domain.Order;
import com.lectures.business.objects.domain.anemic.AnemicOrderLine;
import com.lectures.business.objects.domain.anemic.OrderCalculator;
import com.lectures.business.objects.domain.anemic.ProductBean;

/**
 *
 * @author burak
 */
public class BusinessObjects {

    public static void main(String[] args) {
        // Case 00: Creating an invalid product
        var someProduct = new ProductBean();

        System.out.println(someProduct.getProductName()); // null   — NOT NULL 
        System.out.println(someProduct.getProductId()); // 0
        System.out.println(someProduct.isDiscontinued()); // false
        System.out.println(someProduct.getUnitPrice()); // 0.0
        System.out.println(someProduct.getUnitPrice() * 3); // 55.800003
        System.out.println(someProduct.getUnitsInStock()); // -5
        // System.out.println(someProduct.getCategoryId() + 1); // NullPointerException

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
        System.out.println("Address type equality: " + workAddress.equals(workAddressAgain)); // true
        System.out.println("Address hash code equality: " + (workAddress.hashCode() == workAddressAgain.hashCode())); // true
        System.out.println("Address reference equality: " + (workAddress == workAddressAgain)); // false
        System.out.println(workAddress.singleLine());
        // var invalidAddress = new Address("", "Berlin", "10115", "Germany");
        //// should throw an exception due to empty street
        // System.out.println(invalidAddress);

        // Case 02: Creating a valid PostalAddress
        // use 
        // javap -p target/classes/com/lectures/business/objects/domain/PostalAddress.class 
        // to inspect the class content

        // Case 03: Money is not a double.
        var total = 0.1 + 0.2;
        System.out.println("0.1 + 0.2 = " + total); // may not be exactly 0.3 due to floating-point precision issues
        float price = 9.14f;
        System.out.println("9.14F * 3 = " + price * 3);
        var total2 = new BigDecimal("0.1").add(new BigDecimal("0.2"));
        System.out.println("BigDecimal 0.1 + 0.2 = " + total2); // should be exactly 0.3
        System.out.println(new BigDecimal(0.1)); // may not be exactly 0.1 due to floating-point precision issues

        var money = Money.tl("24.60");
        System.out.println("money from 24.60 = " + money);
        var money2 = Money.tl("24.6").times(3);
        System.out.println("money from 24.6 * 3 = " + money2);
        var money3 = Money.tl("1000").discountedBy(new BigDecimal("0.15"));
        System.out.println("money from 1000 discounted by BigDecimal 0.15 = " + money3);
        // // should throw an exception due to currency mismatch
        // var money4 = Money.tl("50").plus(new Money(new BigDecimal("15"), Currency.getInstance("USD")));
        // System.out.println(money4);

        /* Case 04: Rich Entity (AnemicOrder vs Rich Order)
        
        Look at the difference between anemic and rich orders total amount of lines calculation
         */
        Order rich = new Order(10248, "VINET", LocalDate.of(1996, 7, 4));
        AnemicOrder anemic = new AnemicOrder();

        for (int i = 0; i < PRODUCTS.length; i++) {
            rich.addLine(PRODUCTS[i], Money.tl(PRICES[i]), QUANTITIES[i], new BigDecimal(DISCOUNTS[i]));

            AnemicOrderLine line = new AnemicOrderLine();
            line.setProductId(PRODUCTS[i]);
            line.setUnitPrice(Double.parseDouble(PRICES[i]));
            line.setQuantity(QUANTITIES[i]);
            line.setDiscount(Double.parseDouble(DISCOUNTS[i]));
            anemic.getLines().add(line);
        }

        System.out.println("anemic total : " + new OrderCalculator().total(anemic));
        System.out.println("rich   total : " + rich.total());
    }
    private static final int[] PRODUCTS = {11, 42, 72, 28, 39};
    private static final String[] PRICES = {"14.00", "9.80", "34.80", "45.60", "18.00"};
    private static final int[] QUANTITIES = {12, 10, 5, 9, 21};
    private static final String[] DISCOUNTS = {"0.05", "0.15", "0.10", "0.25", "0.05"};
}
