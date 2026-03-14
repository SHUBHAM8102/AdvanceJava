package pom.capgemini.orderservice.service;

import pom.capgemini.orderservice.entity.Order;
import pom.capgemini.orderservice.repository.OrderRepository;
import pom.capgemini.orderservice.dto.OrderEvent;
import pom.capgemini.orderservice.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        OrderEvent orderEvent = new OrderEvent(savedOrder.getOrderId(), savedOrder.getBookId(),
                                                 savedOrder.getQuantity(), "ORDER_PLACED");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(orderEvent);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "order.created", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Order updateOrder(Long orderId, Order orderDetails) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            if (orderDetails.getBookId() != null) {
                existingOrder.setBookId(orderDetails.getBookId());
            }
            if (orderDetails.getQuantity() != null) {
                existingOrder.setQuantity(orderDetails.getQuantity());
            }
            if (orderDetails.getTotalPrice() != null) {
                existingOrder.setTotalPrice(orderDetails.getTotalPrice());
            }
            if (orderDetails.getStatus() != null) {
                existingOrder.setStatus(orderDetails.getStatus());
            }
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}

