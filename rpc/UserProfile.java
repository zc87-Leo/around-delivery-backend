package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.MySQLConnection;
import entity.User;

/**
 * Servlet implementation class UserProfile
 */
public class UserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserProfile() {
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
		// read the json body of the http request
		JSONObject userInfo = RpcHelper.readJSONObject(request);
		
		// decouple the body to individual elements
		String user_id = userInfo.getString("user_id");
		String email = userInfo.getString("email");
		String[] name = userInfo.getString("name").split(" ");
		String first_name = name[0];
		String last_name = name[1];
		String phone = userInfo.getString("phoneNumber");
		String address = userInfo.getString("primaryAddress") + " " + userInfo.getString("zipCode");
		
		// create a User object using the parameters above
		User user = new User();
		user.setAddress(address);
		user.setEmail(email);
		user.setFirst_name(first_name);
		user.setLast_name(last_name);
		user.setPhone(phone);
		user.setUser_id(user_id);
		
		// communicate with database
		MySQLConnection connection = new MySQLConnection();
		JSONObject obj = new JSONObject();
		if (connection.updateProfile(user)) {
			obj.put("updated", "true");
		}
		else {
			obj.put("updated", "false");
		}
		connection.close();
		RpcHelper.writeJsonObject(response, obj);
	}
}
