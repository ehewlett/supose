package com.soebes.supose.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import com.soebes.supose.config.model.RepositoryConfigContainer;
import com.soebes.supose.config.model.io.xpp3.RepositoriesXpp3Reader;
import com.soebes.supose.config.model.io.xpp3.RepositoriesXpp3Writer;

public class Configuration {
	private static Logger LOGGER = Logger.getLogger(Configuration.class);

	private RepositoryConfigContainer configContainer;

	public Configuration() {
		configContainer = new RepositoryConfigContainer();
	}

	@Override
	public String toString() {
		StringWriter stringWriter = new StringWriter();
		RepositoriesXpp3Writer xmlWriter = new RepositoriesXpp3Writer();
		try {
			xmlWriter.write(stringWriter, this.configContainer);
			stringWriter.close();
		} catch (IOException e) {
			LOGGER.error("Problem during writing of configuration.", e);
		}
		return stringWriter.toString();
	}

	/**
	 * Store the configuration to the given file in 
	 * XML format.
	 * @param output
	 * @throws IOException
	 */
	public void save(File output) {
		try {
			FileOutputStream fos = new FileOutputStream(output);
			RepositoriesXpp3Writer xmlWriter = new RepositoriesXpp3Writer();
			xmlWriter.write(fos, this.configContainer);
			fos.close();
		} catch (IOException e) {
			LOGGER.error("IOException during writing of XML file.", e);
		}
	}

	/**
	 * Load the configuration from the given XML file.
	 * @param configFile
	 */
	public void load(File configFile) {
		try {
			RepositoriesXpp3Reader xmlReader = new RepositoriesXpp3Reader();
			FileInputStream fis = new FileInputStream(configFile);
			configContainer = xmlReader.read(fis);
		} catch (FileNotFoundException e) {
			LOGGER.error("FileNotFoundException:", e);
		} catch (IOException e) {
			LOGGER.error("IOException:", e);
		} catch (XmlPullParserException e) {
			LOGGER.error("XmlPullParser:", e);
		}
	}

}
