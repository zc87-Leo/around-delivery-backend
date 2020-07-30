package entity;

public class TrackingInfo {
	//status, created_at, estimated_delivered_at, delay, previous_destination, previous_destination_start_time
	
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getDeliveredAt() {
		return deliveredAt;
	}
	public void setDeliveredAt(String deliveredAt) {
		this.deliveredAt = deliveredAt;
	}
	public boolean isDelay() {
		return delay;
	}
	public void setDelay(boolean delay) {
		this.delay = delay;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getTransitStart() {
		return transitStart;
	}
	public void setTransitStart(String transitStart) {
		this.transitStart = transitStart;
	}
	private String createdAt;
	private String deliveredAt;
	private boolean delay;
	private String destination;
	private String transitStart;
	
	

}
