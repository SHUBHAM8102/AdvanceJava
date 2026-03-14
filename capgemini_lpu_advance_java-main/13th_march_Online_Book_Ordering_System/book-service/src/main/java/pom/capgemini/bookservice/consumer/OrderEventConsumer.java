package pom.capgemini.bookservice.consumer;

import pom.capgemini.bookservice.dto.OrderEvent;
import pom.capgemini.bookservice.service.BookService;
import pom.capgemini.bookservice.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderEventConsumer {

    @Autowired
    private BookService bookService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeOrderEvent(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderEvent orderEvent = objectMapper.readValue(message, OrderEvent.class);
            bookService.reduceStock(orderEvent.getBookId(), orderEvent.getQuantity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

