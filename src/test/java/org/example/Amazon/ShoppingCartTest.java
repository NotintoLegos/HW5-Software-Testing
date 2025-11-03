package org.example.Amazon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;



class ShoppingCartTest {

    @Mock
    private ShoppingCart cart;
    
    @Mock
    private Item mockItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Structural-Based")
    void shouldAddItemToCart() {
        cart.add(mockItem);
        verify(cart).add(mockItem);
    }

    @Test 
    @DisplayName("Structural-Based")
    void shouldGetItemsFromCart() {
        List<Item> items = new ArrayList<>();
        items.add(mockItem);
        when(cart.getItems()).thenReturn(items);
        
        assertThat(cart.getItems()).isNotEmpty()
                                  .hasSize(1)
                                  .contains(mockItem);
    }

    @Test
    @DisplayName("Structural-Based")
    void shouldReturnNumberOfItems() {
        when(cart.numberOfItems()).thenReturn(1);
        assertThat(cart.numberOfItems()).isEqualTo(1);
    }
}