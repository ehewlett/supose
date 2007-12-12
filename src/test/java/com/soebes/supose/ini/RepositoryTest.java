package com.soebes.supose.ini;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.soebes.supose.jobs.ConfigurationRepositories;
import com.soebes.supose.jobs.RepositoryConfiguration;
import com.soebes.supose.jobs.RepositoryFactory;
import com.soebes.supose.scan.Repository;

@Test
public class RepositoryTest {
	private ConfigurationRepositories confRepos = null; 
	
	@BeforeClass
	public void beforeClass() {
		confRepos = new ConfigurationRepositories("/repositories-test.ini");
		assertNotNull(confRepos, "We had expected to get an instance");
	}

	public void testConnection() {
    	RepositoryConfiguration reposConfig = confRepos.getRepositoryConfiguration(confRepos.getNames()[0]);
    	assertNotNull(reposConfig, "We had expected to get a configuration for a single repository.");
    	Repository repository = RepositoryFactory.createInstance(reposConfig);
    	assertEquals(repository.validConnection(), true, "We have expected to get an connection");
	}

}
