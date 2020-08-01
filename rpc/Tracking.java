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
import entity.TrackingInfo;
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
		JSONObject obj = new JSONObject();
		String trackingId = input.getString("tracking_id");

		MySQLConnection connection = new MySQLConnection();
		String orderId = connection.getOrderId(trackingId);
		TrackingInfo trackingInfo = connection.getTrackingInfo(trackingId);
		List<String> orderDetails = connection.getDetail(orderId);
		StationAndMachineInfo smi = connection.getStationAndMachineInfo;

		String createdTime = "";
		String deliverStatus = "";
		String deliveredTime = "";
		String transitTime = "";
		String senderAddr = "";
		String receiverAddr = "";
		boolean delay = false;
		String machineType = "";
		
//		LatLng senderLatLng = null;
		LatLng receiverLatLng = null;
		LatLng currLocation = null;

		String machineType = "";
		int stationId = -1;
		String stationAddress = "";
		double lon = 0.0;
		double lat = 0.0;

		if(smi != null){
			stationId = smi.getStationId();
			obj.put("station id", stationId);
			machineType = smi.getMachineType();
			obj.put("machine type", machineType);
			stationAddress = smi.getStationAddress();
			obj.put("stationAdress",stationAddress);
			lon = smi.getLon();
			obj.put("lon",lon);
			lat = smi.getLat();
			obj.put("lat",lat);
		}

		if (trackingInfo != null && orderDetails != null) {

			createdTime = trackingInfo.getCreatedAt();
			deliverStatus = trackingInfo.getStatus();
			delay = trackingInfo.isDelay();
			deliveredTime = trackingInfo.getDeliveredAt();
			transitTime = trackingInfo.getTransitStart();
			senderAddr = trackingInfo.getDestination();

			receiverAddr = orderDetails.get(8);
			machineType = orderDetails.get(1);
		}
		obj.put("created time", createdTime);
//		try {
//			senderLatLng = GoogMatrixRequest.getLatLng(senderAddr);
//		} catch (ApiException | InterruptedException | IOException e1) {
//			e1.printStackTrace();
//		}
//		
		try {
			receiverLatLng = GoogMatrixRequest.getLatLng(receiverAddr);
		} catch (ApiException | InterruptedException | IOException e1) {
			e1.printStackTrace();
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = df.format(new Date());
		
		try {
			double distRatio;
			if (deliverStatus.contentEquals("in transit")) {
				long curr = df.parse(currentTime).getTime();
				long start = df.parse(transitTime).getTime();
				long dest = df.parse(deliveredTime).getTime();
				if (curr >= start && curr <= dest) {
					distRatio = (float) (curr - start) / (float) (dest - start);
					System.out.println(distRatio);
					if (machineType.equals("drone")) {
						currLocation = GoogMatrixRequest.getNewLocation(senderAddr, receiverAddr, distRatio);
					} else {
						try {
							List<LatLng> points = GoogMatrixRequest.getDirectionPoints(senderAddr, receiverAddr);
							int currIndex = Math.max(0,(int) (distRatio * points.size()) - 1);
							currLocation = points.get(currIndex);
							System.out.println(points.size());
						} catch (NullPointerException e) {}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		obj.put("status", deliverStatus).put("delay", delay).put("estimated delivered time", deliveredTime).put("destination", receiverLatLng);
		if (currLocation == null) {
			obj.put("current location", org.json.JSONObject.NULL);
		}
		else {
			obj.put("current location", currLocation);
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
