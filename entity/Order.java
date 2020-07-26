package entity;

public class Order {

	private String orderId;
	private String userId;
	private String trackingId;
	private String senderAddress;
	private String senderFirstName;
	private String senderLastName;
	private String senderPhoneNumber;
	private String senderEmail;
	private String recipientAddress;
	private String recipientFirstName;
	private String recipientLastName;
	private String recipientPhoneNumber;
	private String recipientEmail;

	private String orderCreateTime;
	private String orderPickupTime;
	private String orderDeliveryTime;

	private Boolean active;
	private Float packageWeight;
	private Float packageHeight;
	private Float packageLength;
	private Float packageWidth;
	private Boolean isFragile;
	private String carrier;
	private String deliveryTime;
	private Float totalCost;

//public Order(OrderBuilder builder) {
//	// TODO Auto-generated constructor stub
//	this.orderId = orderId;
//	this.userId = userId;
//	this.trackingId = trackingId;
//	this.senderAddress = senderAddress;
//	this.recipientAddress = recipientAddress;
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

	public String getRecipientAddress() {
		return recipientAddress;
	}

	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
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

	public String getSenderFirstName() {
		return senderFirstName;
	}

	public void setSenderFirstName(String senderFirstName) {
		this.senderFirstName = senderFirstName;
	}

	public String getSenderLastName() {
		return senderLastName;
	}

	public void setSenderLastName(String senderLastName) {
		this.senderLastName = senderLastName;
	}

	public String getSenderPhoneNumber() {
		return senderPhoneNumber;
	}

	public void setSenderPhoneNumber(String senderPhoneNumber) {
		this.senderPhoneNumber = senderPhoneNumber;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getRecipientFirstName() {
		return recipientFirstName;
	}

	public void setRecipientFirstName(String recipientFirstName) {
		this.recipientFirstName = recipientFirstName;
	}

	public String getRecipientLastName() {
		return recipientLastName;
	}

	public void setRecipientLastName(String recipientLastName) {
		this.recipientLastName = recipientLastName;
	}

	public String getRecipientPhoneNumber() {
		return recipientPhoneNumber;
	}

	public void setRecipientPhoneNumber(String recipientPhoneNumber) {
		this.recipientPhoneNumber = recipientPhoneNumber;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	public Float getPackageLength() {
		return packageLength;
	}

	public void setPackageLength(Float packageLength) {
		this.packageLength = packageLength;
	}

	public Float getPackageWidth() {
		return packageWidth;
	}

	public void setPackageWidth(Float packageWidth) {
		this.packageWidth = packageWidth;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

//public JSONObject toJSONObject() {
//	JSONObject obj = new JSONObject();
//	obj.put("order_id", orderId);
//	obj.put("user_id", userId);
//	obj.put("tracking_id", trackingId);
//	obj.put("sender_address", senderAddress);
//	obj.put("recipient_address", recipientAddress);
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
//	 private String recipientAddress;
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
//	public void setrecipientAddress(String recipientAddress) {
//		this.recipientAddress = recipientAddress;
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