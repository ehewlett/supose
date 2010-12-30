package com.soebes.subversion.pbac;

import junit.framework.Assert;

import org.testng.annotations.Test;

public class GroupTest extends TestBase {

	@Test
	public void groupAddTest() {
		User userHarry = new User("harry");
		User userBrian = new User("brian");
		Group developerGroup = new Group("developer");

		// @developer = harry, brian
		developerGroup.add(userHarry);
		developerGroup.add(userBrian);

		Assert.assertEquals(2, developerGroup.getUserList().size());
	}

	@Test
	public void groupContainsTest() {
		User userHarry = new User("harry");
		User userBrian = new User("brian");
		Group developerGroup = new Group("developer");

		// @developer = harry, brian
		developerGroup.add(userHarry);
		developerGroup.add(userBrian);

		Assert.assertTrue(developerGroup.contains(userHarry.getName()));
		Assert.assertTrue(developerGroup.contains(userHarry));
	}
}
