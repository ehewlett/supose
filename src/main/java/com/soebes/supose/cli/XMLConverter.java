package com.soebes.supose.cli;

import java.util.List;

import org.apache.log4j.Logger;

import com.soebes.supose.search.ResultEntry;
import com.thoughtworks.xstream.XStream;

public class XMLConverter {
	private static Logger LOGGER = Logger.getLogger(XMLConverter.class);

	private XStream xstream = null;

	public XMLConverter() {
		xstream = new XStream();
		xstream.alias("entry", ResultEntry.class);
		XMLFieldConvert fc = new XMLFieldConvert();
//		xstream.registerConverter(fc);
	}
	
	public String toXML(List<ResultEntry> resultList) {
		return xstream.toXML(resultList);
	}
	public String toXML(ResultEntry result) {
		return xstream.toXML(result);
	}
}
