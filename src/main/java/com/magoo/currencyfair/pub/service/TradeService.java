package com.magoo.currencyfair.pub.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.magoo.currencyfair.pub.model.IsoGeoLocation;
import com.magoo.currencyfair.pub.model.Trade;

@Service
public class TradeService {

	private static AtomicLong id = new AtomicLong();

	public Trade getTrade() {
		RestTemplate restTemplate = new RestTemplate();
		Trade trade = restTemplate.getForObject("http://localhost:8282/rft", Trade.class);

		if (trade != null) {
			enrichTrade(trade);
		}

		return trade;
	}

	private void enrichTrade(Trade trade) {
		// FIXME builder
		IsoGeoLocation location = IsoGeoLocation.valueOf(trade.getOriginatingCountry());
		trade.setId(id.getAndIncrement());
		trade.setLat(location.getLatitude());
		trade.setLng(location.getLongtitude());
	}

}
