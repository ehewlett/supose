package com.soebes.supose.jobs;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.tmatesoft.svn.core.wc.SVNRevision;

import com.soebes.supose.scan.ScanRepository;

public class JobClass implements Job {
	private static Logger LOGGER = Logger.getLogger(JobClass.class);

	private ScanRepository scanRepos = null;

	public JobClass () {
		LOGGER.info("JobClass: ctor called.");
		scanRepos = new ScanRepository();
		scanRepos.setCreateIndex(true);
		scanRepos.setRepositoryURL("http://svn.traveler/jagosi");
		scanRepos.setStartRevision(1);
		scanRepos.setEndRevision(SVNRevision.HEAD.getNumber());
		scanRepos.setIndexDirectory("testIndex.JobClass");
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("JobClass: here i am");
		LOGGER.info("JobClass: started scanning repository...");
		scanRepos.scan();
		LOGGER.info("JobClass: scanning repository done...");
	}
	
}
