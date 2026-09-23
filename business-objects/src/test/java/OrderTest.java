
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.lectures.business.objects.domain.Address;
import com.lectures.business.objects.domain.Money;
import com.lectures.business.objects.domain.Order;
import com.lectures.business.objects.domain.OrderStatus;

public class OrderTest {

    private static final BigDecimal NO_DISCOUNT = BigDecimal.ZERO;
    private static final LocalDate ORDER_DATE = LocalDate.of(1996, 7, 4);
    private static final Address DESTINATION
            = new Address("59 rue de l'Abbaye", "Reims", "51100", "France");

    private Order newDraft() {
        return new Order(10248, "VINET", ORDER_DATE);
    }

    /**
     * A draft that already satisfies everything confirm() requires.
     */
    private Order newConfirmableDraft() {
        Order order = newDraft();
        order.addLine(11, Money.tl("14.00"), 12, NO_DISCOUNT);
        order.shipTo(DESTINATION);
        return order;
    }

    @Test
    @DisplayName("total sums the lines and applies discounts")
    void totalSumsLines() {
        Order order = newDraft();
        order.addLine(11, Money.tl("14.00"), 12, NO_DISCOUNT);
        order.addLine(42, Money.tl("9.80"), 10, new BigDecimal("0.15"));

        assertThat(order.total()).isEqualTo(Money.tl("251.30"));
    }

    @Test
    @DisplayName("adding the same product twice increases the quantity")
    void sameProductIsMerged() {
        Order order = newDraft();
        order.addLine(11, Money.tl("14.00"), 5, NO_DISCOUNT);
        order.addLine(11, Money.tl("14.00"), 7, NO_DISCOUNT);

        assertThat(order.lines()).hasSize(1);
        assertThat(order.lines().get(0).quantity()).isEqualTo(12);
    }

    @Test
    @DisplayName("an empty order cannot be confirmed")
    void emptyOrderCannotBeConfirmed() {
        assertThatThrownBy(() -> newDraft().confirm())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("without lines");
    }

    @Test
    @DisplayName("an order without a destination cannot be confirmed")
    void destinationIsRequired() {
        Order order = newDraft();
        order.addLine(11, Money.tl("14.00"), 12, NO_DISCOUNT);

        assertThatThrownBy(order::confirm)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("shipping address");
    }

    @Test
    @DisplayName("the destination is replaced as a whole, never edited in part")
    void destinationIsReplaced() {
        Order order = newConfirmableDraft();
        order.shipTo(new Address("Obere Str. 57", "Berlin", "12209", "Germany"));

        assertThat(order.shippingAddress()).contains(
                new Address("Obere Str. 57", "Berlin", "12209", "Germany"));
    }

    @Test
    @DisplayName("a shipped order rejects new lines and a new destination")
    void shippedOrderIsClosed() {
        Order order = newConfirmableDraft();
        order.confirm();
        order.ship(ORDER_DATE.plusDays(12));

        assertThatThrownBy(() -> order.addLine(42, Money.tl("9.80"), 1, NO_DISCOUNT))
                .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> order.shipTo(DESTINATION))
                .isInstanceOf(IllegalStateException.class);
        assertThat(order.status()).isEqualTo(OrderStatus.SHIPPED);
    }

    @Test
    @DisplayName("the line list handed out is a copy")
    void linesAreDefensivelyCopied() {
        Order order = newDraft();
        order.addLine(11, Money.tl("14.00"), 12, NO_DISCOUNT);

        assertThatThrownBy(() -> order.lines().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThat(order.lines()).hasSize(1);
    }

    @Test
    @DisplayName("orders are equal by identity, money by value")
    void equalitySemantics() {
        Order a = newDraft();
        Order b = newDraft();
        b.addLine(11, Money.tl("14.00"), 12, NO_DISCOUNT);

        assertThat(a).isEqualTo(b);                                  // same orderId
        assertThat(Money.tl("18.6")).isEqualTo(Money.tl("18.60")); // same value
    }
}
