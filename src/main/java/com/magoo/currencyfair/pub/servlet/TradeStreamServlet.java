package com.magoo.currencyfair.pub.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.magoo.currencyfair.pub.model.Trade;
import com.magoo.currencyfair.pub.service.TradeService;

@Configuration
@WebServlet("/tradestream")
public class TradeStreamServlet extends AbstractStreamServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TradeService tradeService;

	@Override
	protected void publish(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter printWriter = response.getWriter();

		boolean live = !isIntegrationTestPort();

		while (live) {
			Trade newTrade = tradeService.getTrade();
			if (newTrade != null) {
				printWriter.print("data:" + "{\n");
				printWriter.print("data:\"id\": \"" + newTrade.getId() + "\",\n");
				printWriter.print("data:\"lat\": " + newTrade.getLat() + ",\n");
				printWriter.print("data:\"lng\": " + newTrade.getLng() + "\n");
				printWriter.print("data:" + "}\n\n");
				printWriter.flush();
			}

			sleep();
		}
	}

}
