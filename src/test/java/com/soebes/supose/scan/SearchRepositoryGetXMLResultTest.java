/**
 * The (Su)bversion Re(po)sitory (S)earch (E)ngine (SupoSE for short).
 *
 * Copyright (c) 2007, 2008, 2009, 2010 by SoftwareEntwicklung Beratung Schulung (SoEBeS)
 * Copyright (c) 2007, 2008, 2009, 2010 by Karl Heinz Marbaise
 *
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
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 *
 * The License can viewed online under http://www.gnu.org/licenses/gpl.html
 * If you have any questions about the Software or about the license
 * just write an email to license@soebes.de
 */

package com.soebes.supose.scan;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.soebes.supose.FieldNames;
import com.soebes.supose.TestBase;
import com.soebes.supose.cli.XMLConverter;
import com.soebes.supose.search.ResultEntry;
import com.soebes.supose.search.SearchRepository;

@Test
public class SearchRepositoryGetXMLResultTest extends TestBase {
	private static Logger LOGGER = Logger.getLogger(SearchRepositoryGetXMLResultTest.class);

	private static SearchRepository searchRepository = new SearchRepository();
	
	private static XMLConverter xmlConverter = new XMLConverter();
	
	@BeforeClass
	public void beforeClass() {
		searchRepository.setIndexDirectory(getIndexDirectory());
	}
	@AfterClass
	public void afterClass() throws IOException {
		IndexReader reader = searchRepository.getReader();
		reader.close();
	}

