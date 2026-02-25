package com.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserServiceTest {
	
	@Test
	public void testGetTypeOfCustomer() {
		UsersDao ud = new UsersDao();
		UserService userService = new UserService(ud);
		String actualRes = userService.typeOfUser(1);
		assertEquals("premium user", actualRes);
	}
}
