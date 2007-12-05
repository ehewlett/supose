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
package com.soebes.supose.cli;

import java.util.List;

import org.apache.commons.cli2.CommandLine;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.option.Command;

/**
 * @author Karl Heinz Marbaise
 *
 */
public class ScanCommand extends CLIBase {

    private Option optionURL = null;
    private Option optionUsername = null;
    private Option optionPassword = null;
    private Option optionFromRev = null;

	public ScanCommand() {
		setCommand(createCommand());
	}

	private Command createCommand() {
    	optionURL = obuilder
    		.withShortName("U")
    		.withLongName("url")
    		.withArgument(abuilder.withName("url").create())
    		.withDescription("Define the position where to find the index created by an scan.")
    		.create();
    	
    	optionUsername = obuilder
			.withLongName("username")
			.withArgument(abuilder.withName("username").create())
			.withDescription("Define the username which is used to make an authorization against the Subversion repository.")
			.create();

    	optionPassword = obuilder
    		.withShortName("p")
			.withLongName("password")
			.withArgument(abuilder.withName("password").create())
			.withDescription("Define the password which is used to make an authorization against the Subversion repository.")
			.create();

    	optionFromRev = obuilder
			.withShortName("r")
			.withLongName("fromrev")
			.withArgument(abuilder.withName("fromrev").create())
			.withDescription("Define the revision from which we will start to scan the repository.")
			.create();
    	
    	Group scanOptionIndex = gbuilder
    		.withOption(optionURL)
    		.withOption(optionUsername)
    		.withOption(optionPassword)
    		.withOption(optionFromRev)
    		.create();
    	
    	return cbuilder
	    	.withName("scan")
	    	.withName("sc")
	    	.withDescription("Scan a given repository for later search.")
	    	.withChildren(scanOptionIndex)
	    	.create();
	}

	public Option getOptionURL() {
		return optionURL;
	}

	public Option getOptionUsername() {
		return optionUsername;
	}

	public Option getOptionPassword() {
		return optionPassword;
	}

	public Option getOptionFromRev() {
		return optionFromRev;
	}

	@SuppressWarnings("unchecked")
	public String getURL (CommandLine cline) {
		List<String> list = cline.getValues((getOptionURL()));
		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public String getUsername (CommandLine cline) {
		List<String> list = cline.getValues((getOptionUsername()));
		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public String getPassword (CommandLine cline) {
		List<String> list = cline.getValues((getOptionPassword()));
		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public Long getFromRev (CommandLine cline) {
		List<String> list = cline.getValues((getOptionFromRev()));
		if (list == null || list.size() == 0) {
			return new Long(1);
		} else {
			return Long.parseLong(list.get(0));
		}
	}
	
}
