package com.soebes.subversion.pbac;

import java.util.ArrayList;

import org.testng.annotations.Test;

public class AccessTest extends TestBase {

	@Test
	public void accessTest() {
		User user = new User("harry");

		AccessCheck check = new AccessCheck("");

//		AccessLevel level = check.access(user, "/branches/tags");

		// @group = rw
		//
		Group group = new Group("group");
		
		ArrayList<IReference> gud = new ArrayList<IReference>();
		
	}
}
