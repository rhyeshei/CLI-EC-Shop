package util;

public final class ConsoleFormatter {
	private ConsoleFormatter() {
	}

	// 右側に空白を追加するメソッド
	public static String padRight(String text, int targetWidth) {
		// 現在の表示幅を取得
		int displayWidth = getDisplayWidth(text);
		// 追加する空白を計算
		int padding = targetWidth - displayWidth;
		// text の右側に空白を追加
		// padding がマイナスの場合は、追加する空白数を 0 にする
		return text + " ".repeat(Math.max(padding, 0));
	}

	// 左側に空白を追加するメソッド
	public static String padLeft(String text, int targetWidth) {
		// 現在の表示幅を取得
		int displayWidth = getDisplayWidth(text);
		// 追加する空白を計算
		int padding = targetWidth - displayWidth;
		// text の左側に空白を追加
		// padding がマイナスの場合は、追加する空白数を 0 にする
		return " ".repeat(Math.max(padding, 0)) + text;
	}

	// 左右に空白を追加するメソッド
	public static String padCenter(String text, int targetWidth) {
		// 現在の表示幅を取得
		int displayWidth = getDisplayWidth(text);
		// 追加する空白を計算
		int totalPadding = Math.max(targetWidth - displayWidth, 0);

		// 左右の空白を計算
		int leftPadding = totalPadding / 2;
		int rightPadding = totalPadding - leftPadding;
		// textの左右に計算した数の空白を追加
		return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
	}

	// 文字列の見た目の幅を計算するメソッド
	private static int getDisplayWidth(String text) {
		// 幅合計の初期化
		int width = 0;
		// 文字列を char型 の配列に変換し、1文字ずつ半角か判定
		for (char currentChar : text.toCharArray()) {

			if (isHalfWidth(currentChar)) {
				width += 1;
			} else {
				width += 2;
			}
		}
		return width;
	}

	// 1文字が半角か判定するメソッド
	// '\u007E' 以下          は、英数字・一般的な半角記号
	// '\uFF61' 以上 '\uFF9F' は、半角カタカナ
	// 上記以外を全角文字（表示幅2）として扱う
	private static boolean isHalfWidth(char character) {
		return character <= '\u007E' || character >= '\uFF61' && character <= '\uFF9F';
	}

	// カート表の見出しを作るメソッド
	public static String formatCartHeader() {
		return padRight("ID", 6)
				+ padRight("商品名", 28)
				+ padRight("価格", 18)
				+ padRight("数量", 9)
				+ padRight("小計", 18);
	}

	// カート表の商品1行を作るメソッド
	public static String formatCartRow(int productId, String productName, int price, int quantity, int subtotal) {
		// 3桁ごとにカンマで区切る
		String formattedPrice = String.format("%,d円", price);
		String formattedSubtotal = String.format("%,d円", subtotal);

		// .valueOf で、商品IDと数量をint型からString型へ変換して整形
		return padRight(String.valueOf(productId), 6)
				+ padRight(productName, 24)
				+ padLeft(formattedPrice, 12)
				+ padLeft(String.valueOf(quantity), 10)
				+ padLeft(formattedSubtotal, 18);
	}

}
