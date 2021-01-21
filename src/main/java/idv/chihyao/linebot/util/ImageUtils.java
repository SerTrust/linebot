package idv.chihyao.linebot.util;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUtils {
    public static void downloadImage(String imagePath, URL url) throws IOException {
        File file = new File(imagePath);
        if (!file.exists()) file.mkdirs();

        // todo 請求相關的程式碼，抽離工具類
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        // Activate the new trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
        // And as before now you can use URL and URLConnection
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();

        // .. then download the file
        try (InputStream in = is) {
            Files.copy(in, Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
