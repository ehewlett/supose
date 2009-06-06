package com.soebes.supose.release.web;

import java.io.Serializable;

public class TestSession implements Serializable {

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

}
