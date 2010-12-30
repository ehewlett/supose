package com.soebes.subversion.pbac;

import java.util.ArrayList;

public class AccessRule {

	private ArrayList<Access> accessList;

	private String repositoryName;
	private String repositoryPath;

	public AccessRule(String repositoryName, String path) {
		super();
		if (!path.endsWith("/")) {
			path += "/";
		}
		setRepositoryName(repositoryName);
		setRepositoryPath(path);
		setAccessList(new ArrayList<Access>());
	}

	public String getRepositoryName() {
		return repositoryName;
	}
	
	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}
	
	public String getRepositoryPath() {
		return repositoryPath;
	}
	public void setRepositoryPath(String path) {
		this.repositoryPath = path;
	}

	public void setAccessList(ArrayList<Access> accessList) {
		this.accessList = accessList;
	}

	public ArrayList<Access> getAccessList() {
		return accessList;
	}

	public void add(User user, AccessLevel readWrite) {
		getAccessList().add(new Access(user, readWrite));
	}

	public AccessLevel getAccess(User user, String repository, String path) {
		return getAccess(user.getName(), repository, path);
	}

	public AccessLevel getAccess(String user, String repository, String path) {
		AccessLevel result = AccessLevel.NOTHING;
		if (getRepositoryName().equalsIgnoreCase(repository)) {
			Path repositoryPath = new Path(getRepositoryPath());
			if (repositoryPath.contains(path)) {
				result = getAccessForUser(user);
			}
		}
		
		return result;
	}

	public AccessLevel getAccessForUser(User user) {
		return getAccessForUser(user.getName());
	}

	public AccessLevel getAccessForUser(String user) {
		AccessLevel result = AccessLevel.NOTHING;
		for (Access item : getAccessList()) {
			if (item.getUser().getName().equalsIgnoreCase(user)) {
				result = item.getLevel();
			}
		}
		return result;
	}

}
