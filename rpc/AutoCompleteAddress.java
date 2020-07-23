package rpc;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.Candidate;


import external.AutoCompleteClient;

/**
 * Servlet implementation class AutoCompleteAddress
 */
public class AutoCompleteAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AutoCompleteAddress() {
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
		JSONObject input = RpcHelper.readJSONObject(request);
		String address = input.getString("address");

		// auto complete the address
		List<Candidate> suggestions = null;
		try {
			suggestions = AutoCompleteClient.completeAddress(address);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmartyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONArray array = new JSONArray();
		Set<String> exist = new HashSet<>();
		if (suggestions != null && suggestions.size() != 0) {
			for (Candidate suggestion : suggestions) {
				String streetAddress = suggestion.getDeliveryLine1();
				String zipCode = suggestion.getComponents().getZipCode() + "-" + suggestion.getComponents().getPlus4Code();
				if (!exist.contains(streetAddress + zipCode)) {
					array.put(new JSONObject().put("address", streetAddress).put("zipCode", zipCode));
					exist.add(streetAddress + zipCode);
				}
			}
		} 
		RpcHelper.writeJsonArray(response, array);
	}

}
