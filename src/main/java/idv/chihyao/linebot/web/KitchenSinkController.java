/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package idv.chihyao.linebot.web;

import com.linecorp.bot.model.event.*;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import idv.chihyao.linebot.message.ReplyOperations;
import idv.chihyao.linebot.object.TextMap;
import idv.chihyao.linebot.object.WeatherTownship;
import idv.chihyao.linebot.provider.image.RandomImageProvider;
import idv.chihyao.linebot.provider.image.Type;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.*;

@Slf4j
@LineMessageHandler
public class KitchenSinkController {

    @Autowired
    private TextMap textMap;

    private ReplyOperations replyOperations;

    public static int num = 0;

    Map<Type, RandomImageProvider> randomImageProviderMap;

    @Autowired
    public void setRandomImageProviderMap(Map<Type, RandomImageProvider> randomImageProviderMap) {
        this.randomImageProviderMap = randomImageProviderMap;
    }

    public void setReplyOperations(ReplyOperations replyOperations) {
        this.replyOperations = replyOperations;
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent message = event.getMessage();
        handleTextContent(event.getReplyToken(), event, message);
    }


    private void replyText(@NonNull String replyToken, @NonNull String message) {
        if (replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken must not be empty");
        }
        if (message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "……";
        }
        replyOperations.replyText(replyToken, message);
    }


    private void handleTextContent(String replyToken, Event event, TextMessageContent content)
            throws Exception {

        final String text = content.getText();

        log.info("Got text message from replyToken:{}: text:{} emojis:{}", replyToken, text,
                content.getEmojis());


        switch (text) {
            default:
                log.info("Returns echo message {}: {}", replyToken, text);
                boolean replyOK = false;
                Map<String, String[]> map = textMap.getTextMap();
                Set set = map.keySet();
                for (Object o : set) {
                    String key = (String) o;
                    if (text.contains(key)) {
                        String[] textArray = map.get(o);
                        int random = new Random().nextInt(50);
                        String sendText = textArray[random % textArray.length];
                        this.replyText(
                                replyToken, sendText
                        );
                        replyOK = true;
                        break;
                    }
                }
                if (text.startsWith("天氣")) {
                    this.replyText(replyToken, WeatherTownship.getWeather(text));
                    break;
                }
                if (text.contains("狗狗")) {
                    String path = randomImageProviderMap.get(Type.狗狗).getRandomImages();
                    replyOperations.replyImage(replyToken, new URI(path), new URI(path));
                    break;
                } else if (text.contains("美女") || text.contains("母狗")) {
                    String path = randomImageProviderMap.get(Type.美女).getRandomImages();
//                    copyImage(MessageFormat.format("/image/rich{0}/bitch.jpg", num));
//                    this.reply(replyToken, ImagemapMessage
//                            .builder()
//                            .baseUrl(createUri(MessageFormat.format("/image/rich{0}/", num)))
////                            .baseUrl(createUri("/static/rich/"))
//                            .altText("阿魚傳送了圖片")
//                            .baseSize(new ImagemapBaseSize(1040, 800))
//                            .actions(Arrays.asList(URIImagemapAction.builder()
//                                    .linkUri(createUri(MessageFormat.format("/image/rich{0}/bitch.jpg", num++)).toString())
//                                    .area(new ImagemapArea(0, 0, 800, 1040))
//                                    .build()))
//                            .build());
                    replyOperations.replyImage(replyToken, path, path);
                    break;
                } else if (text.contains("臭宅")) {
                    String path = randomImageProviderMap.get(Type.動漫).getRandomImages();
                    replyOperations.replyImage(replyToken, path, path);
                    break;
                } else if (text.contains("貓貓")) {
                    break;
                }
//            	String emoji = String.valueOf(Character.toChars(0x100078)); 表情符號使用方式
//                if (!replyOK)
//                    this.replyText(
//                            replyToken, "這超過我能理解的程度了 " + String.valueOf(Character.toChars(0x100083))
//                    );
                break;
        }
    }

