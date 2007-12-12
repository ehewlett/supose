package com.soebes.supose.jobs;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;

import com.soebes.supose.scan.Repository;
import com.soebes.supose.scan.ScanRepository;

public class JobClass implements StatefulJob {
	private static Logger LOGGER = Logger.getLogger(JobClass.class);

	private ScanRepository scanRepos = null;

	public JobClass () {
		LOGGER.info("JobClass: ctor called.");
		scanRepos = new ScanRepository();
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info(
				context.getJobDetail().getDescription() + " " 
			+	context.getJobDetail().getName() + " "
			+	context.getJobDetail().getFullName()
		);
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		Repository repos = (Repository) jobDataMap.get("repository");
		ISVNAuthenticationManager authManager = repos.getAuthManager();

		LOGGER.info("URL: " + repos.getUrl());
		try {
			LOGGER.info("URL: " + repos.getRepository().getRepositoryUUID(true));
		} catch (SVNException e) {
			LOGGER.error("Faild during getRepositoryUUID(false) " + e);
		}

		String indexDirectory = (String) jobDataMap.get("index");
		
		for(int i=0; i<10; i++) {
			try {
				//Simulate to do many things...
				Thread.sleep(1000);
			} catch (Exception e) {
				LOGGER.error("Thread.sleep() has failed. " + e);
			}
			LOGGER.info("Working: " + (i+1));
		}

//		scanRepos.scan();

		repos.close();
		LOGGER.info("JobClass: scanning repository done...");
	}
	
}
