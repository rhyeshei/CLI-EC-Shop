package main;

import java.util.List;
import java.util.Scanner;

import model.Cart;
import model.CartItem;
import model.Order;
import model.Product;
import service.CartService;
import service.OrderService;
import service.ProductService;
import util.InputUtil;

public class Main {
	// 0. Main 処理	
	public static void main(String[] args) {
		// try-with-resources でScanner を自動的に閉じる
		// tryブロック終了時にscanner.close()が実行される
		try (Scanner scanner = new Scanner(System.in)) {
			// 各Serviceクラスのインスタンスを生成
			ProductService productService = new ProductService();
			CartService cartService = new CartService();
			OrderService orderService = new OrderService();
			// メインメニュー処理
			runMainMenu(scanner, productService, cartService, orderService);
		}
	}

	// 1. メインメニュー処理
	private static void runMainMenu(Scanner scanner, ProductService productService, CartService cartService,
			OrderService orderService) {
		while (true) {
			// メインメニューの表示
			Menu.showMainMenu();
			int mainMenuNumber = InputUtil.readInt(scanner);

			if (mainMenuNumber == 1) {
				// 商品メニューへ
				runProductMenu(scanner, productService, cartService);
			} else if (mainMenuNumber == 2) {
				// カートメニューへ
				boolean shouldExit = runCartMenu(scanner, productService, cartService, orderService);
				//	注文完了後に終了が選択された場合（runCartMenu が true の場合）
				if (shouldExit) {
					System.out.println("CLI EC Shopを終了します。");
					return;
				}
			} else if (mainMenuNumber == 0) {
				// アプリケーション終了
				System.out.println("CLI EC Shopを終了します。");
				return;
			} else {
				// エラー表示
				System.out.println("0〜2のメニュー番号を入力してください。");
				System.out.println();
			}
		}

	}

	// 2. 商品メニュー処理
	private static void runProductMenu(Scanner scanner, ProductService productService, CartService cartService) {
		while (true) {
			// 商品メニューの表示
			Menu.showProductMenu();
			int productMenuNumber = InputUtil.readInt(scanner);

			if (productMenuNumber == 1) {
				// 商品一覧表示へ
				runProductList(scanner, productService, cartService);
			} else if (productMenuNumber == 2) {
				// 商品検索へ
				runProductSearch(scanner, productService, cartService);
			} else if (productMenuNumber == 0) {
				// メインメニューへ戻る
				return;
			} else {
				// エラー表示
				System.out.println("0〜2のメニュー番号を入力してください。");
				System.out.println();
			}
		}
	}

	// 3. 商品一覧から商品を選択し商品詳細へ移動
	private static void runProductList(Scanner scanner, ProductService productService, CartService cartService) {
		// 商品一覧表示
		productService.showProductList();
		// 商品選択プロンプト（商品IDの入力）
		Menu.showProductSelectionPrompt();
		// 商品ID を取得
		int selectedProductId = InputUtil.readInt(scanner);

		if (selectedProductId == 0) {
			// 商品メニューへ戻る
			return;
		}
		// 商品IDから商品を取得
		Product selectedProduct = productService.findProductById(selectedProductId);

		if (selectedProduct == null) {
			// 該当商品がない場合
			System.out.println();
			System.out.println("商品が見つかりません");
			System.out.println();
			return;
		}

		// 選択した商品の商品詳細画面へ移動
		runProductDetail(scanner, productService, selectedProduct, cartService);
	}

