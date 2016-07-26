package com.magoo.currencyfair.pub.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.magoo.currencyfair.pub.model.CurrencyPair;

/**
 * The Class PairVolumeCalculator providing currency pair volume calculations.
 */
@Component
class PairVolumeCalculator {

	private Map<CurrencyPair, Long> pairVolume = new ConcurrentHashMap<>();

	public synchronized void initOrUpdatePairVolume(CurrencyPair pair) {
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
