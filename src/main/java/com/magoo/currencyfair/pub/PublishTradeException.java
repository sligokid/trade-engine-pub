package com.magoo.currencyfair.pub;

public class PublishTradeException extends Exception {

	private static final long serialVersionUID = -2357777609485061989L;

	public PublishTradeException(String cause) {
		super(cause);
	}

	public PublishTradeException(Exception cause) {
		super(cause);
	}

}
