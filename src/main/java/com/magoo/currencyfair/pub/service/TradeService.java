package com.magoo.currencyfair.pub.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.magoo.currencyfair.pub.model.CurrencyPair;
import com.magoo.currencyfair.pub.model.MarketVolumeIndicator;
import com.magoo.currencyfair.pub.model.Trade;
import com.magoo.currencyfair.pub.model.TradeDao;

/**
 * The Class TradeService providing trade sourcing via REST endpoint and
 * validation.
 */
@Service
public class TradeService {

	private static final Logger LOG = LoggerFactory.getLogger(TradeService.class);

	private static final String API_RFT_URL = "http://localhost:8102/api/rft";

	private Map<CurrencyPair, Long> pairVolume = new ConcurrentHashMap<>();

	private Map<MarketVolumeIndicator, Long> marketVolume = new ConcurrentHashMap<>();

	private Map<CurrencyPair, List<BigDecimal>> buyers = new ConcurrentHashMap<>();

	private Map<CurrencyPair, List<BigDecimal>> sellers = new ConcurrentHashMap<>();

	/**
	 * Gets the enriched trade dao.
	 *
	 * @return the trade or null if no trade is currently available / validation
	 *         fails
	 */
	public TradeDao getTrade() {
		RestTemplate restTemplate = new RestTemplate();
		Trade trade = restTemplate.getForObject(API_RFT_URL, Trade.class);
		if (trade != null) {
			initOrUpdateMarketVolume(MarketVolumeIndicator.PROCESSED);
		}
		TradeDao tradeDao = null;
		try {
			tradeDao = new TradeDao.TradeDaoBuilder(trade).build();
			initOrUpdatePairVolume(tradeDao.getCurrencyPair());
			initOrUpdateMarketVolume(MarketVolumeIndicator.LIVE);
			initOrUpdateBuyersAndSellers(tradeDao);
			matchTrade(tradeDao);
			LOG.info("Streaming tradeDao: {}", tradeDao);
		} catch (IllegalStateException e) {
			// LOG.warn(e.toString());
		} catch (IllegalArgumentException e) {
			initOrUpdateMarketVolume(MarketVolumeIndicator.INVALID);
			LOG.warn(e.toString());
		}

		return tradeDao;
	}

	private synchronized void initOrUpdatePairVolume(CurrencyPair pair) {
		Long value = pairVolume.get(pair);
		if (value == null) {
			pairVolume.put(pair, new Long(1));
			return;
		}
		pairVolume.put(pair, new Long(++value));
	}

	private synchronized void initOrUpdateMarketVolume(MarketVolumeIndicator key) {
		Long value = marketVolume.get(key);
		if (value == null) {
			marketVolume.put(key, new Long(1));
			return;
		}
		marketVolume.put(key, new Long(++value));
	}

	private synchronized void decrementMarketVolume(MarketVolumeIndicator key) {
		Long value = marketVolume.get(key);
		marketVolume.put(key, new Long(--value));
	}

	private synchronized void initOrUpdateBuyersAndSellers(TradeDao tradeDao) {
		if (isSeller(tradeDao)) {
			updateSellers(tradeDao);
			return;
		}
		updateBuyers(tradeDao);
	}

	private boolean isSeller(TradeDao tradeDao) {
		return CurrencyPair.isDirect(tradeDao.getCurrencyFrom(), tradeDao.getCurrencyTo());
	}

	private void updateSellers(TradeDao tradeDao) {
		CurrencyPair currencyPair = tradeDao.getCurrencyPair();
		List<BigDecimal> values = sellers.get(currencyPair);
		BigDecimal rate = tradeDao.getRate();
		if (values == null) {
			List<BigDecimal> l = new ArrayList<BigDecimal>();
			l.add(rate);
			sellers.put(currencyPair, l);
			return;
		}
		values.add(rate);
	}

	private void updateBuyers(TradeDao tradeDao) {
		CurrencyPair currencyPair = tradeDao.getCurrencyPair();
		List<BigDecimal> values = buyers.get(currencyPair);
		BigDecimal rate = tradeDao.getRate();
		if (values == null) {
			List<BigDecimal> l = new ArrayList<BigDecimal>();
			l.add(rate);
			buyers.put(currencyPair, l);
			return;
		}
		values.add(tradeDao.getRate());
	}

	private void matchTrade(TradeDao tradeDao) {
		BigDecimal numerator = BigDecimal.ONE;
		// BigDecimal inverseRate = numerator.divide(tradeDao.getRate(), 4,
		// RoundingMode.HALF_EVEN);
		if (isSeller(tradeDao)) {
			// sell find buyer
			List<BigDecimal> allAvailableSells = buyers.get(tradeDao.getCurrencyPair());
			if (allAvailableSells != null) {
				// for (BigDecimal rate : allAvailableSells) {
				// if (rate.compareTo(inverseRate) == 0) {
				initOrUpdateMarketVolume(MarketVolumeIndicator.MATCHED);
				decrementMarketVolume(MarketVolumeIndicator.LIVE);
				// }
				// }
			}
		} else {
			// buy find seller
			List<BigDecimal> allAvailableBuys = sellers.get(tradeDao.getCurrencyPair());
			if (allAvailableBuys != null) {
				// for (BigDecimal rate : allAvailableBuys) {
				// if (rate.compareTo(inverseRate) == 0) {
				initOrUpdateMarketVolume(MarketVolumeIndicator.MATCHED);
				decrementMarketVolume(MarketVolumeIndicator.LIVE);
				// }
				// }

			}
		}
	}

	public Map<CurrencyPair, Long> getPairVolume() {
		return pairVolume;
	}

	public Map<MarketVolumeIndicator, Long> getMarketVolume() {
		return marketVolume;
	}

}
