package com.capgemini.customerapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.customerapp.entity.Customer;
import com.capgemini.customerapp.repository.CustomerRepository;
import com.capgemini.customerapp.service.impl.CustomerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CustomerServiceTest {

	@Mock
	CustomerRepository customerRepository;

	@InjectMocks
	CustomerServiceImpl customerService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAuthenticateCustomer() throws Exception {
		Customer customer = new Customer(1234, "Reshetha", "reshethayadav@gmail.com", "Airoli, Mumbai", "15");
		when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
		assertEquals(customerService.authentication(customer), customer);
	}

	@Test
	public void testEditCustomer() throws Exception {
		Customer customer = new Customer(1234, "Reshetha", "reshethayadav@gmail.com", "Airoli, Mumbai", "15");
		when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
		when(customerRepository.save(customer)).thenReturn(customer);
		assertEquals(customerService.editCustomer(customer), customer);

	}

	@Test
	public void testGetCustomer() throws Exception {
		Customer customer = new Customer(1234, "Reshetha", "reshethayadav@gmail.com", "Airoli, Mumbai", "15");
		when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
		assertEquals(customerService.getCustomerById(12345), customer);
	}

	@Test
	public void testGetAllCustomer() throws Exception {
		List<Customer> customers = new ArrayList<>();
		Customer customer = new Customer(1234, "Reshetha", "reshethayadav@gmail.com", "Airoli, Mumbai", "15");
		customers.add(customer);
		when(customerRepository.findAll()).thenReturn(customers);
		assertEquals(customerService.getAllCustomers(), customers);
	}

	@Test
	public void testDeleteCustomer() throws Exception {
		Customer customer = new Customer(1234, "Reshetha", "reshethayadav@gmail.com", "Airoli, Mumbai", "15");
		when(customerRepository.findById(1234)).thenReturn(Optional.of(customer));
		customerService.deleteCustomer(customer);
		verify(customerRepository, times(1)).delete(customer);
	}

	@Test
	public void testAddCustomer() throws Exception {
		Customer customer = new Customer(123456, "Reshetha", "reshethayadav@gmail.com", "Airoli, Mumbai", "15");
		when(customerRepository.save(customer)).thenReturn(customer);
		assertEquals(customerService.addCustomer(customer), customer);
	}

}
