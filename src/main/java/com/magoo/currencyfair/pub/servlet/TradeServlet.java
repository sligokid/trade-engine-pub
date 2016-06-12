package com.magoo.currencyfair.pub.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;

@Configuration
@WebServlet("/tradestream")
public class TradeServlet extends AbstractHttpServlet {

	private static final long serialVersionUID = 1L;

	private static AtomicLong id = new AtomicLong();

	@Override
	protected void runPrintWriter(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter printWriter = response.getWriter();

		while (true) {

			double lat = Math.random() * 180;
			double lng = Math.random() * 180;

			printWriter.print("data:" + "{\n");
			printWriter.print("data:\"id\": \"" + id.getAndIncrement() + "\",\n");
			printWriter.print("data:\"lat\": " + lat + ",\n");
			printWriter.print("data:\"lng\": " + lng + "\n");
			printWriter.print("data:" + "}\n\n");
			printWriter.flush();

			try {
				Thread.currentThread();
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
