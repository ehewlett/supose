package com.soebes.supose.release.web;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class TestSession implements Serializable {
	private static Logger LOGGER = Logger.getLogger(TestSession.class);

	private static final long serialVersionUID = 120672378912357403L;

	private String query;

	public TestSession() {
	}	

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public String query() {
		LOGGER.info("query() we start");
		LOGGER.info("The query:" + getQuery());
		//Do some things here...
		return "SUCCESS";
	}
	

}
