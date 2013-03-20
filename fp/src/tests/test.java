package tests;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

public class test {
	public static void main(String[] args) throws ParseException{
//		int year = Calendar.getInstance().get(Calendar.YEAR);
//		Date date = Date.valueOf("2011-04-04");
//		String textDate = date.toString();
//		textDate += " "+"12"+":"+"11"+":00";
//		System.out.println(textDate);
		String a = "09";
		int b = Integer.parseInt(a);
		System.out.println(b==9);
	}
}
