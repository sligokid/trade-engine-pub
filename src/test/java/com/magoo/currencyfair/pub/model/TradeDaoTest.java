package com.magoo.currencyfair.pub.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.magoo.currencyfair.pub.NoTradeAvailableException;
import com.magoo.currencyfair.pub.PublishTradeException;

public class TradeDaoTest {

	@Test(expected = NoTradeAvailableException.class)
	public void validate_null_trade_fails() throws PublishTradeException {
		Trade trade = null;

		new TradeDao.TradeDaoBuilder().validateTrade(trade);
	}

	@Test(expected = PublishTradeException.class)
	public void validate_curr_from_fails() throws PublishTradeException {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateCurrencyFrom(trade);
	}

	@Test(expected = PublishTradeException.class)
	public void validate_curr_to_fails() throws PublishTradeException {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateCurrencyTo(trade);
	}

	@Test(expected = PublishTradeException.class)
	public void validate_curr_pair_fails() throws PublishTradeException {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateCurrencyPair(trade);
	}

	@Test(expected = PublishTradeException.class)
	public void validate_invalid_curr_pair_fails() throws PublishTradeException {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateCurrencyPair(trade);
	}

	@Test(expected = PublishTradeException.class)
	public void validate_amt_sell_fails() throws PublishTradeException {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateAmountSell(trade);
	}

	@Test(expected = PublishTradeException.class)
	public void validate_amt_buy_fails() throws PublishTradeException {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateAmountBuy(trade);
	}

	@Test(expected = PublishTradeException.class)
	public void validate_amt_rate_fails() throws PublishTradeException {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateRate(trade);
	}

	@Test(expected = PublishTradeException.class)
	public void validate_orig_country_fails() throws PublishTradeException {
		Trade trade = new Trade();

		new TradeDao.TradeDaoBuilder().validateOriginatingCountry(trade);
	}

	@Test(expected = PublishTradeException.class)
	public void validate_longtitude_no_entry_fails() throws PublishTradeException {
		Trade trade = new Trade();
		trade.setOriginatingCountry("AA");

		new TradeDao.TradeDaoBuilder().validateLongtitude(trade);
	}

	@Test(expected = PublishTradeException.class)
	public void validate_latitude_fails() throws PublishTradeException {
		Trade trade = new Trade();
		trade.setOriginatingCountry("AA");

		new TradeDao.TradeDaoBuilder().validateLatitude(trade);
	}

	@Test
	public void builder_returns_comple_dao() throws PublishTradeException {
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
