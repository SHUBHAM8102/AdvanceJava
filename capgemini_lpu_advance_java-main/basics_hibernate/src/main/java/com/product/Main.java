package com.product;

public class Main {
	public static void main(String[] args) {
		ProductDao dao=new ProductDao();
		
		Product p=new Product();
		p.setId(2);
		p.setName("Pen");
		p.setPrice(10);
		p.setQuantity(10);
		
		dao.insertProduct(p);
		
//		 p.setPrice(55000);
//	     p.setQuantity(10);
//	     dao.update(p);
//	     
//	      dao.deleteById(1);

		
	}
}