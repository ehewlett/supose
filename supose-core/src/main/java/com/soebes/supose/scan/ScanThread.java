package com.soebes.supose.scan;

import org.apache.log4j.Logger;

public class ScanThread extends Thread {
	private static Logger LOGGER = Logger.getLogger(ScanThread.class);

	private ScanRepository scanRepository;
	private String indexDirectory;
	private boolean create;

	public ScanThread(
		ScanRepository scanRepository, 
		String indexdirectory, 
		boolean create
	) {
		this.scanRepository = scanRepository;
		this.indexDirectory = indexdirectory;
		this.create = create;
	}
	
	public void run() {
		try {
			ScanSingleRepository.scanSingleRepos(this.scanRepository, this.indexDirectory, this.create);
		} catch (Exception e) {
			LOGGER.error("Exception had occured:", e);
		}
	}
}
