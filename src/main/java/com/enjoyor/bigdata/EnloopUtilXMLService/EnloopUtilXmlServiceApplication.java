package com.enjoyor.bigdata.EnloopUtilXMLService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnloopUtilXmlServiceApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnloopUtilXmlServiceApplication.class);

	public static void main(String[] args) {
		LOGGER.info(" Enloop-Util-XML-Service Application has been StartUp.");
		SpringApplication.run(EnloopUtilXmlServiceApplication.class, args);
	}
}
