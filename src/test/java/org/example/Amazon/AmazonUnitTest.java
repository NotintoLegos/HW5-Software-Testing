package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AmazonUnitTest {

    @Test
    @DisplayName("specification-based")
    void calculate_aggregatesAllRules_specification() {
        // arrange
        Item item = mock(Item.class);
        List<Item> items = Arrays.asList(item);

        ShoppingCart carts = mock(ShoppingCart.class);
        when(carts.getItems()).thenReturn(items);

        PriceRule rule1 = mock(PriceRule.class);
        PriceRule rule2 = mock(PriceRule.class);
        when(rule1.priceToAggregate(items)).thenReturn(10.0);
        when(rule2.priceToAggregate(items)).thenReturn(5.5);

        Amazon amazon = new Amazon(carts, Arrays.asList(rule1, rule2));

        // act
        double total = amazon.calculate();

        // assert
        assertThat(total).isEqualTo(15.5);
        verify(rule1, times(1)).priceToAggregate(items);
        verify(rule2, times(1)).priceToAggregate(items);
    }

    @Test
    @DisplayName("structural-based")
    void addToCart_delegatesToShoppingCart_structural() {
        // arrange
        ShoppingCart carts = mock(ShoppingCart.class);
        Amazon amazon = new Amazon(carts, Collections.emptyList());
        Item item = new Item(org.example.Amazon.Cost.ItemType.OTHER, "X", 1, 1.0);

        // act
        amazon.addToCart(item);

        // assert
        verify(carts, times(1)).add(item);
    }
}
