package service;

import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Product;
import util.ConsoleFormatter;

public class CartService {
	// カート内商品を保持するリスト
	private final List<CartItem> cartItems = new ArrayList<>();

	// 0. カート内書品リストを取得
	public List<CartItem> getCartItems() {
		return cartItems;
	}

	// 1. 商品をカートへ追加（成功：true、失敗：false）
	public boolean addToCart(Product product, int quantity) {
		// 追加数量が0以下の場合、追加しない
		if (quantity <= 0) {
			return false;
		}

		// カート内に同じ商品があるか確認 → ある場合の追加（すでに追加済＋今回追加分）
		for (CartItem currentCartItem : cartItems) {
			if (currentCartItem.getProduct().getProductId() == product.getProductId()) {
				// 追加商品がカート内にある場合、現在のカート内数量と追加数量を合算
				int updatedQuantity = currentCartItem.getQuantity() + quantity;

				if (updatedQuantity > product.getStock()) {
					// 合算した数量が在庫数を超える場合
					return false;
				}
				// 既存のCartItemの数量を更新
				currentCartItem.setQuantity(updatedQuantity);
				return true;
			}
		}

		// 商品の追加数量が在庫数を超える場合は追加しない
		if (quantity > product.getStock()) {
			return false;
		}

		// 商品と数量からカート内商品を作成
		CartItem newCartItem = new CartItem(product, quantity);
		// カートへ追加		
		cartItems.add(newCartItem);
		return true;

	}

	// 2. カート内合計金額計算処理
	public int calculateTotalPrice() {
		// 合計金額を初期化
		int totalPrice = 0;

		// カート内商品の小計を順番に計算
		for (CartItem currentCartItem : cartItems) {
			totalPrice += currentCartItem.getSubtotal();
		}
		return totalPrice;
	}

	// 3. 商品IDからカート内商品を取得	
	public CartItem findCartItemByProductId(int productId) {
		// 引数に渡された商品IDと一致する CartItem を返す
		for (CartItem currentCartItem : cartItems) {
			if (currentCartItem.getProduct().getProductId() == productId) {
				return currentCartItem;
			}
		}
		// 一致するカート内商品がない場合はnullを返す
		return null;
	}

	// 4. カート内商品の数量変更（成功したらtrue）
	public boolean updateCartItemQuantity(CartItem cartItem, int newQuantity) {
		// 変更後の数量が0以下の場合、追加しない
		if (newQuantity <= 0) {
			return false;
		}

		if (newQuantity > cartItem.getProduct().getStock()) {
			// 変更後の数量が、在庫数より多い場合
			return false;
		}

		// 変更した数量をセット（更新）
		cartItem.setQuantity(newQuantity);
		return true;
	}

	// 5. 商品IDからカート内商品削除（成功したらtrue）
	public boolean removeCartItemByProductId(int productId) {
		// 商品IDから、削除商品を特定
		CartItem deleteItem = findCartItemByProductId(productId);

		if (deleteItem == null) {
			return false;
		}

		// カート内アイテムを削除
		cartItems.remove(deleteItem);
		return true;
	}

	// 6. カート内一覧表示処理
	public void showCartList() {
		showCartContents("カート内一覧");
	}

	// 7. 注文確認用カート内容表示処理
	public void showOrderConfirmationList() {
		showCartContents("注文内容の確認");
	}

	// 8. 指定されたタイトルでカート内容を表示
	private void showCartContents(String title) {
		System.out.println();
		System.out.println("[ " + title + " ]");
		System.out.println();

		// 表の見出しを表示
		System.out.println(ConsoleFormatter.formatCartHeader());

		// カート内商品の表示
		for (CartItem currentCartItem : cartItems) {
			System.out.println(ConsoleFormatter.formatCartRow(
					currentCartItem.getProduct().getProductId(),
					currentCartItem.getProduct().getProductName(),
					currentCartItem.getProduct().getPrice(),
					currentCartItem.getQuantity(),
					currentCartItem.getSubtotal()));
		}

		System.out.println("-".repeat(70));

		// 合計金額を3桁ごとでカンマ区切り・末尾に「円」を付ける
		String formattedTotal = String.format("%,d円", calculateTotalPrice());
		System.out.println("合計金額：" + formattedTotal);

		System.out.println();
	}

}
