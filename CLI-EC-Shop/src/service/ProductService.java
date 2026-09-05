package service;

import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductService {
	private List<Product> allProducts = new ArrayList<>();

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public void setAllProducts(List<Product> allProducts) {
		this.allProducts = allProducts;
	}

	//商品リストへ商品を追加	
	public ProductService() {
		allProducts.add(new Product(1, "MacBook Air　M5", 184800, 10));
		allProducts.add(new Product(2, "MacBook Pro　M5", 449800, 11));
		allProducts.add(new Product(3, "iPhone 17", 129800, 6));
		allProducts.add(new Product(4, "iPhone Air", 177800, 8));
		allProducts.add(new Product(5, "Magic Mouse", 10800, 7));
		allProducts.add(new Product(6, "Apple EarPods", 2980, 5));
		allProducts.add(new Product(7, "AirPods 4", 32800, 4));
		allProducts.add(new Product(8, "Apple Watch Ultra 3", 113000, 7));
		allProducts.add(new Product(9, "AirTag", 16980, 7));
		allProducts.add(new Product(10, "iPad Pro", 209800, 6));

	}

	//商品一覧を表示させるメソッド	
	public void showProductList() {
		System.out.println("====================");
		System.out.println("　　　　商品一覧　　　　");
		System.out.println("====================");
		System.out.printf("%-4s %-18s %-12s %-8s", "ID", "商品名", "価格", "在庫数");
		System.out.println();

		for (Product product : allProducts) {
			System.out.printf("%-4s %-18s %-12s %-8s%n",
					product.getProductId(),
					product.getProductName(),
					product.getPrice(),
					product.getStock());
		}

		System.out.println();

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

		for (Product currentProduct : allProducts) {
			if (currentProduct.getProductId() == productId) {
				return currentProduct;
			}
		}
		return null;
	}

	//商品詳細表示：特定した商品を表示させる
	public void showProductDetail(Product selectProduct) {
		System.out.println("商品詳細");
		System.out.println("--------------------");
		System.out.printf("%-4s %-18s %-12s %-8s", "ID", "商品名", "価格", "在庫数");
		System.out.println();
		System.out.printf("%-4s %-18s %-12s %-8s%n",
				selectProduct.getProductId(),
				selectProduct.getProductName(),
				selectProduct.getPrice(),
				selectProduct.getStock());
		System.out.println();
		System.out.println();
	}
}
