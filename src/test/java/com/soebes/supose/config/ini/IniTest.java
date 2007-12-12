package com.soebes.supose.config.ini;

import java.io.IOException;
import java.util.Iterator;

import org.ini4j.Ini;
import org.ini4j.InvalidIniFormatException;
import org.ini4j.Ini.Section;
import org.testng.annotations.Test;

@Test
public class IniTest {

	public void testReadIni() {
		try {
			Ini ini = new Ini(IniTest.class.getResourceAsStream("/repositories.ini"));
			System.out.println("Nr: " + ini.size());
			for(Iterator iter= ini.keySet().iterator(); iter.hasNext(); ) {
				String key = (String) iter.next();
				Section value = ini.get(key);
				System.out.println("Section: " + key);
				for(Iterator iterSection = value.keySet().iterator(); iterSection.hasNext(); ) {
					String keySection = (String) iterSection.next();
					String valueSection = value.get(keySection);
					System.out.println("  -> key: " + keySection + " value:" + valueSection);
				}
			}
		} catch (InvalidIniFormatException e) {
			System.out.println("Error: " + e);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
}
