package external;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.RoadsApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.SnappedPoint;
import com.google.maps.model.TravelMode;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GoogMatrixRequest {
	// Earth's mean radius in meter
	private static final int R = 6378137;
	private static final String API_KEY = "AIzaSyBdIJ5MrQ4rwNhyx52hGx2J3KDwftrGps0";
//	private static final String API_KEY = "AIzaSyC91YIc2UnTLDQ8FX3sfgHce1lr1AWmyjY";
	// set up key
	private static final GeoApiContext distCalcer = new GeoApiContext.Builder().apiKey(API_KEY).build();

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
		Request request = new Request.Builder().url(url).build();
		okhttp3.Response response = client.newCall(request).execute();
		// response.body().rows[0].elements[0].distance.value
		System.out.println(response);
		return response.body().string();
	}

	public static double getBicyclingDistance(String addrOne, String addrTwo)
			throws ApiException, InterruptedException, IOException {

		DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
		TravelMode travel;

		travel = TravelMode.BICYCLING;

		DistanceMatrix result = req.origins(addrOne).destinations(addrTwo).mode(travel).avoid(RouteRestriction.TOLLS)
				.language("en-US").await();
		double distApart = result.rows[0].elements[0].distance.inMeters * 0.000621371192;
		return distApart;
	}

	public static double getDirectDistance(String senderAddr, String receiverAddr)
			throws ApiException, InterruptedException, IOException {

		GeocodingResult[] resultOrigin = GeocodingApi.geocode(distCalcer, senderAddr).await();
		GeocodingResult[] resultDestination = GeocodingApi.geocode(distCalcer, receiverAddr).await();
		double lat1 = resultOrigin[0].geometry.location.lat;
		double lng1 = resultOrigin[0].geometry.location.lng;
		double lat2 = resultDestination[0].geometry.location.lat;
		double lng2 = resultDestination[0].geometry.location.lng;

		double dlng = Radians(lng2 - lng1);
		double dlat = Radians(lat2 - lat1);

		double a = (Math.sin(dlat / 2) * Math.sin(dlat / 2))
				+ Math.cos(Radians(lat1)) * Math.cos(Radians(lat2)) * (Math.sin(dlng / 2) * Math.sin(dlng / 2));
		double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		// return the distance in miles
		return R * angle * 0.000621371192;
	}
	
	
	/**
	 * Convert the address from String to lat/lng double 
	 * 
	 * @param address
	 * @return
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static double[] getLatLngDouble(String address) throws ApiException, InterruptedException, IOException {
		double[] latLng = new double[2];
		latLng[0] = getLatLng(address).lat;
		latLng[1] = getLatLng(address).lng;
		return latLng;
	}
	
	/**
	 * Convert the address from String to LatLng 
	 * 
	 * @param address
	 * @return
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static LatLng getLatLng(String address) throws ApiException, InterruptedException, IOException {
		GeocodingResult[] result = GeocodingApi.geocode(distCalcer, address).await();
		LatLng latLng = result[0].geometry.location;
		return latLng;
	}

	/**
	 * Calculate the current location based on the complete ratio of the total distance from sender and receiver. 
	 * 
	 * @param senderAddr
	 * @param receiverAddr
	 * @param ratio 		
	 * @return
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static double[] getNewLocation(String senderAddr, String receiverAddr, double ratio)
			throws ApiException, InterruptedException, IOException {
		double[] newLatLng = new double[2];
		GeocodingResult[] resultOrigin = GeocodingApi.geocode(distCalcer, senderAddr).await();
		GeocodingResult[] resultDestination = GeocodingApi.geocode(distCalcer, receiverAddr).await();
		double lat1 = resultOrigin[0].geometry.location.lat;
		double lng1 = resultOrigin[0].geometry.location.lng;
		double lat2 = resultDestination[0].geometry.location.lat;
		double lng2 = resultDestination[0].geometry.location.lng;
		double dlng = Radians(lng2 - lng1);
		double dlat = Radians(lat2 - lat1);

		double a = (Math.sin(dlat / 2) * Math.sin(dlat / 2))
				+ Math.cos(Radians(lat1)) * Math.cos(Radians(lat2)) * (Math.sin(dlng / 2) * Math.sin(dlng / 2));
		double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		// distance in km
		double wholeDistance = R * angle;

		double dx = ratio * wholeDistance * Math.sin(angle);
		double dy = ratio * wholeDistance * Math.cos(angle);

		double curr_lat = lat1 + Radians(dy / R);
		double curr_log = lng1 + Radians(dx / R) / Math.cos(Radians(lat1));
		newLatLng[0] = curr_lat;
		newLatLng[1] = curr_log;
		// convert lat/lng to formatted address, don't need for now
		// String currAddress = GeocodingApi.reverseGeocode(distCalcer, new
		// LatLng(curr_lat, curr_log)).await()[0].formattedAddress;

		return newLatLng;
	}

	/**
	 * Interpolate the route points between sender and receiver location using Road Api. 
	 * The performance is acceptable when the distance of points in the path is less than 300m so we don't use it here.  
	 * 
	 * @param sender   	latitude and longitude of the sender location 
	 * @param receiver  latitude and longitude of the receiver location
	 * @return
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static double[][] getRoutePoints(double[] sender, double[] receiver)
			throws ApiException, InterruptedException, IOException {

		LatLng[] path = new LatLng[] { new LatLng(sender[0], sender[1]), new LatLng(receiver[0], receiver[1]) };
		System.out.print(sender[0]);
		SnappedPoint[] points = RoadsApi.snapToRoads(distCalcer, true, path).await();
		int n = points.length;
		double[][] result = new double[n][2];

		for (int i = 0; i < n; i++) {
			result[i][0] = points[i].location.lat;
			result[i][1] = points[i].location.lng;
		}
		return result;
	}

	/**
	 * Get the location points on the route from senderAdd to receiverAddr, using Direction Api.
	 * 
	 * @param senderAddr
	 * @param receiverAddr
	 * @return
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static List<LatLng> getDirectionPoints(String senderAddr, String receiverAddr)
			throws ApiException, InterruptedException, IOException {
		DirectionsResult result = DirectionsApi.newRequest(distCalcer).mode(TravelMode.BICYCLING).origin(senderAddr)
				.destination(receiverAddr).await();
		DirectionsStep[] res = result.routes[0].legs[0].steps;
//		System.out.println(res.length);
		List<LatLng> pointsLatLngs = new ArrayList<>();
		for (int i = 0; i < res.length; i++) {
			pointsLatLngs.add(res[i].startLocation);
			System.out.println(res[i].startLocation);
		}
		return pointsLatLngs;
	}

//    .avoid(
//    DirectionsApi.RouteRestriction.HIGHWAYS,
//    DirectionsApi.RouteRestriction.TOLLS,
//    DirectionsApi.RouteRestriction.FERRIES)

	private static double Radians(double x) {
		return x * Math.PI / 180;
	}

	public static double[][] getDistance(String senderAddr, String receiverAddr, double weight)
			throws IOException, ApiException, InterruptedException {
		// method 1 drone
		double[][] result = new double[2][2];
		double droneDistance = getDirectDistance(senderAddr, receiverAddr);
		result[0][0] = droneDistance / 50;
		result[0][1] = calculatePrice(weight, droneDistance, true);
		// method 2 robot
		double robotDistance = getBicyclingDistance(senderAddr, receiverAddr);
		result[1][0] = robotDistance / 10;
		result[1][1] = calculatePrice(weight, robotDistance, false);

//	  GoogMatrixRequest request = new GoogMatrixRequest();
//	  String url_request = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Seattle&destinations=San+Francisco&mode=bicycling&language=en-GB&key=" + API_KEY;
//	  String response = request.run(url_request);
//	  long robotDistance = response.rows[0].elements[0].distance.value;
//	  System.out.println(calculatePrice(weight, robotDistance, false));
		return result;
	}

	// price = base fare (surcharge) + distance/speed as constant * price per minute
	// + weight * price per lb
	public static double calculatePrice(double weight, double distance, boolean mode) {
		// from, to, weight, mode
		double cost = 0.0;

		// query db to get data, assume we already have base price, additional rate
		if (mode == true) {
			// method 1 drone
			cost = 3.99 + distance * weight * 0.4;
		} else {
			// method 2 robot
			cost = 1.99 + distance * weight * 0.2;
		}
		if (mode) {
			System.out.println("Vehicle: Drone; The shipping cost: " + cost + "; The distance: " + distance);
		} else {
			System.out.println("Vehicle: Robot; The shipping cost: " + cost + "; The distance: " + distance);
		}

		return cost;
	}
}