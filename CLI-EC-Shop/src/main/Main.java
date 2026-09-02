package main;

import java.util.Scanner;

import service.ProductService;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ProductService productService = new ProductService();

		runMainMenu(scanner, productService);

		scanner.close();
	}

	// メインメニューを繰り返し表示し、選択された画面へ遷移する	
	private static void runMainMenu(Scanner scanner, ProductService productService) {
		while (true) {
			Menu.showMainMenu();
			int mainMenuNumber = scanner.nextInt();

			if (mainMenuNumber == 1) {
				runProductMenu(scanner, productService);
			} else if (mainMenuNumber == 2) {
				runCartMenu(scanner);
			} else if (mainMenuNumber == 0) {
				System.out.println("CLI EC Shopを終了します。");
				return;
			} else {
				System.out.println("0〜2のメニュー番号を入力してください。");
				System.out.println();
			}
		}

	}

	// 商品メニューを繰り返し表示し、一覧または検索処理へ遷移する
	private static void runProductMenu(Scanner scanner, ProductService productService) {
		while (true) {
			Menu.showProductMenu();
			int productMenuNumber = scanner.nextInt();

			if (productMenuNumber == 1) {
				runProductList(scanner, productService);
			} else if (productMenuNumber == 2) {
				runProductSearch(scanner, productService);
			} else if (productMenuNumber == 0) {
				return;
			} else {
				System.out.println("0〜2のメニュー番号を入力してください。");
				System.out.println();
			}
		}
	}

	// 商品一覧を表示し、詳細を確認する商品IDを受け取る
	private static void runProductList(Scanner scanner, ProductService productService) {
		productService.showProductList();
		Menu.showProductSelectionPrompt();

		int selectedProductId = scanner.nextInt();

		// 入力された商品IDから商品を取得し、詳細を表示する
		if (selectedProductId == 0) {
			return;
		}

	}

	// 商品名を受け取り、該当する商品を検索する
	private static void runProductSearch(Scanner scanner, ProductService productService) {
		Menu.showProductSearchPrompt();

		String searchProductName = scanner.next();

		if (searchProductName.equals("0")) {
			return;
		}

		// 商品名から検索する処理
		// 検索結果を表示する処理
	}

	// カートメニューを繰り返し表示し、各カート操作へ遷移する
	private static void runCartMenu(Scanner scanner) {
		while (true) {
			Menu.showCartMenu();
			int cartMenuNumber = scanner.nextInt();

			if (cartMenuNumber == 1) {
				//数量変更処理
			} else if (cartMenuNumber == 2) {
				//商品削除処理
			} else if (cartMenuNumber == 3) {
				//注文処理
			} else if (cartMenuNumber == 0) {
				return;
			} else {
				System.out.println("0〜3のメニュー番号を入力してください。");
				System.out.println();
			}
		}

	}

}
