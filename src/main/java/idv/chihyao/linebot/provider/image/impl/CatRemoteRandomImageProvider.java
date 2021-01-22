package idv.chihyao.linebot.provider.image.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public class CatRemoteRandomImageProvider extends RemoteRandomImageProvider {

    @Override
    String namingStrategy() {
        return MessageFormat.format("cat_{0}.jpg", UUID.randomUUID());
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

            ResponseEntity<String> entity = restTemplate.getForEntity(uriComponents.toUri(), String.class);

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<Info> infos = objectMapper.readValue(entity.getBody(),
                        new TypeReference<List<Info>>() {
                        });
                Preconditions.checkState(infos.size() == 1, "cat api should return single obj");
                Info info = infos.get(0);
                return info.getUrl();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static class Info {

        private Object[] breeds;
        private String id;
        private String url;
        private int width;
        private int height;

        public Object[] getBreeds() {
            return breeds;
        }

        public void setBreeds(Object[] breeds) {
            this.breeds = breeds;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

}
