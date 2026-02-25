package com.casestudy1;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Service {
	
	 private UserDao userDao = new UserDao();
	 private OrderDao orderDao = new OrderDao();
	 private PaymentDao paymentDao = new PaymentDao();

	 public void registerUserWithProfile(User user, Profile profile) {

		    // FIRST save profile manually
		    ProfileDao profileDao = new ProfileDao();
		    profileDao.saveProfile(profile);

		    // THEN attach profile to user
		    user.setProfile(profile);

		    // NOW save user
		    userDao.saveUser(user);

		    System.out.println("User registered with profile");
		}

	 
	
	 public void addOrdersToUser(Long userId, List<PurchaseOrder> orders) {

	        User user = userDao.findUser(userId);

	        if (user == null) {
	            System.out.println("User not found");
	            return;
	        }

	        user.setPurchaseOrder(orders);
	        userDao.updateUser(user);

	        System.out.println("Orders added to user");
	  }
	 
	 public void addPaymentToOrder(Long orderId, Payment payment) {

	        PurchaseOrder order = orderDao.findOrder(orderId);

	        if (order == null) {
	            System.out.println("Order not found");
	            return;
	        }

	        payment.setPurchaseOrder(order);
	        paymentDao.savePayment(payment);

	        System.out.println("Payment added to order");
	    }
	 
	 public User fetchUserWithOrders(Long userId) {

	        User user = userDao.findUser(userId);

	        if (user == null) {
	            System.out.println("User not found");
	            return null;
	        }

	        System.out.println("User found with orders");
	        return user;
	    }
	 
	    public void deleteUser(Long userId) {

	        userDao.deleteUser(userId);
	        System.out.println("User deleted");
	    }
	    
	    
	    public void updateOrderAmount(Long orderId, double newAmount) {

	        PurchaseOrder order = orderDao.findOrder(orderId);

	        if (order == null) {
	            System.out.println("Order not found");
	            return;
	        }

	        order.setAmount(newAmount);
	        orderDao.saveOrder(order);   // or create updateOrder()

	        System.out.println("Order amount updated");
	    }
	    
	    public static void main(String[] args) {

	        
	    	Service service = new Service();
	        Profile p = new Profile(1L, "999999", "Hyderabad");
	        User u = new User(1L, "Puneeth", "p@gmail.com");

	        UserDao ud = new UserDao();
	        service.registerUserWithProfile(u, p);
	        
	    }

	    
}
