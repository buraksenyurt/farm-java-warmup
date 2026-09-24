package com.lectures.business.objects.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Order aggregate. Every state change goes through a method that checks the
 * rule first. There is no setter to bypass.
 *
 * Constraints: - There are no setters to bypass the rules. - State changes can
 * only occur through the provided
 * methods(addLine,removeLine,confirm,shipTo,ship,cancel) - shipToAddress
 * changes the shipping address as a whole; there is no partial update of the
 * address. - lines function can't return a modifiable list; it provides a
 * defensive copy.(List.copyOf is used) - shippedDate function returns an
 * Optional to indicate that the order may not have been shipped yet. - There
 * are no jakarta, java.sql or System.out dependencies; the aggregate is
 * self-contained. - The total method returns zero if there are no order lines.
 */
public final class Order {

    private static final int MAX_LINES = 50;

    private final int orderId;
    private final String customerId;
    private final LocalDate orderDate;
    private final List<OrderLine> lines = new ArrayList<>();

    private OrderStatus status = OrderStatus.DRAFT;
    private Address shippingAddress;
    private LocalDate shippedDate;

    public Order(int orderId, String customerId, LocalDate orderDate) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("orderId must be positive: " + orderId);
        }
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("customerId must not be blank");
        }
        this.orderId = orderId;
        this.customerId = customerId.strip();
        this.orderDate = Objects.requireNonNull(orderDate, "orderDate must not be null");
    }

    // --- behaviour begin ---
    /**
     * Adds a product, or increases the quantity when it is already on the
     * order.
     */
    public void addLine(int productId, Money unitPrice, int quantity, BigDecimal discount) {
        requireStatus(OrderStatus.DRAFT, "add a line");

        int existing = indexOfProduct(productId);
        if (existing >= 0) {
            lines.set(existing, lines.get(existing).withAdditionalQuantity(quantity));
            return;
        }
        if (lines.size() == MAX_LINES) {
            throw new IllegalStateException("an order cannot hold more than " + MAX_LINES + " lines");
        }
        lines.add(new OrderLine(productId, unitPrice, quantity, discount));
    }

    public void removeLine(int productId) {
        requireStatus(OrderStatus.DRAFT, "remove a line");
        int index = indexOfProduct(productId);
        if (index < 0) {
            throw new IllegalArgumentException("product is not on this order: " + productId);
        }
        lines.remove(index);
    }

    /**
     * Sets the destination. A value object is replaced as a whole — there is no
     * setShipCity(), because half an address is not an address.
     */
    public void shipTo(Address address) {
        requireStatus(OrderStatus.DRAFT, "change the shipping address");
        shippingAddress = Objects.requireNonNull(address, "address must not be null");
    }

    public void confirm() {
        requireStatus(OrderStatus.DRAFT, "confirm");
        if (lines.isEmpty()) {
            throw new IllegalStateException("an order without lines cannot be confirmed");
        }
        if (shippingAddress == null) {
            throw new IllegalStateException("an order without a shipping address cannot be confirmed");
        }
        status = OrderStatus.CONFIRMED;
    }

    public void ship(LocalDate date) {
        requireStatus(OrderStatus.CONFIRMED, "ship");
        Objects.requireNonNull(date, "shipped date must not be null");
        if (date.isBefore(orderDate)) {
            throw new IllegalArgumentException("shipped date cannot precede the order date");
        }
        shippedDate = date;
        status = OrderStatus.SHIPPED;
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("a shipped order cannot be cancelled");
        }
        status = OrderStatus.CANCELLED;
    }

    // --- behaviour end ---    
    public Money total() {
        return lines.stream()
                .map(OrderLine::lineTotal)
                .reduce(Money::plus)
                .orElse(Money.tl("0"));
    }

    // --- state begin ---
    public int orderId() {
        return orderId;
    }

    public String customerId() {
        return customerId;
    }

    public LocalDate orderDate() {
        return orderDate;
    }

    public OrderStatus status() {
        return status;
    }

    public Optional<Address> shippingAddress() {
        return Optional.ofNullable(shippingAddress);
    }

    public Optional<LocalDate> shippedDate() {
        return Optional.ofNullable(shippedDate);
    }

    /**
     * Defensive copy: callers cannot reach into the aggregate.
     */
    public List<OrderLine> lines() {
        return List.copyOf(lines);
    }
    // --- state end ---

    // --- helpers begin ---
    private int indexOfProduct(int productId) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).productId() == productId) {
                return i;
            }
        }
        return -1;
    }

    private void requireStatus(OrderStatus expected, String action) {
        if (status != expected) {
            throw new IllegalStateException(
                    "cannot " + action + " an order in status " + status);
        }
    }

    // When remove the following methods, the equalitySemantics() test in OrderTest will fail.
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Order order)) {
            return false;
        }
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(orderId);
    }
    // --- helpers end ---
}
