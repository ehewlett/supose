package com.soebes.subversion.pbac;

public class Access {

	private User user;
	private AccessLevel level;

	public Access() {
		setUser(null);
		setLevel(AccessLevel.NOTHING);
	}

	public Access(User user, AccessLevel level) {
		super();
		setUser(user);
		setLevel(level);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AccessLevel getLevel() {
		return level;
	}

	public void setLevel(AccessLevel level) {
		this.level = level;
	}
}
