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
		products.add(new Product(1, "キーボード", 2500, 12));
		products.add(new Product(2, "マウス", 1500, 11));
		products.add(new Product(3, "モニター", 15000, 6));
		products.add(new Product(4, "急速充電器", 3500, 8));
	}

	public void showProductMenu() {
		System.out.println("====================");
		System.out.println("　　　　商品一覧　　　　");
		System.out.println("====================");
		System.out.println();
		for (int i = 0; i < products.size(); i++) {
		}
	}

}
