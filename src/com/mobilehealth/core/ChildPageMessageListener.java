package com.mobilehealth.core;

public interface ChildPageMessageListener {
	
	void changeToPage(int toIndex);
	
	void changeCenterCaption(String str, int visibility);
}
