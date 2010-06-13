package com.soebes.supose.config.xml;

import java.io.IOException;
import java.io.StringWriter;

import org.testng.annotations.Test;

import com.soebes.supose.config.model.RepositoryConfigContainer;
import com.soebes.supose.config.model.RepositoryItem;
import com.soebes.supose.config.model.RepositoryList;
import com.soebes.supose.config.model.ScheduledRepositoryItem;
import com.soebes.supose.config.model.SchedulerRepositoryList;
import com.soebes.supose.config.model.io.xpp3.RepositoriesXpp3Writer;

public class CreateRepositoryConfigurationTest {

	public String convertToString(RepositoryConfigContainer repositoryConfigContainer) throws IOException {
		StringWriter stringWriter = new StringWriter();
		RepositoriesXpp3Writer xmlWriter = new RepositoriesXpp3Writer();
		xmlWriter.write(stringWriter, repositoryConfigContainer);
		return stringWriter.toString();
	}
	
	@Test
	public void createTest() throws IOException {
		RepositoryConfigContainer rcc = new RepositoryConfigContainer();
		RepositoryList rl = new RepositoryList();

		
		RepositoryItem ri1 = new RepositoryItem();
		ri1.setBlockSize(10000);
		ri1.setFromRevision("1");
		ri1.setToRevision("1000");
		ri1.setId("SupoSE");
		ri1.setName("SupoSE Repository");
		ri1.setUsername("username");
		ri1.setPassword("Password");
		
		rl.addRepository(ri1);
		
		RepositoryItem ri2 = new RepositoryItem();
		ri2.setBlockSize(10000);
		ri2.setFromRevision("1");
		ri2.setToRevision("1000");
		ri2.setId("JaGOSi");
		ri2.setName("JaGOSiRepository");
		ri2.setUsername("username");
		ri2.setPassword("Password");
		
		rl.addRepository(ri2);
		rcc.setRepository(rl);
		
		SchedulerRepositoryList srl = new SchedulerRepositoryList();
		
		ScheduledRepositoryItem sri = new ScheduledRepositoryItem();
		sri.setRepositoryId("SupoSE");
		sri.setSchedulerTrigger("0 0 0 00 ");
		srl.addScheduledRepository(sri);

		rcc.setScheduler(srl);
		rcc.setBaseDirectory("/home/test");
		System.out.println(convertToString(rcc));
	}
}
