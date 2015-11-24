package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Converter {

	public static final DateTimeFormatter dateTimeFormatter = 
			DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	public static String dateToString(Date date) {
		LocalDateTime dateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		return dateTimeFormatter.format(dateTime);
	}
	
	public static Date stringToDate(String string) {
		LocalDateTime localDateTime = LocalDateTime.parse(string, dateTimeFormatter);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

}
