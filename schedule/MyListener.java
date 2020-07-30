package schedule;


import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyListener implements ServletContextListener {
	
	private ScheduledExecutorService scheduler;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new MyTask(), 0, 120, TimeUnit.SECONDS);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("shut down");
		scheduler.shutdownNow();
	}
}
