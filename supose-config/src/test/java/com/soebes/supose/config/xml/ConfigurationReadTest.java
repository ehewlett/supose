package com.soebes.supose.config.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.soebes.supose.TestBase;
import com.soebes.supose.config.model.RepositoryConfigContainer;
import com.soebes.supose.config.model.io.xpp3.RepositoriesXpp3Reader;
import com.soebes.supose.config.model.io.xpp3.RepositoriesXpp3Writer;

public class ConfigurationReadTest extends TestBase {

	public String convertToString(RepositoryConfigContainer repositoryConfigContainer) throws IOException {
		StringWriter stringWriter = new StringWriter();
		RepositoriesXpp3Writer xmlWriter = new RepositoriesXpp3Writer();
		xmlWriter.write(stringWriter, repositoryConfigContainer);
		return stringWriter.toString();
	}

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
		Assert.assertEquals(config.getRepository().getRepository().size(), 2);
		Assert.assertEquals(config.getScheduler().getScheduledRepository().size(), 1);
	}
}
