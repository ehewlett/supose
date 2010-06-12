package com.soebes.supose.config.xml;

public class RepositoryIndexConfig {
	
	private String tempIndex;
	private String targetIndex;
	private int commits;
	public String getTempIndex() {
		return tempIndex;
	}
	public void setTempIndex(String tempIndex) {
		this.tempIndex = tempIndex;
	}
	public String getTargetIndex() {
		return targetIndex;
	}
	public void setTargetIndex(String targetIndex) {
		this.targetIndex = targetIndex;
	}
	public void setCommits(int commits) {
		this.commits = commits;
	}
	public int getCommits() {
		return commits;
	}
	
}
