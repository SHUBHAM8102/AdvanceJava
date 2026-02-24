package com.secondlevel;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(
    usage = CacheConcurrencyStrategy.READ_WRITE
)
public class Product {
	@Id
	private int id;
	private String name;
	public Product(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	public Product() {}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}
	
	
	
	

}