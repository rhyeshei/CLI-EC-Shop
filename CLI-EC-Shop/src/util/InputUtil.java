package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtil {

	public static int readInt(Scanner scanner) {

		while (true) {
			try {
				int inputNum = scanner.nextInt();
				scanner.nextLine();
				return inputNum;

			} catch (InputMismatchException e) {
				System.out.println("整数を入力してください");
				scanner.nextLine();
			}
		}
	}

	public static int readPositiveInt(Scanner scanner) {
		while (true) {
			int inputNum = readInt(scanner);

			if (inputNum >= 1) {
				return inputNum;
			}
			System.out.println("1以上の整数を入力してください。");
		}
	}

	public static String readNonBlankString(Scanner scanner) {
		while (true) {
			String inputText = scanner.nextLine().trim();

			if (!inputText.isBlank()) {
				return inputText;
			}

			System.out.println("空白です。文字を入力してください。");
		}
	}

}
