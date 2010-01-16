package com.soebes.supose.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.thoughtworks.xstream.XStream;

public class RepositoriesConfigFile {

	public static RepositoriesConfig getConfiguration(File configurationFile) throws FileNotFoundException {
		XStream xs = createInstance();
		FileInputStream fip = new FileInputStream(configurationFile);
		return (RepositoriesConfig)xs.fromXML(fip);
	}

	public static XStream createInstance() {
		XStream xs = new XStream();
		
		xs.alias("repositories", RepositoriesConfig.class);
		xs.addImplicitCollection(RepositoriesConfig.class, "repositories");

		xs.alias("repository", RepositoryConfig.class);

		xs.alias("indexer", RepositoryIndexConfig.class);

//		xs.alias("licenses", CheckLicenses.class);
//		xs.alias("license", CheckLicense.class);
//	
//		xs.alias("name", CheckLicense.class);
//		xs.alias("url", CheckLicense.class);
//		xs.registerLocalConverter(CheckLicense.class, "names", new NamingCollectionConverter("name", String.class, xs.getMapper()));
//		xs.registerLocalConverter(CheckLicense.class, "urls", new NamingCollectionConverter("url", String.class, xs.getMapper()));
		return xs;
	}

}
