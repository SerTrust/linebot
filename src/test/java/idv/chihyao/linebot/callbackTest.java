package idv.chihyao.linebot;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class callbackTest {
	
	static boolean flag = true;
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("執行第一行");
		System.out.println("執行第二行");
		System.out.println("執行第三行");
		Caller caller = new Caller();
		caller.doAsync(new Callback() {
			@Override
			public void success(String i) {
				System.out.println(i);
				flag = false;
			}
		});
//	        String s = caller.doSync();
//	        System.out.println(s);
		System.out.println("執行第四行");
		System.out.println("執行第五行");
		System.out.println("執行第六行");
		int count = 0;
		while (flag) {
			System.out.print("\r");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < count; i++) {
				sb.append("*");
			}
			System.out.print(sb.toString());
			count++;
			Thread.sleep(500);
		}
	}
}

class Caller {
	public String doSync() throws InterruptedException {
		System.out.println("發出請求");
		Thread.sleep(5000);
		System.out.println("收到回應");
		return "回傳結果";
	}

	public void doAsync(Callback callback) {
		System.out.println("發出請求");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					System.out.println("收到回應");
					callback.success("回傳結果");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}

interface Callback {
	void success(String i);
}
