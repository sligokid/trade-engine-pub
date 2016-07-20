package com.magoo.currencyfair.pub.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class TradeDaoTest {

	@Test(expected = IllegalStateException.class)
	public void validate_null_trade_fails() {
		Trade trade = null;

		new TradeDao.TradeDaoBuilder().validateTrade(trade);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validate_curr_from_fails() {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateCurrencyFrom(trade);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validate_curr_to_fails() {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateCurrencyTo(trade);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validate_curr_pair_fails() {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateCurrencyPair(trade);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validate_invalid_curr_pair_fails() {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateCurrencyPair(trade);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validate_amt_sell_fails() {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateAmountSell(trade);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validate_amt_buy_fails() {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateAmountBuy(trade);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validate_amt_rate_fails() {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateRate(trade);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validate_orig_country_fails() {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateOriginatingCountry(trade);
	}

	@Test(expected = NullPointerException.class)
	public void validate_longtitude_fails() {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateLongtitude(trade);
	}

	@Test(expected = NullPointerException.class)
	public void validate_latitude_fails() {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateLatitude(trade);
	}

	// {"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP",
	// "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" :
	// "24-JAN-15 10:27:44", "originatingCountry" : "FR"}

	@Test
	public void builder_returns_comple_dao() {
		Trade trade = createValidTrade();

		TradeDao tradeDao = new TradeDao.TradeDaoBuilder(trade).build();

		assertEquals("134256", tradeDao.getUserId());
		assertEquals("EUR", tradeDao.getCurrencyFrom());
		assertEquals("GBP", tradeDao.getCurrencyTo());
		assertEquals(CurrencyPair.EUR_GBP, tradeDao.getCurrencyPair());
		assertEquals(new BigDecimal("1000"), tradeDao.getAmountSell());
		assertEquals(new BigDecimal("747.10"), tradeDao.getAmountBuy());
		assertEquals(new BigDecimal("0.7471"), tradeDao.getRate());
		assertEquals("FR", tradeDao.getOriginatingCountry());
		assertEquals(new Double("2.0"), tradeDao.getLongtitude());
		assertEquals(new Double("46.0"), tradeDao.getLatitude());
	}

	private Trade createValidTrade() {
		Trade trade = new Trade();
		trade.setUserId("134256");
		trade.setCurrencyFrom("EUR");
		trade.setCurrencyTo("GBP");
		trade.setAmountSell(new BigDecimal("1000"));
		trade.setAmountBuy(new BigDecimal("747.10"));
		trade.setRate(new BigDecimal("0.7471"));
		trade.setOriginatingCountry("FR");
		return trade;
	}

}
