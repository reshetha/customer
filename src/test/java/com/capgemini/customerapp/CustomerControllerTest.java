package com.capgemini.customerapp;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.customerapp.comtroller.CustomerController;
import com.capgemini.customerapp.entity.Customer;
import com.capgemini.customerapp.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CustomerControllerTest {

	MockMvc mockMvc;

	@Mock
	CustomerService customerService;

	@InjectMocks
	CustomerController customerController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	public void testAuthenticateCustomer() throws Exception {
		String content = "{\r\n" + "  \"customerId\": 1234,\r\n" + "  \"customerPassword\": \"15\"\r\n" + "}";
		Customer customer = new Customer(1234, "Reshetha", "reshethayadav@gmail.com", "Airoli, Mumbai", "15");
		when(customerService.authentication(Mockito.isA(Customer.class))).thenReturn(customer);
		mockMvc.perform(post("/v1/auth").content(content).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(jsonPath("$.customerEmail").exists())
				.andExpect(jsonPath("$.customerEmail").value("reshethayadav@gmail.com"));
		verify(customerService).authentication(Mockito.isA(Customer.class));
	}

	@Test
	public void testEditCustomer() throws Exception {
		String content = "{\r\n" + 
				"  \"customerId\": 1234,\r\n" + 
				"  \"customerName\": \"Reshetha\",\r\n" + 
				"  \"customerEmail\": \"reshethayadav@gmail.com\",\r\n" + 
				"  \"customerAddress\": \"Airoli, Mumbai\",\r\n" + 
				"  \"customerPassword\": \"15\"\r\n" + 
				"}";
		Customer customer = new Customer(1234, "Reshetha", "reshethayadav@gmail.com", "Airoli, Mumbai", "15");
		when(customerService.editCustomer(Mockito.isA(Customer.class))).thenReturn(customer);
		mockMvc.perform(put("/v1/customer").content(content).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(jsonPath("$.customerEmail").exists())
				.andExpect(jsonPath("$.customerName").value("Reshetha"));
	}

	@Test
	public void testGetCustomer() throws Exception {
		String content = "{\r\n" + 
				"  \"customerId\": 1234,\r\n" + 
				"  \"customerName\": \"Reshetha\",\r\n" + 
				"  \"customerEmail\": \"reshethayadav@gmail.com\",\r\n" + 
				"  \"customerAddress\": \"Airoli, Mumbai\",\r\n" + 
				"  \"customerPassword\": \"15\"\r\n" + 
				"}";
		Customer customer = new Customer(1234, "Reshetha", "reshethayadav@gmail.com", "Airoli, Mumbai", "15");
		when(customerService.getCustomerById(1234)).thenReturn(customer);
		mockMvc.perform(get("/v1/customer/1234").contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(jsonPath("$.customerEmail").exists())
				.andExpect(jsonPath("$.customerName").value("Reshetha"));
	}

	@Test
	public void testDeleteCustomer() throws Exception {
		Customer customer = new Customer(1234, "Reshetha", "reshethayadav@gmail.com", "Airoli, Mumbai", "15");
		when(customerService.getCustomerById(1234)).thenReturn(customer);
		mockMvc.perform(delete("/v1/customer/1234").contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

}
