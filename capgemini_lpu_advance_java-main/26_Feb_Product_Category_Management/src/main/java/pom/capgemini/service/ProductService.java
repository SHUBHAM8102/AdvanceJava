package pom.capgemini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pom.capgemini.entity.Product;
import pom.capgemini.exception.CategoryNotFoundException;
import pom.capgemini.exception.InvalidProductDataException;
import pom.capgemini.exception.ProductNotFoundException;
import pom.capgemini.repository.CategoryRepository;
import pom.capgemini.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product createProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new InvalidProductDataException("Product name cannot be empty");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new InvalidProductDataException("Product price must be greater than 0");
        }
        if (product.getCategory() == null || product.getCategory().getCategoryId() == null) {
            throw new InvalidProductDataException("Category ID is required");
        }

        boolean categoryExists = categoryRepository.existsById(product.getCategory().getCategoryId());
        if (!categoryExists) {
            throw new CategoryNotFoundException("Category not found with ID: " + product.getCategory().getCategoryId());
        }

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
    }

    public Product updateProduct(Long productId, Product productDetails) {
        Product product = getProductById(productId);

        if (productDetails.getProductName() != null && !productDetails.getProductName().trim().isEmpty()) {
            product.setProductName(productDetails.getProductName());
        }

        if (productDetails.getPrice() != null && productDetails.getPrice() > 0) {
            product.setPrice(productDetails.getPrice());
        } else if (productDetails.getPrice() != null && productDetails.getPrice() <= 0) {
            throw new InvalidProductDataException("Product price must be greater than 0");
        }

        if (productDetails.getCategory() != null && productDetails.getCategory().getCategoryId() != null) {
            boolean categoryExists = categoryRepository.existsById(productDetails.getCategory().getCategoryId());
            if (!categoryExists) {
                throw new CategoryNotFoundException("Category not found with ID: " + productDetails.getCategory().getCategoryId());
            }
            product.setCategory(productDetails.getCategory());
        }

        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }

    public List<Product> searchProductByName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new InvalidProductDataException("Product name cannot be empty for search");
        }
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        boolean categoryExists = categoryRepository.existsById(categoryId);
        if (!categoryExists) {
            throw new CategoryNotFoundException("Category not found with ID: " + categoryId);
        }
        return productRepository.findByCategoryCategoryId(categoryId);
    }
}

