package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Cart;
import model.CartItem;
import model.Order;
import model.Product;

public class OrderService {
	private List<Order> orders = new ArrayList<>();
	private int nextOrderId = 1;

	public List<Order> getOrders() {
		return orders;
	}

	public Order confirmOrder(Cart cart) {

		//カート内確認
		if (cart.getItems().isEmpty()) {
			return null;
		}

		//注文商品用リスト		
		List<CartItem> orderItems = new ArrayList<>();
		int totalPrice = 0;

		for (CartItem currentCartItem : cart.getItems()) {

			// 注文用リストへカート内の商品をコピー			
			CartItem orderItem = new CartItem(currentCartItem.getProduct(), currentCartItem.getQuantity());
			orderItems.add(orderItem);

			// 合計金額の計算			
			totalPrice += currentCartItem.getProduct().getPrice() * currentCartItem.getQuantity();

			// 在庫数の更新			
			Product currentProduct = currentCartItem.getProduct();
			int updateStock = currentProduct.getStock() - currentCartItem.getQuantity();
			currentProduct.setStock(updateStock);
		}

		Order order = new Order(nextOrderId, LocalDateTime.now(), orderItems, totalPrice);

		orders.add(order);
		nextOrderId += 1;
		cart.getItems().clear();

		return order;
	}

	public void showOrderComplete(Order order) {
		System.out.println("====================");
		System.out.println("　　　　注文完了　　　　");
		System.out.println("====================");
		System.out.println("注文を確定しました。");
		System.out.println();

		System.out.printf(
				"%-8s %-30s %-12s%n",
				"注文ID", "日時", "合計金額");

		System.out.printf(
				"%-8s %-30s %-12s%n",
				order.getOrderId(),
				order.getOrderDate(),
				order.getTotalPrice() + "円");

		System.out.println("--------------------");
		System.out.printf(
				"%-4s %-18s %-12s %-8s %-8s%n",
				"ID", "商品名", "価格", "数量", "小計");

		for (CartItem currentCartItem : order.getItems()) {
			int subtotal = currentCartItem.getProduct().getPrice()
					* currentCartItem.getQuantity();

			System.out.printf(
					"%-4s %-18s %-12s %-8s %-8s%n",
					currentCartItem.getProduct().getProductId(),
					currentCartItem.getProduct().getProductName(),
					currentCartItem.getProduct().getPrice(),
					currentCartItem.getQuantity(),
					subtotal);
		}

		System.out.println("--------------------");
		System.out.println(
				"合計金額：" + order.getTotalPrice() + "円");
		System.out.println();
		System.out.println();
		System.out.println();

	}

}
