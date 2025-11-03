package org.example.Barnes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;



class BarnesAndNobleTest {

    @Test
    @DisplayName("Specification-Based")
    void getPriceForCart_returnsNull_whenOrderIsNull() {
        BookDatabase bookDatabase = mock(BookDatabase.class);
        BuyBookProcess process = mock(BuyBookProcess.class);

        BarnesAndNoble bn = new BarnesAndNoble(bookDatabase, process);

        PurchaseSummary result = bn.getPriceForCart(null);

        assertThat(result).isNull();
        verifyNoInteractions(bookDatabase, process);
    }

    @Test
    @DisplayName("Specification-Based")
    void getPriceForCart_returnsSummary_andInvokesProcess_forSingleBook_whenQuantitySufficient() {
        BookDatabase bookDatabase = mock(BookDatabase.class);
        BuyBookProcess process = mock(BuyBookProcess.class);
        Book book = mock(Book.class);

        when(bookDatabase.findByISBN("isbn-1")).thenReturn(book);
        when(book.getQuantity()).thenReturn(5);

        BarnesAndNoble bn = new BarnesAndNoble(bookDatabase, process);

        PurchaseSummary summary = bn.getPriceForCart(Map.of("isbn-1", 3));

        assertThat(summary).isNotNull();
        verify(bookDatabase).findByISBN("isbn-1");
        verify(process).buyBook(book, 3);
    }

    @Test
    @DisplayName("Specification-Based")
    void getPriceForCart_usesAvailableQuantity_andMarksUnavailable_whenRequestedMoreThanStock() {
        BookDatabase bookDatabase = mock(BookDatabase.class);
        BuyBookProcess process = mock(BuyBookProcess.class);
        Book book = mock(Book.class);

        when(bookDatabase.findByISBN("isbn-2")).thenReturn(book);
        when(book.getQuantity()).thenReturn(2);

        BarnesAndNoble bn = new BarnesAndNoble(bookDatabase, process);

        PurchaseSummary summary = bn.getPriceForCart(Map.of("isbn-2", 7));

        assertThat(summary).isNotNull();
        // should call buyBook with available quantity (2)
        verify(process).buyBook(book, 2);
        verify(bookDatabase).findByISBN("isbn-2");
    }

    @Test
    @DisplayName("Specification-Based")
    void getPriceForCart_handlesMultipleDifferentBooks() {
        BookDatabase bookDatabase = mock(BookDatabase.class);
        BuyBookProcess process = mock(BuyBookProcess.class);
        Book bookA = mock(Book.class);
        Book bookB = mock(Book.class);

        when(bookDatabase.findByISBN("A")).thenReturn(bookA);
        when(bookDatabase.findByISBN("B")).thenReturn(bookB);
        when(bookA.getQuantity()).thenReturn(4);
        when(bookB.getQuantity()).thenReturn(1);

        BarnesAndNoble bn = new BarnesAndNoble(bookDatabase, process);

        PurchaseSummary summary = bn.getPriceForCart(Map.of("A", 2, "B", 1));

        assertThat(summary).isNotNull();
        verify(bookDatabase).findByISBN("A");
        verify(bookDatabase).findByISBN("B");
        verify(process).buyBook(bookA, 2);
        verify(process).buyBook(bookB, 1);
    }
}