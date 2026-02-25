package com.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	UserDao daoMock;
	
	@InjectMocks
	UserService service;
	
	@Test
	public void typeOfUser() {
//		UserDao daomock = mock(UserDao.class);
		
		User fakeObject = new User();
		fakeObject.setId(1);
		fakeObject.setName("puneeth");
		fakeObject.setBalance(1000);
		
		//mention what object should return
		when(daoMock.findById(1)).thenReturn(fakeObject);
		
		//injecting mock
//		service = new UserService(daoMock);
		String res = service.typeOfUser(1);
		
		assertEquals("premium user", res);
		
	}
}
