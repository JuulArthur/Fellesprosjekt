package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.joda.time.DateTime;

import com.model.CalendarModel;
import com.view.calendar.CalendarLayout;

public class CalendarWeekController implements PropertyChangeListener, ActionListener{
	
	/*Models*/
	private ArrayList<CalendarModel> calendarsModels;
	
	/*Views*/
	private CalendarLayout calendarView;
	
	private DateTime firstDayGivenWeek;
	
	public CalendarWeekController(CalendarLayout cl) {
		//Inits
		this.calendarsModels = new ArrayList<>();
		this.calendarView = cl;
		
		firstDayGivenWeek = new DateTime();
		firstDayGivenWeek = firstDayGivenWeek.withDayOfWeek(1);


	}
	
	public void addModel(CalendarModel cm){
		calendarsModels.add(cm);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == calendarView.getButtonLastWeek()){
			firstDayGivenWeek = firstDayGivenWeek.plusWeeks(-1);
			firstDayGivenWeek = firstDayGivenWeek.withDayOfWeek(1);
		}
		else if(e.getSource() == calendarView.getButtonNextWeek()){
			System.out.println(firstDayGivenWeek);
			firstDayGivenWeek.plusWeeks(-1);
			System.out.println(firstDayGivenWeek);
		}
		
	}
	
	public static void main(String[] args) {
		DateTime jodaDT = new DateTime();
		DateTime uka;
		//System.out.println(jodaDT);
		//System.out.println(jodaDT.with(13));
		//System.out.println(uka);
		//uka = jodaDT.getWeekOfWeekyear()); //Gets the week
		//System.out.println(jodaDT);
		//System.out.println(jodaDT.withDayOfWeek(1)); //Gets the day in the week
		//System.out.println(jodaDT.withWeekyear(12));
		
	}

}
