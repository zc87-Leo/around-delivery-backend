package rpc;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.time.LocalDateTime;

import org.json.JSONObject;

import db.MySQLConnection;
import entity.Order;
import entity.UuidUtil;

/**
 * Servlet implementation class NewOrder
 */
public class NewOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewOrder() {
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
        //tracking & Order id & order create time (我们在后端生成的数据)
        String orderId = UuidUtil.generateShortUuid();
        String trackingId = UuidUtil.generateShortUuid();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderCreateTime= df.format(new Date());
        // initialize other attributes for newOrder（我们需要在前端request中获取的数据）
        String userId = "";
        String senderAddress = "";
        String recipentAddress = "";
        String orderPickupTime = "";
        String orderDeliveryTime = "";
        Boolean active = false;
        Float packageWeight = (float) 0.0;
        Float packageHeight = (float) 0.0;
        Boolean isFragile = false;
        Float totalCost = (float) 0.0;

        // get order info from JSON object via HTTP request
        JSONObject orderInfo = RpcHelper.readJSONObject(request);

        if (orderInfo.getString("userId") != null) {
            userId = orderInfo.getString("userId");
        }

        if (orderInfo.getString("senderAddress") != null) {
            senderAddress = orderInfo.getString("senderAddress");
        }

        if (orderInfo.getString("recipentAddress") != null) {
            recipentAddress = orderInfo.getString("recipentAddress");
        }

        active = orderInfo.getBoolean("active");

        if (orderInfo.getDouble("packageWeight") >= 0) {
            packageWeight = (float) orderInfo.getDouble("packageWeight");
        }

        if (orderInfo.getDouble("packageHeight") >= 0) {
            packageHeight = (float) orderInfo.getDouble("packageHeight");
        }

        isFragile = orderInfo.getBoolean("isFragile");


        if (orderInfo.getDouble("totalCost") >= 0) {
            totalCost = (float) orderInfo.getDouble("totalCost");
        }

        //Get current time in milliseconds
//        double dTDouble = Double.parseDouble(deliveryTime.substring(0,deliveryTime.length()-2));
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String currentTime= df.format(new Date());
//		Date cT = null;
//		try {
//			cT = df.parse(currentTime);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        long creatTimeInSecs = cT.getTime();
//        long deliveryTimeInSecs = (long) (creatTimeInSecs + dTDouble * 60000 * 60);
//        Timestamp tsDelivery = new Timestamp(deliveryTimeInSecs);
//        String deliveredAt = df.format(tsDelivery);
//        String createdAt = currentTime;
        // prepare time in milliseconds
//        Long prepareTime = 1L * 1000L * 60L * 60L;
//
//        //Double shippingTimeTemp = 2.40; // in hours
//        Double shippingTimeTemp = shippingTime * 60.00 * 60.00 * 1000.00;
//
//
//        // calculate pickup time and delivery time
//        Long pickupTimeInSecs = creatTimeInSecs + prepareTime;
//        Long deliveryTimeInSecs = creatTimeInSecs + Math.round(shippingTimeTemp);
//
//        // Convert TimeStamp to String and return to response
//        orderCreateTime = currentTime;
//
//        Timestamp tsPickup = new Timestamp(pickupTimeInSecs);
//        orderPickupTime = df.format(tsPickup);
//
//        Timestamp tsDelivery = new Timestamp(deliveryTimeInSecs);
//        orderDeliveryTime = df.format(tsDelivery);
//
        // create a new order via builder pattern
        Order newOrder = new Order();

        newOrder.setOrderId(orderId);
        newOrder.setTrackingId(trackingId);
        newOrder.setUserId(userId);
        newOrder.setSenderAddress(senderAddress);
        newOrder.setRecipentAddress(recipentAddress);
        newOrder.setOrderCreateTime(orderCreateTime);
        newOrder.setPackageWeight(packageWeight);
        newOrder.setPackageHeight(packageHeight);
        newOrder.setIsFragile(isFragile);
        newOrder.setTotalCost(totalCost);
        newOrder.setActive(active);

//        Order order = newOrder.build(); // will be stored in DB
//
//        // use UserId and SenderAddress to create a orderNum
//        String orderNumHash = userId + orderCreateTime;
//        Long newOrderId = Math.abs((long) orderNumHash.hashCode());
//
//        // generate newOrderInfo for confirmation page
//        JSONObject newOrderInfo = order.toJSONObject();
//
//        // return newOrder to confirmation page
//        if (newOrderId != null) {
//            newOrderInfo.put("orderId", newOrderId);
//
//            RpcHelper.writeJsonObject(response, newOrderInfo);
//        }

        MySQLConnection connection = new MySQLConnection();
        JSONObject obj = new JSONObject();
        if(connection.createOrder(newOrder)) {
            obj.put("status", "Order Created Successfully!").put("tracking id", trackingId);
        }else {
            obj.put("status", "Order Created Unsuccessfully!");
        }
        connection.close();
        RpcHelper.writeJsonObject(response, obj);
    }
}