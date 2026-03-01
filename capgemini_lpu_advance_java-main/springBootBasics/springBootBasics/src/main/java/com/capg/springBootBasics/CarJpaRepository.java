package com.capg.springBootBasics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
@Repository
public interface CarJpaRepository extends JpaRepository<Car, Integer>{
	public Car getByPrice(double price);
	
	@Modifying
	@Transactional
	//@Query("delete from Car c where c.brand=:carbrand")
	@Query (value="delete from car where brand=:carbrand",nativeQuery=true)
	public int deleteByBrand(@Param("carbrand")String brand);

}
