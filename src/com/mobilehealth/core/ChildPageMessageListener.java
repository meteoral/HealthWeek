package com.mobilehealth.core;

public interface ChildPageMessageListener {
	
	void changeToPage(int toIndex);
	
	void childPageChanged(int firstLevelIndex, int secondLevelIndex);
}