	// 4. 商品名検索処理
	private static void runProductSearch(Scanner scanner, ProductService productService, CartService cartService) {
		while (true) {
			// 商品検索プロンプト
			Menu.showProductSearchPrompt();
			// 検索キーワードを取得
			String keyword = InputUtil.readNonBlankString(scanner);

			if (keyword.equals("0")) {
				// 商品メニューに戻る（入力される値が、0の場合）
				return;
			}

			// 商品名に一致する商品のリストを取得
			List<Product> searchResults = productService.searchProductsByName(keyword);

			if (searchResults.isEmpty()) {
				// 該当する商品がない場合
				System.out.println();
				System.out.println("[ 検索結果：0件 ]");
				System.out.println();
				System.out.println("該当する商品がありません。");
				System.out.println();
				// やり直し
				continue;
			}

			// 検索結果表示
			productService.showSearchResults(searchResults);

			// 検索結果からの処理
			while (true) {
				// 検索結果後の処理プロンプト
				Menu.showSearchResultSelectionPrompt();
				// 入力された商品IDを取得
				int selectedProductId = InputUtil.readInt(scanner);
				System.out.println();

				if (selectedProductId == -1) {
					// 再度検索へ
					break;
				}

				if (selectedProductId == 0) {
					// 商品メニューへ戻る
					return;
				}

				// 検索結果から、商品IDに一致する商品を取得
				Product selectedProduct = productService.findProductById(searchResults, selectedProductId);

				if (selectedProduct == null) {
					// 検索結果に表示されない商品IDを入力の場合
					System.out.println();
					System.out.println("検索結果に該当する商品IDがありません。");
					System.out.println();
					// やり直し
					continue;
				}

				// 選択した商品（selectedProduct）の商品詳細画面へ
				runProductDetail(scanner, productService, selectedProduct, cartService);
				return;
			}

		}
	}

	// 5. 商品詳細処理
	private static void runProductDetail(Scanner scanner, ProductService productService, Product selectedProduct,
			CartService cartService) {
		//商品詳細を表示
		productService.showProductDetail(selectedProduct);

		//商品詳細メニューの繰り返し表示
		while (true) {
			// 商品詳細メニューの表示
			Menu.showProductDetailMenu();
			int detailMenuNum = InputUtil.readInt(scanner);

			if (detailMenuNum == 1) {
				// カート追加処理（購入数量を入力する処理）
				runAddToCart(scanner, selectedProduct, cartService);
				return;

			} else if (detailMenuNum == 0) {
				//商品メニューへ				
				return;
			} else {
				// エラー表示
				System.out.println("0〜1の操作番号を入力してください。");
			}
		}

	}

	// 6. カート追加処理
	private static void runAddToCart(Scanner scanner, Product selectedProduct, CartService cartService) {
		while (true) {
			Menu.showAddToCartPrompt();
			// 購入数を取得
			int quantity = InputUtil.readPositiveInt(scanner);

			// 商品をカートへ追加し、追加結果を取得
			boolean isAdded = cartService.addToCart(selectedProduct, quantity);

			if (isAdded) {
				// カート追加に成功した場合（true の場合）
				System.out.println("カートに　" + selectedProduct.getProductName() + "　を" + quantity + "個追加しました。");
				System.out.println();
				return;
			}
			// 在庫不足（購入数を再入力）
			System.out.println("在庫数が足りません。購入数を再度入力してください。");
			System.out.println();
		}
	}

	// 7. カートメニュー処理
	// true の場合、アプリケーション終了。
	// false の場合、メインメニューを続行。
	private static boolean runCartMenu(Scanner scanner, ProductService productService, CartService cartService,
			OrderService orderService) {
		while (true) {
			// カート内一覧表示
			cartService.showCartList();

			// カートが空の場合、メインメニューへ戻る
			if (cartService.getCart().getItems().isEmpty()) {
				System.out.println("カートに商品がありません。");
				System.out.println("メインメニューへ戻ります。");
				System.out.println();

				return false;
			}

			// カートメニュー表示
			Menu.showCartMenu();
			int cartMenuNumber = InputUtil.readInt(scanner);

			if (cartMenuNumber == 1) {
				//数量変更処理
				runCartQuantityUpdate(scanner, cartService);
			} else if (cartMenuNumber == 2) {
				//商品削除処理
				runCartItemDelete(scanner, cartService);
			} else if (cartMenuNumber == 3) {
				//注文処理
				int orderResult = runOrderConfirmation(scanner, cartService, orderService);

				if (orderResult == 1) {
					// 買い物を続行 商品メニューへ
					runProductMenu(scanner, productService, cartService);
					return false;
				}

				if (orderResult == 0) {
					// アプリケーション終了
					return true;
				}
				// -1 の場合処理が戻り、カートメニュー再表示

			} else if (cartMenuNumber == 0) {
				// メインメニューへ戻る
				return false;
			} else {
				// エラー表示
				System.out.println("0〜3のメニュー番号を入力してください。");
				System.out.println();
			}
		}

	}

