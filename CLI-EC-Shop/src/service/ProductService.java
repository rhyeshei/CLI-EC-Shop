package service;

import java.util.ArrayList;
import java.util.List;

import model.Product;
import util.ConsoleFormatter;

public class ProductService {
	// 全商品を保持する空のArrayListを生成
	// private：他クラスから直接アクセスを禁止
	// final：別リストの再代入を禁止
	private final List<Product> allProducts = new ArrayList<>();

	// 0. コンストラクタで初期商品データを登録	
	public ProductService() {
		allProducts.add(new Product(1, "MacBook Air M5", 184800, 10));
		allProducts.add(new Product(2, "MacBook Pro M5", 449800, 11));
		allProducts.add(new Product(3, "iPhone 17", 129800, 6));
		allProducts.add(new Product(4, "iPhone Air", 177800, 8));
		allProducts.add(new Product(5, "Magic Mouse", 10800, 7));
		allProducts.add(new Product(6, "Apple EarPods", 2980, 5));
		allProducts.add(new Product(7, "AirPods 4", 32800, 4));
		allProducts.add(new Product(8, "Apple Watch Ultra 3", 113000, 7));
		allProducts.add(new Product(9, "AirTag", 16980, 7));
		allProducts.add(new Product(10, "iPad Pro", 209800, 6));

	}

	// 1. 商品一覧表示
	public void showProductList() {
		System.out.println();
		System.out.println("[ 商品一覧 ]");
		System.out.println();

		showProductTable(allProducts);

	}

	// 2. 検索結果表示
	public void showSearchResults(List<Product> searchResults) {
		System.out.println();
		System.out.println("[ 検索結果：" + searchResults.size() + "件 ]");
		System.out.println();

		showProductTable(searchResults);
	}

	// 3. 商品名検索メソッド
	public List<Product> searchProductsByName(String keyword) {
		// 検索結果を保持する空のリスト（searchResults）を作成
		List<Product> searchResults = new ArrayList<>();
		// 検索キーワードを小文字に変換
		String keywordLower = keyword.toLowerCase();

		// 全商品を順番に確認
		for (Product currentProduct : allProducts) {
			// 商品名を取得し小文字に変換
			String productNameLower = currentProduct.getProductName().toLowerCase();
			if (productNameLower.contains(keywordLower)) {
				// 商品名に検索キーワードに含まれている場合、searchResults に追加
				searchResults.add(currentProduct);
			}
		}

		return searchResults;
	}

	// 4. 全商品から商品IDに一致する商品を取得
	// 実際の検索処理は5番メソッドで実施
	public Product findProductById(int productId) {
		return findProductById(allProducts, productId);
	}

	// 5. 指定された商品リストから商品IDに一致する商品を取得
	// 商品名の検索結果から商品を選択するときに使用
	public Product findProductById(List<Product> products, int productId) {

		for (Product currentProduct : products) {
			if (currentProduct.getProductId() == productId) {
				return currentProduct;
			}
		}
		return null;
	}

	// 6. 選択（入力）された商品の詳細を表示
	public void showProductDetail(Product selectedProduct) {
		System.out.println();
		System.out.println("[ 商品詳細 ]");
		System.out.println();

		showProductHeader();
		showProductRow(selectedProduct);

		System.out.println();
		System.out.println();
	}

	// 7. 商品リストを表形式で表示
	private void showProductTable(List<Product> products) {
		showProductHeader();

		for (Product currentProduct : products) {
			showProductRow(currentProduct);
		}

		System.out.println();
	}

	// 8. 商品表のヘッダーを整形して表示
	private void showProductHeader() {
		System.out.println(
				ConsoleFormatter.padRight("ID", 6)
						+ ConsoleFormatter.padRight("商品名", 24)
						+ ConsoleFormatter.padCenter("価格", 21)
						+ ConsoleFormatter.padCenter("在庫数", 8));
	}

	// 9. 商品1件分を整形して表示
	private void showProductRow(Product product) {
		// 価格を3桁ごとにカンマで区切り、末尾に「円」を付ける
		String formattedPrice = String.format("%,d円", product.getPrice());

		System.out.println(
				ConsoleFormatter.padRight(String.valueOf(product.getProductId()), 6)
						+ ConsoleFormatter.padRight(product.getProductName(), 26)
						+ ConsoleFormatter.padLeft(formattedPrice, 14)
						+ ConsoleFormatter.padLeft(String.valueOf(product.getStock()), 8));
	}

}
