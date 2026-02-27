package pom.capgemini;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantService {

    private DeliveryService deliveryService;

    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void prepareOrder() {
        System.out.println("Restaurant is preparing the order");
        deliveryService.deliverOrder();
    }
}

