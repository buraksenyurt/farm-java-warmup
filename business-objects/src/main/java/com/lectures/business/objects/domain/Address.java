package com.lectures.business.objects.domain;

import java.util.Objects;

/*
    Final class representing an immutable address.
    Use of final because of immutability; once an Address object is created, 
    its state cannot be changed.

    Constraints:
    - class is final, fields are final, there are no setters
    - Getter names not prefixed with "get" (e.g., street() instead of getStreet())
 */
public final class Address {

    private final String street;
    private final String city;
    private final String postalCode; // can be Value Object
    private final String country;

    public Address(String street, String city, String postalCode, String country) {
        this.street = requireText(street, "street");
        this.city = requireText(city, "city");
        this.postalCode = requireText(postalCode, "postalCode");
        this.country = requireText(country, "country");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.strip();
    }

    public String street() {
        return street;
    }

    public String city() {
        return city;
    }

    public String postalCode() {
        return postalCode;
    }

    public String country() {
        return country;
    }

    public String singleLine() {
        return street + ", " + postalCode + " " + city + ", " + country;
    }

    public boolean isIn(String countryName) {
        return country.equalsIgnoreCase(countryName);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Address address)) {
            return false;
        }
        return street.equals(address.street)
                && city.equals(address.city)
                && postalCode.equals(address.postalCode)
                && country.equals(address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, postalCode, country);
    }

    @Override
    public String toString() {
        return "Address[" + street + ", " + postalCode + " " + city + ", " + country + "]";
    }
}
