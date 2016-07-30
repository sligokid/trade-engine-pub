package com.magoo.currencyfair.pub.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.magoo.currencyfair.pub.NoTradeAvailableException;
import com.magoo.currencyfair.pub.PublishTradeException;
import com.magoo.currencyfair.pub.model.CurrencyPair;
import com.magoo.currencyfair.pub.model.MarketVolumeIndicator;
import com.magoo.currencyfair.pub.model.Trade;
import com.magoo.currencyfair.pub.model.TradeDao;

/**
 * The Class TradeService providing trade sourcing via REST endpoint and
 * validation.
 */
@Service
class TradeServiceImpl implements TradeService {

	private static final Logger LOG = LoggerFactory.getLogger(TradeServiceImpl.class);

	private static final String API_RFT_URL = "http://localhost:8102/api/rft";

	@Autowired
	private MarketVolumeCalculator marketVolumeCalculator;

	@Autowired
	private PairVolumeCalculator pairVolumeCalculator;

	@Autowired
	private TradeMatcher tradeMatcher;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.magoo.currencyfair.pub.service.TradeService#getTrade()
	 */
	@Override
	public TradeDao getTrade() {
		RestTemplate restTemplate = new RestTemplate();
		Trade trade = restTemplate.getForObject(API_RFT_URL, Trade.class);
		if (trade != null) {
			marketVolumeCalculator.initOrUpdateMarketVolume(MarketVolumeIndicator.PROCESSED);
		}
		TradeDao tradeDao = null;
		try {
			tradeDao = new TradeDao.TradeDaoBuilder(trade).build();
			pairVolumeCalculator.initOrUpdatePairVolume(tradeDao.getCurrencyPair());
			marketVolumeCalculator.initOrUpdateMarketVolume(MarketVolumeIndicator.LIVE);
			tradeMatcher.initOrUpdateBuyersAndSellers(tradeDao);
			if (tradeMatcher.isTradeMatched(tradeDao)) {
				marketVolumeCalculator.initOrUpdateMarketVolume(MarketVolumeIndicator.MATCHED);
				marketVolumeCalculator.decrementMarketVolume(MarketVolumeIndicator.LIVE);
			}
			LOG.info("Streaming tradeDao: {}", tradeDao);
		} catch (NoTradeAvailableException e) {
			// LOG.warn(e.toString());
		} catch (PublishTradeException e) {
			marketVolumeCalculator.initOrUpdateMarketVolume(MarketVolumeIndicator.INVALID);
			LOG.warn(e.toString());
		}

		return tradeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.magoo.currencyfair.pub.service.TradeService#getPairVolume()
	 */
	@Override
	public Map<CurrencyPair, Long> getPairVolume() {
		return pairVolumeCalculator.getPairVolume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.magoo.currencyfair.pub.service.TradeService#getMarketVolume()
	 */
	@Override
	public Map<MarketVolumeIndicator, Long> getMarketVolume() {
		return marketVolumeCalculator.getMarketVolume();
	}

}
