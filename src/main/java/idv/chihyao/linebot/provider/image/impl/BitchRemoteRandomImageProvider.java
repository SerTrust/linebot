package idv.chihyao.linebot.provider.image.impl;

import java.text.MessageFormat;
import java.util.UUID;

public class BitchRemoteRandomImageProvider extends RemoteRandomImageProvider  {

    @Override
    String namingStrategy() {
        return MessageFormat.format("bitch_{0}.jpg", UUID.randomUUID());
    }
}
