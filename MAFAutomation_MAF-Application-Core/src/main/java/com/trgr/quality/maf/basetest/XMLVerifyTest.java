package com.trgr.quality.maf.basetest;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.trgr.quality.maf.commonutils.XMLAttributeModifier;

public class XMLVerifyTest extends XMLAttributeModifier {

	@Test
	public void XMLBrowserChangeTest() throws ParserConfigurationException, SAXException, IOException {
		CheckXMLAttributeAndModifyQCSuites();
	}
	
}
