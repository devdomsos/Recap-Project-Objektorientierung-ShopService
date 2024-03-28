import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void testAddOrder_Success() throws ProductDoesNotExist {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        Order actual = null;

        //WHEN
        try {
            actual = shopService.addOrder(productsIds);
        } catch (ProductDoesNotExist e) {
            fail("Error: " + e.getMessage());
        }

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }
    @Test
    void testAddOrder_whenInvalidProductId_expectProductDoesNotExistException() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        Order actual = null;

        //WHEN
        try {
            actual = shopService.addOrder(productsIds);
        } catch (ProductDoesNotExist e) {
            //THEN
            assertThrows(ProductDoesNotExist.class, () -> shopService.addOrder(productsIds));
        }
    }

    @Test
    void filterOrders_Test() {
        //GIVEN
        ShopService shopService = new ShopService();
        OrderStatus orderStatus = OrderStatus.PROCESSING;
        List<String> productsIds = List.of("1");
        Order newOrder = null;

        //WHEN
        try {
            newOrder = shopService.addOrder(productsIds);
        } catch (ProductDoesNotExist e) {
            fail("Product does not exist: " + e.getMessage());
        }

        List<Order> actual = shopService.filterOrders(orderStatus);

        //THEN
        List<Order> expected = List.of(
                new Order(newOrder.id(), List.of(new Product("1", "Apfel")), orderStatus)
        );
        assertEquals(expected, actual);
    }

    @Test
    void testUpdateOrderStatusById_whenValidProductId_expectSuccess() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        OrderStatus newOrderStatus = OrderStatus.IN_DELIVERY;
        Order newOrder = null;
        //WHEN
        try {
            newOrder = shopService.addOrder(productsIds);
        } catch (ProductDoesNotExist e) {
            fail("Product does not exist: " + e.getMessage());
        }
        Order updatedOrder = shopService.updateOrderStatusById(newOrder.id(), newOrderStatus);
        // THEN
        assertEquals(newOrderStatus, updatedOrder.orderStatus());
    }
}
