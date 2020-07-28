package schedule;

import java.io.IOException;
import com.google.maps.errors.ApiException;

public class MyTask implements Runnable {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int stationId = 1; stationId <= 3; stationId++) {
			Scheduler droneScheduler = new Scheduler(stationId, "drone");
			try {
				System.out.println("Station " + stationId + " + drone:");
				droneScheduler.dispatchOrders();
			} catch (ApiException | InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Scheduler robotScheduler = new Scheduler(stationId, "robot");
			try {
				System.out.println("Station " + stationId + " + robot:");
				robotScheduler.dispatchOrders();
			} catch (ApiException | InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//		System.out.println("Main thread: " + java.time.LocalDateTime.now());
//		
//		scheduledExecutorService.schedule(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("Second thread: " + java.time.LocalDateTime.now()); 
//				MySQLConnection connection = new MySQLConnection();
//				connection.updateStatusInTracking("1", "6116f318d08611eaa70aa0afbdea02d5");
//				connection.close();
//			}
//		}, 30, TimeUnit.SECONDS);
//		
//		scheduledExecutorService.shutdown();
		
	}
}
