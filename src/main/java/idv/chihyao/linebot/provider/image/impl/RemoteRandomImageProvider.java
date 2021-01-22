package idv.chihyao.linebot.provider.image.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import idv.chihyao.linebot.provider.image.RandomImageProvider;
import idv.chihyao.linebot.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.MessageFormat;

public abstract class RemoteRandomImageProvider implements RandomImageProvider {

    protected String downloadPath;

    protected String remotePath;

    RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${common.config.local.random-image-location}")
    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    @Override
    public String getRandomImages() {
        String path = MessageFormat.format("{0}/{1}", downloadPath, namingStrategy());//    D://dog_sdfsdfsd.jpg

        try {
            String url = processUrl(remotePath);
            ImageUtils.downloadImage(path, new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = Paths.get(path).toFile();
        return file.getName();
    }

    //todo check url syntax
    String processUrl(String url) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(url), "url shouldn't be empty ot null");
        return url;
    }

    abstract String namingStrategy();
}
