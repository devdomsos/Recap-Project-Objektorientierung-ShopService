import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void filterOrders_Test() {
        //GIVEN
        ShopService shopService = new ShopService();
        OrderStatus orderStatus = OrderStatus.PROCESSING;
        List<String> productsIds = List.of("1");
        Order newOrder = shopService.addOrder(productsIds);
        //WHEN

        List<Order> actual = shopService.filterOrders(orderStatus);

        //THEN
        List<Order> expected = List.of(
                new Order(newOrder.id(), List.of(new Product("1", "Apfel")), orderStatus)
        );
        assertEquals(expected, actual);
    }
}
