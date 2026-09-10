package util;

public final class ConsoleFormatter {
	private ConsoleFormatter() {
	}

	// 文字列を指定した表示幅まで右側の空白で埋める
	public static String padRight(String text, int targetWidth) {

		int displayWidth = getDisplayWidth(text);
		int padding = targetWidth - displayWidth;

		return text + " ".repeat(Math.max(padding, 0));
	}

	// 文字列を指定した表示幅まで左側の空白で埋める
	public static String padLeft(String text, int targetWidth) {

		int displayWidth = getDisplayWidth(text);
		int padding = targetWidth - displayWidth;

		return " ".repeat(Math.max(padding, 0)) + text;
	}

	// コンソール上での見た目の文字幅を計算する
	private static int getDisplayWidth(String text) {
		int width = 0;

		for (char currentChar : text.toCharArray()) {

			if (isHalfWidth(currentChar)) {
				width += 1;
			} else {
				width += 2;
			}
		}

		return width;
	}

	// 半角文字か判定する
	private static boolean isHalfWidth(char character) {
		return character <= '\u007E' || character >= '\uFF61' && character <= '\uFF9F';
	}

	public static String padCenter(
			String text,
			int targetWidth) {

		int displayWidth = getDisplayWidth(text);
		int totalPadding = Math.max(
				targetWidth - displayWidth, 0);

		int leftPadding = totalPadding / 2;
		int rightPadding = totalPadding - leftPadding;

		return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
	}

	public static String formatCartHeader() {
		return padRight("ID", 6)
				+ padRight("商品名", 28)
				+ padRight("価格", 18)
				+ padRight("数量", 9)
				+ padRight("小計", 18);
	}

	public static String formatCartRow(int productId, String productName, int price, int quantity, int subtotal) {

		String formattedPrice = String.format("%,d円", price);

		String formattedSubtotal = String.format("%,d円", subtotal);

		return padRight(String.valueOf(productId), 6)
				+ padRight(productName, 24)
				+ padLeft(formattedPrice, 12)
				+ padLeft(String.valueOf(quantity), 10)
				+ padLeft(formattedSubtotal, 18);
	}

}
