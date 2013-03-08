package com.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

/*
Length-prefix each document (in bytes).
Read the length of the first document from the socket
Read that much data from the socket, dumping it into a ByteArrayOutputStream
Create a ByteArrayInputStream from the results
Parse that ByteArrayInputStream to get the first document
Repeat for the second document etc
*/

//http://stackoverflow.com/questions/10520757/parsing-xml-data-in-java

/**
 * Langt ifra ferdig
 * @author perok
 *
 */
@Deprecated
public class XML {
	private String username = "peri";
	private String password = "hemmelig";

	private ArrayList<String> rolev;
	/*
	public boolean readXML(String xml) {
		
        rolev = new ArrayList<String>();
        
        Document dom;
        
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(xml);

            Element doc = dom.getDocumentElement();

            role1 = getTextValue(role1, doc, "role1");
            if (role1 != null) {
                if (!role1.isEmpty())
                    rolev.add(role1);
            }
            role2 = getTextValue(role2, doc, "role2");
            if (role2 != null) {
                if (!role2.isEmpty())
                    rolev.add(role2);
            }
            role3 = getTextValue(role3, doc, "role3");
            if (role3 != null) {
                if (!role3.isEmpty())
                    rolev.add(role3);
            }
            role4 = getTextValue(role4, doc, "role4");
            if ( role4 != null) {
                if (!role4.isEmpty())
                    rolev.add(role4);
            }
            return true;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return false;
    }*/
	
	public StreamResult saveToXML() {
		StreamResult sr = new StreamResult();
	    Document dom;
	    Element e = null;

	    // instance of a DocumentBuilderFactory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        // use factory to get an instance of document builder
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        // create instance of DOM
	        dom = db.newDocument();

	        // create the root element
	        Element rootEle = dom.createElement("user");

	        // create data elements and place them under root
	        e = dom.createElement("username");
	        e.appendChild(dom.createTextNode(username));
	        rootEle.appendChild(e);
	        
	        e = dom.createElement("password");
	        e.appendChild(dom.createTextNode(password));
	        rootEle.appendChild(e);

	        dom.appendChild(rootEle);

	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "user.dtd");
	            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	            // send DOM to file
	            tr.transform(new DOMSource(dom), sr);//new StreamResult(new FileOutputStream(xml)));
	        } catch (TransformerException te) {
	            System.out.println(te.getMessage());
	        } //catch (IOException ioe) {
	         //   System.out.println(ioe.getMessage());
	        //}
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	    }
	    
	    
	    return sr;    
	}
	
	private String getTextValue(String def, Element doc, String tag) {
	    String value = def;
	    NodeList nl;
	    nl = doc.getElementsByTagName(tag);
	    if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
	        value = nl.item(0).getFirstChild().getNodeValue();
	    }
	    return value;
	}
	
	public static void main(String[] args) {
		//new XML().go();
	}
	
	//public void go(){
		//saveToXML("hei.xml");
	//}
	
}
