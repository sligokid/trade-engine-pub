package com.magoo.currencyfair.pub.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TradeServiceTest {

	@Test
	public void get_trade_returns_trade() {
		TradeService tradeService = new TradeService();

		assertNotNull(tradeService.getTrade());
	}

}
