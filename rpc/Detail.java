package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.MySQLConnection;

/**
 * Servlet implementation class Detail
 */
public class Detail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Detail() {
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
		// read the content in the request
		JSONObject input = RpcHelper.readJSONObject(request);
		String orderId = input.getString("order_id");
		
		// use the content to communicate with database
		MySQLConnection connection = new MySQLConnection();
		JSONObject obj = new JSONObject();
		List<String> details = connection.getDetail(orderId);
		List<String> cols = new ArrayList<String>();
		cols.add("total cost");
		cols.add("machine_type");
		cols.add("delivered_at");
		cols.add("sender_name");
		cols.add("sender_address");
		cols.add("sender_phone");
		cols.add("sender_email");
		cols.add("recipient_name");
		cols.add("recipient_address");
		cols.add("recipient_phone");
		cols.add("recipient_email");
		cols.add("package_weight");
		cols.add("package_height");
		cols.add("package_fragile");
		cols.add("package_length");
		cols.add("package_width");
		if (details.isEmpty()) {
			obj.put("alert", "Order Id Not Valid");
		}
		else {
			try {
				// put received information into the json object
				for (int i = 0; i < 16; i++) {
					obj.put(cols.get(i), details.get(i));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// close the connection and write outputs
		connection.close();
		RpcHelper.writeJsonObject(response, obj);
	}

}
