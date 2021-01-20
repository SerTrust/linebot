package idv.chihyao.linebot.object;

import java.net.URL;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherTownship {
	
	private static String[] counties = {"宜蘭縣","桃園市","新竹縣","苗栗縣","彰化縣","南投縣","雲林縣","嘉義縣","屏東縣","臺東縣","花蓮縣","澎湖縣","基隆市","新竹市","嘉義市","臺北市","高雄市","新北市","臺中市","臺南市","連江縣","金門縣"};
	private static String[] countiesCode = {"F-D0047-001","F-D0047-005","F-D0047-009","F-D0047-013","F-D0047-017","F-D0047-021","F-D0047-025","F-D0047-029","F-D0047-033","F-D0047-037"
			,"F-D0047-041","F-D0047-045","F-D0047-049","F-D0047-053","F-D0047-057","F-D0047-061","F-D0047-065","F-D0047-069","F-D0047-073","F-D0047-077","F-D0047-081","F-D0047-085"};
	
	public static String getWeather(String text) throws Exception {
		String code = "";
		String name = "";
		text = text.replace("台", "臺");
		text = text.replace("　", " ");
		if(text.split(" ").length<2) return "查詢格式不正確，請重新輸入！";
		for(int i=0;i<counties.length;i++) {
			if(counties[i].equals(text.split(" ")[1])) { 
				code = countiesCode[i];
				break;
			}
		}
		name = text.split(" ")[2];
		RestTemplate rt = new RestTemplate();
		System.out.println("https://opendata.cwb.gov.tw/api/v1/rest/datastore/"+code+"?Authorization=CWB-0F46C12E-EFBF-4BD4-B206-9B7D130AB4C0&locationName="+name);
		URL url = new URL("https://opendata.cwb.gov.tw/api/v1/rest/datastore/"+code+"?Authorization=CWB-0F46C12E-EFBF-4BD4-B206-9B7D130AB4C0&locationName="+name);
		ResponseEntity<String> forEntity = rt.getForEntity(url.toURI(), String.class);

		ObjectMapper deserializeMapper = new ObjectMapper();
//		TypeReference<WeatherModule> typeRef = new TypeReference<WeatherModule>() {
//		};
		WeatherTownshipModule module = deserializeMapper.readValue(forEntity.getBody(), WeatherTownshipModule.class);
		String message = module.getRecords().getLocations().get(0).getLocationsName()+module.getRecords().getLocations().get(0).getLocation().get(0).getLocationName()+" 目前天氣預報 ";
		message += "\n" + module.getRecords().getLocations().get(0).getLocation().get(0).getWeatherElement().get(6).getDescription() +"："+ module.getRecords().getLocations().get(0).getLocation().get(0).getWeatherElement().get(6).getTime().get(0).getElementValue().get(0).getValue();
		return message;
	}
}
