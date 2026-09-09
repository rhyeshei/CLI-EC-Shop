package service;

import model.Cart;
import model.CartItem;
import model.Product;

public class CartService {

	private Cart cart = new Cart();

	public Cart getCart() {
		return cart;
	}

	// カート追加処理	
	public boolean addToCart(Product product, int quantity) {
		//カート内に追加商品があるか確認＋ある場合の追加（すでに追加済＋今回追加分）
		for (CartItem currentCartItem : cart.getItems()) {
			if (currentCartItem.getProduct().getProductId() == product.getProductId()) {
				// 現在の数量と追加数量を合算
				int updatedQuantity = currentCartItem.getQuantity() + quantity;

				// 合算した数量が在庫数を超える場合
				if (updatedQuantity > product.getStock()) {
					return false;
				}

				// 既存のCartItemの数量を更新
				currentCartItem.setQuantity(updatedQuantity);

				return true;
			}
		}

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

	//カート内合計金額計算処理
	public int calculateTotalPrice() {
		int totalPrice = 0;

		// cart.getItems()をfor文で繰り返す
		for (CartItem currentCartItem : cart.getItems()) {
			totalPrice += currentCartItem.getProduct().getPrice() * currentCartItem.getQuantity();
		}

		return totalPrice;
	}

	public void showCartList() {
		System.out.println("====================");
		System.out.println("　　　カート内一覧　　　");
		System.out.println("====================");
		System.out.printf("%-4s %-18s %-12s %-8s %-8s", "ID", "商品名", "価格", "数量", "小計");
		System.out.println();

		for (CartItem currentCartItem : cart.getItems()) {
			int subtotal = currentCartItem.getProduct().getPrice() * currentCartItem.getQuantity();

			System.out.printf("%-4s %-18s %-12s %-8s %-8s%n",
					currentCartItem.getProduct().getProductId(),
					currentCartItem.getProduct().getProductName(),
					currentCartItem.getProduct().getPrice(),
					currentCartItem.getQuantity(),
					subtotal);
		}

		System.out.println("--------------------");
		System.out.println("合計金額：" + calculateTotalPrice() + "円");
		System.out.println();
	}

	//商品IDからカート内の商品検索	
	public CartItem findCartItemByProductId(int productId) {
		for (CartItem currentCartItem : cart.getItems()) {
			if (currentCartItem.getProduct().getProductId() == productId) {
				return currentCartItem;
			}
		}
		return null;
	}

	//数量変更メソッド
	public boolean updateCartItemQuantity(CartItem cartItem, int newQuantity) {
		if (newQuantity > cartItem.getProduct().getStock()) {
			return false;
		}

		cartItem.setQuantity(newQuantity);

		return true;
	}

}