	public void testQueryForFilenameOnly() {
		List<ResultEntry> result = searchRepository.getResult("+filename:f1.txt");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 4);
	}

	public void testBug246QueryForFilenameWithHyphenAsterik() {
		List<ResultEntry> result = searchRepository.getResult("+filename:testEXCEL*.xls");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 2);
	}

	public void testBug246QueryForFilenameWithHyphenAndDot() {
		List<ResultEntry> result = searchRepository.getResult("+filename:\"testEXCEL\\-formats.xls\"");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 1);
	}

	public void testBug246QueryForFilenameWithHyphenNotQuoted() {
		List<ResultEntry> result = searchRepository.getResult("+filename:testEXCEL\\-formats\\.xls");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 1);
	}

	public void testBug246QueryForFilenameWithDotReplacedByQuestionMark() {
		List<ResultEntry> result = searchRepository.getResult("+filename:testEXCEL-formats?xls");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 1);
	}

	public void testQueryForFilenameOnlyUppercase() {
		List<ResultEntry> result = searchRepository.getResult("+filename:F1.txt");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 4);
	}

	public void testQueryForFilenameMixedCaseTestPPT() {
		List<ResultEntry> result = searchRepository.getResult("+filename:testPPT.*");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 2);
	}

	public void testQueryForFilenameLowercaseTestPPT() {
		List<ResultEntry> result = searchRepository.getResult("+filename:testppt.*");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 2);
	}

	public void testQueryForFilenameWithPrefixedWildcardTextFiles() {
		List<ResultEntry> result = searchRepository.getResult("+filename:*.txt");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 8);
	}
	
	public void testQueryForFilenameWithPrefixedWildcardExcelFiles() {
		List<ResultEntry> result = searchRepository.getResult("+filename:*.xls");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 2);
	}

	public void testQueryForFilenameWithPrefixedWildcardExcel2007Files() {
		List<ResultEntry> result = searchRepository.getResult("+filename:*.xlsx");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 2);
	}
	
	public void testQueryForPathMixedCase() {
		List<ResultEntry> result = searchRepository.getResult("+path:/*/B_*");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 6);
	}

	public void testQueryForPathLowerCase() {
		List<ResultEntry> result = searchRepository.getResult("+path:/*/b_*");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 6);
	}

	public void testQueryForTermForExcelWorksheet() {
		List<ResultEntry> result = searchRepository.getResult("+contents:\"Sample Excel Worksheet\"");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 2);
	}
	
	public void testQueryForTermForExcelWorksheetCombination() {
		List<ResultEntry> result = searchRepository.getResult("+contents:\"Sample Excel Worksheet\" +filename:*.xls");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 1);
	}
	
	public void testQueryForTermFromWord() {
		List<ResultEntry> result = searchRepository.getResult("+contents:\"Sample Word\"");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 2);
	}
	
	public void testQueryForTermFromWordCombination() {
		List<ResultEntry> result = searchRepository.getResult("+contents:\"Sample Word\" +filename:*.doc");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 1);
	}

	public void testQueryForTermOfPowerPoint() {
		List<ResultEntry> result = searchRepository.getResult("+contents:\"Sample Powerpoint\"");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 2);
	}
	
	public void testQueryOpenOfficeODP() {
		//Das ist ein Test mit OpenOffice 3.0 auf Windows XP
		List<ResultEntry> result = searchRepository.getResult("+contents:\"OpenOffice 3.0 auf Windows XP\"");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 1);
	}

	public void testQueryOpenOfficeODS() {
		//Test Mit OpenOffice
		//3.0
		//Windows XP
		List<ResultEntry> result = searchRepository.getResult("+contents:\"Test Mit OpenOffice 3.0 Windows XP\"");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 1);
	}

	public void testQueryOpenOfficeODT() {
		//This is a Test
		//In OpenOffice
		//3.0
		//Windows XP
		List<ResultEntry> result = searchRepository.getResult("+contents:\"This is a Test In OpenOffice 3.0 Windows XP\"");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 1);
	}

	public void testQueryArchiveContentsTAR() {
		List<ResultEntry> result = searchRepository.getResult("+contents:\"This file is contined in a archive\"");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 1);
	}

	public void testQueryArchiveContentsZIP() {
		List<ResultEntry> result = searchRepository.getResult("+contents:\"This file is contents of a zip archive\"");
		System.out.println(xmlConverter.toXML(result));
	    assertEquals(result.size(), 1);
	}

	public void testQueryForTagsOfAllKind() {
		List<ResultEntry> result = searchRepository.getResult("+tag:*");
		//This will be 4 entries which are coming from the tag entry
		//and one entry which is coming from the maventag.
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 7);
	}

	public void testQueryForMavenTags() {
		List<ResultEntry> result = searchRepository.getResult("+maventag:*");
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 4);
	}

	public void testQueryForTagsOnly() {
		List<ResultEntry> result = searchRepository.getResult("+tag:* -maventag:* -subversiontag:*");
		//This has to be result of the tags only.
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 1);
	}

	public void testQueryForSubversionTagsOnly() {
		List<ResultEntry> result = searchRepository.getResult("+subversiontag:*");
		System.out.println(xmlConverter.toXML(result));
		//This has to be result into a single entry for the tag.
		assertEquals(result.size(), 2);
	}

	public void testQueryForBranchPath() {
		List<ResultEntry> result = searchRepository.getResult("+path:*/branches/*");
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 7);
	}

	public void testQueryForBranches() {
		List<ResultEntry> result = searchRepository.getResult("+branch:*");
		//We have only a single entry here
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 1);
	}
	
	public void testQueryForKind() {
		List<ResultEntry> result = searchRepository.getResult("+kind:D");
		//We have only a single entry here
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 3);
	}

	public void testQueryForNode() {
		List<ResultEntry> result = searchRepository.getResult("+node:dir");
		//We have only a single entry here
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 12);
	}
	
	public void testQueryForDeletedTag() throws CorruptIndexException, IOException {
		List<ResultEntry> result = searchRepository.getResult("+path:*/tags/* +kind:d");
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 1);

		assertEquals(result.get(0).getFilename().length(), 0, "We have expected to get an empty filename field for a tag which is a directory.");

		assertNotNull(result.get(0).getPath(), "We have expected to find the " + FieldNames.PATH + " field.");
		assertEquals(result.get(0).getPath(), "/project1/tags/RELEASE-0.0.1/", "We have expected to get an particular path value");
	}

	public void testQueryForSVNProperty() {
		List<ResultEntry> result = searchRepository.getResult("+svn\\:mergeinfo:*");
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 3);
	}

	public void testQueryForSVNPropertyContent() {
		List<ResultEntry> result = searchRepository.getResult("+svn\\:mergeinfo:*/branches/*");
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 3);
	}

	public void testQueryForSVNPropertyContentFile() {
		List<ResultEntry> result = searchRepository.getResult("+svn\\:mergeinfo:*/f3.txt\\:*");
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 1);
	}

	public void testQueryForSVNPropertyContentPath() {
		List<ResultEntry> result = searchRepository.getResult("+svn\\:mergeinfo:*/B_0.0.2/*");
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 1);
	}

	/**
	 * This test is based on issue Bug #215
	 */
	public void testQueryForREADMEFileIssue215() {
		List<ResultEntry> result = searchRepository.getResult("+filename:README");
		System.out.println(xmlConverter.toXML(result));
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getFilename(), "README");
		assertEquals(result.get(0).getPath(), "/project1/trunk/");
	}

	public void testCallGetterByName() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ResultEntry re = new ResultEntry();
		re.setAuthor("TestAuthor");
		re.setRevision("123123123");
		Object result = searchRepository.callGetterByName(re, "revision");
		assertEquals(result, re.getRevision());
	}
}
