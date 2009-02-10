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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.admin.SVNAdminClient;

/**
 * @author Karl Heinz Marbaise
 *
 */
public class InitRepositoryTest {
	private static Logger LOGGER = Logger.getLogger(InitRepositoryTest.class);

	/**
	 * The property basedir is defined by Maven during
	 * the Test processing.
	 */
	private String mavenBaseDir = System.getProperty("basedir", "." );
	
	private String repositoryDirectory = null;
	
	private String dumpDirectory = mavenBaseDir + "/src/test/resources";
	
	private SVNURL repositoryURL = null;

	/**
	 * The first step is to create a test repository which
	 * will be used to test the functionality of the scanning
	 * indexing and search process.
	 * @throws SVNException 
	 */
	@BeforeSuite
	public void createRepository() throws SVNException {
		LOGGER.info("Maven base directory: " + mavenBaseDir);

		repositoryDirectory = mavenBaseDir + "/target/repos";
		LOGGER.info("Maven Integration Test Repository directory: " + repositoryDirectory);
		repositoryURL = SVNRepositoryFactory.createLocalRepository(new File(repositoryDirectory), false, true);
		LOGGER.info("Integration Test Repository created.");
		LOGGER.info("The URL:" + repositoryURL.toString());
	}
	
	@Test
	public void RevisionLoadTest () throws FileNotFoundException, SVNException {
		SVNAdminClient admin = new SVNAdminClient((ISVNAuthenticationManager)null, null);
		FileInputStream dumpStream = new FileInputStream(dumpDirectory + "/repos.dump");
		admin.doLoad(new File(repositoryDirectory), dumpStream);
	}
}
