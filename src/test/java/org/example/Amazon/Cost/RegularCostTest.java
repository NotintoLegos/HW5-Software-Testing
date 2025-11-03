package org.example.Amazon.Cost;

import org.example.Amazon.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;





public class RegularCostTest {

    @Test
    @DisplayName("Specification-Based")
    public void priceToAggregate_emptyCart_returnsZero() {
        RegularCost sut = new RegularCost();
        double result = sut.priceToAggregate(Collections.emptyList());
        assertThat(result).isEqualTo(0.0);
    }

    @Test
    @DisplayName("Specification-Based")
    public void priceToAggregate_singleItem_correctTotal() {
        Item item = mock(Item.class);
        when(item.getPricePerUnit()).thenReturn(10.5);
        when(item.getQuantity()).thenReturn(2);

        RegularCost sut = new RegularCost();
        double result = sut.priceToAggregate(Arrays.asList(item));

        assertThat(result).isEqualTo(21.0);
        verify(item, times(1)).getPricePerUnit();
        verify(item, times(1)).getQuantity();
    }

    @Test
    @DisplayName("Specification-Based")
    public void priceToAggregate_multipleItems_correctSum() {
        Item a = mock(Item.class);
        when(a.getPricePerUnit()).thenReturn(3.0);
        when(a.getQuantity()).thenReturn(3);

        Item b = mock(Item.class);
        when(b.getPricePerUnit()).thenReturn(2.5);
        when(b.getQuantity()).thenReturn(4);

        Item c = mock(Item.class);
        when(c.getPricePerUnit()).thenReturn(0.0);
        when(c.getQuantity()).thenReturn(10);

        RegularCost sut = new RegularCost();
        List<Item> cart = Arrays.asList(a, b, c);
        double result = sut.priceToAggregate(cart);

        double expected = 3.0 * 3 + 2.5 * 4 + 0.0 * 10;
        assertThat(result).isEqualTo(expected);

        verify(a).getPricePerUnit();
        verify(a).getQuantity();
        verify(b).getPricePerUnit();
        verify(b).getQuantity();
        verify(c).getPricePerUnit();
        verify(c).getQuantity();
    }

    @Test
    @DisplayName("Specification-Based")
    public void priceToAggregate_zeroQuantityProducesZeroContribution() {
        Item item = mock(Item.class);
        when(item.getPricePerUnit()).thenReturn(100.0);
        when(item.getQuantity()).thenReturn(0);

        RegularCost sut = new RegularCost();
        double result = sut.priceToAggregate(Arrays.asList(item));

        assertThat(result).isEqualTo(0.0);
        verify(item).getPricePerUnit();
        verify(item).getQuantity();
    }
}