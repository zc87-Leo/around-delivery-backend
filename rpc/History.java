package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.MySQLConnection;

/**
 * Servlet implementation class History
 */
public class History extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public History() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// read content in the request 
		JSONObject input = RpcHelper.readJSONObject(request);
		String userId = input.getString("user_id");
		
		// use the content to communicate with database
		MySQLConnection connection = new MySQLConnection();
		JSONArray arr = new JSONArray();
		List<String> items = connection.getHistory(userId);
		if (items.size() == 0) {
			arr.put(new JSONObject().put("alert", "order id not valid"));
		}
		else {
			try {
				while (!items.isEmpty()) {
					// aggregate result of one history item
					JSONObject cur = new JSONObject();
					List<String> cols = new ArrayList<String>();
					cols.add("Order ID");
					cols.add("Order Status");
					cols.add("Recipient");
					cols.add("Delivery Address");
					cols.add("Delivery Time");
					cols.add("Order Date");
					cols.add("Tracking ID");
					for (int i = 0; i < 7; i++) {
						cur.put(cols.get(i), items.get(0));
						items.remove(0);
					}
					// put the current array json object into the json array
					arr.put(cur);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// close database connection and return response object
		connection.close();
		RpcHelper.writeJsonArray(response, arr);
	}

}
