package org.example.Amazon;

import org.example.Amazon.Cost.ExtraCostForElectronics;
import org.example.Amazon.Cost.RegularCost;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AmazonIntegrationTest {

    private Database db;
    private ShoppingCartAdaptor adaptor;

    @BeforeAll
    void setupAll() {
        db = new Database();
    }

    @BeforeEach
    void resetDb() {
        db.resetDatabase();
        adaptor = new ShoppingCartAdaptor(db);
    }

    @AfterAll
    void teardown() {
        db.close();
    }

    @Test
    @DisplayName("specification-based")
    void calculate_withRealAdaptor_andCosts_matchesExpectedTotal() {
        // arrange: add an electronic item and a book
        Item phone = new Item(org.example.Amazon.Cost.ItemType.ELECTRONIC, "Phone", 1, 200.0);
    Item book = new Item(org.example.Amazon.Cost.ItemType.OTHER, "Book", 2, 10.0);

        adaptor.add(phone);
        adaptor.add(book);

        Amazon amazon = new Amazon(adaptor, Arrays.asList(new RegularCost(), new ExtraCostForElectronics()));

        // act
        double total = amazon.calculate();

        // assert: regular cost = 200 + (2*10)=220, extra for electronics = 7.5
        assertThat(total).isEqualTo(227.5);
    }

    @Test
    @DisplayName("structural-based")
    void getItems_returnsPersistedItems() {
        // arrange
    Item t = new Item(org.example.Amazon.Cost.ItemType.OTHER, "ToyCar", 3, 5.0);
        adaptor.add(t);

        // act
        var items = adaptor.getItems();

        // assert
        assertThat(items).hasSize(1);
        assertThat(items.get(0).getName()).isEqualTo("ToyCar");
        assertThat(items.get(0).getQuantity()).isEqualTo(3);
    }
}
