package com.soebes.supose.config;

import java.util.ArrayList;

public class RepositoriesConfig {

	private String baseDirectory;
	private ArrayList<RepositoryConfig> repositories = new ArrayList<RepositoryConfig>();

	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	public void setRepositories(ArrayList<RepositoryConfig> repositories) {
		this.repositories = repositories;
	}

	public ArrayList<RepositoryConfig> getRepositories() {
		return repositories;
	}
	
	public void addRepository(RepositoryConfig repositroy) {
		this.repositories.add(repositroy);
	}
}
