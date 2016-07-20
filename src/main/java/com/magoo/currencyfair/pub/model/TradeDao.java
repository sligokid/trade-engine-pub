package com.magoo.currencyfair.pub.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public class TradeDao {

	private static AtomicLong idGenerator = new AtomicLong();

	private final long id;

	private final String userId;

	private final String currencyFrom;

	private final String currencyTo;

	private CurrencyPair currencyPair;

	private final BigDecimal amountSell;

	private final BigDecimal amountBuy;

	private final BigDecimal rate;

	private final String originatingCountry;

	private final Double longtitude;

	private final Double latitude;

	private TradeDao(TradeDaoBuilder tradeDaoBuilder) {
		this.id = idGenerator.incrementAndGet();
		this.userId = tradeDaoBuilder.getUserId();
		this.currencyFrom = tradeDaoBuilder.getCurrencyFrom();
		this.currencyTo = tradeDaoBuilder.getCurrencyTo();
		this.currencyPair = tradeDaoBuilder.getCurrencyPair();
		this.amountSell = tradeDaoBuilder.getAmountSell();
		this.amountBuy = tradeDaoBuilder.getAmountBuy();
		this.rate = tradeDaoBuilder.getRate();
		this.originatingCountry = tradeDaoBuilder.getOriginatingCountry();
		this.longtitude = tradeDaoBuilder.getLongtitude();
		this.latitude = tradeDaoBuilder.getLatitude();
	}

	public static class TradeDaoBuilder {

		private String userId;

		private String currencyFrom;

		private String currencyTo;

		private CurrencyPair currencyPair;

		private BigDecimal amountSell;

		private BigDecimal amountBuy;

		private BigDecimal rate;

		private String originatingCountry;

		private Double longtitude;

		private Double latitude;

		public TradeDaoBuilder(Trade trade) {
			validateTrade(trade);
			this.userId = validateUserId(trade);
			this.currencyFrom = validateCurrencyFrom(trade);
			this.currencyTo = validateCurrencyTo(trade);
			this.currencyPair = validateCurrencyPair(trade);
			this.amountSell = validateAmountSell(trade);
			this.amountBuy = validateAmountBuy(trade);
			this.rate = validateRate(trade);
			this.originatingCountry = validateOriginatingCountry(trade);
			this.longtitude = validateLongtitude(trade);
			this.latitude = validateLatitude(trade);
		}

		// UT
		TradeDaoBuilder() {
		}

		void validateTrade(Trade trade) {
			if (trade == null) {
				throw new IllegalStateException("no trade available");
			}
		}

		String validateUserId(Trade trade) {
			String val = trade.getUserId();
			if (val == null) {
				throw new IllegalArgumentException("userId cannot be null");
			}
			return val;
		}

		String validateCurrencyFrom(Trade trade) {
			String val = trade.getCurrencyFrom();
			if (val == null) {
				throw new IllegalArgumentException("currencyFrom cannot be null");
			}
			return val;
		}

		String validateCurrencyTo(Trade trade) {
			String val = trade.getCurrencyTo();
			if (val == null) {
				throw new IllegalArgumentException("currencyFrom cannot be null");
			}
			return val;
		}

		CurrencyPair validateCurrencyPair(Trade trade) {
			CurrencyPair val = CurrencyPair.getPair(trade.getCurrencyFrom(), trade.getCurrencyTo());
			if (val == null) {
				throw new IllegalArgumentException(
						"currencyPair " + trade.getCurrencyFrom() + "_" + trade.getCurrencyTo() + " is not supported");
			}
			return val;
		}

		BigDecimal validateAmountSell(Trade trade) {
			BigDecimal val = trade.getAmountSell();
			if (val == null) {
				throw new IllegalArgumentException("amtountSell cannot be null");
			}
			return val;
		}

		BigDecimal validateAmountBuy(Trade trade) {
			BigDecimal val = trade.getAmountBuy();
			if (val == null) {
				throw new IllegalArgumentException("amountBuy cannot be null");
			}
			return val;
		}

		BigDecimal validateRate(Trade trade) {
			BigDecimal val = trade.getRate();
			if (val == null) {
				throw new IllegalArgumentException("rate cannot be null");
			}
			return val;
		}

		String validateOriginatingCountry(Trade trade) {
			String val = trade.getOriginatingCountry();
			if (val == null) {
				throw new IllegalArgumentException("originatingCountry cannot be null");
			}
			return val;
		}

		Double validateLongtitude(Trade trade) {
			IsoGeoLocation location = IsoGeoLocation.valueOf(trade.getOriginatingCountry());
			return location.getLongtitude();
		}

		Double validateLatitude(Trade trade) {
			IsoGeoLocation location = IsoGeoLocation.valueOf(trade.getOriginatingCountry());
			return location.getLatitude();
		}

		public String getUserId() {
			return userId;
		}

		public String getCurrencyFrom() {
			return currencyFrom;
		}

		public String getCurrencyTo() {
			return currencyTo;
		}

		public CurrencyPair getCurrencyPair() {
			return currencyPair;
		}

		public BigDecimal getAmountSell() {
			return amountSell;
		}

		public BigDecimal getAmountBuy() {
			return amountBuy;
		}

		public BigDecimal getRate() {
			return rate;
		}

		public String getOriginatingCountry() {
			return originatingCountry;
		}

		public Double getLongtitude() {
			return longtitude;
		}

		public Double getLatitude() {
			return latitude;
		}

		public TradeDao build() {
			return new TradeDao(this);
		}

	}

	public long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public static AtomicLong getIdGenerator() {
		return idGenerator;
	}

	public String getCurrencyFrom() {
		return currencyFrom;
	}

	public String getCurrencyTo() {
		return currencyTo;
	}

	public CurrencyPair getCurrencyPair() {
		return currencyPair;
	}

	public BigDecimal getAmountSell() {
		return amountSell;
	}

	public BigDecimal getAmountBuy() {
		return amountBuy;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public Double getLongtitude() {
		return longtitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	@Override
	public String toString() {
		return "TradeDao [id=" + id + ", userId=" + userId + ", currencyFrom=" + currencyFrom + ", currencyTo="
				+ currencyTo + ", currencyPair=" + currencyPair + ", amountSell=" + amountSell + ", amountBuy="
				+ amountBuy + ", rate=" + rate + ", originatingCountry=" + originatingCountry + ", longtitude="
				+ longtitude + ", latitude=" + latitude + "]";
	}

}