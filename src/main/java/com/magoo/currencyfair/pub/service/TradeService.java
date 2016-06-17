package com.magoo.currencyfair.pub.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.magoo.currencyfair.pub.model.Trade;

@Service
public class TradeService {

	private static AtomicLong id = new AtomicLong();

	public Trade getTrade() {
		// TODO MQ / REST
		Trade trade = getRandomTrade();
		return trade;
	}

	private Trade getRandomTrade() {
		// FIXME builder
		RestTemplate restTemplate = new RestTemplate();
		// Trade trade =
		// restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random",
		// Quote.class);
		Trade trade = restTemplate.getForObject("http://localhost:8282/rft", Trade.class);

		if (trade == null) {
			return null;
		}
		// Trade trade = new Trade();
		double lat = Math.random() * 180;
		double lng = Math.random() * 180;

		trade.setId(id.getAndIncrement());
		trade.setLat(lat);
		trade.setLng(lng);
		return trade;
	}

}
