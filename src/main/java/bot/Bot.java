package bot;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

public class Bot {
	public Bot(){
		}
	private String pogoda;
	private String godzina;
	private String dzien;

	 public String answer(String question){
		 String odp="";
	        if(question.equals("!pogoda"))
	        	return odp=readWeather();
	        else if(question.equals("!godzina"))
		        return odp=readTime();
	        else if(question.equals("!dzien"))
		        return odp=readDay();
	        else return odp="Niestety nie mam takiej opcji :(";        
	        
	}
	public String readTime(){
		 Calendar cal = Calendar.getInstance();
	     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	     return godzina="Aktulana godzina: "+sdf.format(cal.getTime()).toString();
	}
	
	public String readDay(){
		LocalDate date = LocalDate.now();
		DayOfWeek dow = date.getDayOfWeek();
		return dzien="Dzisiaj jest "+changeToPolish(dow.getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH));
	}
	
	private String changeToPolish(String a){
		switch(a){
		case "Monday": return "Poniedzia³ek";
		case "Tuesday": return "Wtorek";
		case "Wednesday": return "Œroda";
		case "Thursday": return "Czwartek";
		case "Friday": return "Pi¹tek";
		case "Saturday": return "Sobota";
		case "Sunday": return "Niedziela";
		default: return "";
		}
	}
	
	public String readWeather(){
		Weather weather = new Weather();
		pogoda = weather.ReadWeather();
		return pogoda;
	}
}
