package com.lectures.business.objects.domain;

/*
The same value object as {@link Address}, written as a record.
The compiler generates the constructor, accessors, `equals()`, `hashCode()`, and `toString()` methods automatically.

To look at the class content
javap -p target/classes/com/lectures/business/objects/domain/PostalAddress.class
 */
public record PostalAddress(
        String street,
        String city,
        String postalCode,
        String country
        ) {

    /*
    Compact constructor. It runs before the fields are assigned.
     */
    public PostalAddress {
        street = requireText(street, "street");
        city = requireText(city, "city");
        postalCode = requireText(postalCode, "postalCode");
        country = requireText(country, "country");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.strip();
    }

    public PostalAddress withCountry(String newCountry) {
        return new PostalAddress(street, city, postalCode, newCountry);
    }
}
