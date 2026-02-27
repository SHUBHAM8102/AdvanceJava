package pom.capgemini;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    private final NotificationService notificationService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    public OrderService(@Qualifier("smsNotification") NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void placeOrder() {
        System.out.println("Order placed");
        restaurantService.prepareOrder();
        notificationService.sendNotification("Your order has been placed successfully!");
    }
}

