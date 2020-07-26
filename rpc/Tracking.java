package rpc;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;

import db.MySQLConnection;
import entity.DateUtil;
import external.GoogMatrixRequest;

/**
 * Servlet implementation class Tracking
 */
public class Tracking extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Tracking() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject input = RpcHelper.readJSONObject(request);
		String trackingId = input.getString("tracking_id");
		
		MySQLConnection connection = new MySQLConnection();
		String orderId = connection.getOrderId(trackingId);
		List<String> times = connection.getTimes(trackingId);

//		String machineId = connection.getMachineId(orderId);

		List<String> orderDetails = connection.getDetail(orderId);
		if (orderDetails == null) {
			throw new IllegalStateException("No order information found.");
		}
		String machineType = orderDetails.get(1);
		String senderAddr = orderDetails.get(4);
		String receiverAddr = orderDetails.get(8);
		LatLng senderLatLng = null;
		try {
			senderLatLng = GoogMatrixRequest.getLatLng(senderAddr);
		} catch (ApiException | InterruptedException | IOException e1) {
			e1.printStackTrace();
		}
		LatLng receiverLatLng = null;
		try {
			receiverLatLng = GoogMatrixRequest.getLatLng(receiverAddr);
		} catch (ApiException | InterruptedException | IOException e1) {
			e1.printStackTrace();
		}

//		String createTime = "2018-07-28 14:42:32";
//		String deliveredTime = "2018-07-29 12:26:32";
		JSONObject obj = new JSONObject();
//		DateUtil du = new DateUtil();
		if (times.size() == 0) {
			obj.put("alert", "Invalid tracking id!");
		} else {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = df.format(new Date());
				String createdTime = times.get(0);
				String deliveredTime = times.get(1);
//				int pickAfter;
//				if (machineType.equals("drone")) {;
//					pickAfter = (int)GoogMatrixRequest.getDirectDistance(stationAddr, enderAddr) / 50;
//				} else {
//					pickAfter = (int)GoogMatrixRequest.getBicyclingDistance(stationAddr, enderAddr) / 10;
//				}
				
				long pickedUpTime = DateUtil.addMins(createdTime, 30);// 创建订单后30分钟上门取货
				long transmitTime = pickedUpTime + 5 * 60000; // 取货后5分钟开始送货
				obj.put("pick up time", df.format(pickedUpTime));
				obj.put("pick up time", deliveredTime);
				String deliverStatus;
				if (DateUtil.getDistanceTime(deliveredTime, currentTime)) {
					deliverStatus = "Order delivered";
				} else if (DateUtil.getDistanceTime2(currentTime, transmitTime)) {
					deliverStatus = "Order out for delivery";
				} else if (DateUtil.getDistanceTime2(currentTime, pickedUpTime)) {
					deliverStatus = "Order picked up";
				} else {
					deliverStatus = "Order created";
				}
				obj.put("status", deliverStatus);
				connection.updateTimes(trackingId, deliverStatus, currentTime);

				try {
					double distRatio;
//					long start = df.parse(createdTime).getTime();
					long curr = df.parse(currentTime).getTime();
					long dest = df.parse(deliveredTime).getTime();

					long delivered = dest + 5 * 60000; // 到货后5分钟显示在目的地

					// don't consider 
//					if (curr < pickedUpTime) {
//						distRatio = (curr - start) / (pickedUpTime - start);
//						
//					}  
					if (pickedUpTime <= curr && curr <= transmitTime) {
						// show the location of the sender address for 5 minutes
						obj.put("currLocation", senderLatLng);
					} else if (curr < dest) {
						// show the location between sender and receiver
						distRatio = (float) (curr - transmitTime) / (float) (dest - transmitTime);
						if (machineType.equals("drone")) {
							obj.put("currLocation",
									GoogMatrixRequest.getNewLocation(senderAddr, receiverAddr, distRatio));
						} else {
							try {
								// test a near point
//								double[] a = { 37.4775252, -122.1460077 };

								// RoadApi
								// double [][] route = GoogMatrixRequest.getRoutePoints(senderLatLng,
								// receiverLatLng);

								// testing address
//								String add1 = "2310-2354 Folsom St, San Francisco, CA 94110";
//								String add2 = "1691 John F Kennedy Dr, San Francisco, CA 94121";

								List<LatLng> points = GoogMatrixRequest.getDirectionPoints(senderAddr, receiverAddr);
//								obj.put("currLocation1", senderLatLng);
//								obj.put("currLocation2", receiverLatLng);
								int currIndex = (int) (distRatio * points.size()) - 1;
								obj.put("currLocation", points.get(currIndex));
//							obj.put("index", currIndex);
							} catch (NullPointerException e) {
								obj.put("currLocation", "error");
							}
						}
					} else if (curr <= delivered) {
						// show the location of the recipient address for 5 minutes
						obj.put("currLocation", receiverLatLng);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		connection.close();
		RpcHelper.writeJsonObject(response, obj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