	// 8. カート内数量変更処理
	private static void runCartQuantityUpdate(Scanner scanner, CartService cartService) {
		// カート内数量変更プロンプトの表示
		Menu.showCartQuantityUpdatePrompt();
		int selectedProductId = InputUtil.readInt(scanner);

		//カートメニューへ戻る
		if (selectedProductId == 0) {
			return;
		}

		//入力された商品IDからカート内商品を取得		
		CartItem selectedCartItem = cartService.findCartItemByProductId(selectedProductId);

		if (selectedCartItem == null) {
			System.out.println("カート内に該当する商品がありません");
			System.out.println();
			return;
		}

		while (true) {
			// 数量変更プロンプトの表示（変更後の購入数を入力）
			Menu.showNewQuantityPrompt();
			int updateQuantity = InputUtil.readPositiveInt(scanner);

			// 数量変更を実施し結果を取得（false or true）
			boolean isUpdated = cartService.updateCartItemQuantity(selectedCartItem, updateQuantity);

			// 数量変更に成功した場合（true の場合）
			if (isUpdated) {
				System.out.println("数量を変更しました。");
				System.out.println();
				return;
			}

			// 数量変更に失敗した場合（false の場合）
			System.out.println();
			System.out.println("在庫数が足りません。再度数量を入力してください。");
			System.out.println("在庫数：" + selectedCartItem.getProduct().getStock());
			System.out.println();
		}
	}

	// 9. カート内商品削除処理	
	private static void runCartItemDelete(Scanner scanner, CartService cartService) {
		// カート内アイテム削除プロンプトの表示
		Menu.showCartItemDeletePrompt();
		int selectedProductId = InputUtil.readInt(scanner);

		if (selectedProductId == 0) {
			// カートメニューに戻る
			return;
		}

		// 商品削除を実施し結果を取得（false or true）
		boolean isDeleted = cartService.removeCartItemByProductId(selectedProductId);

		if (!isDeleted) {
			// false の場合（削除が実行できなかった場合）
			System.out.println("カート内に該当する商品がありません");
			System.out.println();
			return;
		}

		System.out.println("商品をカートから削除しました。");
		System.out.println();
	}

	// 10. 注文確認・確定処理
	// 戻り値：1 = 買い物を続ける
	// 戻り値：0 = アプリを終了
	// 戻り値：-1 = 注文せずカートメニューへ戻る
	private static int runOrderConfirmation(Scanner scanner, CartService cartService, OrderService orderService) {
		// 現在のカートを取得
		Cart cart = cartService.getCart();

		// カートが空の場合は注文せず、カートメニューへ戻る
		if (cart.getItems().isEmpty()) {
			System.out.println("カートに商品がありません。");
			System.out.println();
			return -1;
		}

		// カート内商品表示
		cartService.showOrderConfirmationList();

		while (true) {
			// 注文確認メニューの表示
			Menu.showOrderConfirmationMenu();
			int selectedNum = InputUtil.readInt(scanner);

			if (selectedNum == 1) {
				// 注文を確定し、作成された注文情報を取得
				Order order = orderService.confirmOrder(cart);

				// 注文確定→注文完了表示
				orderService.showOrderComplete(order);

				// 注文確定後の選択（買い物を続ける or このアプリを終了する）
				while (true) {
					// 注文確認後のメニュー表示
					Menu.showAfterOrderMenu();
					int afterOrderNum = InputUtil.readInt(scanner);

					if (afterOrderNum == 1) {
						// 買い物を続ける
						return 1;
					} else if (afterOrderNum == 0) {
						// アプリを終了
						return 0;

					} else {
						// エラー表示
						System.out.println("0〜1の操作番号を入力してください。");
						System.out.println();
					}
				}
			} else if (selectedNum == 0) {
				// 注文せず、カートメニューに戻る
				return -1;
			} else {
				// エラー処理
				System.out.println("0〜1の操作番号を入力してください。");
				System.out.println();
			}

		}
	}
}
