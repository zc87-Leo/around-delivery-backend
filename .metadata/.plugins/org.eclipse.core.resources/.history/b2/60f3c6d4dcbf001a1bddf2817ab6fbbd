package external;

import java.io.IOException;

import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GoogMatrixRequest {
  private static final String API_KEY = "AIzaSyBdIJ5MrQ4rwNhyx52hGx2J3KDwftrGps0";
  private OkHttpClient client;
//  private String addrOne;
//  private String addrTwo;
//  private double weight;
//  private boolean mode;
  
//  public GoogMatrixRequest(String addrOne, String addrTwo, double weight, boolean mode) {
//	   this.client = new OkHttpClient();
//	   this.addrOne = addrOne;
//	   this.addrTwo = addrTwo;
//	   this.weight = weight;
//	   this.mode = mode;
//  }

  public String run(String url) throws IOException {
	  Request request = new Request.Builder()
			  .url(url)
			  .build();
	  okhttp3.Response response = client.newCall(request).execute();
	  // response.body().rows[0].elements[0].distance.value
	  System.out.println(response);
	  return response.body().string();
  }
  
  public static double getDriveDist(String addrOne, String addrTwo, boolean mode) throws ApiException, InterruptedException, IOException{
		
	  //set up key
	  GeoApiContext distCalcer = new GeoApiContext.Builder()
			  .apiKey(API_KEY)
			  .build();
	   	
	  DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer); 
	  TravelMode travel;
	  if (mode) {
		  travel = TravelMode.DRIVING;
	  } else {
		  travel = TravelMode.WALKING;
	  }
	  DistanceMatrix result = req.origins(addrOne)
			  .destinations(addrTwo)
	          .mode(travel)
	          .avoid(RouteRestriction.TOLLS)
	          .language("en-US")
	          .await();
	  double distApart = result.rows[0].elements[0].distance.inMeters * 0.000621371192;
	  return distApart;
  }

  public static double[][] getDistance(String addrOne, String addrTwo, double weight) throws IOException, ApiException, InterruptedException {
	  // method 1 drone
	  double[][] result = new double[2][2];
	  double droneDistance = getDriveDist(addrOne, addrTwo, true);
	  result[0][0] = droneDistance / 50;
	  result[0][1] = calculatePrice(weight, droneDistance, true);  
	  // method 2 robot
	  double robotDistance = getDriveDist(addrOne, addrTwo, false);
	  result[1][0] = robotDistance / 10;
	  result[1][1] = calculatePrice(weight, robotDistance, false);
	  
//	  GoogMatrixRequest request = new GoogMatrixRequest();
//	  String url_request = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Seattle&destinations=San+Francisco&mode=bicycling&language=en-GB&key=" + API_KEY;
//	  String response = request.run(url_request);
//	  long robotDistance = response.rows[0].elements[0].distance.value;
//	  System.out.println(calculatePrice(weight, robotDistance, false));
	  return result;
  }
  
  // price = base fare (surcharge) + distance/speed as constant * price per minute + weight * price per lb
  public static double calculatePrice(double weight, double distance, boolean mode) {
	  // from, to, weight, mode
	  double cost = 0.0;
	  
	  // query db to get data, assume we already have base price, additional rate
	  if (mode == true) {
		  // method 1 drone
		  cost = 5.99 + distance * weight * 0.5;
	  } else {
		  // method 2 robot
		  cost = 2.99 + distance * weight * 0.2;
	  }	  
	  // output
	  System.out.println("The shipping cost is " + cost);
	  return cost;
  }
}

