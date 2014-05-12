package com.mobilehealth.core;

public interface ChildPageListener {
	
    void changeToPage(Class<?> clazz);
	
	void childPageChanged(int firstLevelIndex, String className);
}
