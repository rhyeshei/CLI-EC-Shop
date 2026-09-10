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
	private static final int ORDER_CANCELED = -1;
	private static final int CONTINUE_SHOPPING = 1;
	private static final int EXIT_APPLICATION = 0;

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			ProductService productService = new ProductService();
			CartService cartService = new CartService();
			OrderService orderService = new OrderService();

			runMainMenu(scanner, productService, cartService, orderService);

		}
	}

	// メインメニューを繰り返し表示し、選択された画面へ遷移する	
	private static void runMainMenu(Scanner scanner, ProductService productService, CartService cartService,
			OrderService orderService) {
		while (true) {
			Menu.showMainMenu();
			int mainMenuNumber = InputUtil.readInt(scanner);

			if (mainMenuNumber == 1) {
				runProductMenu(scanner, productService, cartService);
			} else if (mainMenuNumber == 2) {
				boolean shouldExit = runCartMenu(scanner, productService, cartService, orderService);

				if (shouldExit) {
					System.out.println(
							"CLI EC Shopを終了します。");

					return;
				}
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
	private static void runProductMenu(Scanner scanner, ProductService productService, CartService cartService) {
		while (true) {
			Menu.showProductMenu();
			int productMenuNumber = InputUtil.readInt(scanner);

			if (productMenuNumber == 1) {
				runProductList(scanner, productService, cartService);
			} else if (productMenuNumber == 2) {
				runProductSearch(scanner, productService, cartService);
			} else if (productMenuNumber == 0) {
				return;
			} else {
				System.out.println("0〜2のメニュー番号を入力してください。");
				System.out.println();
			}
		}
	}

	private static void runProductDetail(Scanner scanner, ProductService productService, Product selectedProduct,
			CartService cartService) {
		//商品詳細を表示
		productService.showProductDetail(selectedProduct);

		//商品詳細メニューの繰り返し表示
		while (true) {
			Menu.showProductDetailMenu();
			int detailMenuNum = InputUtil.readInt(scanner);

			if (detailMenuNum == 1) {
				//購入数量を入力する処理
				runAddToCart(scanner, selectedProduct, cartService);
				return;

			} else if (detailMenuNum == 0) {
				//商品メニューへ				
				return;
			} else {
				System.out.println("0〜1の操作番号を入力してください。");
			}
		}

	}

	// 商品一覧を表示し、詳細を確認する
	private static void runProductList(Scanner scanner, ProductService productService, CartService cartService) {
		productService.showProductList();
		Menu.showProductSelectionPrompt();

		int selectedProductId = InputUtil.readInt(scanner);

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

		runProductDetail(scanner, productService, selectedProduct, cartService);
	}

	// 商品名を受け取り、該当する商品を検索する
	private static void runProductSearch(Scanner scanner, ProductService productService, CartService cartService) {
		while (true) {
			//検索プロンプトの表示		
			Menu.showProductSearchPrompt();
			//検索キー		
			String keyword = InputUtil.readNonBlankString(scanner);

			//検索処理		
			if (keyword.equals("0")) {
				return;
			}

			List<Product> searchResults = productService.searchProductsByName(keyword);

			if (searchResults.isEmpty()) {
				System.out.println();
				System.out.println("[ 検索結果：0件 ]");
				System.out.println();
				System.out.println("該当する商品がありません。");
				System.out.println();
				continue;
			}

			productService.showSearchResults(searchResults);

			while (true) {
				// 商品IDを入力させ、商品詳細を表示させる				
				Menu.showSearchResultSelectionPrompt();
				int selectedProductId = InputUtil.readInt(scanner);
				System.out.println();
				// 入力された商品IDから商品を取得し、詳細を表示する
				if (selectedProductId == -1) {
					break;
				}

				if (selectedProductId == 0) {
					return;
				}

				Product selectedProduct = productService.findProductById(searchResults, selectedProductId);

				if (selectedProduct == null) {
					System.out.println();
					System.out.println("検索結果に該当する商品IDがありません。");
					System.out.println();

					continue;
				}

				runProductDetail(scanner, productService, selectedProduct, cartService);

				break;
			}

		}
	}

	// カートメニューを繰り返し表示し、各カート操作へ遷移する
	private static boolean runCartMenu(Scanner scanner, ProductService productService, CartService cartService,
			OrderService orderService) {
		while (true) {
			cartService.showCartList();
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

				if (orderResult == CONTINUE_SHOPPING) {
					runProductMenu(scanner, productService, cartService);
					return false;
				}

				if (orderResult == EXIT_APPLICATION) {
					return true;
				}
			} else if (cartMenuNumber == 0) {
				return false;
			} else {
				System.out.println("0〜3のメニュー番号を入力してください。");
				System.out.println();
			}
		}

	}

	private static void runCartQuantityUpdate(Scanner scanner, CartService cartService) {
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
			Menu.showNewQuantityPrompt();
			int updateQuantity = InputUtil.readPositiveInt(scanner);

			boolean isUpdated = cartService.updateCartItemQuantity(selectedCartItem, updateQuantity);

			if (isUpdated) {
				System.out.println("数量を変更しました。");
				System.out.println();
				return;
			}
			System.out.println();
			System.out.println("在庫数が足りません。再度数量を入力してください。");
			System.out.println("在庫数：" + selectedCartItem.getProduct().getStock());
			System.out.println();

		}

	}

	private static void runCartItemDelete(Scanner scanner, CartService cartService) {
		Menu.showCartItemDeletePrompt();
		int selectedProductId = InputUtil.readInt(scanner);

		if (selectedProductId == 0) {
			return;
		}

		boolean isDeleted = cartService.removeCartItemByProductId(selectedProductId);

		if (!isDeleted) {
			System.out.println("カート内に該当する商品がありません");
			System.out.println();
			return;
		}

		System.out.println("商品をカートから削除しました。");
		System.out.println();
	}

	private static int runOrderConfirmation(Scanner scanner, CartService cartService, OrderService orderService) {
		Cart cart = cartService.getCart();

		if (cart.getItems().isEmpty()) {
			System.out.println("カートに商品がありません。");
			System.out.println();
			return ORDER_CANCELED;
		}

		cartService.showOrderConfirmationList();

		while (true) {
			Menu.showOrderConfirmationMenu();
			int selectedNum = InputUtil.readInt(scanner);

			if (selectedNum == 1) {
				Order order = orderService.confirmOrder(cart);
				orderService.showOrderComplete(order);
				// 注文完了後の選択
				while (true) {
					Menu.showAfterOrderMenu();

					int afterOrderNum = InputUtil.readInt(scanner);

					if (afterOrderNum == CONTINUE_SHOPPING) {
						return CONTINUE_SHOPPING;

					} else if (afterOrderNum == EXIT_APPLICATION) {

						return EXIT_APPLICATION;

					} else {
						System.out.println("0〜1の操作番号を入力してください。");
						System.out.println();
					}
				}
			} else if (selectedNum == 0) {
				return ORDER_CANCELED;
			} else {
				System.out.println("0〜1の操作番号を入力してください。");
				System.out.println();
			}

		}
	}

	private static void runAddToCart(Scanner scanner, Product selectedProduct, CartService cartService) {

		while (true) {
			Menu.showAddToCartPrompt();

			int quantity = InputUtil.readPositiveInt(scanner);

			boolean isAdded = cartService.addToCart(selectedProduct, quantity);

			if (isAdded) {
				System.out.println("カートに　" + selectedProduct.getProductName() + "　を" + quantity + "個追加しました。");

				System.out.println();
				return;
			}

			System.out.println("在庫数が足りません。購入数を再度入力してください。");

			System.out.println();
		}
	}
}
