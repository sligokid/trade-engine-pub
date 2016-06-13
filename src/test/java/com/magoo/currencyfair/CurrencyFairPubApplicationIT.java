package com.magoo.currencyfair;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.magoo.currencyfair.pub.CurrencyFairPubApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CurrencyFairPubApplication.class)
@WebAppConfiguration
public class CurrencyFairPubApplicationIT {

	@Autowired
	private ApplicationContext context;

	@Test
	public void application_context_loads_correctly() {
		assertNotNull(context);
	}

}
