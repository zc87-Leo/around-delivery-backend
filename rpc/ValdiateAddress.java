package rpc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.smartystreets.api.us_street.*;

import external.SmartyStreetsClient;

/**
 * Servlet implementation class ValdiateAddress
 */
public class ValdiateAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ValdiateAddress() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject input = RpcHelper.readJSONObject(request);
		String senderAddr = input.getString("senderAddr");
		String receiverAddr = input.getString("receiverAddr");

		// validate the addresses

		List<List<Candidate>> validateResult = SmartyStreetsClient.isValidAddress(senderAddr, receiverAddr);
		JSONObject obj = new JSONObject();
		
		if (validateResult.get(0).size() == 0 || validateResult.get(1).size() == 0) {
			if (validateResult.get(0).size() == 0) {
				obj.put("SenderAddrStatus", "Invalid");
			} else {
				obj.put("SenderAddrStatus", "Valid");
			}
			if (validateResult.get(1).size() == 0) {
				obj.put("ReceiverAddrStatus", "Invalid");
			} else {
				obj.put("ReceiverAddrStatus", "Valid");
			}
			RpcHelper.writeJsonObject(response, obj);
			return;
		}
		Candidate sendCandidate = validateResult.get(0).get(0);
		Candidate receiverCandidate = validateResult.get(1).get(0);
		Components sendComponent = sendCandidate.getComponents();
		Components receiveComponent = receiverCandidate.getComponents();

		String suggestSenderAddr = sendCandidate.getDeliveryLine1() + ", " + sendComponent.getCityName() + ", "
				+ sendComponent.getState() + " " + sendComponent.getZipCode() + "-" + sendComponent.getPlus4Code();
		String suggestReceiverAddr = receiverCandidate.getDeliveryLine1() + ", " + receiveComponent.getCityName() + ", "
				+ receiveComponent.getState() + " " + receiveComponent.getZipCode() + "-"
				+ receiveComponent.getPlus4Code();
		obj.put("SenderAddrStatus", "Valid");
		obj.put("SenderAddress", suggestSenderAddr);
		obj.put("ReceiverAddrStatus", "Valid");
		obj.put("ReceiverAddress", suggestReceiverAddr);
		RpcHelper.writeJsonObject(response, obj);
	}

}
