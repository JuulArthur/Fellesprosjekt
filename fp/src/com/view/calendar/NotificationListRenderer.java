package com.view.calendar;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import com.model.NotificationModel;

public class NotificationListRenderer extends DefaultListCellRenderer{
	
	//private static ImageIcon maleIcon;
	//private static ImageIcon femaleIcon;
	
	@Override 
	public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {  
        JLabel lbl = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus );  
        
        /*
        if(maleIcon == null){
        	maleIcon = new ImageIcon(new ImageIcon("img/Comics-Hero-Red-icon.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
        	femaleIcon = new ImageIcon(new ImageIcon("img/Street-Fighter-Chun-Li-icon.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
        }
        */
        
        NotificationModel notification = (NotificationModel)value;
        
        /*
        if(person.getGender() == Gender.female)      
            lbl.setIcon(femaleIcon);
        else
            lbl.setIcon(maleIcon);
            */
		
		lbl.setText("<html><b>" + notification.getText() + "</b></html>");//<br/><i>" + calendar.getOwner() +"</i></html>");

		return lbl;
	}
}
