package com.soebes.subversion.pbac;

public class AccessRule {

	private String repositoryName;
	private String path;

	public AccessRule(String repositoryName, String path) {
		super();
		this.repositoryName = repositoryName;
		this.path = path;
	}
	public String getRepositoryName() {
		return repositoryName;
	}
	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
