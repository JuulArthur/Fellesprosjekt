package com.view.layout;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import java.awt.Insets;
import java.util.GregorianCalendar;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class CalendarJDialog extends JDialog {

	String[] months = { "Januar", "Februar", "Mars", "April", "Mai", "Juni", "July", "August",
		      "September", "Oktober", "November", "Desember" };
	
	JList list = new JList(months);
	JScrollPane scrollPane;
	CalendarModel model = new CalendarModel();
	JTable table = new JTable(model);
	
	private final int thisYear = 2013;
	private String dayChosen = "";
	//private final JButton btnExit = new JButton("Exit");
	private boolean dateSet = false;
	private JTextField txtClockTime = new JTextField();
	private final JLabel lblSkrivInnKlokkeslett = new JLabel("Skriv inn klokkeslett");
	
	public CalendarJDialog() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		table.addMouseListener(new MouseclickedClass());
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setGridColor(Color.black);
		table.setShowGrid(true);		
		GridBagConstraints gbc_table_1 = new GridBagConstraints();
		gbc_table_1.gridwidth = 8;
		gbc_table_1.insets = new Insets(0, 0, 5, 5);
		gbc_table_1.fill = GridBagConstraints.BOTH;
		gbc_table_1.gridx = 0;
		gbc_table_1.gridy = 0;
		getContentPane().add(table, gbc_table_1);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(new ListSelected());
		
		scrollPane = new JScrollPane(list);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 8;
		gbc_scrollPane.gridy = 0;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		GridBagConstraints gbc_lblSkrivInnKlokkeslett = new GridBagConstraints();
		gbc_lblSkrivInnKlokkeslett.gridwidth = 3;
		gbc_lblSkrivInnKlokkeslett.insets = new Insets(0, 0, 5, 5);
		gbc_lblSkrivInnKlokkeslett.gridx = 0;
		gbc_lblSkrivInnKlokkeslett.gridy = 3;
		getContentPane().add(lblSkrivInnKlokkeslett, gbc_lblSkrivInnKlokkeslett);
		
		GridBagConstraints gbc_txtClockTime = new GridBagConstraints();
		gbc_txtClockTime.gridwidth = 3;
		gbc_txtClockTime.insets = new Insets(0, 0, 0, 5);
		gbc_txtClockTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClockTime.gridx = 0;
		gbc_txtClockTime.gridy = 4;
		txtClockTime.setColumns(15);		
		getContentPane().add(txtClockTime, gbc_txtClockTime);
		
		setVisible(true);
		setSize(450,300);
		setLocationRelativeTo(null);
	}
	
	class ListSelected implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent e) {
			model.setMonth(list.getSelectedIndex());
			table.repaint();
		}
		
	}
	
	public String getdayChosen () {
		return dayChosen;
	}
	
	public String getclockTime () {
		return txtClockTime.getText();
	}
	
	 class MouseclickedClass implements MouseListener {
		  public void mouseClicked(MouseEvent e) {
	  		if (e.getClickCount() >= 1) {
	  			int row = table.getSelectedRow();
	  			int column = table.getSelectedColumn();
	  			System.out.println("Row" + row +"Column" + column);
	  			System.out.println(model.getValueAt(row, column));
	  			dayChosen = model.getValueAt(row, column);
	  			dayChosen += "." + months[list.getSelectedIndex()];
	  		}
		  }
		  
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	  }
	
	class CalendarModel extends AbstractTableModel {

		private String [] days = { "S�n", "Man", "Tir", "Ons", "Tor", "Fri", "L�r" };
		
		private int [] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		
		private String [][] calendar = new String[getRowCount()][getColumnCount()];
				
		public CalendarModel() {
			for (int i = 0; i < days.length; i++) {
				calendar[0][i] = days[i];
			}
			for (int j = 1; j < getRowCount(); j++) {
				for (int k = 0; k < getColumnCount(); k++)
					calendar[j][k] = "";
			}
		}
		
		@Override
		public int getColumnCount() {
			return 7;
		}

		@Override
		public int getRowCount() {
			return 7;
		}

		@Override
		public String getValueAt(int rowIndex, int columnIndex) {
			return calendar[rowIndex][columnIndex];
		}
		
		public void setMonth (int month) {
			for (int i = 1; i < 7; ++i) {
				for (int j = 0; j < 7; ++j) {
					calendar[i][j] = " ";
				}
			}
					
			GregorianCalendar cal = new GregorianCalendar();
			cal.set(thisYear, month, 1);
			int offset = cal.get(GregorianCalendar.DAY_OF_WEEK) - 1;
			offset += getRowCount();
			int numberOfDays = numDaysInMonth(month);
			
			for (int i = 0; i < numberOfDays; ++i) {
			    calendar[offset / getRowCount()][offset % getColumnCount()] = Integer.toString(i + 1);
			    ++offset;
			}	
		}
		
		public int numDaysInMonth (int month) {
			return daysInMonth[month];
		}	
	}
}
	

