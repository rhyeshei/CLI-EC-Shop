package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.CartItem;
import model.Order;
import util.ConsoleFormatter;

public class OrderService {
	// 注文ID
	private int orderId = 1;

	// 1. 注文の確定
	public Order confirmOrder(List<CartItem> cartItems) {
		// 注文商品用リストの作成
		List<CartItem> orderItemList = new ArrayList<>(cartItems);
		int totalPrice = 0;

		for (CartItem cartItem : cartItems) {
			// 合計金額の計算（小計を加算）
			totalPrice += cartItem.getSubtotal();
			// 在庫数の更新
			int updatedStock = cartItem.getProduct().getStock() - cartItem.getQuantity();
			cartItem.getProduct().setStock(updatedStock);
		}

		// 注文情報の作成
		Order order = new Order(orderId, LocalDateTime.now(), orderItemList, totalPrice);
		// 注文IDを1増やす（次回用）
		orderId++;
		// カート内アイテムを全て削除
		cartItems.clear();

		// 注文情報を返す
		return order;
	}

	// 2. 注文完了画面の表示
	public void showOrderComplete(Order order) {
		System.out.println();
		System.out.println("[ 注文完了 ]");
		System.out.println();
		System.out.println("注文を確定しました。");
		System.out.println();

		System.out.println("注文ID　：" + order.getOrderId());
		System.out.println("注文日時：" + formatOrderDate(order.getOrderDate()));
		System.out.println();
		System.out.println("[ 注文内容 ]");
		System.out.println();
		System.out.println(ConsoleFormatter.formatCartHeader());

		for (CartItem orderItem : order.getItems()) {
			System.out.println(
					ConsoleFormatter.formatCartRow(
							orderItem.getProduct().getProductId(),
							orderItem.getProduct().getProductName(),
							orderItem.getProduct().getPrice(),
							orderItem.getQuantity(),
							orderItem.getSubtotal()));
		}

		System.out.println("-".repeat(70));
		String formattedTotal = String.format("%,d円", order.getTotalPrice());
		System.out.println("合計金額：" + formattedTotal);
		System.out.println();
	}

	// 3. 注文日時を xxxx-xx-xx xx:xx の形に整形
	private String formatOrderDate(LocalDateTime orderDate) {
		String month = String.format("%02d", orderDate.getMonthValue());
		String day = String.format("%02d", orderDate.getDayOfMonth());
		String hour = String.format("%02d", orderDate.getHour());
		String minute = String.format("%02d", orderDate.getMinute());

		return orderDate.getYear() + "-" + month + "-" + day + " " + hour + ":" + minute;
	}

}
