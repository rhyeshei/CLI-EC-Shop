package service;

import java.util.ArrayList;
import java.util.List;

import model.Product;
import util.ConsoleFormatter;

public class ProductService {
	private final List<Product> allProducts = new ArrayList<>();

	private void showProductHeader() {
		System.out.println(
				ConsoleFormatter.padRight("ID", 6)
						+ ConsoleFormatter.padRight("商品名", 24)
						+ ConsoleFormatter.padCenter("価格", 21)
						+ ConsoleFormatter.padCenter("在庫数", 8));
	}

	//商品リストへ商品を追加	
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

	private void showProductRow(Product product) {
		String formattedPrice = String.format("%,d円", product.getPrice());

		System.out.println(
				ConsoleFormatter.padRight(String.valueOf(product.getProductId()), 6)
						+ ConsoleFormatter.padRight(product.getProductName(), 26)
						+ ConsoleFormatter.padLeft(formattedPrice, 14)
						+ ConsoleFormatter.padLeft(String.valueOf(product.getStock()), 8));
	}

	//商品一覧を表示させるメソッド	
	public void showProductList() {
		System.out.println();
		System.out.println("[ 商品一覧 ]");
		System.out.println();

		showProductTable(allProducts);

	}

	//	検索結果を表示させるメソッド
	public void showSearchResults(List<Product> searchResults) {
		System.out.println();
		System.out.println("[ 検索結果：" + searchResults.size() + "件 ]");
		System.out.println();

		showProductTable(searchResults);
	}

	//商品名検索のメソッド	
	public List<Product> searchProductsByName(String keyword) {
		List<Product> searchResults = new ArrayList<>();
		String keywordLower = keyword.toLowerCase();

		for (Product currentProduct : allProducts) {
			String productNameLower = currentProduct.getProductName().toLowerCase();
			if (productNameLower.contains(keywordLower)) {
				searchResults.add(currentProduct);
			}
		}

		return searchResults;
	}

	//商品詳細表示：商品IDから、商品を特定する
	public Product findProductById(int productId) {

		return findProductById(allProducts, productId);
	}

	public Product findProductById(List<Product> products, int productId) {

		for (Product currentProduct : products) {
			if (currentProduct.getProductId() == productId) {
				return currentProduct;
			}
		}
		return null;
	}

	//商品詳細表示：特定した商品を表示させる
	public void showProductDetail(Product selectedProduct) {
		System.out.println();
		System.out.println("[ 商品詳細 ]");
		System.out.println();

		showProductHeader();
		showProductRow(selectedProduct);

		System.out.println();
		System.out.println();
	}

	private void showProductTable(List<Product> products) {
		showProductHeader();

		for (Product currentProduct : products) {
			showProductRow(currentProduct);
		}

		System.out.println();
	}

}
