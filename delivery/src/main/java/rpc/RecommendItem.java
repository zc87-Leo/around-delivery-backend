//testing

package rpc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import com.smartystreets.api.us_street.*;

import external.GoogMatrixRequest;
import external.SmartyStreetsClient;

/**
 * Servlet implementation class RecommendItem
 */
public class RecommendItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecommendItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		GeoApiContext context = new GeoApiContext.Builder().apiKey("YOUR API KEY").build();
		GeocodingResult[] results = null;
		try {
			results = GeocodingApi.geocode(context, "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(results[0].addressComponents));
		JSONObject obj = new JSONObject();
		obj.put("result", gson.toJson(results[0].addressComponents));
		RpcHelper.writeJsonObject(response, obj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject input = RpcHelper.readJSONObject(request);
		String oneAddr = input.getString("oneAddr");
		String twoAddr = input.getString("twoAddr");
		double weight = input.getDouble("weight");

		// validate the addresses
		boolean isSendAddrValid = true;
		boolean isReceiveAddrValid = true;
		List<List<Candidate>> validateResult = SmartyStreetsClient.isValidAddress(oneAddr, twoAddr);
		JSONObject obj = new JSONObject();
		for (int i = 0; i < validateResult.size(); i++) {
			if (i == 0 && validateResult.get(i).size() == 0) {
				isSendAddrValid = false;
			}
			if (i == 1 && validateResult.get(i).size() == 0) {
				isReceiveAddrValid = false;
			}
		}
		if (!isSendAddrValid || !isReceiveAddrValid) {
			if (!isSendAddrValid && !isReceiveAddrValid) {
				obj.put("Sender address is", " invalid");
				obj.put("Receiver address is", " invalid");
			} else if (!isSendAddrValid) {
				obj.put("Sender address is ", "invalid");
				obj.put("Receiver address is", "valid");
			} else {
				obj.put("Sender address is ", "valid");
				obj.put("Receiver address is", "invalid");
			}
			RpcHelper.writeJsonObject(response, obj);
			return;
		}
		
		Candidate sendCandidate = validateResult.get(0).get(0);
		Candidate receiverCandidate = validateResult.get(1).get(0);
		
		Components sendComponent = sendCandidate.getComponents();
		Components receiveComponent = receiverCandidate.getComponents();
		String suggestSenderAddr = sendComponent.getPrimaryNumber() + " " + sendComponent.getStreetName() + ", " + 
				   sendComponent.getCityName() + ", " + sendComponent.getState() + " " + 
				   sendComponent.getZipCode() + "-" + sendComponent.getPlus4Code();
		String suggestReceiverAddr = receiveComponent.getPrimaryNumber() + " " + receiveComponent.getStreetName() + ", " + 
				receiveComponent.getCityName() + ", " + receiveComponent.getState() + " " + 
				receiveComponent.getZipCode() + "-" + receiveComponent.getPlus4Code();
		obj.put("Suggested sender address", suggestSenderAddr);
		obj.put("Suggested receiver address", suggestReceiverAddr);

		double[][] result = new double[2][2];
		try {
			result = GoogMatrixRequest.getDistance(oneAddr, twoAddr, weight);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		obj.put("Drone Estimated Delivery Time (fastest)", result[0][0]);
		obj.put("Drone Price (fastest)", result[0][1]);
		obj.put("Robot Estimated Delivery Time (cheapest)", result[1][0]);
		obj.put("Robot Price (cheapest)", result[1][1]);
		RpcHelper.writeJsonObject(response, obj);
	}
}
