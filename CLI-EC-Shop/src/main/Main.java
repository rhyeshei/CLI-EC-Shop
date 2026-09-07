package main;

import java.util.List;
import java.util.Scanner;

import model.Product;
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

	private static void runProductDetail(Scanner scanner, ProductService productService, Product selectedProduct) {
		//商品詳細を表示
		productService.showProductDetail(selectedProduct);

		//商品詳細メニューの繰り返し表示
		while (true) {
			Menu.showProductDetailMenu();
			int detailMenuNum = scanner.nextInt();

			if (detailMenuNum == 1) {
				//カート追加処理					

			} else if (detailMenuNum == 0) {
				//商品メニューへ				
				return;
			} else {
				System.out.println("0~1の操作番号を入力してください。");
			}
		}
	}

	// 商品一覧を表示し、詳細を確認する
	private static void runProductList(Scanner scanner, ProductService productService) {
		productService.showProductList();
		Menu.showProductSelectionPrompt();

		int selectedProductId = scanner.nextInt();

		// 入力された商品IDから商品を取得し、詳細を表示する
		if (selectedProductId == 0) {
			return;
		}

		Product selectedProduct = productService.findProductById(selectedProductId);

		if (selectedProduct == null) {
			System.out.println();
			System.out.println("商品が見つかりません");
			System.out.println();
			return;
		}

		runProductDetail(scanner, productService, selectedProduct);
	}

	// 商品名を受け取り、該当する商品を検索する
	private static void runProductSearch(Scanner scanner, ProductService productService) {
		//検索プロンプトの表示		
		Menu.showProductSearchPrompt();
		//検索キー		
		String keyword = scanner.next();

		//検索処理		
		if (keyword.equals("0")) {
			return;
		}

		List<Product> searchResults = productService.searchProductsByName(keyword);

		if (searchResults.isEmpty()) {
			System.out.println("======検索結果=======");
			System.out.println("該当する商品がありません");
			return;
		}
		System.out.println("======検索結果=======");
		System.out.println(searchResults.size() + "件の商品");
		System.out.println();
		System.out.printf("%-4s %-18s %-12s %-8s", "ID", "商品名", "価格", "在庫数");
		System.out.println();
		for (Product currentProduct : searchResults) {
			System.out.printf("%-4s %-18s %-12s %-8s%n",
					currentProduct.getProductId(),
					currentProduct.getProductName(),
					currentProduct.getPrice(),
					currentProduct.getStock());
		}
		System.out.println();
		// 商品IDを入力させ、商品詳細を表示させる				
		Menu.showProductSelectionPrompt();
		int selectedProductId = scanner.nextInt();
		System.out.println();
		// 入力された商品IDから商品を取得し、詳細を表示する
		if (selectedProductId == 0) {
			return;
		}

		Product selectedProduct = productService.findProductById(selectedProductId);

		if (selectedProduct == null) {
			System.out.println();
			System.out.println("商品が見つかりません");
			System.out.println();
			return;
		}

		runProductDetail(scanner, productService, selectedProduct);
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
