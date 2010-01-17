package com.soebes.supose.config.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.soebes.supose.TestBase;
import com.thoughtworks.xstream.XStream;

public class RepositoriesConfigFileTest extends TestBase {

	@Test
	public void writeTest() throws MalformedURLException {
	
		String expectedXML = "<repositories>\n"
		+"  <baseDirectory>/home/kama/</baseDirectory>\n"
		+"  <repository>\n"
		+"    <id>SupoSE</id>\n"
		+"    <name>The First Repository</name>\n"
		+"    <url>http://svn.supose.org/supose</url>\n"
		+"    <username>kama</username>\n"
		+"    <password>kamapass</password>\n"
		+"    <fromRevision>1</fromRevision>\n"
		+"    <toRevision>HEAD</toRevision>\n"
		+"    <scheduler>0 * * * ? *</scheduler>\n"
		+"    <indexer>\n"
		+"      <tempIndex>/home/result.SuposeTemp</tempIndex>\n"
		+"      <targetIndex>/home/result.Supose</targetIndex>\n"
		+"      <commits>200</commits>\n"
		+"    </indexer>\n"
		+"  </repository>\n"
		+"</repositories>";

		RepositoryConfig rc = new RepositoryConfig();
		rc.setId("SupoSE");
		rc.setName("The First Repository");
		rc.setUrl(new URL("http://svn.supose.org/supose"));
		rc.setUsername("kama");
		rc.setPassword("kamapass");
		rc.setScheduler("0 * * * ? *");
		rc.setFromRevision("1");
		rc.setToRevision("HEAD");

		RepositoryIndexConfig ric = new RepositoryIndexConfig();
		ric.setCommits(200);
		ric.setTargetIndex("/home/result.Supose");
		ric.setTempIndex("/home/result.SuposeTemp");
		rc.setIndexer(ric);

		RepositoriesConfig rcs = new RepositoriesConfig();
		rcs.setBaseDirectory("/home/kama/");
		rcs.addRepository(rc);

		XStream xs = RepositoriesConfigFile.createInstance();
		Assert.assertEquals(xs.toXML(rcs), expectedXML);
	}

	@Test
	public void readFileTest() throws FileNotFoundException {
		RepositoriesConfig result = RepositoriesConfigFile.getConfiguration(new File(getTestResourcesDirectory() + "repositories.xml"));
		Assert.assertNotNull(result);
		Assert.assertTrue(result.getRepositories().size() > 0);
		Assert.assertEquals(result.getRepositories().get(0).getId(), "SupoSE");
		Assert.assertEquals(result.getRepositories().get(0).getName(), "This is the Name we use");
		Assert.assertEquals(result.getRepositories().get(0).getUrl().toString(), "http://svn.traveler/supose");
		Assert.assertEquals(result.getRepositories().get(0).getUsername(), "kama");
		Assert.assertEquals(result.getRepositories().get(0).getPassword(), "kama");
		Assert.assertEquals(result.getRepositories().get(0).getFromRevision(), "1");
		Assert.assertEquals(result.getRepositories().get(0).getToRevision(), "HEAD");
		Assert.assertEquals(result.getRepositories().get(0).getScheduler(), "0 * * ? * *");

	}
}
