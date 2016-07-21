package com.magoo.currencyfair.pub.model;

/**
 * The Enum CurrencyPair.
 * 
 * Finite set of the most active (Supported) Currency pairs.
 */
public enum CurrencyPair {
	//@formatter:off
	AUD_CAD,
	AUD_CHF,
	AUD_JPY,
	AUD_NZD,
	AUD_USD,
	BGN_RON,
	CAD_CHF,
	CAD_JPY,
	CHF_BGN,
	CHF_JPY,
	CHF_RON,
	CHF_TRY,
	EUR_AUD,
	EUR_CAD,
	EUR_CHF,
	EUR_CZK,
	EUR_DKK,
	EUR_GBP,
	EUR_HKD,
	EUR_HUF,
	EUR_ILS,
	EUR_JPY,
	EUR_MXN,
	EUR_NOK,
	EUR_NZD,
	EUR_PLN,
	EUR_RON,
	EUR_RUB,
	EUR_SEK,
	EUR_SGD,
	EUR_TRY,
	EUR_USD,
	EUR_ZAR,
	GBP_AUD,
	GBP_BGN,
	GBP_CAD,
	GBP_CHF,
	GBP_CZK,
	GBP_DKK,
	GBP_HKD,
	GBP_HUF,
	GBP_JPY,
	GBP_NOK,
	GBP_NZD,
	GBP_PLN,
	GBP_RON,
	GBP_SEK,
	GBP_SGD,
	GBP_TRY,
	GBP_USD,
	GBP_ZAR,
	HKD_JPY,
	NZD_CAD,
	NZD_CHF,
	NZD_JPY,
	NZD_USD,
	SGD_HKD,
	SGD_JPY,
	TRY_BGN,
	TRY_JPY,
	TRY_RON,
	USD_BGN,
	USD_CAD,
	USD_CHF,
	USD_CZK,
	USD_DKK,
	USD_HKD,
	USD_HUF,
	USD_ILS,
	USD_JPY,
	USD_MXN,
	USD_NOK,
	USD_PLN,
	USD_RON,
	USD_RUB,
	USD_SEK,
	USD_SGD,
	USD_TRY,
	USD_ZAR;

	/**
	 * Gets the pair in the correct direct / indirect form.
	 *
	 * @param base the base
	 * @param foreign the foreign
	 * @return the pair
	 */
	public static CurrencyPair getPair(String base, String foreign) {
		String directPair = base + "_" + foreign;
		String IndirectPair = foreign + "_" + base;
		for (CurrencyPair pair : values()) {
			if (pair.name().equals(directPair) || pair.name().equals(IndirectPair)) {
				return pair;
			}
		}
		return null;
	}
}
