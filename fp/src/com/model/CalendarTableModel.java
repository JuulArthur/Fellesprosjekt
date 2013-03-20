package com.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class CalendarTableModel extends AbstractTableModel{

	ArrayList[] weekView;
	
	public CalendarTableModel() {
		/* Setup */
		weekView = new ArrayList[7];
		for (int i = 0; i < weekView.length; i++) {
			weekView[i] = new ArrayList<String>();
		}
	}
	
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return weekView.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
