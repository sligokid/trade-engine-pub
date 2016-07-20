package com.magoo.currencyfair.pub.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.magoo.currencyfair.pub.model.CurrencyPair;
import com.magoo.currencyfair.pub.model.TradeDao;
import com.magoo.currencyfair.pub.service.TradeService;

@Configuration
@WebServlet(TradeStreamServlet.TRADESTREAM_URI)
public class TradeStreamServlet extends AbstractStreamServlet {

	static final String TRADESTREAM_URI = "/pub/tradestream";

	private static final long serialVersionUID = 1L;

	@Autowired
	private TradeService tradeService;

	@Override
	protected void publish(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter printWriter = response.getWriter();

		boolean live = !isIntegrationTestPort();

		while (live) {
			TradeDao newTrade = tradeService.getTrade();

			if (newTrade != null) {
				Map<CurrencyPair, Long> volume = tradeService.getPairVolume();

				printWriter.print("data:" + "{\n");
				printWriter.print("data:\"id\": \"" + newTrade.getId() + "\",\n");
				printWriter.print("data:\"userId\": \"" + newTrade.getUserId() + "\",\n");
				printWriter.print("data:\"lat\": " + newTrade.getLatitude() + ",\n");
				printWriter.print("data:\"lng\": " + newTrade.getLongtitude() + ",\n");
				printWriter.print("data:\"currencyFrom\": \"" + newTrade.getCurrencyFrom() + "\",\n");
				printWriter.print("data:\"currencyTo\": \"" + newTrade.getCurrencyTo() + "\",\n");
				printWriter.print("data:\"amountBuy\": \"" + newTrade.getAmountBuy() + "\",\n");
				printWriter.print("data:\"amountSell\": \"" + newTrade.getAmountSell() + "\",\n");
				printWriter.print("data:\"rate\": \"" + newTrade.getRate() + "\",\n");

				printWriter.print("data:\"originatingCountry\": \"" + newTrade.getOriginatingCountry() + "\",\n");

				// printWriter.print("data:\"volume\": \"" + volume + "\"\n");

				for (Map.Entry<CurrencyPair, Long> entry : volume.entrySet()) {
					printWriter.print("data:\"" + entry.getKey() + "\": \"" + entry.getValue() + "\",\n");
				}

				// printWriter.print("data:\"volume\": \"\n");
				printWriter.print("data:\"volume\": \"\"\n");

				// printWriter.println("data:\"volume\": \"" + "{" +
				// volume.toString() + "}" + "\"\\n");
				// + "\",\n");

				printWriter.print("data:" + "}\n\n");
				printWriter.flush();
				sleep();
			}

		}
	}

	protected void sleep() {
		try {
			Thread.sleep(PUBLISH_INTERVAL);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
