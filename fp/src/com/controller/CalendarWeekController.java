package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.joda.time.DateTime;

import com.model.AppointmentModel;
import com.model.CalendarModel;
import com.model.CalendarViewModel;
import com.view.calendar.CalendarLayout;

public class CalendarWeekController implements PropertyChangeListener, ActionListener{
	
	/*Models*/
	private ArrayList<CalendarModel> calendarsModels;
	private ArrayList<AppointmentModel> appointments;
	
	private CalendarViewModel calendarViewModel;
	
	/*Views*/
	private CalendarLayout calendarView;
	
	private DateTime firstDayGivenWeek;
	
	public CalendarWeekController(CalendarLayout cl) {
		//Inits
		this.calendarsModels = new ArrayList<CalendarModel>();
		this.calendarView = cl;
		this.calendarViewModel = this.calendarView.getCalViewModel();
		
		//this.calendarView.getTable().setModel(null);
		
		firstDayGivenWeek = new DateTime();
		firstDayGivenWeek = firstDayGivenWeek.withDayOfWeek(1);
		this.calendarView.getLabelGivenWeek().setText("Uke: " + firstDayGivenWeek.getWeekOfWeekyear());
	}
	
	public void addModel(CalendarModel cm){
		calendarsModels.add(cm);

		if(cm.getAppointments() != null)
			calendarViewModel.addManyEvents(cm.getAppointments());
				/*
				am.addPropertyChangeListener(this);
				DateTime appointment = new DateTime(am.getDate());
				System.out.println(appointment);*/
			
		
	}
	
	public void setModel(CalendarModel cm){
		calendarsModels = new ArrayList<CalendarModel>();
		calendarsModels.add(cm);
		if(cm.getAppointments() != null)
			calendarViewModel.addManyEvents(cm.getAppointments());
			/*
			for(AppointmentModel am : cm.getAppointments()){
				am.addPropertyChangeListener(this);
				DateTime appointment = new DateTime(am.getDate());
				System.out.println(appointment);
			}*/
		
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
			try {
				calendarViewModel.setWeekStart(firstDayGivenWeek);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == calendarView.getButtonNextWeek()){
			firstDayGivenWeek = firstDayGivenWeek.plusWeeks(1);
			firstDayGivenWeek = firstDayGivenWeek.withDayOfWeek(1);
			try {
				calendarViewModel.setWeekStart(firstDayGivenWeek);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		
		this.calendarView.getLabelGivenWeek().setText("Uke: " + firstDayGivenWeek.getWeekOfWeekyear());
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