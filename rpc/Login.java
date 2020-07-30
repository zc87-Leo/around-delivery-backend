package rpc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.MySQLConnection;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
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
		String userId = input.getString("user_id");
		String password = input.getString("password");
		MySQLConnection connection = new MySQLConnection();
		JSONObject obj = new JSONObject();
		if (connection.verifyLogin(userId, password)) {
			List<String> name= connection.getFullname(userId);
			obj.put("status", "OK").put("user_id", userId).put("first_name", name.get(0)).put("last_name", name.get(1));
			List<String> user = connection.getUserProfiles(userId);
			if (user != null & user.size() > 0) {
				obj.put("email_address", user.get(0));
				obj.put("phone_number", user.get(1));
				obj.put("primary_address", user.get(2));
			}
		} else {
			obj.put("status", "User Doesn't Exist");
			response.setStatus(401);
		}
		connection.close();
		RpcHelper.writeJsonObject(response, obj);
	}
}
