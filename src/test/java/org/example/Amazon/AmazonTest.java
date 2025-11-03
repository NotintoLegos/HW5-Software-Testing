package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class AmazonTest {

    @Mock
    ShoppingCart carts;

    @Mock
    PriceRule rule1;

    @Mock
    PriceRule rule2;

    @Test
    void calculate_aggregatesAllRules() {
        // arrange
        Item item = mock(Item.class);
        List<Item> items = Arrays.asList(item);
        when(carts.getItems()).thenReturn(items);

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
    void calculate_withNoRules_returnsZero() {
        // arrange
        Amazon amazon = new Amazon(carts, Collections.emptyList());

        // act
        double total = amazon.calculate();

        // assert
        assertThat(total).isEqualTo(0.0);
        // no rules to verify calls on
    }

    @Test
    void addToCart_delegatesToShoppingCart() {
        // arrange
        Item item = mock(Item.class);
        Amazon amazon = new Amazon(carts, Collections.emptyList());

        // act
        amazon.addToCart(item);

        // assert
        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(carts, times(1)).add(captor.capture());
        assertThat(captor.getValue()).isSameAs(item);
    }
}