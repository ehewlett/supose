package com.soebes.supose.repository;

import java.io.File;

/**
 * This is a possible base class for many test which
 * need a base directory to put some result files in or
 * do need to create directories etc. during tests.
 * 
 * @author Karl Heinz Marbaise
 *
 */
public class TestBase {

	/**
	 * Return the base directory of the project.
	 * @return
	 */
	public String getMavenBaseDir() {
		//basedir is defined by Maven 
		//but the above will not work under Eclipse.
		//So there I'm using user.dir 
		return System.getProperty("basedir", System.getProperty("user.dir", "."));
	}

	
	/**
	 * Return the target directory of the current project.
	 * @return
	 */
	public String getTargetDir() {
		return getMavenBaseDir() + File.separatorChar + "target";
	}
}
