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

import org.joda.time.DateTime;

import bizcal.common.Calendar;
import bizcal.common.CalendarModel;
import bizcal.common.Event;
import bizcal.swing.DayView;
import bizcal.util.DateInterval;
import bizcal.util.DateUtil;

public class CalendarViewModel extends CalendarModel.BaseImpl {

	private List<Event> events = new ArrayList<Event>();
	private DateInterval interval;
	private Calendar cal;
	
	private DayView myDayView;

	public CalendarViewModel() throws Exception {
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
			e1.printStackTrace();
		}
	}
	
	public void removeAllEvents(){
		events = new ArrayList<Event>();
		
		try {
			this.myDayView.refresh();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public DateTime addIntegerHoursMinutes(DateTime time, int hoursMinutes){
		
		int count = 0;
		while(hoursMinutes >= 60){
			count++;
			hoursMinutes -= 60;
		}
		time = time.withHourOfDay(count);
		time = time.withMinuteOfHour(hoursMinutes);
		return time;
	}
	
	public void addManyEvents(ArrayList<AppointmentModel> ams){
		for(AppointmentModel am : ams){
			System.out.println(am.getTitle());
			Event event = new Event();
			event.setId(am.getId());
			
			DateTime dt = new DateTime(am.getDate());
			
			
			
			dt = addIntegerHoursMinutes(dt, am.getStartTime());
			
			System.out.println(dt);
			
			event.setStart(dt.toDate());
			
			dt = addIntegerHoursMinutes(dt, am.getEndTime());

			System.out.println(dt);
			event.setEnd(dt.toDate());
			
			
			event.setDescription(am.getText());
			event.setSummary(am.getTitle());
			events.add(event);
		}
		
		try {
			this.myDayView.refresh();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public List<Event> getEvents(Object calId) throws Exception {
		return events;
	}

	@SuppressWarnings("rawtypes")
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
