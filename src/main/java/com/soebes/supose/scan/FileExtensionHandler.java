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
package com.soebes.supose.scan;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.io.SVNRepository;

import com.soebes.supose.utility.FileExtensionProperty;
import com.soebes.supose.utility.FileName;

/**
 * @author Karl Heinz Marbaise
 *
 */
public class FileExtensionHandler {
	private Document doc;

	private Map fileProperties;

	private static Logger LOGGER = Logger.getLogger(FileExtensionProperty.class);

	public void execute(SVNRepository repository, String path, long revision) {
		FileName fn = new FileName(path);

		ArrayList<SVNDirEntry> dirEntries = new ArrayList<SVNDirEntry>();
		try {
			repository.getDir(fn.getBaseName(), revision, null, dirEntries);
		} catch (Exception e) {
			LOGGER.error("Error during getDir() " + e);
		}
		int index = dirEntries.indexOf(fn.getFileName());
		LOGGER.debug("Entry: " + dirEntries.get(index).getName() + " " + dirEntries.get(index).getSize());
		
		//Check if we have an extension...
		if (fn.getExt().length() > 0) {
			try {
				String className = FileExtensionProperty.getInstance().getProperty(fn.getExt());
				try {
					Class handlerClass = Class.forName(className);
					AScanDocument dh = (AScanDocument) handlerClass.newInstance();
					dh.setProperties(getFileProperties());
					dh.setDocument(doc);
					dh.indexDocument(repository, path, revision);
				} catch (ClassNotFoundException e) {
					LOGGER.error("Cannot create instance of : " + className + " " + e);
				} catch (InstantiationException e) {
					LOGGER.error("Cannot create an instance of : " + className + " " + e);
				} catch (IllegalAccessException e) {
					LOGGER.error("Illegal Access of instance of : " + className + " " + e);
				}
			} catch (Exception e) {
				//There is no entry for the extension so we use the default
				//scanner for all other document types.
				LOGGER.info("There is no property entry defined for the file extension '" + fn.getExt() + "'");
				AScanDocument dh = new ScanDefaultDocument();
				dh.setProperties(getFileProperties());
				dh.setDocument(doc);
				dh.indexDocument(repository, path, revision);
			}
		} else {
			LOGGER.info("We have no file extension found for the file '" + path + "'");
		}
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public Map getFileProperties() {
		return fileProperties;
	}

	public void setFileProperties(Map fileProperties) {
		this.fileProperties = fileProperties;
	}
}
