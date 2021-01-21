package idv.chihyao.linebot.provider.image.impl;

import idv.chihyao.linebot.provider.image.RandomImageProvider;
import idv.chihyao.linebot.util.ImageUtils;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.UUID;

public class AnimeRandomImageProvider implements RandomImageProvider {

    private static String URL = "https://api.catyo.cn/rimg/2dyrimg.php";

    @Override
    public String getRandomImages() {
        String downloadPath = MessageFormat.format("/image/anime_{0}.jpg", UUID.randomUUID());
        try {
            ImageUtils.downloadImage(downloadPath, new URL(URL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }
}
