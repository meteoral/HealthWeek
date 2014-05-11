package com.mobilehealth.core;

public interface ChildPageMessageListener {
	
	void changeToPage(Class<?> clazz);
	
	void childPageChanged(int firstLevelIndex, String className);
	
	int getPageIndex(String className);
}
