package com.magoo.currencyfair.pub.model;

import java.math.BigDecimal;

public class Trade {

	private long id;

	private long userId;

	private String currencyFrom;

	private String currencyTo;

	private BigDecimal amountSell;

	private BigDecimal amountBuy;

	private BigDecimal rate;

	// private Date timePlaced;

	private String originatingCountry;

	private Double lng;

	private Double lat;

	// {"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP",
	// "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" :
	// "24-JAN-15 10:27:44", "originatingCountry" : "FR"}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCurrencyFrom() {
		return currencyFrom;
	}

	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
	}

	public String getCurrencyTo() {
		return currencyTo;
	}

	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}

	public BigDecimal getAmountSell() {
		return amountSell;
	}

	public void setAmountSell(BigDecimal amountSell) {
		this.amountSell = amountSell;
	}

	public BigDecimal getAmountBuy() {
		return amountBuy;
	}

	public void setAmountBuy(BigDecimal amountBuy) {
		this.amountBuy = amountBuy;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	// public Date getTimePlaced() {
	// return timePlaced;
	// }

	// public void setTimePlaced(Date timePlaced) {
	// this.timePlaced = timePlaced;
	// }

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}

	// FIXME use Java 7 Objects method

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (id != other.id)
			return false;
		return true;
	}

}