package main;

import java.util.Scanner;

import service.ProductService;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ProductService productService = new ProductService();

		while (true) {
			Menu.showMainMenu();
			int selectMainMenuNum = scanner.nextInt();

			if (selectMainMenuNum == 1) {
				Menu.showProductMenu();
				int selectProductMenuNum = scanner.nextInt();
				if (selectProductMenuNum == 1) {
					// 商品一覧表示
				} else if (selectProductMenuNum == 2) {
					// 商品検索
				} else {
					// 戻る
					continue;
				}
			} else if (selectMainMenuNum == 2) {
				// カート処理へ
			} else if (selectMainMenuNum == 0) {
				// 終了する				
				break;
			} else {
				// エラー			
				System.out.println("0〜2のメニュー番号を入力してください。");
				System.out.println();
			}

		}

		scanner.close();

	}

}
