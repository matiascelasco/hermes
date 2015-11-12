package utils;

import java.time.Instant;
import java.time.LocalDate;
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
		LocalDate localDate = LocalDate.parse(string, dateTimeFormatter);
		Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

}
