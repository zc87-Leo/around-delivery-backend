package entity;

import org.json.JSONObject;

public class Order {

 private String orderId;
 private String userId;
 private String trackingId;
 private String senderAddress;
 private String recipentAddress;


 private String orderCreateTime;
 private String orderPickupTime;
 private String orderDeliveryTime;

 private Boolean active;
 private Float packageWeight;
 private Float packageHeight;
 private Boolean isFragile;
 private Float totalCost;
 
//public Order(OrderBuilder builder) {
//	// TODO Auto-generated constructor stub
//	this.orderId = orderId;
//	this.userId = userId;
//	this.trackingId = trackingId;
//	this.senderAddress = senderAddress;
//	this.recipentAddress = recipentAddress;
//	this.orderCreateTime = orderCreateTime;
//	this.orderPickupTime = orderPickupTime;
//	this.orderDeliveryTime = orderDeliveryTime;
//	this.active = active;
//	this.packageWeight = packageWeight;
//	this.packageHeight = packageHeight;
//	this.isFragile = isFragile;
//	this.totalCost = totalCost;
//}

public String getOrderId() {
	return orderId;
}

public void setOrderId(String orderId) {
	this.orderId = orderId;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getTrackingId() {
	return trackingId;
}

public void setTrackingId(String trackingId) {
	this.trackingId = trackingId;
}

public String getSenderAddress() {
	return senderAddress;
}

public void setSenderAddress(String senderAddress) {
	this.senderAddress = senderAddress;
}

public String getRecipentAddress() {
	return recipentAddress;
}

public void setRecipentAddress(String recipentAddress) {
	this.recipentAddress = recipentAddress;
}

public String getOrderCreateTime() {
	return orderCreateTime;
}

public void setOrderCreateTime(String orderCreateTime) {
	this.orderCreateTime = orderCreateTime;
}

public String getOrderPickupTime() {
	return orderPickupTime;
}

public void setOrderPickupTime(String orderPickupTime) {
	this.orderPickupTime = orderPickupTime;
}

public String getOrderDeliveryTime() {
	return orderDeliveryTime;
}

public void setOrderDeliveryTime(String orderDeliveryTime) {
	this.orderDeliveryTime = orderDeliveryTime;
}

public Boolean getActive() {
	return active;
}

public void setActive(Boolean active) {
	this.active = active;
}

public Float getPackageWeight() {
	return packageWeight;
}

public void setPackageWeight(Float packageWeight) {
	this.packageWeight = packageWeight;
}

public Float getPackageHeight() {
	return packageHeight;
}

public void setPackageHeight(Float packageHeight) {
	this.packageHeight = packageHeight;
}

public Boolean getIsFragile() {
	return isFragile;
}

public void setIsFragile(Boolean isFragile) {
	this.isFragile = isFragile;
}

public Float getTotalCost() {
	return totalCost;
}

public void setTotalCost(Float totalCost) {
	this.totalCost = totalCost;
}

//public JSONObject toJSONObject() {
//	JSONObject obj = new JSONObject();
//	obj.put("order_id", orderId);
//	obj.put("user_id", userId);
//	obj.put("tracking_id", trackingId);
//	obj.put("sender_address", senderAddress);
//	obj.put("recipent_address", recipentAddress);
//	obj.put("order_create_time", orderCreateTime);
//	obj.put("order_pickup_time", orderPickupTime);
//	obj.put("order_delivery_time", orderDeliveryTime);
//	obj.put("active", active);
//	obj.put("package_weight", packageWeight);
//	obj.put("package_height", packageHeight);
//	obj.put("is_fragile", isFragile);
//	obj.put("total_cost", totalCost);
//	return obj;
//}

//public static class OrderBuilder {
//	private String orderId;
//	 private String userId;
//	 private String trackingId;
//	 private String senderAddress;
//	 private String recipentAddress;
//
//
//	 private String orderCreateTime;
//	 private String orderPickupTime;
//	 private String orderDeliveryTime;
//
//	 private Boolean active;
//	 private Float packageWeight;
//	 private Float packageHeight;
//	 private Boolean isFragile;
//	 private Float totalCost;
//
//	public void setOrderId(String orderId) {
//		this.orderId = orderId;
//	}
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
//	public void setTrackingId(String trackingId) {
//		this.trackingId = trackingId;
//	}
//	public void setSenderAddress(String senderAddress) {
//		this.senderAddress = senderAddress;
//	}
//	public void setRecipentAddress(String recipentAddress) {
//		this.recipentAddress = recipentAddress;
//	}
//	public void setOrderCreateTime(String orderCreateTime) {
//		this.orderCreateTime = orderCreateTime;
//	}
//	public void setOrderPickupTime(String orderPickupTime) {
//		this.orderPickupTime = orderPickupTime;
//	}
//	public void setOrderDeliveryTime(String orderDeliveryTime) {
//		this.orderDeliveryTime = orderDeliveryTime;
//	}
//	public void setActive(Boolean active) {
//		this.active = active;
//	}
//	public void setPackageWeight(Float packageWeight) {
//		this.packageWeight = packageWeight;
//	}
//	public void setPackageHeight(Float packageHeight) {
//		this.packageHeight = packageHeight;
//	}
//	public void setIsFragile(Boolean isFragile) {
//		this.isFragile = isFragile;
//	}
//	public void setTotalCost(Float totalCost) {
//		this.totalCost = totalCost;
//	}
//	public Order build() {
//		return new Order(this);
//	}
//}
}