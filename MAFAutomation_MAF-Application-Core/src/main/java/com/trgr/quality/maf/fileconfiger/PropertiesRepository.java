package com.trgr.quality.maf.fileconfiger;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.tree.OverrideCombiner;
import org.slf4j.LoggerFactory;

public final class PropertiesRepository {
	
	private static CombinedConfiguration propAggregator = new CombinedConfiguration(new OverrideCombiner());
	
	public static void appendProperties(String propertiesFile) throws Exception {
    
    
     
		PropertiesConfiguration properties = null;
		try {
			LoggerFactory.getLogger(PropertiesRepository.class).info("Loading property file : " + propertiesFile);
			properties = new PropertiesConfiguration(propertiesFile);
			properties.setDelimiterParsingDisabled(true);
		} catch (ConfigurationException ce) {
			throw new Exception("Unable to load properties", ce);
		}
		if (properties != null) {
			propAggregator.addConfiguration(properties);
		}
	}


	public static String getString(String key)
	{
		return  propAggregator.getString(key);
		
		//propAggregator.getProperty(key);
	}
	
	public static int getInt(String key)
	{
		return propAggregator.getInt(key);
	}
	

	public static long getLong(String key) {
		// TODO Auto-generated method stub
		return propAggregator.getLong(key);
	}
}
