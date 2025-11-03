package org.example.Barnes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


class BookTest {

    @Test
    @DisplayName("Structural-Based")
    void constructorAndGettersShouldWork() {
        Book book = new Book("1234567890", 25, 10);
        assertThat(book.getPrice()).isEqualTo(25);
        assertThat(book.getQuantity()).isEqualTo(10);
    }

    @Test 
    @DisplayName("Structural-Based")

    void equalsShouldReturnTrueForSameISBN() {
        Book book1 = new Book("1111", 10, 5);
        Book book2 = new Book("1111", 20, 7);
        assertThat(book1).isEqualTo(book2);
    }

    @Test
    @DisplayName("Structural-Based")
    void equalsShouldReturnFalseForDifferentISBN() {
        Book book1 = new Book("1111", 10, 5);
        Book book2 = new Book("2222", 10, 5);
        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    @DisplayName("Structural-Based")
    void equalsShouldReturnFalseForNull() {
        Book book = new Book("1111", 10, 5);
        assertThat(book.equals(null)).isFalse();
    }

    @Test
    @DisplayName("Structural-Based")
    void equalsShouldReturnFalseForDifferentClass() {
        Book book = new Book("1111", 10, 5);
        String notABook = "NotABook";
        assertThat(book.equals(notABook)).isFalse();
    }

    @Test
    @DisplayName("Structural-Based")
    void hashCodeShouldBeSameForSameISBN() {
        Book book1 = new Book("1111", 10, 5);
        Book book2 = new Book("1111", 20, 7);
        assertThat(book1.hashCode()).isEqualTo(book2.hashCode());
    }

    @Test
    @DisplayName("Structural-Based")
    void hashCodeShouldBeDifferentForDifferentISBN() {
        Book book1 = new Book("1111", 10, 5);
        Book book2 = new Book("2222", 10, 5);
        assertThat(book1.hashCode()).isNotEqualTo(book2.hashCode());
    }
}