package idv.chihyao.linebot.provider.image.impl;

import idv.chihyao.linebot.provider.image.RandomImageProvider;
import idv.chihyao.linebot.util.ImageUtils;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.UUID;

public class BitchRandomImageProvider implements RandomImageProvider {

    private static String URL = "https://api.nmb.show/xiaojiejie1.php";

    @Override
    public String getRandomImages() {
        String downloadPath = MessageFormat.format("/image/bitch_{0}.jpg", UUID.randomUUID());
        try {
            ImageUtils.downloadImage(downloadPath, new URL(URL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }
}
