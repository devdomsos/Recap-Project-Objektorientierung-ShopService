import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) throws ProductDoesNotExist {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new ProductDoesNotExist("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
            }
            products.add(productToOrder.get());
        }
        Instant orderTimestamp = Instant.now();
        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, orderTimestamp);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> filterOrders(OrderStatus orderStatus) {
        return orderRepo.getOrders()
                .stream()
                .filter(o -> orderStatus == o.orderStatus()).toList();
    }

    public Order updateOrderStatusById(String orderId, OrderStatus orderStatus) {
        Order orderToUpdate = orderRepo.getOrderById(orderId);
        return orderToUpdate.withOrderStatus(orderStatus);
    }
}
