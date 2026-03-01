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
public class CustomerContoller {
	@Autowired
	CustomerJpaRepository cjr;
	
	@PostMapping("/addcus")
	public String createCustomer(@RequestBody Customer c) {
		System.out.println(c);
		return c.toString();
	}
	@GetMapping("/customer/{id}")
	public Customer getCustomerById(@PathVariable int id) {
		Optional<Customer> c = cjr.findById(id);
	
		return c.get();
		
	}
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers(){
		return cjr.findAll();
	}
	
	@PutMapping("/update/{id}")
	public boolean updateCustomerById(@PathVariable int id, @RequestBody Customer c) {
		Optional<Customer> optional = cjr.findById(id);
		
		if(optional.isPresent()) {
			Customer customer = optional.get();
			customer.setPhone(c.getPhone());
			customer.setEmail(c.getEmail());
			customer.setName(c.getName());
			customer.setAge(c.getAge());
			customer.setGender(c.getGender());
			customer.setDob(c.getDob());

			
			cjr.save(customer);
			return true;
			
		}
		return false;
		
	}
	
	@PatchMapping("/update-phone/{id}")
	public boolean updatePhoneById(@PathVariable int id, @RequestBody Customer c) {
		Optional<Customer> optional = cjr.findById(id);
		
		if(optional.isPresent()) {
			Customer customer = optional.get();
			customer.setPhone(c.getPhone());
			
			cjr.save(customer);
			return true;
			
		}
		return false;
		
	}
	
	@DeleteMapping("/delete/{id}")
	public boolean delete(@PathVariable int id) {
		Customer c = cjr.findById(id).get();
		if(c != null) {
			cjr.delete(c);
			return true;
		}
		return false;
	}
	
	 @GetMapping("/customer/email/{email}")
	 public Customer getByEmail(@PathVariable String email) {
	     return cjr.findByEmail(email);
	 }

	 @GetMapping("/customer/name-email")
	 public Customer getByNameAndEmail(@RequestParam String name,
	                                   @RequestParam String email) {
	     return cjr.findByNameAndEmail(name, email);
	 }

	 @GetMapping("/customer/name-or-email")
	 public Customer getByNameOrEmail(@RequestParam String name,
	                                  @RequestParam String email) {
	     return cjr.findByNameOrEmail(name, email);
	 }

	 @GetMapping("/customer/id-between")
	 public List<Customer> getByIdBetween(@RequestParam Long start,
	                                      @RequestParam Long end) {
	     return cjr.findByIdBetween(start, end);
	 }

	    @GetMapping("/customer/age-greater/{age}")
	    public List<Customer> getByAgeGreaterThan(@PathVariable Integer age) {
	        return cjr.findByAgeGreaterThan(age);
	    }

	    @GetMapping("/customer/id-less/{id}")
	    public List<Customer> getByIdLessThan(@PathVariable Long id) {
	        return cjr.findByIdLessThan(id);
	    }

	    @GetMapping("/customer/email-null")
	    public List<Customer> getEmailNull() {
	        return cjr.findByEmailIsNull();
	    }

	    @GetMapping("/customer/email-not-null")
	    public List<Customer> getEmailNotNull() {
	        return cjr.findByEmailIsNotNull();
	    }

	    @GetMapping("/customer/name-contains/{keyword}")
	    public List<Customer> getByNameContaining(@PathVariable String keyword) {
	        return cjr.findByNameContaining(keyword);
	    }

	    @GetMapping("/customer/name-starts/{prefix}")
	    public List<Customer> getByNameStartingWith(@PathVariable String prefix) {
	        return cjr.findByNameStartingWith(prefix);
	    }

	    @GetMapping("/customer/name-ends/{suffix}")
	    public List<Customer> getByNameEndingWith(@PathVariable String suffix) {
	        return cjr.findByNameEndingWith(suffix);
	    }

	    @GetMapping("/customer/name-ignorecase/{name}")
	    public List<Customer> getByNameIgnoreCase(@PathVariable String name) {
	        return cjr.findByNameIgnoreCase(name);
	    }

	    @GetMapping("/customer/latest")
	    public Customer getLatestCustomer() {
	        return cjr.findTop1ByOrderByIdDesc();
	    }

	    @GetMapping("/customer/top3/{name}")
	    public List<Customer> getTop3ByName(@PathVariable String name) {
	        return cjr.findFirst3ByName(name);
	    }
	

}
