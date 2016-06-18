package com.magoo.currencyfair.pub.service;

import static org.junit.Assert.assertNotNull;

public class TradeServiceTest {

	// FIXME mock the service
	// @Test
	public void get_trade_returns_trade() {
		TradeService tradeService = new TradeService();

		assertNotNull(tradeService.getTrade());
	}

}
