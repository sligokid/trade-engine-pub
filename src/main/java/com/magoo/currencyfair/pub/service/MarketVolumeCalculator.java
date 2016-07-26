package com.magoo.currencyfair.pub.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.magoo.currencyfair.pub.model.MarketVolumeIndicator;

/**
 * The Class MarketVolumeCalculator providing market volume calculations.
 */
@Component
class MarketVolumeCalculator {

	private Map<MarketVolumeIndicator, Long> marketVolume = new ConcurrentHashMap<>();

	public synchronized void initOrUpdateMarketVolume(MarketVolumeIndicator key) {
		Long value = marketVolume.get(key);
		if (value == null) {
			marketVolume.put(key, new Long(1));
			return;
		}
		marketVolume.put(key, new Long(++value));
	}

	public synchronized void decrementMarketVolume(MarketVolumeIndicator key) {
		Long value = marketVolume.get(key);
		marketVolume.put(key, new Long(--value));
	}

	public Map<MarketVolumeIndicator, Long> getMarketVolume() {
		return marketVolume;
	}

}
