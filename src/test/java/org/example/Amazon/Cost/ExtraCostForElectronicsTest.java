package org.example.Amazon.Cost;

import org.example.Amazon.Item;
import org.junit.jupiter.api.DisplayName;
//import org.example.Amazon.Cost.ItemType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;





class ExtraCostForElectronicsTest {

    private final ExtraCostForElectronics rule = new ExtraCostForElectronics();

    @Test
    @DisplayName("Specification-Based")
    void returnsExtraWhenCartContainsAnElectronic() {
        Item electronic = Mockito.mock(Item.class);
        Mockito.when(electronic.getType()).thenReturn(ItemType.ELECTRONIC);

        double result = rule.priceToAggregate(List.of(electronic));

        assertThat(result).isEqualTo(7.5);
    }

    @Test
    @DisplayName("Specification-Based")
    void returnsExtraWhenCartContainsMultipleElectronics() {
        Item e1 = Mockito.mock(Item.class);
        Item e2 = Mockito.mock(Item.class);
        Mockito.when(e1.getType()).thenReturn(ItemType.ELECTRONIC);
        Mockito.when(e2.getType()).thenReturn(ItemType.ELECTRONIC);

        double result = rule.priceToAggregate(List.of(e1, e2));

        assertThat(result).isEqualTo(7.5);
    }

    @Test
    @DisplayName("Specification-Based")
    void returnsZeroWhenNoElectronicPresent() {
        Item nonElectronic = Mockito.mock(Item.class);
        // return null to simulate a non-electronic type without depending on other enum values
        Mockito.when(nonElectronic.getType()).thenReturn(null);

        double result = rule.priceToAggregate(List.of(nonElectronic));

        assertThat(result).isZero();
    }

    @Test
    @DisplayName("Specification-Based")
    void returnsZeroForEmptyCart() {
        double result = rule.priceToAggregate(List.of());

        assertThat(result).isZero();
    }
}