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
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.tmatesoft.svn.core.SVNException;

import com.soebes.supose.scan.Repository;
import com.soebes.supose.scan.ScanRepository;

public class RepositoryScanJob implements StatefulJob {
	private static Logger LOGGER = Logger.getLogger(RepositoryScanJob.class);

	private ScanRepository scanRepos = null;

	public RepositoryScanJob () {
		LOGGER.info("RepositoryScanJob: ctor called.");
		scanRepos = new ScanRepository();
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info(
				"["
			+	context.getJobDetail().getName() + "/"
			+	context.getJobDetail().getFullName()
			+	"]"
		);
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		Repository repos = (Repository) jobDataMap.get(JobDataNames.REPOSITORY);

		LOGGER.info("URL: " + repos.getUrl());
		try {
			LOGGER.info("URL: " + repos.getRepository().getRepositoryUUID(true));
		} catch (SVNException e) {
			LOGGER.error("Faild during getRepositoryUUID(false) " + e);
		}

		String indexDirectory = (String) jobDataMap.get(JobDataNames.INDEX);
		
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
		LOGGER.info("RepositoryScanJob: scanning repository done...");
	}
	
}
