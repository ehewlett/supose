package com.soebes.supose.ini;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.soebes.supose.jobs.ConfigurationRepositories;
import com.soebes.supose.jobs.RepositoryConfiguration;

@Test
public class ConfigurationRepositoriesTest {
	private ConfigurationRepositories confRepos = null; 
	
	@BeforeClass
	public void beforeClass() {
		confRepos = new ConfigurationRepositories("/repositories-test.ini");		
		assertNotNull(confRepos, "We had expected to get an instance");
	}

	public void testGetNames() {
		assertNotNull(confRepos.getNames(), "We had expected to get an list of available repositories.");
		assertEquals(confRepos.getNames().length, 1, "We had expected to get an array with one element");
	}

	@Test(dependsOnMethods={"testGetNames"})
	public void testGetRepositoryConfiguration() {
		String repositoryName = confRepos.getNames()[0];
		RepositoryConfiguration rconfig = confRepos.getRepositoryConfiguration(repositoryName);
		assertNotNull(rconfig, "We had expected to get an instance of RepositoryConfiguration");
	}

	@Test(dependsOnMethods={"testGetRepositoryConfiguration"})
	public void testRepositoryConfiguration10() {
		String repositoryName = confRepos.getNames()[0];
		RepositoryConfiguration rconfig = confRepos.getRepositoryConfiguration(repositoryName);
		assertEquals(rconfig.getUrl(), "http://svn.traveler/jagosi", "We had expected to get an URL");
		assertEquals(rconfig.getIndexUsername(), "kama", "We had expected to get the username 'kama'");
		assertEquals(rconfig.getIndexPassword(), "ka4m", "We had expected to get the username 'kama'");
		assertEquals(rconfig.getFromRev(), "1", "We had expected to get the revision 1");
		assertEquals(rconfig.getToRev(), "HEAD", "We had expected to get the revision HEAD");
	}

}
