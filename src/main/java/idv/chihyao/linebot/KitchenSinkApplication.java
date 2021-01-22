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

package idv.chihyao.linebot;

import idv.chihyao.linebot.message.ReplyOperations;
import idv.chihyao.linebot.message.impl.ReplyOperationsImpl;
import idv.chihyao.linebot.provider.image.RandomImageProvider;
import idv.chihyao.linebot.provider.image.Type;
import idv.chihyao.linebot.provider.image.impl.AnimeRemoteRandomImageProvider;
import idv.chihyao.linebot.provider.image.impl.BitchRemoteRandomImageProvider;
import idv.chihyao.linebot.provider.image.impl.CatRemoteRandomImageProvider;
import idv.chihyao.linebot.provider.image.impl.DogRemoteRandomImageProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class KitchenSinkApplication {
    public static Path downloadedContentDir;

    public static void main(String[] args) throws IOException {
        downloadedContentDir = Files.createTempDirectory("line-bot");
        SpringApplication.run(KitchenSinkApplication.class, args);
    }

    @Bean
    public Map<Type, RandomImageProvider> randomImageProviderMap() {
        Map<Type, RandomImageProvider> randomImageProviderMap = new HashMap<>();

        randomImageProviderMap.put(Type.狗狗, dogRemoteRandomImageProvider());
        randomImageProviderMap.put(Type.美女, bitchRemoteRandomImageProvider());
        randomImageProviderMap.put(Type.動漫, animeRemoteRandomImageProvider());
        randomImageProviderMap.put(Type.貓貓, catRemoteRandomImageProvider());

        return randomImageProviderMap;
    }

    @Bean
    public Map<Type, String> imageApiCollector() {
        Map<Type, String> imageApiCollector = new HashMap<>();

        imageApiCollector.put(Type.狗狗, "http://shibe.online/api/shibes?count=1");
        imageApiCollector.put(Type.美女, "https://api.nmb.show/xiaojiejie1.php");
        imageApiCollector.put(Type.動漫, "https://api.catyo.cn/rimg/2dyrimg.php");
        imageApiCollector.put(Type.貓貓, "https://api.thecatapi.com/v1/images/search");
        
        return imageApiCollector;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

    @Bean
    public RandomImageProvider dogRemoteRandomImageProvider() {
        DogRemoteRandomImageProvider dogRemoteRandomImageProvider = new DogRemoteRandomImageProvider();
        dogRemoteRandomImageProvider.setRemotePath(imageApiCollector().get(Type.狗狗));
        return dogRemoteRandomImageProvider;
    }

    @Bean
    public RandomImageProvider bitchRemoteRandomImageProvider() {
        BitchRemoteRandomImageProvider bitchRemoteRandomImageProvider = new BitchRemoteRandomImageProvider();
        bitchRemoteRandomImageProvider.setRemotePath(imageApiCollector().get(Type.美女));
        return bitchRemoteRandomImageProvider;
    }

    @Bean
    public RandomImageProvider animeRemoteRandomImageProvider() {
        AnimeRemoteRandomImageProvider animeRemoteRandomImageProvider = new AnimeRemoteRandomImageProvider();
        animeRemoteRandomImageProvider.setRemotePath(imageApiCollector().get(Type.動漫));
        return animeRemoteRandomImageProvider;
    }

    @Bean
    public RandomImageProvider catRemoteRandomImageProvider() {
        CatRemoteRandomImageProvider catRemoteRandomImageProvider = new CatRemoteRandomImageProvider();
        catRemoteRandomImageProvider.setRemotePath(imageApiCollector().get(Type.貓貓));
        return catRemoteRandomImageProvider;

    }

//    @Bean
//    public ReplyOperations replyOperationsImpl() {
//        ReplyOperationsImpl replyOperations = new ReplyOperationsImpl();
//        replyOperations.setHost("0f47db7a133c.ngrok.io");
//        return replyOperations;
//    }

}
