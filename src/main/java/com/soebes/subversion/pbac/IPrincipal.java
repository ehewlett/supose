package com.soebes.subversion.pbac;

public interface IPrincipal {

	String getName();
	void setName(String name);
	boolean isEqual(String name);
}
