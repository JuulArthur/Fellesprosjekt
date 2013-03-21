/*******************************************************************************
 * Bizcal is a component library for calendar widgets written in java using swing.
 * Copyright (C) 2007  Frederik Bertilsson 
 * Contributors:       Martin Heinemann martin.heinemann(at)tudor.lu
 * 
 * http://sourceforge.net/projects/bizcal/
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 * 
 *******************************************************************************/
package com.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import org.joda.time.DateTime;

import bizcal.common.Calendar;
import bizcal.common.CalendarModel;
import bizcal.common.DayViewConfig;
import bizcal.common.Event;
import bizcal.swing.DayView;
import bizcal.util.DateInterval;
import bizcal.util.DateUtil;

public class CalendarViewModel extends CalendarModel.BaseImpl {
	/*
	 * public static void main(String[] args) throws Exception { DayView dayView
	 * = new DayView(new DayViewConfig()); dayView.setModel(new
	 * CalendarViewModel()); JFrame frame = new JFrame("Bizcal Demo");
	 * dayView.refresh(); frame.setContentPane(dayView.getComponent());
	 * frame.setSize(800, 600); frame.setVisible(true); }
	 */
	private List<Event> events = new ArrayList<Event>();
	private DateInterval interval;
	private Calendar cal;
	
	private DayView myDayView;

	@SuppressWarnings("unchecked")
	public CalendarViewModel() throws Exception {
		
		/*
		Date date = DateUtil.round2Week(new Date());
		date = new Date(date.getTime() + 8 * 60 * 60 * 1000);
		for (int i = 0; i < 7; i++) {
			Event event = new Event();
			event.setStart(date);
			event.setEnd(new Date(date.getTime() + 90 * 60 * 1000));
			event.setSummary("Test " + i);
			events.add(event);
			date = DateUtil.getDiffDay(date, +1);
			date = new Date(date.getTime() + 60 * 60 * 1000);
		}*/
		Date start = DateUtil.round2Week(new Date());
		Date end = DateUtil.getDiffDay(start, +7);
		interval = new DateInterval(start, end);
		cal = new Calendar();
		cal.setId(1);
		cal.setSummary("Week view");
		
		

	}
	
	public void setWeekStart(DateTime start) throws Exception{
		Date _start = DateUtil.round2Week(start.toDate());
		Date end = DateUtil.getDiffDay(start.toDate(), +7);
		interval = new DateInterval(_start, end);
		
		try {
			this.myDayView.refresh();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void removeAllEvents(){
		events = new ArrayList<Event>();
		
		try {
			this.myDayView.refresh();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void addManyEvents(ArrayList<AppointmentModel> ams){
		for(AppointmentModel am : ams){
			Event event = new Event();
			event.setId(am.getId());
			DateTime dt = new DateTime(am.getDate());
			dt = dt.withHourOfDay(13);
			
			System.out.println(dt.toDate());
			
			event.setStart(dt.toDate());
			
			dt = dt.withHourOfDay(17);
			System.out.println(dt);
			event.setEnd(dt.toDate());
			
			
			event.setDescription(am.getText());
			event.setSummary(am.getTitle());
			events.add(event);
		}
		
		try {
			this.myDayView.refresh();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public List<Event> getEvents(Object calId) throws Exception {
		return events;
	}

	public List getSelectedCalendars() throws Exception {
		return Collections.nCopies(1, cal);
	}

	public DateInterval getInterval() {
		return interval;
	}
	
	public void setDayView(DayView dayVIew){
		this.myDayView = dayVIew;
	}

}
