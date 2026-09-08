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

}
