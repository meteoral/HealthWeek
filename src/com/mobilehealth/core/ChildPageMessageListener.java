package com.mobilehealth.core;

public interface ChildPageMessageListener {
	
	void changeToPage(Class<?> clazz);
	
	void childPageChanged(int firstLevelIndex, int secondLevelIndex);
	
	int getPageIndex(String className);
}
