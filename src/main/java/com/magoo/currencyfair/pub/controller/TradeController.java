package com.magoo.currencyfair.pub.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magoo.currencyfair.pub.model.Trade;

@RestController
public class TradeController {

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/trade")
	public Trade trade(@RequestParam(value = "name", defaultValue = "World") String name) {
		Trade trade = new Trade();
		trade.setId(counter.incrementAndGet());
		return trade;

	}
}