package com.soebes.subversion.pbac;

import junit.framework.Assert;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AccessRuleTest {

	private AccessRule accessRule;

	/**
	 * This method will define the following rule:
	 * <pre>
	 * [repository:/test/trunk]
	 * harry = rw
	 * brian = r
	 * </pre>
	 */
	@BeforeMethod
	public void beforeMethod() {
		User userHarry = new User("harry");
		User userBrian = new User("brian");

		accessRule = new AccessRule("repository", "/test/trunk");

		accessRule.add(userHarry, AccessLevel.READ_WRITE);
		accessRule.add(userBrian, AccessLevel.READ);

	}
	@Test
	public void accessTest() {
		AccessLevel al_harry = accessRule.getAccessForUser("harry");
		Assert.assertEquals(AccessLevel.READ_WRITE, al_harry);

		AccessLevel al_brian = accessRule.getAccessForUser("brian");
		Assert.assertEquals(AccessLevel.READ, al_brian);

		AccessLevel al_hugo = accessRule.getAccessForUser("hugo");
		Assert.assertEquals(AccessLevel.NOTHING, al_hugo);
	}
	
    @DataProvider(name = "createAccessSet")
    public Object[][] createAccessSet() {
        return new Object[][] {
      		{ "harry", "repository", "/",			 				AccessLevel.NOTHING },
    		{ "harry", "repository", "/test/trunk/", 				AccessLevel.READ_WRITE },
    		{ "harry", "repository", "/test/trunk/src/", 			AccessLevel.READ_WRITE },
    		{ "harry", "repository", "/test/trunk/src/xyz.java", 	AccessLevel.READ_WRITE },
    		{ "harry", "repository", "/test/trunk/src/CHANGELOG", 	AccessLevel.READ_WRITE },
    		{ "brian", "repository", "/test/trunk/src/xyz.java", 	AccessLevel.READ },
    		{ "brian", "repository", "/test/trunk/src/CHANGELOG", 	AccessLevel.READ },
    		{ "sally", "repository", "/test/trunk/src/xyz.java", 	AccessLevel.NOTHING },
    		{ "brian", "repository", "/test/trunk/",	 			AccessLevel.READ },
    		{ "sally", "repository", "/test/trunk/",	 			AccessLevel.NOTHING },
    		{ "harry", "different", "/test/trunk/", 				AccessLevel.NOTHING },
        };
    }

    @Test(dataProvider = "createAccessSet")
	public void accessRuleCheck(String user, String repository, String accessPath, AccessLevel expectedLevel) {
		//Check the permission of the current user in the path /test/trunk 
		AccessLevel al_user = accessRule.getAccess(user, repository, accessPath);
		Assert.assertEquals(expectedLevel, al_user);
	}

}
