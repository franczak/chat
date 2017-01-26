package bot;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class Weather{
	private String pogoda;
	public Weather(){
		}
	public String ReadWeather(){
	try{
	URL link = new URL("http://api.apixu.com/v1/current.json?key=8baf23668f9243449fe150504172101&q=Cracow");
	Gson gson = new Gson();
	MyPojo weather = gson.fromJson(new JsonReader(new InputStreamReader(link.openStream())),MyPojo.class);
	pogoda="Aktualna pogoda w Krakowie:\n"+
	"Temperatura: "+weather.getCurrent().getTemp_c()+" °C\n"+
	"Ciœnienie: "+weather.getCurrent().getPressure_mb()+" hPa\n"+
	"Prêdkoœæ wiatru: "+weather.getCurrent().getWind_kph()+" km/h\n";
	
	}catch(Exception e){
		e.printStackTrace();
	}
	return pogoda;
	}
}


