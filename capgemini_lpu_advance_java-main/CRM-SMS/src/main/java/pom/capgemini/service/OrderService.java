package pom.capgemini.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import pom.capgemini.entity.Customer;
import pom.capgemini.entity.Order;
import pom.capgemini.entity.Product;
import java.time.LocalDate;
import java.util.List;

public class OrderService {

    private EntityManager entityManager;

    public OrderService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Place an order for a customer
     */
    public Long placeOrder(Long customerId, List<Long> productIds) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Customer customer = entityManager.find(Customer.class, customerId);
            if (customer == null) {
                System.out.println("✗ Customer not found with ID: " + customerId);
                transaction.rollback();
                return null;
            }

            Order order = new Order(LocalDate.now(), 0, customer);
            double totalAmount = 0;

            for (Long productId : productIds) {
                Product product = entityManager.find(Product.class, productId);
                if (product != null) {
                    order.getProducts().add(product);
                    totalAmount += product.getPrice();
                } else {
                    System.out.println("⚠ Product not found with ID: " + productId + ", skipping");
                }
            }

            if (order.getProducts().isEmpty()) {
                System.out.println("✗ No valid products provided for order");
                transaction.rollback();
                return null;
            }

            order.setTotalAmount(totalAmount);
            entityManager.persist(order);
            transaction.commit();
            System.out.println("✓ Order placed successfully for customer: " + customer.getName() +
                    " | Total Amount: " + totalAmount);
            return order.getId();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error placing order: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get order by ID
     */
    public Order getOrder(Long orderId) {
        try {
            return entityManager.find(Order.class, orderId);
        } catch (Exception e) {
            System.out.println("✗ Error retrieving order: " + e.getMessage());
            return null;
        }
    }

    /**
     * Calculate order total
     */
    public double calculateOrderTotal(Long orderId) {
        try {
            Order order = entityManager.find(Order.class, orderId);
            if (order != null) {
                return order.getTotalAmount();
            }
        } catch (Exception e) {
            System.out.println("✗ Error calculating order total: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Update order
     */
    public boolean updateOrder(Order order) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(order);
            transaction.commit();
            System.out.println("✓ Order updated successfully");
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error updating order: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete order
     */
    public boolean deleteOrder(Long orderId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Order order = entityManager.find(Order.class, orderId);
            if (order == null) {
                System.out.println("✗ Order not found with ID: " + orderId);
                transaction.rollback();
                return false;
            }
            entityManager.remove(order);
            transaction.commit();
            System.out.println("✓ Order deleted successfully");
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error deleting order: " + e.getMessage());
            return false;
        }
    }
}

