package idv.chihyao.linebot.message.impl;

import com.google.common.base.Preconditions;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.response.BotApiResponse;
import idv.chihyao.linebot.message.ReplyOperations;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

@Slf4j
@Component
public class ReplyOperationsImpl implements ReplyOperations {


    public LineMessagingClient lineMessagingClient;
    @Value("${common.config.web.scheme}")
    private String host;
    @Value("${common.config.web.hostname}")
    private String scheme;

    @Autowired
    public void setLineMessagingClient(LineMessagingClient lineMessagingClient) {
        this.lineMessagingClient = lineMessagingClient;
    }

    @Value(value = "${common.config.web.hostname}")
    public void setHost(String host) {
        this.host = host;
    }

    @Value(value = "${common.config.web.scheme}")
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public void replyText(@NonNull String replyToken, @NonNull String message) {
        reply(replyToken, singletonList(new TextMessage(message)));
    }

    @Override
    public void replyText(@NonNull String replyToken, @NonNull List<String> messages) {
        reply(replyToken,
                messages.stream()
                        .map(message -> new TextMessage(message))
                        .collect(Collectors.toList()),
                false);
    }

    @Override
    public void replyImage(@NonNull String replyToken, @NonNull URI originalContentUrl, @NonNull URI previewImageUrl) {

    }

    @Override
    public void replyImage(@NonNull String replyToken, @NonNull String originalContentUrl, @NonNull String previewImageUrl) {
        this.reply(replyToken, singletonList(
                ImageMessage
                        .builder()
                        .originalContentUrl(createUri(originalContentUrl))
                        .previewImageUrl(createUri(previewImageUrl))
                        .build())
        );
    }

    @Override
    public void replyCarousel(@NonNull String replyToken, @NonNull String altText, @NonNull CarouselTemplate carouselTemplate) {

    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        reply(replyToken, messages, false);
    }

    private void reply(@NonNull String replyToken,
                       @NonNull List<Message> messages,
                       boolean notificationDisabled) {
        Preconditions.checkArgument(messages.size() >= 1 || messages.size() <= 5, "messages size must between 1 to 5");
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages, notificationDisabled))
                    .get();
            log.info("Sent messages: {}", apiResponse);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private URI createUri(String path) {
        return ServletUriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .path(path)
                .build()
                .toUri();
    }

}
