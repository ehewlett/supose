package com.soebes.supose.cli;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class XMLFieldConvert implements Converter {
	private static Logger LOGGER = Logger.getLogger(XMLFieldConvert.class);

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
//		
//        LOGGER.debug("marshal(): name:" + tag.getName() + " revision:" + tag.getRevision());
//        writer.addAttribute("revision", tag.getRevision());
//        writer.setValue(tag.getName());
		
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return null;
	}

	public boolean canConvert(Class type) {
        return XMLFieldConvert.class == type;
	}

}
