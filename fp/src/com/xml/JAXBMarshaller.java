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
	
	JAXBContext jaxbContext;
	Marshaller jaxbMarshaller;
	
	public JAXBMarshaller() {
		try {
			/*UserModel*/
			JAXBContext jaxbContext = JAXBContext.newInstance(UserModel.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends the XML edition of UserModel through the given OutputStream
	 * @param os
	 */
	public void jaxbUserModel(UserModel um, OutputStream os){
		try {
			jaxbMarshaller.marshal(um, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
