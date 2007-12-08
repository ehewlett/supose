package com.soebes.supose.jobs;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {

    public static void main(String[] args) {

        try {
            // Grab the Scheduler instance from the Factory 
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            JobDetail jobDetail = new JobDetail("MyFirstJob", null, JobClass.class);
            
            CronTrigger cronTrigger = null;
			try {
				cronTrigger = new CronTrigger("myCronTrigger",
						Scheduler.DEFAULT_GROUP, "0 * * ? * *");
			} catch (Exception e) {
				System.err.println("Error for cronTrigger: " + e);
				System.exit(1);
			}            
//			Trigger trigger = TriggerUtils.makeMinutelyTrigger();
//            trigger.setStartTime(TriggerUtils.getEvenMinuteDate(new Date()));
//            trigger.setName("myTrigger");
            scheduler.scheduleJob(jobDetail, cronTrigger);

//            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}