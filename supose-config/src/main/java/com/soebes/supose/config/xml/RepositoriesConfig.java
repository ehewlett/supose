package com.soebes.supose.config.xml;

import java.util.HashMap;

public class RepositoriesConfig {

	private String baseDirectory;
	private HashMap<String, RepositoryConfig> repositories = new HashMap<String, RepositoryConfig>(); 
//	private ArrayList<RepositoryConfig> repositories = new ArrayList<RepositoryConfig>();

	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	public void addRepository(String key, RepositoryConfig repositroy) {
		this.getRepositories().put(key, repositroy);
	}

	public void setRepositories(HashMap<String, RepositoryConfig> repositories) {
		this.repositories = repositories;
	}

	public HashMap<String, RepositoryConfig> getRepositories() {
		return repositories;
	}
}
