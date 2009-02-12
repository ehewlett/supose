/**
 * The (S)ubversion Re(po)sitory (S)earch (E)ngine (SupoSE for short).
 *
 * Copyright (c) 2007, 2008, 2009 by SoftwareEntwicklung Beratung Schulung (SoEBeS)
 * Copyright (c) 2007, 2008, 2009 by Karl Heinz Marbaise
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 *
 * The License can viewed online under http://www.gnu.org/licenses/gpl.html
 * If you have any questions about the Software or about the license
 * just write an email to license@soebes.de
 */
package com.soebes.supose.repository;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.wc.admin.SVNAdminClient;

/**
 * This test will prepare the created repository with
 * particular revision information and different 
 * situations which can happen within a usual 
 * Subversion Repository.
 * 
 * @author Karl Heinz Marbaise
 *
 */
public class InitRevisionsTest {
	private static Logger LOGGER = Logger.getLogger(InitRevisionsTest.class);

	/**
	 */
	@BeforeSuite
	public void createRepository() {
	}
	
	@Test
	public void firstTest () {
//		ArrayList<RevisionOperations> revisionList = ReadRevisionDescriptionFile();
//		for (RevisionOperation revEntry : revisionList) {
//			repository.commit(revEntry, "Message for rev. 1");
//		}
	}
	
	public void initRevs() {
//		RevisionRecord rev = RevisionRecord.createInstance();
//		rev.addDir("/trunk");
//		rev.addDir("/tags");
//		rev.addDir("/branches");
//
//		repository.commit(rev, "Message");
//		
//		rev = RevisionRecord.createInstance();
//		rev.copy("/trunk", "/branches/B_1");
//
//		repository.commit(rev, "Branch created.");
//		
	}
}
