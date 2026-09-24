/**
 * Business objects: entities, value objects and the rules that guard them.
 *
 * <p>This package depends on {@code java.base} only. No persistence, no HTTP,
 * no framework annotations, no logging framework — a domain object must be
 * testable with a plain {@code new}.
 *
 * <p>Aggregate rule: an aggregate references another aggregate by identity
 * ({@code String customerId}), never by object reference. It owns its own parts
 * ({@code OrderLine}) and its value objects ({@code Address}, {@code Money}).
 * 
 * <table>
 * <tr>
 * <th>Referance</th>
 * <th>How</th>
 * <th>Why</th>
 * </tr>
 * <tr>
 * <td>Order -> Customer</td>
 * <td>by identity ({@code String customerId})</td>
 * <td>Seperate aggregates, seperate modules(probably), seperate transaction</td>
 * </tr>
 * <tr>
 * <td>Order -> OrderLine</td>
 * <td>by ownership ({@code List<OrderLine> lines})</td>
 * <td>Order owns its lines; they are part of the aggregate. If there are no lines, does not make sense to have an order</td>
 * </tr>
 * <tr>
 * <td>Order -> Address</td>
 * <td>by ownership ({@code Address shippingAddress})</td>
 * <td>Value object; no identity, copied by value</td>
 * </tr>
 * <tr>
 * <td>Order -> Money</td>
 * <td>by ownership ({@code Money unitPrice})</td>
 * <td>Value object; no identity, copied by value</td>
 * </tr>
 * </table>
 *
 * <p>See ADR-0003 in farm-java-lab-northwind for the layer matrix this prepares for.
 */
package com.lectures.business.objects.domain;
