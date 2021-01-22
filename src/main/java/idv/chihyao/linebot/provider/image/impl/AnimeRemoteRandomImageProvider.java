package idv.chihyao.linebot.provider.image.impl;

import java.text.MessageFormat;
import java.util.UUID;

public class AnimeRemoteRandomImageProvider extends RemoteRandomImageProvider {

    @Override
    String namingStrategy() {
        return MessageFormat.format("anmie_{0}.jpg", UUID.randomUUID());
    }
}
