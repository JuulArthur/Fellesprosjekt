package com.model;

import javax.swing.table.AbstractTableModel;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CalendarModel extends AbstractTableModel{
	
	private String[][] calendar = new String[13][7];
	
	/* for jabx */
	public CalendarModel() {}
	
	public int getColumnCount() {
		return calendar[0].length;
	}
	
	public int getRowCount() {
		return calendar.length;
	}
	
	public Object getValueAt(int row, int column) {
		return calendar[row][column];
	}
	
	public void setValueAt(Object value, int row, int column) {
		calendar[row][column] = (String) value;
	}

}
