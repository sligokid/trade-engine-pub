package com.magoo.currencyfair.pub.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.magoo.currencyfair.pub.model.CurrencyPair;
import com.magoo.currencyfair.pub.model.Trade;
import com.magoo.currencyfair.pub.model.TradeDao;

@Service
public class TradeService {

	private static final Logger LOG = LoggerFactory.getLogger(TradeService.class);

	private static final String API_RFT_URL = "http://localhost:8102/api/rft";

	private Map<CurrencyPair, Long> pairVolume = new ConcurrentHashMap<>();

	public TradeDao getTrade() {
		RestTemplate restTemplate = new RestTemplate();
		Trade trade = restTemplate.getForObject(API_RFT_URL, Trade.class);
		TradeDao tradeDao = null;
		try {
			tradeDao = new TradeDao.TradeDaoBuilder(trade).build();
			initOrUpdateVolume(tradeDao.getCurrencyPair());
			LOG.info("Streaming tradeDao: {}", tradeDao);
		} catch (IllegalStateException e) {
			// LOG.warn(e.toString());
		} catch (IllegalArgumentException e) {
			LOG.error(e.toString());
		}

		return tradeDao;
	}

	private synchronized void initOrUpdateVolume(CurrencyPair pair) {
		Long value = pairVolume.get(pair);
		if (value == null) {
			pairVolume.put(pair, new Long(1));
			return;
		}
		pairVolume.put(pair, new Long(++value));
	}

	public Map<CurrencyPair, Long> getPairVolume() {
		return pairVolume;
	}

}
