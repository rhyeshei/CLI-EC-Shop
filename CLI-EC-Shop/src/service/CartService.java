package service;

import model.Cart;
import model.CartItem;
import model.Product;

public class CartService {

	private Cart cart = new Cart();

	public Cart getCart() {
		return cart;
	}

	public void addToCart(Product product, int quantity) {
		//商品と数量からカート内商品を作成
		CartItem newCartItem = new CartItem(product, quantity);

		cart.getItems().add(newCartItem);
	}

}
