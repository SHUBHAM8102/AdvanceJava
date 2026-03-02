package com.onetoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class User {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	private static EntityManager em = emf.createEntityManager();
	private static EntityTransaction et = em.getTransaction();
	
	public static void main(String[] args) {
//		findCar();
		deleteCar();
	}
	
	public static void deleteCar() {
		et.begin();

//		em.createQuery(
//		        "DELETE FROM Engine e WHERE e.id = :id")
//		        .setParameter("id", 100)
//		        .executeUpdate();
		// we cannot directly delete here it gives foreign key constraints exception
		// if we want to delete only engine object then we cannot directly delete that instead of that we need to null the engine_id in car and then delete engine 
		
		int updated = em.createQuery("update Car c set c.engine = null where c.engine.id=?1")
			.setParameter(1, 100)
			.executeUpdate(); // this will make null in the car class engine but it wont change anything in engine
		
		int deleted = em.createQuery("delete from Engine e where e.id = :id")
			.setParameter("id", 100)
			.executeUpdate();
		
		et.commit();
	}
	
	
	public static void findCar() {
		Car car = em.find(Car.class, 1);
		
		if (car != null) {
//			System.out.println("Car Brand: " + car.getBrand());
//			System.out.println("Model: " + car.getModel());
//			System.out.println("Price: " + car.getPrice());
//			
//			Engine engine = car.getEngine();
//			System.out.println("Engine Type: " + engine.getType());
			System.out.println(car);
		} else {
			System.out.println("Car not found");
		}
	}
	
	public static void insertCarAndEngine() {
		
	
		Engine e = new Engine();
		e.setId(100);
		e.setType("V8");
		e.setFuelType("petrol");
		e.setMileage("12");
		e.setcc("3000");
		
		Car c = new Car();
		c.setId(1);
		c.setBrand("Volkswagen");
		c.setModel("polo gt10");
		c.setModel("fijbv");
		c.setPrice(20202020);
		c.setEngine(e);
		
		et.begin();
		em.persist(e);
		em.persist(c);
		et.commit();
		
		
	}
	
}
