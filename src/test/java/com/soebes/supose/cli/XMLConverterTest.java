package com.soebes.supose.cli;

import java.io.UnsupportedEncodingException;

import org.testng.annotations.Test;

import com.soebes.supose.search.ResultEntry;

public class XMLConverterTest {

	@Test
	public void f() throws UnsupportedEncodingException {
		XMLConverter xc = new XMLConverter();
		ResultEntry re = new ResultEntry();
		re.setAuthor("TestAuthor");
		re.setContents("This is & ä ö ü Ö Ä Ü  > < \" ^ ' \n \r !\"§$%&/()=?");

		
		String s = new String(re.getContents().getBytes(), "UTF-8");
		String result = xc.toXML(re);
		System.out.println("Result: " + result);
	}

}
