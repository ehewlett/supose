package com.soebes.supose.jobs;

import org.ini4j.Ini.Section;

public class RepositoryConfiguration {

	private Section section;

	public RepositoryConfiguration(Section section) {
		setSection(section);
	}

	public String getIndexUsername() {
		return section.get("indexusername");
	}

	public void setIndexUsername(String username) {
		section.put("indexusername", username);
	}

	public String getIndexPassword() {
		return section.get("indexpassword");
	}

	public void setIndexPassword(String password) {
		section.put("indexpassword", password);
	}

	public String getFromRev() {
		return section.get("fromrev");
	}
	public void setFromRev(String rev) {
		section.put("fromrev", rev);
	}
	public String getToRev() {
		return section.get("torev");
	}
	public void setToRev(String rev) {
		section.put("torev", rev);
	}

	public String getUrl() {
		return section.get("url");
	}

	public void setUrl(String url) {
		section.put("url", url);
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}
}
