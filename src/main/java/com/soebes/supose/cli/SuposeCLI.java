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
package com.soebes.supose.cli;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.cli2.CommandLine;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.util.HelpFormatter;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.soebes.supose.FieldNames;
import com.soebes.supose.scan.Index;
import com.soebes.supose.scan.Repository;
import com.soebes.supose.scan.ScanRepository;

/**
 * This will define the Command Line Version of SupoSE.
 * 
 * @author Karl Heinz Marbaise
 *
 */
public class SuposeCLI {
	private static Logger LOGGER = Logger.getLogger(SuposeCLI.class);

	private static SuposeCommandLine suposecli = new SuposeCommandLine();
	private static ScanRepository scanRepository = new ScanRepository();
	private static CommandLine commandLine = null;

	public static void main(String[] args) {
		try {
			commandLine = suposecli.doParseArgs(args);
		} catch (OptionException e) {
			System.err.println("Error: Unexpected Option given on command line. " + e);
			System.exit(1);
		}

		if (commandLine.hasOption(suposecli.getGlobalOptionH())) {
	        final StringWriter out = new StringWriter();
	        final HelpFormatter helpFormatter = new HelpFormatter();
	        helpFormatter.setGroup(suposecli.getSuposeOptions());
	        helpFormatter.setPrintWriter(new PrintWriter(out));
	        helpFormatter.printHelp();
	        System.out.println(out);
	        System.exit(0);
		} else if (commandLine.hasOption(suposecli.getScanCommand())) {
			runScan(suposecli.getScliScanCommand());
		} else if (commandLine.hasOption(suposecli.getSearchCommand())) {
			runSearch(suposecli.getScliSearchCommand());
		} else {
			System.err.println("Error: You should define either scan or search as command or the -H option to get help.");
			System.exit(1);
		}
	}

	/**
	 * This will do the command argument extraction and give the parameter to
	 * the scanRepository class which will do the real repository scan.
	 * @param scanCommand The command line.
	 */
	private static void runScan(ScanCommand scanCommand) {
		String url = scanCommand.getURL(commandLine);
		long fromRev = scanCommand.getFromRev(commandLine);
		long toRev = scanCommand.getToRev(commandLine);
		String indexDirectory = scanCommand.getIndexDir(commandLine);
		boolean create = scanCommand.getCreate(commandLine);
		String username = scanCommand.getUsername(commandLine);
		String password = scanCommand.getPassword(commandLine);

		Index index = new Index ();
		//We will create a new one if --create is given on command line
		//otherwise we will append to the existing index.
		index.setCreate(create);
		IndexWriter indexWriter = index.createIndexWriter(indexDirectory);

		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(
			username, 
			password
		);
		Repository repository = new Repository(url, authManager);

		scanRepository.setRepository(repository);

		//We start with the revision which is given on the command line.
		//If it is not given we will start with revision 1.
		scanRepository.setStartRevision(fromRev); 
		//We will scan the repository to the current HEAD of the repository.
		scanRepository.setEndRevision(toRev);

		LOGGER.info("Scanning started.");
		scanRepository.scan(indexWriter);
		LOGGER.info("Scanning ready.");

		try {
			indexWriter.optimize();
			indexWriter.close();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void runSearch(SearchCommand searchCommand) {
		LOGGER.info("Searching started...");
		String indexDirectory = searchCommand.getIndexDir(commandLine);
		String queryLine = searchCommand.getQuery(commandLine);
		List<String> cliFields = searchCommand.getFields(commandLine);

		System.out.println("Query: '" + queryLine + "'");

		for(int i=0; i<cliFields.size(); i++) {
			System.out.print("Field[" + i + "]=" + cliFields.get(i) + " ");
		}
		System.out.println("");

	    IndexReader reader = null;
	    
	    try {
	    	
	    	reader = IndexReader.open(indexDirectory);
	    	
	    	Searcher searcher = new IndexSearcher(reader);
//	    	Analyzer analyzer = new StandardAnalyzer();
	    	Analyzer analyzer = new KeywordAnalyzer();
	    	
	    	//Here we define the default field for searching.
	        QueryParser parser = new QueryParser(FieldNames.CONTENTS, analyzer);
	        Query query = parser.parse(queryLine);
			Hits hits = searcher.search(query);

			for (int i = 0; i < hits.length(); i++) {
				Document doc = hits.doc(i);
				List<Field> fieldList = doc.getFields();
				System.out.print((i+1) + ". ");
				for(int k=0; k<fieldList.size();k++) {
					Field field = (Field) fieldList.get(k);
					if ((cliFields.size() > 0) && cliFields.contains(field.name())) {
						System.out.print(field.name() + ": " + field.stringValue() + " ");
					} else {
						if (FieldNames.REVISION.equals(field.name())) {
							System.out.print("R:" + field.stringValue() + " ");
						}
						if (FieldNames.FILENAME.equals(field.name())) {
							System.out.print("F:" + field.stringValue() + " ");
						}
						if (FieldNames.KIND.equals(field.name())) {
							System.out.print("K:" + field.stringValue() + " ");
						}
					}
				}
				System.out.println("");
			}
			
	    } catch (CorruptIndexException e) {
			System.err.println("Error: The index is corrupted: " + e);
	    } catch (IOException e) {
			System.err.println("Error: IOException: " + e);
		} catch (Exception e) {
			System.err.println("Error: Something has gone wrong: " + e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				System.err.println("Error: IOException during close(): " + e);
			}
		}
	}

}
