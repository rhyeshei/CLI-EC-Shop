package model;

public class Product {
	private final int productId;
	private final String productName;
	private final int price;
	private int stock;

	public Product(int productId, String productName, int price, int stock) {
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.stock = stock;
	}

	public int getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public int getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
