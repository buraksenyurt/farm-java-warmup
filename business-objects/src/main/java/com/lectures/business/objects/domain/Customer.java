package com.lectures.business.objects.domain;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/*
    Customer class is an aggregate root. It's mirrors of the customers table,
    where customer_id is a five-letter code such as ALFKI or VINET.

    Constraints:
    - customerId is final. Never change it after construction (immutable field)
    - equals and hashCode calculations are based solely on customerId
    - contactName is optional and can be cleared (nullable field)
    - Address changes via the relocateTo method (mutable field)
 */
public final class Customer {

    private static final Pattern ID_PATTERN = Pattern.compile("^[A-Z]{5}$");
    private final String customerId;
    private String companyName;
    private String contactName;
    private Address address;

    public Customer(String customerId, String companyName, Address address) {
        this.customerId = normaliseId(customerId);
        this.companyName = requireText(companyName, "companyName");
        this.address = Objects.requireNonNull(address, "address must not be null");
    }

    // --- helpers begin ---
    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.strip();
    }

    private static String normaliseId(String value) {
        String id = requireText(value, "customerId").toUpperCase(Locale.ROOT);
        if (!ID_PATTERN.matcher(id).matches()) {
            throw new IllegalArgumentException(
                    "customerId must be exactly five letters (A-Z): " + value);
        }
        return id;
    }

    // --- helpers end ---
    // --- behaviors begin ---
    public void relocateTo(Address newAddress) {
        this.address = Objects.requireNonNull(newAddress, "newAddress must not be null");
    }

    public void renameCompanyTo(String newCompanyName) {
        this.companyName = requireText(newCompanyName, "companyName");
    }

    public void assignContactName(String newContactName) {
        this.contactName = requireText(newContactName, "contactName");
    }

    public boolean isBasedIn(String countryName) {
        return address.isIn(countryName);
    }

    public void clearContact() {
        this.contactName = null;
    }
    // --- behaviors end ---

    // --- state begin ---
    public String customerId() {
        return customerId;
    }

    public String companyName() {
        return companyName;
    }

    public Optional<String> contactName() {
        return Optional.ofNullable(contactName);
    }

    public Address address() {
        return address;
    }

    // --- state end ---
    // --- overrides begin ---
    @Override
    public String toString() {
        return "Customer[" + customerId + " " + companyName + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Customer customer)) {
            return false;
        }
        return customerId.equals(customer.customerId);
    }

    @Override
    public int hashCode() {
        return customerId.hashCode();
    }

    // --- overrides end ---
}
