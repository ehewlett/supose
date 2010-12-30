package com.soebes.subversion.pbac;

import org.testng.annotations.Test;

public class AccessTest extends TestBase {

//	@Test
//	public void accessTest() {
//		User user = new User("harry");
//
//		AccessCheck check = new AccessCheck("");
//
////		AccessLevel level = check.access(user, "/branches/tags");
//
//		// @group = rw
//		//
//		Group group = new Group("group");
//		
//		ArrayList<IReference> gud = new ArrayList<IReference>();
//		
//	}
//	
//	@Test
//	public void accessTest1() {
//		//
//		User userHarry = new User("harry");
//		Group developerGroup = new Group("developer");
//		
//		//The current path in the repository we would like to check
//		AccessCheck check = new AccessCheck("repository", "/test/trunk");
//		check.add(userHarry, AccessLevel.READ);
//		
//		if (check.isAllowedReading()) {
//			// [repository:/test/trunk]
//			// harry = r
//		}
//	}
//	
	@Test
	public void accessTest2() {

		User userHarry = new User("harry");
		User userBrian = new User("Brain");
		Group developerGroup = new Group("developer");

		// @developer = harry, brian
		developerGroup.add(userHarry);
		developerGroup.add(userBrian);
		
//		// [repository:/test/trunk]
		AccessRule ar = new AccessRule("repository", "/test/trunk");
		// harry = rw
		ar.add(userHarry, AccessLevel.READ_WRITE);
		// brian = r
		ar.add(userBrian, AccessLevel.READ);

		AccessRules ars = new AccessRules();
		ars.add(ar);

		if (ars.isAllowedREAD(userBrian, "repository", "/test/trunk/xyz")) {
			//
		}
//		//The current path in the repository we would like to check
//
//		// [repository:/test/trunk]
//		AccessCheck check = new AccessCheck("repository", "/test/trunk");
//
//		// harry = rw
//		check.add(userHarray, AccessLevel.READ_WRITE);
//		// @developer = r
//		check.add(developerGroup, AccessLevel.READ);
//
//		if (check.isAllowedREAD(userHarry, "repository", "/test/trunk/xyz/")) {
//			//
//		}
//		if (check.isAllowedWRITE(userHarry, "repository", "/test/trunk/xyz/")) {
//			//
//		}
		
	}
}
