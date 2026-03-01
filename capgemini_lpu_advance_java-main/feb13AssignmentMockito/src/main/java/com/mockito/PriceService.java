package com.mockito;

public class PriceService{
	
	private DiscountRepository discountRepository;
	
	public PriceService(DiscountRepository discountRepository) {
		this.discountRepository = discountRepository;
	}
	
	double calculateFinalPrice(double price, String productCode) {
		double discount = discountRepository.getDiscountPercentage(productCode);
		double finalPrice = price - (price * discount / 100);
        return finalPrice;	
	}
	
	
	
	
}
