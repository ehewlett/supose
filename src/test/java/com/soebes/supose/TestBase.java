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
package com.soebes.supose;

import java.net.URL;

/**
 * @author Karl Heinz Marbaise
 */
public class TestBase {
	/**
	 * This method will give you back
	 * the filename incl. the absolute path name
	 * to the resource. 
	 * If the resource does not exist it will give
	 * you back the resource name incl. the path.
	 * 
	 * It will give you back an absolute path
	 * incl. the name which is in the same directory 
	 * as the the class you've called it from.
	 * 
	 * @param name
	 * @return
	 */
	public String getFileResource(String name) {
		URL url = this.getClass().getResource(name);
		if (url != null) {
			return url.getFile();
		} else {
			//We have a file which does not exists
			//We got the path
			url = this.getClass().getResource(".");
			return url.getFile() + name;
		}
	}

}
