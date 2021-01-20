package idv.chihyao.linebot;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

@SpringBootTest
class LinebotApplicationTests {

	@Test
	void contextLoads() {
		getJson();
	}

	public String getJson(){
		URL url = null;
		try {
			url = new URL("https://api.thecatapi.com/v1/images/search");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			int responsecode = conn.getResponseCode();

			if (responsecode != 200) {
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			} else {

				String inline = "";
				Scanner scanner = new Scanner(url.openStream());

				//Write all the JSON data into a string using a scanner
				while (scanner.hasNext()) {
					inline += scanner.nextLine();
				}

				//Close the scanner
				scanner.close();

				//Using the JSON simple library parse the string into a json object
				JSONArray array= JSONArray.fromObject(inline);
//				for(int i=0;i<array.size();i++){
					Map map = (Map) array.get(0);
					return (String) map.get("url");
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
