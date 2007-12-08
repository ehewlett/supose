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
// SupoSE
package com.soebes.supose.scan;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.LockObtainFailedException;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.soebes.supose.FieldNames;
import com.soebes.supose.utility.FileName;

/**
 * @author Karl Heinz Marbaise
 *
 */
public class ScanRepository {
	static {
        /*
         * For using over http:// and https://
         */
        DAVRepositoryFactory.setup();
        /*
         * For using over svn:// and svn+xxx://
         */
        SVNRepositoryFactoryImpl.setup();
        
        /*
         * For using over file:///
         */
        FSRepositoryFactory.setup();
	}

	private static Logger LOGGER = Logger.getLogger(ScanRepository.class);

	/**
	 * This defines the revision from where we start to scan the given repository.
	 */
	private long startRevision;
	/**
	 * This defines the revision to which we will scan the given repository. 
	 */
	private long endRevision;
	private String repositoryURL;
	private String username;
	private String password;
	
	private String indexDirectory;

	private SVNRepository repository = null;

	public ScanRepository() {
		setStartRevision(0);
		setEndRevision(0);
		setRepositoryURL(null);
		setUsername(null);
		setPassword(null);
	}

	/**
	 * This will initialize the repository access, based on the given 
	 * <code>repositoryURL</code>.
	 */
	public void initRepository () {
        try {
            /*
             * Creates an instance of SVNRepository to work with the repository.
             * All user's requests to the repository are relative to the
             * repository location used to create this SVNRepository.
             * SVNURL is a wrapper for URL strings that refer to repository locations.
             */
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(repositoryURL));
        } catch (SVNException svne) {
            /*
             * Perhaps a mailformed URL is the cause of this exception.
             */
            System.err.println("error while creating an SVNRepository for the location '"
            		+ repositoryURL + "': " + svne.getMessage());
            System.exit(1);
        }
	}

	public int scan() {
		initRepository();

		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
        repository.setAuthenticationManager(authManager);

        System.out.println("Repositories latest Revision: " + endRevision);
        Collection logEntries = null;
        try {
            logEntries = repository.log(new String[] {""}, null, startRevision, endRevision, true, true);

        } catch (SVNException svne) {
            System.out.println("error while collecting log information for '"
                    + repositoryURL + "': " + svne.getMessage());
            System.exit(1);
        }


        File indexDir = new File(indexDirectory);
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(indexDir, new StandardAnalyzer(), true);
			writer.setUseCompoundFile(false);
			writer.setMergeFactor(1000);
			writer.setMaxBufferedDocs(1000);
//			writer.setInfoStream(System.out);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("LogEntries: " + logEntries.size());
        for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
            SVNLogEntry logEntry = (SVNLogEntry) entries.next();

            LOGGER.debug("---------------------------------------------");
            LOGGER.debug("revision: " + logEntry.getRevision());
            LOGGER.debug("author: " + logEntry.getAuthor());
            LOGGER.debug("date: " + logEntry.getDate());
            LOGGER.debug("log message: " + logEntry.getMessage());
            System.out.printf("\r%7d", logEntry.getRevision());
            System.out.print(" Date: " + logEntry.getDate());

            if (logEntry.getChangedPaths().size() > 0) {
            	LOGGER.debug("changed paths:");
				try {
					workOnChangeSet(writer, repository, logEntry);
				} catch (Exception e) {
	            	LOGGER.debug("ERROR:" + e.getMessage());
				}                
            } else {
            	LOGGER.debug("No changed paths found!");
            }
        }
		int numIndexed = writer.docCount();
		try {
			writer.optimize();
			writer.close();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		repository.closeSession();
		return numIndexed;
	}

	private void workOnChangeSet(IndexWriter indexWriter, SVNRepository repository, SVNLogEntry logEntry) {
		Set changedPathsSet = logEntry.getChangedPaths().keySet();

		int count = 0;
		LOGGER.info("Number of files for revision: " + changedPathsSet.size());
		for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext();) {
			count ++;

			SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
			LOGGER.debug(" "
		            + entryPath.getType()
		            + "	"
		            + entryPath.getPath()
		            + ((entryPath.getCopyPath() != null) ? " (from "
		                    + entryPath.getCopyPath() + " revision "
		                    + entryPath.getCopyRevision() + ")" : ""));

			SVNDirEntry dirEntry = getInformationAboutEntry(repository, logEntry, entryPath);
		    
			try {
				if (SVNLogEntryPath.TYPE_ADDED == entryPath.getType()) {
					LOGGER.debug("File " + entryPath.getPath() + " added...");
					//get All file content and index it.
					indexFile(indexWriter, repository, logEntry, entryPath);
				}
				if (SVNLogEntryPath.TYPE_MODIFIED == entryPath.getType()) {
					//Get all file content and index it...
					LOGGER.debug("Modified file...");
					//get All file content and index it.
					indexFile(indexWriter, repository, logEntry, entryPath);
				}
				if (SVNLogEntryPath.TYPE_REPLACED == entryPath.getType()) {
					//Get all file content and index it...
					LOGGER.debug("Replaced file...");
					//get All file content and index it.
					indexFile(indexWriter, repository, logEntry, entryPath);
				}
				if (SVNLogEntryPath.TYPE_DELETED == entryPath.getType()) {
					LOGGER.debug("The file '" + entryPath.getPath() + "' has been deleted.");
					indexFile(indexWriter, repository, logEntry, entryPath);
					if (dirEntry != null
							&& dirEntry.getKind().equals(SVNNodeKind.DIR)) {
					} else {
					}
				}
			} catch (Exception e) {
				LOGGER.error("something wrong: " + e.getMessage());
			}		     
		}
	}

	protected void addTokenizedField(Document doc, String fieldName, String value) {
		doc.add(new Field(fieldName,  value, Field.Store.YES, Field.Index.TOKENIZED));
	}
	private void addUnTokenizedField(Document doc, String fieldName, String value) {
		doc.add(new Field(fieldName,  value, Field.Store.YES, Field.Index.UN_TOKENIZED));
	}
	private void addUnTokenizedField(Document doc, String fieldName, Long value) {
		doc.add(new Field(fieldName,  value.toString(), Field.Store.YES, Field.Index.UN_TOKENIZED));
	}
	private void addUnTokenizedField(Document doc, String fieldName, Date value) {
		doc.add(new Field(fieldName,  value.toGMTString(), Field.Store.YES, Field.Index.UN_TOKENIZED));
	}
	private void addUnTokenizedField(Document doc, String fieldName, Character value) {
		doc.add(new Field(fieldName,  value.toString(), Field.Store.YES, Field.Index.UN_TOKENIZED));
	}

	private void indexFile(IndexWriter indexWriter, SVNRepository repository, SVNLogEntry logEntry, SVNLogEntryPath entryPath) 
		throws SVNException, IOException {
			Map fileProperties  = new HashMap();
			
			SVNNodeKind nodeKind = repository.checkPath(entryPath.getPath(), logEntry.getRevision());

			Document doc = new Document();
			addUnTokenizedField(doc, FieldNames.REVISION, logEntry.getRevision());
			
			boolean isDir = nodeKind == SVNNodeKind.DIR;
			boolean isFile = nodeKind == SVNNodeKind.FILE;
			FileName fileName = new FileName(entryPath.getPath(), isDir);
			LOGGER.info("FileName: '" + entryPath.getPath() + "'");
			addUnTokenizedField(doc, FieldNames.PATH, fileName.getPath());

			if (isDir) {
				addUnTokenizedField(doc, FieldNames.NODE, "dir");
			} else if (isFile) {
				addUnTokenizedField(doc, FieldNames.NODE, "file");
			} else {
				//This means a file/directory has been deleted.
				addUnTokenizedField(doc, FieldNames.NODE, "unknown");
			}

			
			//Did an copy operation take place...
			if (entryPath.getCopyPath() != null) {
				addUnTokenizedField(doc, FieldNames.FROM, entryPath.getCopyPath());
				addUnTokenizedField(doc, FieldNames.FROMREV, entryPath.getCopyRevision());
			}
			
			addUnTokenizedField(doc, FieldNames.FILENAME, entryPath.getPath());
			addUnTokenizedField(doc, FieldNames.AUTHOR, logEntry.getAuthor() == null ? "" : logEntry.getAuthor());
			
			//We will add the message as tokenized field to be able to search within the log messages.
			addTokenizedField(doc, FieldNames.MESSAGE, logEntry.getMessage() == null ? "" : logEntry.getMessage());
			addUnTokenizedField(doc, FieldNames.DATE, logEntry.getDate());
			
			addUnTokenizedField(doc, FieldNames.KIND, entryPath.getType());

//TODO: May be don't need this if we use repositoryname?
			addUnTokenizedField(doc, FieldNames.REPOSITORY, repository.getRepositoryUUID(false));
			
//TODO: Should be filled with an usable name to distinguish different repositories..
			addUnTokenizedField(doc, FieldNames.REPOSITORYNAME, "TESTREPOS");


			if (nodeKind == SVNNodeKind.NONE) {
				LOGGER.debug("The " + entryPath.getPath() + " is a NONE entry.");
			} else if (nodeKind == SVNNodeKind.DIR) {
				//The given entry is a directory.
				LOGGER.debug("The " + entryPath.getPath() + " is a directory.");
				//Here we need to call getDir to get directory properties.
				Collection dirEntries = null;
				repository.getDir(entryPath.getPath(), logEntry.getRevision(), fileProperties, dirEntries);
				indexProperties(fileProperties, doc);

			} else if (nodeKind == SVNNodeKind.FILE) {
				
				//The given entry is a file.
//TODO: Check if we need to do this in the subclasses instead here.
				//This means we will get every file from the repository....
				
				//Get only the properties of the file
				repository.getFile(entryPath.getPath(), logEntry.getRevision(), fileProperties, null);
				indexProperties(fileProperties, doc);


//TODO: Do we really need this?
//				addUnTokenizedField(doc, FieldNames.SIZE, Long.toString(baos.size()));
				FileExtensionHandler feh = new FileExtensionHandler();
				feh.setFileProperties(fileProperties);
				feh.setDoc(doc);
				feh.execute(repository, entryPath.getPath(), logEntry.getRevision());
			}

			indexWriter.addDocument(doc);
			LOGGER.debug("File " + entryPath.getPath() + " indexed...");
	}


	private void indexProperties(Map fileProperties, Document doc) {
		for (Iterator iterator = fileProperties.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			if (!SVNProperty.isEntryProperty(entry.getKey())) {
				//Every property will be stored with key:value.
				LOGGER.debug("Indexing property: " + entry.getKey() + " value:'" + entry.getValue() + "'"); 
				addUnTokenizedField(doc, entry.getKey(), entry.getValue());
			}
		}
	}

	
	private SVNDirEntry getInformationAboutEntry(SVNRepository repository, SVNLogEntry logEntry, SVNLogEntryPath entryPath) {
		SVNDirEntry dirEntry = null;
		try {
			LOGGER.debug("getInformationAboutEntry() name:" + entryPath.getPath() + " rev:" + logEntry.getRevision());
			dirEntry = repository.info(entryPath.getPath(), logEntry.getRevision());
		} catch (SVNException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return dirEntry;
	}

	public long getStartRevision() {
		return startRevision;
	}

	public void setStartRevision(long startRevision) {
		this.startRevision = startRevision;
	}

	public long getEndRevision() {
		return endRevision;
	}

	public void setEndRevision(long endRevision) {
		this.endRevision = endRevision;
	}

	public String getRepositoryURL() {
		return repositoryURL;
	}

	public void setRepositoryURL(String repositoryURL) {
		this.repositoryURL = repositoryURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIndexDirectory() {
		return indexDirectory;
	}

	public void setIndexDirectory(String indexDirectory) {
		this.indexDirectory = indexDirectory;
	}
	

}
