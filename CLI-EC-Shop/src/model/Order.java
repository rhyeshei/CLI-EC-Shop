package model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
	private final int orderId;
	private final LocalDateTime orderDate;
	private final List<CartItem> items;
	private final int totalPrice;

	public Order(int orderId, LocalDateTime orderDate, List<CartItem> items, int totalPrice) {
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.items = List.copyOf(items);
		this.totalPrice = totalPrice;
	}

	public int getOrderId() {
		return orderId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

}
