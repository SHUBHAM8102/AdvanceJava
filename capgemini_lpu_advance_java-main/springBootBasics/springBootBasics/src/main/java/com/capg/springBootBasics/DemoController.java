package com.capg.springBootBasics;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	@Autowired
	CarJpaRepository carjpa;
	
	//@GetMapping("/a")
	@PostMapping("/a")
	//@ResponseBody   if we re using RestController then noo need to use this it will treat it as data
	public List<String> hello() {
		return List.of("Miller","Mahi","Yuvraj","Sachin","Abhisek");
	}
	@PostMapping("/add")
	public String createPlayer(@RequestBody Cricketer c) {
		System.out.println(c);
		return c.toString();
	}
	
	@PostMapping("/car")
	public String createCar(@RequestBody Car c) {
//		System.out.println(c);
		
		Car car=carjpa.save(c);
		return car.toString();
	}
	@PostMapping("/per")
	public String createPerson(@RequestBody Person p) {
		System.out.println(p);
		return p.toString();
	
			
		
	}
	@PostMapping("/coll")
	public String createCollege(@RequestBody College c) {
		System.out.println(c);
		return c.toString();
		
	}
	
	@GetMapping("/find-id")
	public String  getById(@RequestParam int id) {
		Optional<Car> option=carjpa.findById( id);
		return option.isPresent()?option.get().toString():"Data does not exist";
	}
	//@RequestParam and @PathVariable both are used to 
	
	@DeleteMapping("/delete-id/{id}")
	//http://localhost:8080/delete-id/1
	public boolean deleteCar(@PathVariable int id) {
		Optional<Car> option = carjpa.findById(id);
		if(option.isPresent()) {
			carjpa.delete(option.get());
			return true;
		}
		else {
			return false;
		}
	}
	
	@PutMapping("/update-car/{id}")
	public boolean updateCar(@PathVariable int id,@RequestBody Car c) {
		Optional<Car> option = carjpa.findById(id);
		if(option.isPresent()) {
		Car car=option.get();
		car.setBrand(c.getBrand());
		car.setPrice(c.getPrice());
		carjpa.save(car);
		return true;
		}
		else {
			return false;
		}
		
		
	}
	
	
	
	//-->{"brand":"A",
	@PatchMapping("/update-car/{id}")
	public boolean updateCarData(@PathVariable int id,@RequestBody Car c) {
		Optional<Car> option = carjpa.findById(id);
		if(option.isPresent()) {
			Car car=option.get();
			if(c.getBrand()!=null) {
				car.setBrand(c.getBrand());
			}
			else if(c.getPrice()!=0.0) {
				car.setPrice(c.getPrice());
			}
			carjpa.save(car);
			return true;
		}
		else {
			return false;
		}
	}
	
	
	//finding car by price
	@GetMapping("/find-price/{price}")
	public Car getCarByPrice(@PathVariable double price) {
		return carjpa.getByPrice(price);
	}
	
	
	@DeleteMapping("/car/delete-by-brand/{brand}")
    public String deleteCarByBrand(@PathVariable String brand) {

        carjpa.deleteByBrand(brand);

        return "Cars deleted with brand: " + brand;
    }

}
