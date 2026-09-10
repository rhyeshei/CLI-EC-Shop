package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Cart;
import model.CartItem;
import model.Order;
import model.Product;
import util.ConsoleFormatter;

public class OrderService {
	private static final DateTimeFormatter ORDER_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	private final List<Order> orders = new ArrayList<>();
	private int nextOrderId = 1;

	public List<Order> getOrders() {
		return List.copyOf(orders);
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
			totalPrice += currentCartItem.getSubtotal();

			// 在庫数の更新			
			Product currentProduct = currentCartItem.getProduct();
			int updateStock = currentProduct.getStock() - currentCartItem.getQuantity();
			currentProduct.setStock(updateStock);
		}

		Order order = new Order(nextOrderId, LocalDateTime.now(), orderItems, totalPrice);

		orders.add(order);
		nextOrderId++;
		cart.getItems().clear();

		return order;
	}

	public void showOrderComplete(Order order) {
		System.out.println();
		System.out.println("[ 注文完了 ]");
		System.out.println();
		System.out.println("注文を確定しました。");
		System.out.println();

		String formattedOrderDate = order.getOrderDate()
				.format(ORDER_DATE_FORMATTER);

		System.out.println("注文ID　：" + order.getOrderId());

		System.out.println("注文日時：" + formattedOrderDate);

		System.out.println();
		System.out.println("[ 注文内容 ]");
		System.out.println();

		System.out.println(ConsoleFormatter.formatCartHeader());

		for (CartItem currentCartItem : order.getItems()) {

			Product product = currentCartItem.getProduct();

			System.out.println(
					ConsoleFormatter.formatCartRow(
							product.getProductId(),
							product.getProductName(),
							product.getPrice(),
							currentCartItem.getQuantity(),
							currentCartItem.getSubtotal()));
		}

		System.out.println("-".repeat(70));

		String formattedTotal = String.format("%,d円", order.getTotalPrice());

		System.out.println("合計金額：" + formattedTotal);

		System.out.println();

	}

}
