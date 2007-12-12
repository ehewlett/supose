/*
 * The (S)ubversion Re(po)sitory (S)earch (E)ngine (SupoSE for short).
 *
 * Copyright (c) 2007 by SoftwareEntwicklung Beratung Schulung (SoEBeS)
 * Copyright (C) 2007 by Karl Heinz Marbaise

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
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA *
 *
 * The License can viewed online under http://www.gnu.org/licenses/gpl.html
 * If you have any questions about the Software or about the license
 * just write an email to license@soebes.de
 *
 */
package com.soebes.supose.jobs;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.soebes.supose.scan.Repository;

/**
 * @author Karl Heinz Marbaise
 *
 */
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
            	JobDetail jobDetail = new JobDetail(repositoryName, null, RepositoryScanJob.class);
            	jobDetail.getJobDataMap().put(JobDataNames.REPOSITORY, repository);

            	CronTrigger cronTrigger1 = null;
            	String cronExpression = "";
            	//If the use would like to configure different scheduling trigger
            	if (reposConfig.existCron()) {
            		cronExpression = reposConfig.getCron();
            	} else {
            		//The default every minue...
            		cronExpression = "0 * * ? * *";
            	}
            	try {
            		cronTrigger1 = new CronTrigger(
            				"myCron" + repositoryName,
            				Scheduler.DEFAULT_GROUP,
            				cronExpression
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