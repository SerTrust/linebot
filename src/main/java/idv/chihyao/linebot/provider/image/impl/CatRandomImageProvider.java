package idv.chihyao.linebot.provider.image.impl;

import idv.chihyao.linebot.provider.image.RandomImageProvider;
import idv.chihyao.linebot.util.ImageUtils;
import net.sf.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class CatRandomImageProvider implements RandomImageProvider {

    private static String URL = "https://api.thecatapi.com/v1/images/search";

    @Override
    public String getRandomImages() {
        String downloadPath = MessageFormat.format("/image/cat_{0}.jpg", UUID.randomUUID());
        String url = getJsonUrl(URL);
        try {
            ImageUtils.downloadImage(downloadPath, new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public String getJsonUrl(String URL){
        URL url = null;
        try {
            url = new URL(URL);

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
