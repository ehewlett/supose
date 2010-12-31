package com.soebes.subversion.pbac;

import junit.framework.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GroupTest extends TestBase {

	private Group developerGroup;
	private User userHarry;
	private User userBrian;
	
	
	@BeforeClass
	public void beforeClass() {
		userHarry = new User("harry");
		userBrian = new User("brian");
		developerGroup = new Group("developer");

		// @developer = harry, brian
		developerGroup.add(userHarry);
		developerGroup.add(userBrian);
	}
	@Test
	public void groupAddTest() {
		Assert.assertEquals(2, developerGroup.getUserList().size());
	}

	@Test
	public void groupContainsTest() {
		Assert.assertTrue(developerGroup.contains(userHarry.getName()));
		Assert.assertTrue(developerGroup.contains(userHarry));
		Assert.assertTrue(developerGroup.contains(userBrian));

		Assert.assertFalse(developerGroup.contains("michael"));
	}
}
