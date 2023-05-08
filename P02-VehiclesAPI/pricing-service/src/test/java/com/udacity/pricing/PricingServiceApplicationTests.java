package com.udacity.pricing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.udacity.pricing.api.PricingController;
import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PriceException;
import com.udacity.pricing.service.PricingService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class PricingServiceApplicationTests {

	@Autowired
	private PricingController pricingController;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<Price> json;

	@Before
	public void setup() {
		Price price = new Price("USD", new BigDecimal(10000), 1L);
		try {
			given(PricingService.getPrice(any())).willReturn(price);
		} catch (PriceException e) {
		}
	}

	@Test
	public void contextLoads() {
		assertThat(pricingController).isNotNull();
	}

	@Test
	public void findPrice() throws Exception {

		// Add a test to check that the `get` method works by calling
		// a vehicle by ID. This should utilize the car from `getCar()` below.

		Price price = new Price("USD", new BigDecimal(10000), 1L);
		mvc.perform(get("/services/price?vehicleId=1")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString()
				.contentEquals(json.write(price).getJson());
	}

}
