package com.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

public class PriceServiceTest {
	
	@Test
	public void testMobileDiscount() {
		
		DiscountRepository repoMock = mock(DiscountRepository.class);
		when(repoMock.getDiscountPercentage("MOB")).thenReturn(10.0);
		
		PriceService service = new PriceService(repoMock);
		
		double result = service.calculateFinalPrice(1000, "MOB");
		assertEquals(900.0, result);
		
	}
	
    @Test
    public void testLaptopDiscount() {

        DiscountRepository repoMock = mock(DiscountRepository.class);
        when(repoMock.getDiscountPercentage("LAP")).thenReturn(20.0);
        PriceService service = new PriceService(repoMock);

        double result = service.calculateFinalPrice(2000, "LAP");
        assertEquals(1600.0, result);

    }
    
    @Test
    public void testRepositoryException() {
    		DiscountRepository repoMock = mock(DiscountRepository.class);
    		//here Runtime exception is unchecked means Java does NOT force you to handle or declare
    		when(repoMock.getDiscountPercentage("MOB")).thenThrow(new RuntimeException("Database Error"));
    		
    		PriceService service = new PriceService(repoMock);
    		
    		assertThrows(RuntimeException.class, () -> {
    			service.calculateFinalPrice(1000, "MOB");
    		});
    		
    		
    		
    }
	
}
