package service;

import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductService {
	private List<Product> products = new ArrayList<>();

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public ProductService() {
		products.add(new Product(1, "MacBook Air　M5", 184800, 10));
		products.add(new Product(2, "MacBook Pro　M5", 449800, 11));
		products.add(new Product(3, "iPhone 17", 129800, 6));
		products.add(new Product(4, "iPhone Air", 177800, 8));
		products.add(new Product(5, "Magic Mouse", 10800, 7));
		products.add(new Product(6, "Apple EarPods", 2980, 5));
		products.add(new Product(7, "AirPods 4", 32800, 4));
		products.add(new Product(8, "Apple Watch Ultra 3", 113000, 7));
		products.add(new Product(9, "AirTag", 16980, 7));
		products.add(new Product(10, "iPad Pro", 209800, 6));

	}

	public void showProductList() {
		System.out.println("====================");
		System.out.println("　　　　商品一覧　　　　");
		System.out.println("====================");
		System.out.printf("%-4s %-18s %-12s %-8s", "ID", "商品名", "価格", "在庫数");
		System.out.println();

		for (Product product : products) {
			System.out.printf("%-4s %-18s %-12s %-8s%n",
					product.getProductId(),
					product.getProductName(),
					product.getPrice(),
					product.getStock());
		}

		System.out.println();

	}

}
