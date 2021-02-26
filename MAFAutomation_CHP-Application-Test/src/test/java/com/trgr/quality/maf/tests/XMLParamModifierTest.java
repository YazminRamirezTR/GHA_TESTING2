package com.trgr.quality.maf.tests;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.trgr.quality.maf.commonutils.XMLAttributeModifier;

public class XMLParamModifierTest {
	
	private XMLAttributeModifier xmla;
	
	@Test
	public void XMLParamModifierTest() throws ParserConfigurationException, SAXException, IOException{
		xmla = new XMLAttributeModifier();
		xmla.CheckXMLAttributeAndModifyQCSuites();
	}

}
