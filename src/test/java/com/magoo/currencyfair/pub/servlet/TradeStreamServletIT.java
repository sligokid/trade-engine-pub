package com.magoo.currencyfair.pub.servlet;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.magoo.currencyfair.pub.CurrencyFairPubApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CurrencyFairPubApplication.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
public class TradeStreamServletIT {

	@Value("${local.server.port}")
	private int port;

	private RestTemplate template;

	@Before
	public void before() throws Exception {
		template = new TestRestTemplate();
	}

	@Test
	public void tradestream_endpoint_pusblishes_stream() throws Exception {
		URL url = new URL("http://localhost:" + port + TradeStreamServlet.TRADESTREAM_URI);

		ResponseEntity<String> response = template.getForEntity(url.toString(), String.class);

		assertEquals("200", response.getStatusCode().toString());
		assertEquals("text/event-stream;charset=UTF-8", response.getHeaders().getContentType().toString());
	}

}
