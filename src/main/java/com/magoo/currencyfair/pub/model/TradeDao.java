package com.magoo.currencyfair.pub.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import com.magoo.currencyfair.pub.NoTradeAvailableException;
import com.magoo.currencyfair.pub.PublishTradeException;

/**
 * The Class TradeDao.
 * 
 * Sanitize the Trade entity before validating, enriching and building the DAO
 * representation of the Trade.
 */
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

		public TradeDaoBuilder(Trade trade) throws PublishTradeException {
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

		void validateTrade(Trade trade) throws PublishTradeException {
			if (trade == null) {
				throw new NoTradeAvailableException("no trade available");
			}
		}

		String validateUserId(Trade trade) throws PublishTradeException {
			String val = trade.getUserId();
			if (val == null) {
				throw new PublishTradeException("userId cannot be null");
			}
			return val;
		}

		String validateCurrencyFrom(Trade trade) throws PublishTradeException {
			String val = trade.getCurrencyFrom();
			if (val == null) {
				throw new PublishTradeException("currencyFrom cannot be null");
			}
			return val;
		}

		String validateCurrencyTo(Trade trade) throws PublishTradeException {
			String val = trade.getCurrencyTo();
			if (val == null) {
				throw new PublishTradeException("currencyFrom cannot be null");
			}
			return val;
		}

		CurrencyPair validateCurrencyPair(Trade trade) throws PublishTradeException {
			CurrencyPair val = CurrencyPair.getPair(trade.getCurrencyFrom(), trade.getCurrencyTo());
			if (val == null) {
				throw new PublishTradeException(
						"currencyPair " + trade.getCurrencyFrom() + "_" + trade.getCurrencyTo() + " is not supported");
			}
			return val;
		}

		BigDecimal validateAmountSell(Trade trade) throws PublishTradeException {
			BigDecimal val = trade.getAmountSell();
			if (val == null) {
				throw new PublishTradeException("amtountSell cannot be null");
			}
			return val;
		}

		BigDecimal validateAmountBuy(Trade trade) throws PublishTradeException {
			BigDecimal val = trade.getAmountBuy();
			if (val == null) {
				throw new PublishTradeException("amountBuy cannot be null");
			}
			return val;
		}

		BigDecimal validateRate(Trade trade) throws PublishTradeException {
			BigDecimal val = trade.getRate();
			if (val == null) {
				throw new PublishTradeException("rate cannot be null");
			}
			return val;
		}

		String validateOriginatingCountry(Trade trade) throws PublishTradeException {
			String val = trade.getOriginatingCountry();
			if (val == null) {
				throw new PublishTradeException("originatingCountry cannot be null");
			}
			return val;
		}

		Double validateLongtitude(Trade trade) throws PublishTradeException {
			return getIsoGeoLocation(trade).getLongtitude();
		}

		private IsoGeoLocation getIsoGeoLocation(Trade trade) throws PublishTradeException {
			try {
				IsoGeoLocation location = IsoGeoLocation.valueOf(trade.getOriginatingCountry());
				return location;
			} catch (IllegalArgumentException e) {
				throw new PublishTradeException(e);
			}
		}

		Double validateLatitude(Trade trade) throws PublishTradeException {
			return getIsoGeoLocation(trade).getLatitude();
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

	// TODO hashcode & equals J7 Object API
	@Override
	public String toString() {
		return "TradeDao [id=" + id + ", userId=" + userId + ", currencyFrom=" + currencyFrom + ", currencyTo="
				+ currencyTo + ", currencyPair=" + currencyPair + ", amountSell=" + amountSell + ", amountBuy="
				+ amountBuy + ", rate=" + rate + ", originatingCountry=" + originatingCountry + ", longtitude="
				+ longtitude + ", latitude=" + latitude + "]";
	}

}