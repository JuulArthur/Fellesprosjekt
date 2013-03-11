package com.xml;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.model.UserModel;
import com.net.msg.MSGFlag;
import com.net.msg.MSGType;
import com.net.msg.MSGWrapper;
/**
 * XML creator for the models
 * @author perok
 *
 */
public class JAXBMarshaller {
	
	private boolean verbose = false;
	
	private JAXBContext jaxbContext;
	private Marshaller jaxbMarshaller;
	
	public JAXBMarshaller() {
		try {
			/*UserModel*/
			jaxbContext = JAXBContext.newInstance(UserModel.class, MSGFlag.class, MSGType.class, MSGWrapper.class);
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends the XML edition of UserModel through the given OutputStream
	 * @param os
	 */
	private void jaxbWrapperToXML(Object o, OutputStream os){
		try {
			jaxbMarshaller.marshal(o, os);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getXMLRepresentation(int ID, MSGType type, MSGFlag flag, ArrayList<Object> alist){
		MSGWrapper wrapper = new MSGWrapper(ID, type, flag, alist);
		
		ByteArrayOutputStream baoss = new ByteArrayOutputStream();
		
		jaxbWrapperToXML(wrapper, baoss);
		
		if(verbose) System.out.println("==DEBUG==");
		if(verbose) System.out.println(baoss.toString());
		if(verbose) System.out.println("== END ==");
		
		return baoss.toString();
		
	}
}
