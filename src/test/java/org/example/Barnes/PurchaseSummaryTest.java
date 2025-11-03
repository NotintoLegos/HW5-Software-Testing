package org.example.Barnes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;



class PurchaseSummaryTest {

    @Test
    @DisplayName("Structural-Based")
    void testInitialState() {
        PurchaseSummary summary = new PurchaseSummary();
        assertThat(summary.getTotalPrice()).isZero();
        assertThat(summary.getUnavailable()).isEmpty();
    }

    @Test
    @DisplayName("Structural-Based")
    void testAddToTotalPrice() {
        PurchaseSummary summary = new PurchaseSummary();
        summary.addToTotalPrice(100);
        summary.addToTotalPrice(50);
        assertThat(summary.getTotalPrice()).isEqualTo(150);
    }

    @Test
    @DisplayName("Structural-Based")
    void testAddUnavailable() {
        PurchaseSummary summary = new PurchaseSummary();
        Book book = Mockito.mock(Book.class);
        summary.addUnavailable(book, 3);
        Map<Book, Integer> unavailable = summary.getUnavailable();
        assertThat(unavailable).containsEntry(book, 3);
    }

    @Test
    @DisplayName("Structural-Based")
    void testGetUnavailableReturnsUnmodifiableMap() {
        PurchaseSummary summary = new PurchaseSummary();
        Book book = Mockito.mock(Book.class);
        summary.addUnavailable(book, 2);
        Map<Book, Integer> unavailable = summary.getUnavailable();
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> unavailable.put(book, 5))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("Structural-Based")
    void testMultipleUnavailableBooks() {
        PurchaseSummary summary = new PurchaseSummary();
        Book book1 = Mockito.mock(Book.class);
        Book book2 = Mockito.mock(Book.class);
        summary.addUnavailable(book1, 1);
        summary.addUnavailable(book2, 2);
        Map<Book, Integer> unavailable = summary.getUnavailable();
        assertThat(unavailable).hasSize(2)
                .containsEntry(book1, 1)
                .containsEntry(book2, 2);
    }

}