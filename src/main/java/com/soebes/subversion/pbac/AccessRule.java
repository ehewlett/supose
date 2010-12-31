package com.soebes.subversion.pbac;

import java.util.ArrayList;

public class AccessRule {

	private ArrayList<Access> accessList;

	/**
	 * The name of the repository which this 
	 * AccessRule represents.
	 */
	private String repositoryName;
	/**
	 * The path inside the repository which is represented
	 * by this AccessRule.
	 */
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

	/**
	 * Add the user to the user list with it's appropriate 
	 * {@link AccessLevel}.
	 * @param user
	 * @param readWrite
	 */
	public void add(User user, AccessLevel readWrite) {
		getAccessList().add(new Access(user, readWrite));
	}

	public AccessLevel getAccess(User user, String repository, String path) {
		return getAccess(user.getName(), repository, path);
	}

	/**
	 * 
	 * @param user
	 * @param repository
	 * @param path
	 * @return
	 */
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

	/**
	 * Will get the {@link AccessLevel} for the given user.
	 * @param user The user which will be searched for.
	 * @return The AccessLevel of the given user @{link AccessLevel.NOTHING}
	 *   if the user will not be found.
	 */
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
