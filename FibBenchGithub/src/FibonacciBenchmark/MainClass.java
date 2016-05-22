package FibonacciBenchmark;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ExpectedSarsa.EpsilonGreedyChooser;
import ExpectedSarsa.ExpectedSarsa;
import ExpectedSarsa.FibonacciActionExecutor;
import ExpectedSarsa.QueueLengthRewarder;
import ExpectedSarsa.QueueLengthStateReader;
import ExpectedSarsa.StaticAlphaCalculator;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.MetricsServlet;


public class MainClass {
	public static int 						concurrentThreads	=	4;
	public static int 						concurrencyLevel	=	1;
	public static int						seedingInterval		=	800;
	public static double					epsilonLevel		=	0.1;
	public static ReentrantLock				queueLock			=	new ReentrantLock();
	public static ArrayList<Integer>		queue				=	new ArrayList<Integer>();
	public static 	Gauge	thNumber	=	Gauge.build().name("bench_thLevel").help("Number of working threads").register();
	public static	Gauge	queueDim	=	Gauge.build().name("bench_queueDim").help("Number of queued elements").register();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  Server server = new Server(1234);
		  ServletContextHandler context = new ServletContextHandler();
		  context.setContextPath("/");
		  server.setHandler(context);
		  context.addServlet(new ServletHolder(new MetricsServlet()), "/metrics");
		  try {
			  server.start();
		  } catch (Exception e1) {
			  e1.printStackTrace();
		  }
		Seeder aSeeder		=	new Seeder(queue,seedingInterval,queueLock);
		Thread aThread		=	new Thread(aSeeder);
		aThread.start();
		//Decisor aDecisor	=	new Decisor(30000);
		//aThread			=	new Thread(aDecisor);
		//aThread.start();
		ExpectedSarsa	expSarsa	=	new ExpectedSarsa(3,concurrentThreads,0,new EpsilonGreedyChooser(epsilonLevel),new FibonacciActionExecutor(new QueueLengthRewarder()),new QueueLengthStateReader(7,13),new StaticAlphaCalculator());
		aThread	=	new Thread(expSarsa);
		aThread.start();
		for(int i=0;i<concurrentThreads;i++){
			FibonacciWorker		fw		=	new FibonacciWorker(i,queue,queueLock);
			aThread						=	new Thread(fw);
			aThread.start();
		}
	}

}
