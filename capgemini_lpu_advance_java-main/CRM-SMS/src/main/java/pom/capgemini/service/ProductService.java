package pom.capgemini.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import pom.capgemini.entity.Product;

public class ProductService {

    private EntityManager entityManager;

    public ProductService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Add a new product
     */
    public Long addProduct(String name, double price) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Product product = new Product(name, price);
            entityManager.persist(product);
            transaction.commit();
            System.out.println("✓ Product added successfully: " + product.getName() + " - Price: " + product.getPrice());
            return product.getId();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error adding product: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get product by ID
     */
    public Product getProduct(Long productId) {
        try {
            return entityManager.find(Product.class, productId);
        } catch (Exception e) {
            System.out.println("✗ Error retrieving product: " + e.getMessage());
            return null;
        }
    }

    /**
     * Update product
     */
    public boolean updateProduct(Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(product);
            transaction.commit();
            System.out.println("✓ Product updated successfully");
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error updating product: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete product
     */
    public boolean deleteProduct(Long productId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Product product = entityManager.find(Product.class, productId);
            if (product == null) {
                System.out.println("✗ Product not found with ID: " + productId);
                transaction.rollback();
                return false;
            }
            entityManager.remove(product);
            transaction.commit();
            System.out.println("✓ Product deleted successfully");
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error deleting product: " + e.getMessage());
            return false;
        }
    }
}

