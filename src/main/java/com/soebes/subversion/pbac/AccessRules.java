package com.soebes.subversion.pbac;

import java.util.ArrayList;

public class AccessRules {
	private ArrayList<AccessRule> rules;
	
	public AccessRules() {
		setRules(new ArrayList<AccessRule>());
	}

	public ArrayList<AccessRule> getRules() {
		return rules;
	}

	public void setRules(ArrayList<AccessRule> rules) {
		this.rules = rules;
	}

	public void add(AccessRule ar) {
		if (!getRules().contains(ar)) {
			getRules().add(ar);
		}
	}

	
}
