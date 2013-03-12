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
import com.settings.Global;
/**
 * XML creator for the models
 * @author perok
 *
 */
public class JAXBMarshaller {	
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
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		jaxbWrapperToXML(wrapper, baos);
		
		//if(Global.verbose) System.out.println("[JAXBMarshaller]==DEBUG==");
		//if(Global.verbose) System.out.println(baoss.toString());
		//if(Global.verbose) System.out.println("[JAXBMarshaller]== END ==");
		
		return baos.toString();
		
	}
}