    public String getJsonUrl() {
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
                JSONArray array = JSONArray.fromObject(inline);
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

    private void copyImage(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("檔案不存在!!!");
            return;
        }
        try {
            int width = 320, height = 480; /* set the width and height here */
            BufferedImage inputImage = ImageIO.read(file);
            if (inputImage.getWidth() > inputImage.getHeight()) {
                width = 480;
                height = 320;
            }
            BufferedImage outputImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = outputImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.clearRect(0, 0, width, height);
            g.drawImage(inputImage, 0, 0, width, height, null);
            g.dispose();
            ImageIO.write(outputImage, "jpg", new File(MessageFormat.format("D:/image/rich{0}/240", num)));
            ImageIO.write(outputImage, "jpg", new File(MessageFormat.format("D:/image/rich{0}/300", num)));
            ImageIO.write(outputImage, "jpg", new File(MessageFormat.format("D:/image/rich{0}/460", num)));
            ImageIO.write(outputImage, "jpg", new File(MessageFormat.format("D:/image/rich{0}/700", num)));
            ImageIO.write(outputImage, "jpg", new File(MessageFormat.format("D:/image/rich{0}/1040", num)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getURL(String url) {
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
                result = sb.toString().substring(2, sb.length() - 2);
                System.out.println("result = " + result);
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

    private void downloadPhoto(String url) {
        String path = MessageFormat.format("D:/image/rich{0}/", num);
//        String path = "D:/git/lineBot/linebot2/src/main/resources/static/rich/";
        //如果資料夾不存在，則建立新的的資料夾
        File file = new File(path);
        if (!file.exists()) file.mkdirs();

        FileOutputStream fileOut = null;
        InputStream inputStream = null;
        try {
            URL httpUrl = new URL(url);
            URLConnection urlc = httpUrl.openConnection();
            urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.0.11) ");
            inputStream = urlc.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);

            //寫入到檔案（注意檔案儲存路徑的後面一定要加上檔案的名稱）
            fileOut = new FileOutputStream(path + "dog.jpg");
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //儲存檔案
            while (length != -1) {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

//    public void downloadImage(String imagePath, URL url) throws IOException {
//        File file = new File(imagePath);
//        if (!file.exists()) file.mkdirs();
//        // Create a new trust manager that trust all certificates
//        TrustManager[] trustAllCerts = new TrustManager[]{
//                new X509TrustManager() {
//                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                        return null;
//                    }
//
//                    public void checkClientTrusted(
//                            java.security.cert.X509Certificate[] certs, String authType) {
//                    }
//
//                    public void checkServerTrusted(
//                            java.security.cert.X509Certificate[] certs, String authType) {
//                    }
//                }
//        };
//        // Activate the new trust manager
//        try {
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//        } catch (Exception e) {
//        }
//        // And as before now you can use URL and URLConnection
//        URLConnection connection = url.openConnection();
//        InputStream is = connection.getInputStream();
//
//        //todo 檢查response是否為圖片
//
//        // .. then download the file
//        try (InputStream in = is) {
//            Files.copy(in, Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
//        }
//    }

//    private static URI createUri(String path) {
//        return ServletUriComponentsBuilder.newInstance().scheme("https").host("cb1243d88a25.ngrok.io").path(path).build().toUri();
//    }

//    private static DownloadedContent saveContent(String ext, MessageContentResponse responseBody) {
//        log.info("Got content-type: {}", responseBody);
//
//        DownloadedContent tempFile = createTempFile(ext);
//        try (OutputStream outputStream = Files.newOutputStream(tempFile.path)) {
//            ByteStreams.copy(responseBody.getStream(), outputStream);
//            log.info("Saved {}: {}", ext, tempFile);
//            return tempFile;
//        } catch (IOException e) {
//            throw new UncheckedIOException(e);
//        }
//    }

//    private static DownloadedContent createTempFile(String ext) {
//        String fileName = LocalDateTime.now().toString() + '-' + UUID.randomUUID() + '.' + ext;
//        Path tempFile = KitchenSinkApplication.downloadedContentDir.resolve(fileName);
//        tempFile.toFile().deleteOnExit();
//        return new DownloadedContent(
//                tempFile,
//                createUri("/downloaded/" + tempFile.getFileName()));
//    }

    @Value
    private static class DownloadedContent {
        Path path;
        URI uri;
    }
}
