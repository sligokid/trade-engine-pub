package com.magoo.currencyfair.pub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class CurrencyFairPubApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyFairPubApplication.class, args);
	}
}
