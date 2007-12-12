package com.soebes.supose.jobs;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.ini4j.Ini;
import org.ini4j.InvalidIniFormatException;

public class ConfigurationRepositories {
	private static Logger LOGGER = Logger.getLogger(ConfigurationRepositories.class);

	private String configFile;
	private Ini iniFile;

	public ConfigurationRepositories(String configFile) {
		setConfigFile(configFile);
		try {
			iniFile = new Ini(ConfigurationRepositories.class.getResourceAsStream(configFile));
		} catch (InvalidIniFormatException e) {
			LOGGER.error("The format of the given INI is not correct. " + e);
		} catch (IOException e) {
			LOGGER.error("Some problems happen with the INI File " + e);
		}
	}

	/**
	 * Get the names of the repositories.
	 * @return Array of all available repositories.
	 */
	public String[] getNames() {
		return (String[]) iniFile.keySet().toArray(new String[iniFile.keySet().size()]);
	}
	
	public RepositoryConfiguration getRepositoryConfiguration(String name) {
		return new RepositoryConfiguration(iniFile.get(name));
	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public Ini getIniFile() {
		return iniFile;
	}

	public void setIniFile(Ini iniFile) {
		this.iniFile = iniFile;
	}
}
