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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.soebes.supose.config.RepositoryConfiguration;
import com.soebes.supose.config.RepositoryJobConfiguration;
import com.soebes.supose.repository.Repository;
import com.soebes.supose.scan.Index;
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

//		String indexDirectory = (String) jobDataMap.get(JobDataNames.INDEX);

		String configDir = (String) jobDataMap.get(JobDataNames.CONFIGDIR);

		LOGGER.info("configDir:" + configDir + " URL: " + repos.getUrl() + " Name: " + reposConfig.getRepositoryName());
		
		jobConfig = new RepositoryJobConfiguration(configDir + File.separator + reposConfig.getRepositoryName(), reposConfig);

		Index index = new Index ();
		//We will allways create a new index.
		index.setCreate(true);
		IndexWriter indexWriter = index.createIndexWriter(configDir+ File.separator + "index." + reposConfig.getRepositoryName());

		LOGGER.info("Revision: " + repos.getRepository().getLatestRevision() + " FromRev:" + reposConfig.getFromRev());
		if (repos.getRepository().getLatestRevision() > reposConfig.getFromRev()) {

			long startRev = reposConfig.getFromRev() + 1;
			long endRev = repos.getRepository().getLatestRevision();
			scanRepos.setRepository(repos);
			scanRepos.setStartRevision(startRev);
			scanRepos.setEndRevision(endRev);
			scanRepos.setName(reposConfig.getRepositoryName());

			//New revision existing till the last scanning...
			//scann the content
			scanRepos.scan(indexWriter);

			try {
				indexWriter.optimize();
				indexWriter.close();
			} catch (CorruptIndexException e) {
				LOGGER.error("Corrupted index: " + e);
			} catch (IOException e) {
				LOGGER.error("IOException during closing of index: " + e);
			}

			//Merge the created index into the existing one...
			mergeIndex(configDir+ File.separator + "index." + reposConfig.getResult(), configDir+ File.separator + "index." + reposConfig.getRepositoryName());
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

	
	private void mergeIndex(String destination, String source) {
		ArrayList<String> sourceList = new ArrayList<String>();
		sourceList.add(source);
		mergeIndex(destination, sourceList);
	}
	/**
	 * Merge all given indexes together to a single index.
	 * @param destination This will define the destination directory
	 *   of the index where all other indexes will be merged to.
	 * @param indexList This is the list of indexes which are
	 *   merged into the destination index.
	 */
	private void mergeIndex (String destination, List<String> indexList) {
		LOGGER.debug("We are trying to merge indexes to the destination: " + destination);
		Index index = new Index ();
		//We assume an existing index...
		index.setCreate(false);
		IndexWriter indexWriter = index.createIndexWriter(destination);

		try {
			LOGGER.info("Merging of indexes started.");
			FSDirectory [] fsDirs = new FSDirectory[indexList.size()];
			for (int i = 0; i < indexList.size(); i++) {
				fsDirs[i] = FSDirectory.getDirectory(indexList.get(i));
			}
			indexWriter.addIndexes(fsDirs);
			indexWriter.close();
			LOGGER.info("Merging of indexes succesfull.");
		} catch (Exception e) {
			LOGGER.error("Something wrong during merging of index: " + e);
		}
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			subexecute(context);
		} catch (Exception e) {
			LOGGER.error("We had an unexpected Exception: " + e);
		}
	}
	
}
