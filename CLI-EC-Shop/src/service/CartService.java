package service;

import model.Cart;
import model.CartItem;
import model.Product;

public class CartService {

	private Cart cart = new Cart();

	public Cart getCart() {
		return cart;
	}

	public boolean addToCart(Product product, int quantity) {

		//在庫数確認		
		if (quantity > product.getStock()) {
			return false;
		}

		//商品と数量からカート内商品を作成
		CartItem newCartItem = new CartItem(product, quantity);
		//カートへ追加		
		cart.getItems().add(newCartItem);

		return true;
	}

}
