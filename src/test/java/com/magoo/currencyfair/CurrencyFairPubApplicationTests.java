package com.magoo.currencyfair;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.magoo.currencyfair.pub.CurrencyFairPubApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CurrencyFairPubApplication.class)
@WebAppConfiguration
public class CurrencyFairPubApplicationTests {

	@Test
	public void contextLoads() {
	}

}
