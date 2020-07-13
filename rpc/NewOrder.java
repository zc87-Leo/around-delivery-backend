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
import entity.Order.OrderBuilder;
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
        //tracking/Order id
        String trackingId = UuidUtil.generateShortUuid();

//        // initialize attributes for newOrder
//        String userId = "";
//        String senderAddress = "";
//        String recipentAddress = "";
//        String stationId = "";
//        String machineId = "";
// 
//        // package info
//        Float packageWeight = null;
//        Float packageHeight = null;
//        Boolean isFragile = null;
//        Float totalCost = null;
// 
//        // Order Shipping time.
//        Double shippingTime = null;

        // Order Create time
        String orderCreateTime = "";
//        String orderPickupTime = "";
        String deliveryTime = "";

        // get order info from JSON object via HTTP request
        JSONObject preOrderInfo = RpcHelper.readJSONObject(request);

//        if (preOrderInfo.getString("userId") != null) {
//            userId = preOrderInfo.getString("userId");
//        }
// 
//        if (preOrderInfo.getString("senderAddress") != null) {
//            senderAddress = preOrderInfo.getString("senderAddress");
//        }
// 
//        if (preOrderInfo.getString("recipentAddress") != null) {
//            recipentAddress = preOrderInfo.getString("recipentAddress");
//        }
// 
//        if (preOrderInfo.getString("stationId") != null) {
//            stationId = preOrderInfo.getString("stationId");
//        }
// 
//        if (preOrderInfo.getString("machineId") != null) {
//            machineId = preOrderInfo.getString("machineId");
//        }
// 
//        if (preOrderInfo.getString("packageWeight") != null) {
//            packageWeight = Float.valueOf(preOrderInfo.getString("packageWeight"));
//        }
// 
//        if (preOrderInfo.getString("packageHeight") != null) {
//            packageHeight = Float.valueOf(preOrderInfo.getString("packageHeight"));
//        }
// 
//        if (preOrderInfo.getString("isFragile") != null) {
//            isFragile = Boolean.valueOf(preOrderInfo.getString("isFragile"));
//        }
// 
//        if (preOrderInfo.getString("totalCost") != null) {
//            totalCost = Float.valueOf(preOrderInfo.getString("totalCost"));
//        }
// 
//        if (preOrderInfo.getString("shippingTime") != null) {
//            shippingTime = Double.valueOf(preOrderInfo.getString("shippingTime"));
//        }
        if(preOrderInfo.getString("deliveryTime") != null) {
            deliveryTime = preOrderInfo.getString("deliveryTime");
        }
        //Get current time in milliseconds
        double dTDouble = Double.parseDouble(deliveryTime.substring(0,deliveryTime.length()-2));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime= df.format(new Date());
        Date cT = null;
        try {
            cT = df.parse(currentTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long creatTimeInSecs = cT.getTime();
        long deliveryTimeInSecs = (long) (creatTimeInSecs + dTDouble * 60000 * 60);
        Timestamp tsDelivery = new Timestamp(deliveryTimeInSecs);
        String deliveredAt = df.format(tsDelivery);
        String createdAt = currentTime;
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
        MySQLConnection connection = new MySQLConnection();
        JSONObject obj = new JSONObject();
        if(connection.addTrackingInfo(trackingId, createdAt, deliveredAt)) {
            obj.put("status", "Order Created Successfully!").put("tracking id", trackingId);
        }else {
            obj.put("status", "Order Created Unsuccessfully!");
        }
        connection.close();
        RpcHelper.writeJsonObject(response, obj);

//        // create a new order via builder pattern
//        OrderBuilder newOrder = new OrderBuilder();
// 
//        newOrder.setUserId(userId);
//        newOrder.setSenderAddress(senderAddress);
//        newOrder.setRecipentAddress(recipentAddress);
//        newOrder.setOrderCreateTime(orderCreateTime);
//        newOrder.setOrderPickupTime(orderPickupTime);
//        newOrder.setOrderDeliveryTime(orderDeliveryTime);
//        newOrder.setPackageWeight(packageWeight);
//        newOrder.setPackageHeight(packageHeight);
//        newOrder.setIsFragile(isFragile);
//        newOrder.setTotalCost(totalCost);
//        newOrder.setShippingTime(shippingTime);
// 
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
// 
    }

}