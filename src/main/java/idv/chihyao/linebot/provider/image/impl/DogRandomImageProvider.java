package idv.chihyao.linebot.provider.image.impl;

import idv.chihyao.linebot.provider.image.RandomImageProvider;
import idv.chihyao.linebot.util.ImageUtils;
import net.sf.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.UUID;

public class DogRandomImageProvider implements RandomImageProvider {

    private static String URL = "http://shibe.online/api/shibes?count=1";

    @Override
    public String getRandomImages() {
        String downloadPath = MessageFormat.format("/image/dog_{0}.jpg", UUID.randomUUID());
        String url = getURL(URL);
        try {
            ImageUtils.downloadImage(downloadPath, new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public String getURL(String url) {
        String result = "";
        try {
            URL httpUrl = new URL(url);
            URLConnection urlc = httpUrl.openConnection();
            urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.0.11) ");
            InputStream is = urlc.getInputStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, "utf-8")); //避免中文亂碼問題
                StringBuilder sb = new StringBuilder();
                int cp;
                while ((cp = rd.read()) != -1) {
                    sb.append((char) cp);
                }
                JSONArray array = JSONArray.fromObject(sb.toString());
                result = (String) array.get(0);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                is.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
