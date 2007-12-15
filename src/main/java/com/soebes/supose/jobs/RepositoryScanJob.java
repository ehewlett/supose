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

import java.io.File;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.tmatesoft.svn.core.SVNException;

import com.soebes.supose.config.RepositoryConfiguration;
import com.soebes.supose.config.RepositoryJobConfiguration;
import com.soebes.supose.repository.Repository;
import com.soebes.supose.scan.ScanRepository;

public class RepositoryScanJob implements StatefulJob {
	private static Logger LOGGER = Logger.getLogger(RepositoryScanJob.class);

	private ScanRepository scanRepos = null;
	private RepositoryJobConfiguration jobConfig = null;

	public RepositoryScanJob () {
		LOGGER.debug("RepositoryScanJob: ctor called.");
		scanRepos = new ScanRepository();
	}

	private void subexecute (JobExecutionContext context) throws Exception {
		LOGGER.info(
				"["
			+	context.getJobDetail().getName() + "/"
			+	context.getJobDetail().getFullName()
			+	"]"
		);
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		Repository repos = (Repository) jobDataMap.get(JobDataNames.REPOSITORY);
		RepositoryConfiguration reposConfig = (RepositoryConfiguration) jobDataMap.get(JobDataNames.REPOSITORYCONFIGURATION);


		String indexDirectory = (String) jobDataMap.get(JobDataNames.INDEX);
		
		String configDir = (String) jobDataMap.get(JobDataNames.CONFIGDIR);
		
		LOGGER.info("configDir:" + configDir);
		LOGGER.info("URL: " + repos.getUrl());
		LOGGER.info("Name: " + reposConfig.getRepositoryName());
		jobConfig = new RepositoryJobConfiguration(configDir + File.separator + reposConfig.getRepositoryName(), reposConfig);

		if (repos.getRepository().getLatestRevision() > reposConfig.getFromRev()) {

			long startRev = reposConfig.getFromRev() + 1;
			long endRev = repos.getRepository().getLatestRevision();
			scanRepos.setRepository(repos);
			scanRepos.setStartRevision(startRev);
			scanRepos.setEndRevision(endRev);

//			scanRepos.scan(writer);
			//New revision existing till the last scanning...
			//scann the content
			
			//save the configuration file with the new revision numbers.
			reposConfig.setFromRev(endRev);
			//store the changed configuration items.
			jobConfig.save();
		} else {
			LOGGER.info("Nothing to do, cause no changes had been made at the repository.");
			//Nothing to do, cause no new revision are existing...
		}
		LOGGER.info("RepositoryScanJob: scanning repository done...");
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			subexecute(context);
		} catch (Exception e) {
			LOGGER.error("We had an unexpected Exception: " + e);
		}
	}
	
}
