package com.soebes.subversion.pbac;

public interface IReference {

	public enum RefType {
		User,
		Group,
		Alias
	}
	
	RefType isType();
	
	String getName();
}
