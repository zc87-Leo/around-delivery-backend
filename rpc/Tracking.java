package rpc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.MySQLConnection;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String trackingId = request.getParameter("tracking_id");
		MySQLConnection connection = new MySQLConnection();
		List<Date> times = connection.getTimes(trackingId);
//		String createTime = "2018-07-28 14:42:32";
//		String deliveredTime = "2018-07-29 12:26:32";
		JSONObject obj = new JSONObject();
		try {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentTime = new Date();
			Date createdTime = times.get(0);
			Date deliveredTime = times.get(1);
			if(currentTime.compareTo(deliveredTime) <= 0) {
				obj.put("status","delivered!");
			}else {
				obj.put("status","created!");
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		connection.close();
		RpcHelper.writeJsonObject(response, obj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
package rpc;

		import java.io.IOException;
		import java.text.SimpleDateFormat;
		import java.util.Date;
		import java.util.List;

		import javax.servlet.ServletException;
		import javax.servlet.http.HttpServlet;
		import javax.servlet.http.HttpServletRequest;
		import javax.servlet.http.HttpServletResponse;

		import org.json.JSONObject;

		import db.MySQLConnection;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String trackingId = request.getParameter("tracking_id");
		MySQLConnection connection = new MySQLConnection();
		List<Date> times = connection.getTimes(trackingId);
//		String createTime = "2018-07-28 14:42:32";
//		String deliveredTime = "2018-07-29 12:26:32";
		JSONObject obj = new JSONObject();
		try {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentTime = new Date();
			Date createdTime = times.get(0);
			Date deliveredTime = times.get(1);
			if(currentTime.compareTo(deliveredTime) <= 0) {
				obj.put("status","delivered!");
			}else {
				obj.put("status","created!");
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		connection.close();
		RpcHelper.writeJsonObject(response, obj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
package rpc;

		import java.io.IOException;
		import java.text.SimpleDateFormat;
		import java.util.Date;
		import java.util.List;

		import javax.servlet.ServletException;
		import javax.servlet.http.HttpServlet;
		import javax.servlet.http.HttpServletRequest;
		import javax.servlet.http.HttpServletResponse;

		import org.json.JSONObject;

		import db.MySQLConnection;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String trackingId = request.getParameter("tracking_id");
		MySQLConnection connection = new MySQLConnection();
		List<Date> times = connection.getTimes(trackingId);
//		String createTime = "2018-07-28 14:42:32";
//		String deliveredTime = "2018-07-29 12:26:32";
		JSONObject obj = new JSONObject();
		try {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentTime = new Date();
			Date createdTime = times.get(0);
			Date deliveredTime = times.get(1);
			if(currentTime.compareTo(deliveredTime) <= 0) {
				obj.put("status","delivered!");
			}else {
				obj.put("status","created!");
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		connection.close();
		RpcHelper.writeJsonObject(response, obj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
package rpc;

		import java.io.IOException;
		import java.text.SimpleDateFormat;
		import java.util.Date;
		import java.util.List;

		import javax.servlet.ServletException;
		import javax.servlet.http.HttpServlet;
		import javax.servlet.http.HttpServletRequest;
		import javax.servlet.http.HttpServletResponse;

		import org.json.JSONObject;

		import db.MySQLConnection;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String trackingId = request.getParameter("tracking_id");
		MySQLConnection connection = new MySQLConnection();
		List<Date> times = connection.getTimes(trackingId);
//		String createTime = "2018-07-28 14:42:32";
//		String deliveredTime = "2018-07-29 12:26:32";
		JSONObject obj = new JSONObject();
		try {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentTime = new Date();
			Date createdTime = times.get(0);
			Date deliveredTime = times.get(1);
			if(currentTime.compareTo(deliveredTime) <= 0) {
				obj.put("status","delivered!");
			}else {
				obj.put("status","created!");
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		connection.close();
		RpcHelper.writeJsonObject(response, obj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
