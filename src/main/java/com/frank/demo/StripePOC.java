package com.frank.demo;


import com.braintreegateway.BraintreeGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
@EnableCaching
public class StripePOC extends SpringBootServletInitializer {

	public static BraintreeGateway gateway;


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(StripePOC.class);
	}

	public static void main(String[] args) throws Exception {
		gateway = new BraintreeGateway(
				"sandbox",
				"snz9vbc522wm8hg3",
				"qf8wpcrh9kpmtm5m",
				"a959352275888e0dcbbfc90569482086"
		);
		ApplicationContext ctx = SpringApplication.run(StripePOC.class, args);
	}


}
