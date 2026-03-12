package com.bookstore.orderservice.service;

import com.bookstore.orderservice.client.BookClient;
import com.bookstore.orderservice.dto.BookDTO;
import com.bookstore.orderservice.dto.CreateOrderRequest;
import com.bookstore.orderservice.dto.UpdateOrderStatusRequest;
import com.bookstore.orderservice.model.Order;
import com.bookstore.orderservice.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookClient bookClient;

    public OrderService(OrderRepository orderRepository, BookClient bookClient) {
        this.orderRepository = orderRepository;
        this.bookClient = bookClient;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public Order createOrder(CreateOrderRequest request) {
        BookDTO book;
        try {
            book = bookClient.getBookById(request.getBookId());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found");
        }

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than zero");
        }

        if (book.getQuantity() != null && request.getQuantity() > book.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock");
        }

        Order order = new Order();
        order.setBookId(request.getBookId());
        order.setCustomerName(request.getCustomerName());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(book.getPrice() * request.getQuantity());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long id, UpdateOrderStatusRequest request) {
        Order existing = getOrderById(id);
        existing.setStatus(request.getStatus());
        return orderRepository.save(existing);
    }

    public void deleteOrder(Long id) {
        Order existing = getOrderById(id);
        orderRepository.delete(existing);
    }
}

