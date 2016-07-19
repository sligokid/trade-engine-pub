package com.magoo.currencyfair.pub.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractStreamServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected static final int PUBLISH_INTERVAL = 200;

	@Value("${server.port}")
	private int port;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/event-stream;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");
		response.addHeader("Access-Control-Allow-Origin", "*");

		publish(request, response);
	}

	protected abstract void publish(HttpServletRequest request, HttpServletResponse response) throws IOException;

	protected boolean isIntegrationTestPort() {
		return port == 0;
	}

}