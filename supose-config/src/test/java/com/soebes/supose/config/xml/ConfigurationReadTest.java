package com.soebes.supose.config.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.soebes.supose.TestBase;
import com.soebes.supose.config.model.RepositoryConfigContainer;
import com.soebes.supose.config.model.io.xpp3.RepositoriesXpp3Reader;

public class ConfigurationReadTest extends TestBase {

	public RepositoryConfigContainer read(String xml) throws IOException, XmlPullParserException {
		RepositoriesXpp3Reader xmlReader = new RepositoriesXpp3Reader();
		StringReader reader = new StringReader(xml);
		return xmlReader.read(reader);
	}
	
	public RepositoryConfigContainer read(File xmlFile) throws IOException, XmlPullParserException {
		RepositoriesXpp3Reader xmlReader = new RepositoriesXpp3Reader();
		FileInputStream  fis = new FileInputStream(xmlFile);
		return xmlReader.read(fis);
	}

	@Test
	public void readXMLConfigurationTest() throws IOException, XmlPullParserException {
		File configFile = new File(getTestResourcesDirectory() + File.separator + "repositories.xml");
		RepositoryConfigContainer config = read(configFile);
		Assert.assertNotNull(config);
		Assert.assertEquals(config.getRepositories().getRepository().size(), 2);
		Assert.assertEquals(config.getScheduler().getScheduledRepository().size(), 1);
	}
}
