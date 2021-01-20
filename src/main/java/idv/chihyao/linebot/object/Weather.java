package idv.chihyao.linebot.object;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Weather {
	
	public static String getWeather(String text) throws Exception {
		text = text.replace("　", " ");
		text = text.split(" ")[1];
		RestTemplate rt = new RestTemplate();

		URL url = new URL("https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-0F46C12E-EFBF-4BD4-B206-9B7D130AB4C0&locationName="+text);
		ResponseEntity<String> forEntity = rt.getForEntity(url.toURI(), String.class);

		ObjectMapper deserializeMapper = new ObjectMapper();
//		TypeReference<WeatherModule> typeRef = new TypeReference<WeatherModule>() {
//		};
		WeatherModule module = deserializeMapper.readValue(forEntity.getBody(), WeatherModule.class);
		String message = module.getRecords().getLocation().get(0).getLocationName()+" 目前 " + module.getRecords().getLocation().get(0).getWeatherElement().get(0).getTime().get(0).getParameter().getParameterName();
		message += "\n最低溫：" + module.getRecords().getLocation().get(0).getWeatherElement().get(2).getTime().get(0).getParameter().getParameterName() + "°C";
		message += "\n最高溫：" + module.getRecords().getLocation().get(0).getWeatherElement().get(4).getTime().get(0).getParameter().getParameterName() + "°C";
		message += "\n體感：" + module.getRecords().getLocation().get(0).getWeatherElement().get(3).getTime().get(0).getParameter().getParameterName();
		return message;
	}
}
