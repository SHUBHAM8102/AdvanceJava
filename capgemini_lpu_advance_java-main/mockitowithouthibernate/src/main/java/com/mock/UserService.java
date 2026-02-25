package com.mock;

public class UserService {
	
	private UserDao dao;

	public UserService(UserDao dao) {
		super();
		this.dao = dao;
	}
	
	public String typeOfUser(int id) {
		
		User u = dao.findById(id);
		
		if(u.getBalance() > 1000 && u.getBalance() < 2000) {
			return "new user";
		}else if(u.getBalance() > 2000 && u.getBalance() < 3000) {
			return "regular user";
		}else {
			return "premium user";
		}
		
	}
	
}
