package com.magoo.currencyfair.pub.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.magoo.currencyfair.pub.model.CurrencyPair;
import com.magoo.currencyfair.pub.model.IsoGeoLocation;
import com.magoo.currencyfair.pub.model.Trade;

@Service
public class TradeService {

	private static final String API_RFT_URL = "http://localhost:8282/api/rft";

	private static AtomicLong id = new AtomicLong();

	private Map<CurrencyPair, Long> pairVolume = new ConcurrentHashMap<>();

	public Trade getTrade() {
		RestTemplate restTemplate = new RestTemplate();
		Trade trade = restTemplate.getForObject(API_RFT_URL, Trade.class);

		if (trade != null) {
			enrichTrade(trade);
			recordVolume(trade);
		}

		return trade;
	}

	private void recordVolume(Trade trade) {
		CurrencyPair pair = CurrencyPair.getPair(trade.getCurrencyFrom(), trade.getCurrencyTo());
		if (pair != null) {
			initOrUpdateVolume(pair);
		}
	}

	private synchronized void initOrUpdateVolume(CurrencyPair pair) {
		Long value = pairVolume.get(pair);
		if (value == null) {
			pairVolume.put(pair, new Long(1));
			return;
		}
		pairVolume.put(pair, new Long(++value));
	}

	private void enrichTrade(Trade trade) {
		// FIXME builder
		IsoGeoLocation location = IsoGeoLocation.valueOf(trade.getOriginatingCountry());
		trade.setId(id.getAndIncrement());
		trade.setLat(location.getLatitude());
		trade.setLng(location.getLongtitude());
	}

	public Map<CurrencyPair, Long> getPairVolume() {
		return pairVolume;
	}

}
