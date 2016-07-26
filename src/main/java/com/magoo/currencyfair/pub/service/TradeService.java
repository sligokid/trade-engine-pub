package com.magoo.currencyfair.pub.service;

import java.util.Map;

import com.magoo.currencyfair.pub.model.CurrencyPair;
import com.magoo.currencyfair.pub.model.MarketVolumeIndicator;
import com.magoo.currencyfair.pub.model.TradeDao;

public interface TradeService {

	/**
	 * Gets the enriched trade dao.
	 *
	 * @return the trade or null if no trade is available or validation fails
	 */
	TradeDao getTrade();

	Map<CurrencyPair, Long> getPairVolume();

	Map<MarketVolumeIndicator, Long> getMarketVolume();

}