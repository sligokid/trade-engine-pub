package com.magoo.currencyfair.pub.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.magoo.currencyfair.pub.model.CurrencyPair;
import com.magoo.currencyfair.pub.model.TradeDao;

/**
 * The Class TradeMatcher providing VERY basic trade matching functionality.
 */
@Component
public class TradeMatcher {

	private Map<CurrencyPair, List<BigDecimal>> buyers = new ConcurrentHashMap<>();

	private Map<CurrencyPair, List<BigDecimal>> sellers = new ConcurrentHashMap<>();

	public synchronized void initOrUpdateBuyersAndSellers(TradeDao tradeDao) {
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

	/**
	 * Checks if is trade matched ie. A seller has a buyer or a buyer has a
	 * seller at the correct rate(ie the inverse).
	 * 
	 * Note: In fact we don't match the rate here for the purposes of
	 * demonstration in the UI all trades match when an opposite pair exists
	 * regardless of rate.
	 *
	 * @param tradeDao
	 *            the trade dao
	 * @return true, if is trade matched
	 */
	public boolean isTradeMatched(TradeDao tradeDao) {
		BigDecimal numerator = BigDecimal.ONE;
		BigDecimal inverseRate = numerator.divide(tradeDao.getRate(), 4, RoundingMode.HALF_EVEN);
		if (isSeller(tradeDao)) {
			// sell find buyer
			List<BigDecimal> allAvailableSells = buyers.get(tradeDao.getCurrencyPair());
			if (allAvailableSells != null) {
				for (BigDecimal rate : allAvailableSells) {
					if (isRateMatched(inverseRate, rate)) {
						return true;
					}
				}
			}
		} else {
			// buy find seller
			List<BigDecimal> allAvailableBuys = sellers.get(tradeDao.getCurrencyPair());
			if (allAvailableBuys != null) {
				for (BigDecimal rate : allAvailableBuys) {
					if (isRateMatched(inverseRate, rate)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks if is rate matched.
	 * 
	 * NOTE: For the purposes of demonstration the rate is always matched
	 *
	 * @param inverseRate
	 *            the inverse rate
	 * @param rate
	 *            the rate
	 * @return true, if is rate matched
	 */
	private boolean isRateMatched(BigDecimal inverseRate, BigDecimal rate) {
		// return rate.compareTo(inverseRate) == 0;
		return true;
	}

	Map<CurrencyPair, List<BigDecimal>> getBuyers() {
		return buyers;
	}

	Map<CurrencyPair, List<BigDecimal>> getSellers() {
		return sellers;
	}

}
