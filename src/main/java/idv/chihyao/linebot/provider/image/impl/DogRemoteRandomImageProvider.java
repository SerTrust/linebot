package idv.chihyao.linebot.provider.image.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.UUID;

public class DogRemoteRandomImageProvider extends RemoteRandomImageProvider {

    @Override
    String namingStrategy() {
        return MessageFormat.format("dog_{0}.jpg", UUID.randomUUID());
    }

    @Override
    String processUrl(String url) {
        String checkedUrl = super.processUrl(url);
        try {
            URI uri = new URI(checkedUrl);
            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .uri(uri)
                    .build()
                    .encode();

            ResponseEntity<String[]> entity = restTemplate.getForEntity(uriComponents.toUri(), String[].class);
            return entity.getBody()[0];
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return "";
    }
}
