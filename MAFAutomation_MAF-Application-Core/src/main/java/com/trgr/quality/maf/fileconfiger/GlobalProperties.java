package com.trgr.quality.maf.fileconfiger;


public class GlobalProperties {

	public static final String CHROME="chrome";
	public static final String FIREFOX="firefox";
	public static final String IE="internet explorer";
	public static final String DEV="Dev";
	public static final String EDGE="edge";
	public static final String HUB="hub";
	
	//Selectors
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String LINKTEXT = "linktext";
	public static final String PARTIALLINKTEXT = "partiallink";
	public static final String CLASSNAME = "classname";
	public static final String TAGNAME = "tag";
	public static final String CSS_SELECTOR = "css";
	public static final String XPATH = "xpath";
	
	public static final int EXPLICIT_WAIT = PropertiesRepository.getInt("global.driver.wait");
}

