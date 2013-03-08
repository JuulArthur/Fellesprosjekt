package com.xml;

import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.model.UserModel;
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
			jaxbContext = JAXBContext.newInstance(UserModel.class);
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
	public void jaxbModeltoXML(Object o, OutputStream os){
		try {
			if(o instanceof UserModel)
				jaxbMarshaller.marshal(o, os);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
