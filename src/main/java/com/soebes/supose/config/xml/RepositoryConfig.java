package com.soebes.supose.config.xml;

import java.net.URL;

public class RepositoryConfig {

	private String id;
	private String name;
	private URL url;
	private String username;
	private String password;
	private String fromRevision;
	private String toRevision;
	private String scheduler;
	
	private RepositoryIndexConfig indexer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFromRevision() {
		return fromRevision;
	}

	public void setFromRevision(String fromRevision) {
		this.fromRevision = fromRevision;
	}

	public String getToRevision() {
		return toRevision;
	}

	public void setToRevision(String toRevision) {
		this.toRevision = toRevision;
	}

	public String getScheduler() {
		return scheduler;
	}

	public void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}

	public void setIndexer(RepositoryIndexConfig indexer) {
		this.indexer = indexer;
	}

	public RepositoryIndexConfig getIndexer() {
		return indexer;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
