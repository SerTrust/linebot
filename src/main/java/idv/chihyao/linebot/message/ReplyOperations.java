package idv.chihyao.linebot.message;

import com.linecorp.bot.model.message.template.CarouselTemplate;
import lombok.NonNull;

import java.net.URI;
import java.util.List;

public interface ReplyOperations {

    void replyText(@NonNull String replyToken, @NonNull String message);

    void replyText(@NonNull String replyToken, @NonNull List<String> messages);

    void replyImage(@NonNull String replyToken, @NonNull URI originalContentUrl, @NonNull URI previewImageUrl);

    void replyImage(@NonNull String replyToken, @NonNull String originalContentUrl, @NonNull String previewImageUrl);

    void replyCarousel(@NonNull String replyToken, @NonNull String altText, @NonNull CarouselTemplate carouselTemplate);

}
