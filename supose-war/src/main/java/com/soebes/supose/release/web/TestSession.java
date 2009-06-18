package com.soebes.supose.release.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.soebes.supose.search.ResultEntry;
import com.soebes.supose.search.SearchRepository;

public class TestSession implements Serializable {
	private static Logger LOGGER = Logger.getLogger(TestSession.class);

	private static final long serialVersionUID = 120672378912357403L;
	
	private String index = null;

	private String query;

	public TestSession() {
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	    RepositoryBean repoBean = (RepositoryBean) ctx.getBean("RepositoryBean");
		setIndex(repoBean.getIndex());
	}	

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public List<ResultEntry> getResult() {
		LOGGER.info("getResult(" + getQuery() + ")");
		SearchRepository searchRepository = new SearchRepository(getIndex());
		List<ResultEntry> result = searchRepository.getResult(getQuery());
		return result;
	}
	public String query() {
		LOGGER.info("query() we start");
		LOGGER.info("The query:" + getQuery());
		//Do some things here...
		return "SUCCESS";
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getIndex() {
		return index;
	}

}
