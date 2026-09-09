package model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
	private int orderId;
	private LocalDateTime orderDate;
	private List<CartItem> items;
	private int totalPrice;

	public Order(int orderId, LocalDateTime ordeDate, List<CartItem> items, int totalPrice) {
		this.orderId = orderId;
		this.orderDate = ordeDate;
		this.items = items;
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
