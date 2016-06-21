package com.magoo.currencyfair.pub.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class CurrencyPairTest {

	@Test
	public void euro_dollar_pair_is_found_as_euro_dollar() {
		String base = "EUR";
		String foreign = "USD";

		CurrencyPair pair = CurrencyPair.getPair(base, foreign);

		assertEquals(pair, CurrencyPair.EUR_USD);
	}

	@Test
	public void dollar_euro_pair_is_found_as_euro_dollar() {
		String base = "USD";
		String foreign = "EUR";

		CurrencyPair pair = CurrencyPair.getPair(base, foreign);

		assertEquals(pair, CurrencyPair.EUR_USD);
	}

	@Test
	public void dollar_dollar_pair_is_found_as_null() {
		String base = "USD";
		String foreign = "USD";

		CurrencyPair pair = CurrencyPair.getPair(base, foreign);

		assertNull(pair);
	}

	@Test
	public void null_dollar_pair_is_found_as_null() {
		String base = null;
		String foreign = "USD";

		CurrencyPair pair = CurrencyPair.getPair(base, foreign);

		assertNull(pair);
	}

	@Test
	public void dollar_null_pair_is_found_as_null() {
		String base = "USD";
		String foreign = null;

		CurrencyPair pair = CurrencyPair.getPair(base, foreign);

		assertNull(pair);
	}

}
