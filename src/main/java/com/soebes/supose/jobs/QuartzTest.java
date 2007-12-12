package com.soebes.supose.jobs;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.soebes.supose.scan.Repository;

public class QuartzTest {
	private static Logger LOGGER = Logger.getLogger(QuartzTest.class);

    public static void main(String[] args) {

    	int scheduledJobs = 0;

        try {
            // Grab the Scheduler instance from the Factory 
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            JobSchedulerListener schedulerListener = new JobSchedulerListener();

            scheduler.addSchedulerListener(schedulerListener);


			//HACK: Remove hard coded name of configuration file. 
            ConfigurationRepositories confRepos = new ConfigurationRepositories("/repositories.ini");
            LOGGER.info("We have " + confRepos.getNames().length + " repositories.");
            for(int i=0; i<confRepos.getNames().length; i++) {
            	String repositoryName = confRepos.getNames()[i];
            	RepositoryConfiguration reposConfig = confRepos.getRepositoryConfiguration(repositoryName);
            	Repository repository = RepositoryFactory.createInstance(reposConfig);
            	if (!repository.validConnection()) {
            		//Connection has failed.
            		LOGGER.error("The repository " + repository.getUrl() + " can't be connected!");
            		//Do not make a job from a non connectable repository.
            		continue;
            	}
            	LOGGER.info("The repository " + repositoryName + " is ready for scanning.");
            	JobDetail jobDetail = new JobDetail(repositoryName, null, JobClass.class);
            	jobDetail.getJobDataMap().put("repository", repository);

            	CronTrigger cronTrigger1 = null;
            	try {
            		cronTrigger1 = new CronTrigger(
            				"myCron" + repositoryName,
            				Scheduler.DEFAULT_GROUP,
            				"0 * * ? * *" // Every minute ...
            		);
            	} catch (Exception e) {
            		System.err.println("Error for cronTrigger wrong expression for cron: " + e);
            		System.exit(1);
            	}            
            	
            	scheduler.scheduleJob(jobDetail, cronTrigger1);
            	scheduledJobs++;
            }

            //If we haven't started any job we shutdown...
            if (scheduledJobs == 0) {
            	scheduler.shutdown();
            }

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}