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
import idv.chihyao.linebot.provider.image.impl.AnimeRandomImageProvider;
import idv.chihyao.linebot.provider.image.impl.BitchRandomImageProvider;
import idv.chihyao.linebot.provider.image.impl.CatRandomImageProvider;
import idv.chihyao.linebot.provider.image.impl.DogRandomImageProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

        randomImageProviderMap.put(Type.狗狗, dogRandomImageProvider());
        randomImageProviderMap.put(Type.美女, bitchRandomImageProvider());
        randomImageProviderMap.put(Type.動漫, animeRandomImageProvider());
        randomImageProviderMap.put(Type.貓貓, catRandomImageProvider());

        return randomImageProviderMap;
    }

    @Bean
    public RandomImageProvider dogRandomImageProvider() {
        return new DogRandomImageProvider();
    }

    @Bean
    public RandomImageProvider bitchRandomImageProvider() {
        return new BitchRandomImageProvider();
    }

    @Bean
    public RandomImageProvider animeRandomImageProvider() {
        return new AnimeRandomImageProvider();
    }

    @Bean
    public RandomImageProvider catRandomImageProvider() {
        return new CatRandomImageProvider();
    }

    @Bean
    public ReplyOperations replyOperationsImpl() {
        ReplyOperationsImpl replyOperations = new ReplyOperationsImpl();
        replyOperations.setHost("6283d4c690dc.ngrok.io");
        return replyOperations;
    }

}
