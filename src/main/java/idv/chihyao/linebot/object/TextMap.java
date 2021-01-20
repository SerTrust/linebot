package idv.chihyao.linebot.object;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
//@ConfigurationProperties(prefix = "linebot")
public class TextMap {
	
	Map<String,String[]> textMap = new LinkedHashMap<>();
	
	@Bean
	public void setMessageMap() {
		String[] dinner = {"我也不知道該吃什麼耶～"+String.valueOf(Character.toChars(0x10008E)),"你問我? 我擲筊? "+String.valueOf(Character.toChars(0x10009A)),"是不會去吃大便膩? "+String.valueOf(Character.toChars(0x100097)),"你考倒我了 "+String.valueOf(Character.toChars(0x100094))};
		textMap.put("晚餐", dinner);
		String[] Endofclass = {"回家耍廢啊!\n幾歲了還要我教? "+String.valueOf(Character.toChars(0x10007E)),"回家躺好等我 "+String.valueOf(Character.toChars(0x100078))};
		textMap.put("下課", Endofclass);
		String[] workdown = {"你現在是在炫耀嗎? "+String.valueOf(Character.toChars(0x10007E)),"我要是下班了還會在這跟你嘻嘻哈哈? "+String.valueOf(Character.toChars(0x100094)),"社畜準則第三條第12項\n社畜永遠不會準時下班 "+String.valueOf(Character.toChars(0x10007C)),"你要是下班了就滾回家 "+String.valueOf(Character.toChars(0x10009E))};
		textMap.put("下班", workdown);
		textMap.put("回家", workdown);
		String[] buzy = {"忙到都沒時間大便拉 "+String.valueOf(Character.toChars(0x100099)),"十萬青年十萬肝\n每天輪班救台灣 "+String.valueOf(Character.toChars(0x100094)),"瞎忙算忙嗎? "+String.valueOf(Character.toChars(0x10008F))};
		textMap.put("忙", buzy);
		String[] tzu = {"這是在說我家老大嗎 "+String.valueOf(Character.toChars(0x10008F)),"姿姿最漂亮 "+String.valueOf(Character.toChars(0x100078)),"姿姿大美女找我何事? "+String.valueOf(Character.toChars(0x100096))};
		textMap.put("姿姿", tzu);
		String[] cute = {"可愛就是我的代名詞 "+String.valueOf(Character.toChars(0x100097)),"做機器人能不可愛嗎 "+String.valueOf(Character.toChars(0x10009D)),"沒想到連我的可愛都藏不住 "+String.valueOf(Character.toChars(0x10008F)),"我就是這麼的 可~ "+String.valueOf(Character.toChars(0x10007A))+" 愛~ "+String.valueOf(Character.toChars(0x100084))+" no~ "+String.valueOf(Character.toChars(0x100096))};
		textMap.put("可愛", cute);
		String[] understand = {"無法理解難道臭了嗎 "+String.valueOf(Character.toChars(0x1000A3)),"我就笨 "+String.valueOf(Character.toChars(0x10005D)),"我就爛 "+String.valueOf(Character.toChars(0x10005D))};
		textMap.put("理解", understand);
		String[] wherePlay = {"爬山?踏青?在家耍廢?\n任君挑選 "+String.valueOf(Character.toChars(0x100084)),"癱在家像一條米蟲口以嗎 "+String.valueOf(Character.toChars(0x100086)),"你想去哪玩就去哪 "+String.valueOf(Character.toChars(0x10008A))};
		textMap.put("去哪玩", wherePlay);
		String[] c8c8 = {"能讓你們開心我死而無憾 "+String.valueOf(Character.toChars(0x100094)),"我看起來很可笑嗎 "+String.valueOf(Character.toChars(0x10007B)),"我就讓你們恥笑吧 "+String.valueOf(Character.toChars(0x100094))};
		textMap.put("好笑", c8c8);
		textMap.put("哈哈", c8c8);
		textMap.put("可笑", c8c8);
		String[] c8c8dead = {"那你快去死一死吧 "+String.valueOf(Character.toChars(0x10007E)),"也不照照鏡子,看看誰才好笑 "+String.valueOf(Character.toChars(0x10005D)),"我還沒出全力就要死了? "+String.valueOf(Character.toChars(0x100097))};
		textMap.put("笑死", c8c8dead);
		String[] callbot = {"有人在呼叫帥氣的我嗎 "+String.valueOf(Character.toChars(0x10008F)),"我這不是來了嗎 "+String.valueOf(Character.toChars(0x10009D)),"颯爽登場~~~ "+String.valueOf(Character.toChars(0x100079)),"社畜工作中... "+String.valueOf(Character.toChars(0x10007C))};
		textMap.put("呼叫", callbot);
		textMap.put("在嗎", callbot);
		textMap.put("回答", callbot);
		textMap.put("回應", callbot);
		String[] fishman = {"阿魚怎樣我不知道\n但是我應該比他帥 "+String.valueOf(Character.toChars(0x100079)),"阿魚不在\n就讓我來跟你過兩招吧 "+String.valueOf(Character.toChars(0x10008C))};
		textMap.put("阿魚", fishman);
	}

	public Map<String, String[]> getTextMap() {
		return textMap;
	}

	public void setTextMap(Map<String, String[]> textMap) {
		this.textMap = textMap;
	}
	
	
}

