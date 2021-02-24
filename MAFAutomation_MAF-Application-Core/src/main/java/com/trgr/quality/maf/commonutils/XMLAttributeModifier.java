package com.trgr.quality.maf.commonutils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLAttributeModifier {

	private static List<String> productPaths;
	private static NodeList nodes;
	private static String browserOverride;
	protected static Logger log = LoggerFactory.getLogger(XMLAttributeModifier.class);

	public void CheckXMLAttributeAndModifyQCSuites() throws ParserConfigurationException, SAXException, IOException {
		
		// Set browser
		Scanner bwsrInput = new Scanner(System.in);
		System.out.println("Enter chosen browser: ");
		browserOverride = bwsrInput.nextLine();
		System.out.println("Selected browser: " + browserOverride);

		// File checklist
		productPaths = new ArrayList<String>();
		productPaths.add("TestNGTestScripts\\chparg_qc_Regression.xml");
		productPaths.add("TestNGTestScripts\\chpbr_qc_Regression.xml");
		productPaths.add("TestNGTestScripts\\chpmex_qc_Regression.xml");
		productPaths.add("TestNGTestScripts\\chppe_qc_Regression.xml");
		productPaths.add("TestNGTestScripts\\chpury_qc_Regression.xml");
		productPaths.add("TestNGTestScripts\\chppy_qc_Regression.xml");

		// Iteration for each XML document
		for (int i = 0; i < productPaths.size(); i++) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			// Document builder
			Document input = factory.newDocumentBuilder().parse(productPaths.get(i));

			// Create Xpath epression search and node list with matched nodes
			XPath xpath = XPathFactory.newInstance().newXPath();

			String expr = String.format("//*[contains(@%s,'%s')]","name","Browser");
			try {
				nodes = (NodeList) xpath.evaluate(expr, input, XPathConstants.NODESET);
			} catch (XPathExpressionException e) {
				log.info("Search for matching nodes failed");
				e.printStackTrace();
			}

			// Change node value
			
			/* Optional Form
			 * for (int y = 0; y < nodes.getLength(); y++) { Element value = (Element)
			 * nodes.item(y); value.setAttribute("value", browserOverride); }
			 */

			IntStream.range(0, nodes.getLength())
				.mapToObj(y -> (Element) nodes.item(y))
				.forEach(value -> value.setAttribute("value", browserOverride));
			
			
			//Output Stream result
			
			TransformerFactory tfactory = TransformerFactory.newInstance();
			try {
				tfactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

				Transformer tformer = tfactory.newTransformer();
				DOMSource source = new DOMSource(input);
				//Output propertys (pretty print)
				tformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
				tformer.setOutputProperty(OutputKeys.METHOD, "xml");
				tformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				tformer.setOutputProperty(OutputKeys.INDENT, "yes");
				tformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				//File write
				FileWriter writer = new FileWriter(new File(productPaths.get(i)));
				StreamResult output = new StreamResult(writer);
				tformer.transform(source, output);
			} catch (TransformerConfigurationException e) {
				log.info("Failed XML Constant");
				e.printStackTrace();
			} catch (TransformerException te) {
				log.info("xformer transform failed");
				te.printStackTrace();
			}
		}
		
		
	}

}
