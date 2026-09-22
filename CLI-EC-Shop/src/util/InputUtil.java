package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class InputUtil {
	// 0. 整数の読み取り
	public static int readInt(Scanner scanner) {
		while (true) {
			try {
				int inputNum = scanner.nextInt();
				// nextint の後の改行等を読み込み（切り捨て）
				scanner.nextLine();
				return inputNum;

			} catch (InputMismatchException e) {
				System.out.println("整数を入力してください");
				// 入力の切り捨て
				scanner.nextLine();
			}
		}
	}

	// 1. 1以上の整数の読み取り
	public static int readPositiveInt(Scanner scanner) {
		while (true) {
			int inputNum = readInt(scanner);

			if (inputNum >= 1) {
				return inputNum;
			}
			System.out.println("1以上の整数を入力してください。");
			System.out.println();
		}
	}

	// 2. 空白ではない文字列の読み取り
	public static String readRequiredText(Scanner scanner) {
		while (true) {
			// 文字列の読み取り＋前後の空白の削除
			String inputText = scanner.nextLine().trim();

			if (!inputText.isBlank()) {
				return inputText;
			}

			System.out.println("空白です。文字を入力してください。");
		}
	}

}
