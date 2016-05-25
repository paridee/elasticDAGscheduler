package FibonacciBenchmark;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ExpectedSarsa.EpsilonGreedyChooser;
import ExpectedSarsa.EpsilonGreedyVBDEChooser;
import ExpectedSarsa.ExpectedSarsa;
import ExpectedSarsa.FibonacciActionExecutor;
import ExpectedSarsa.QueueLengthRewarder;
import ExpectedSarsa.QueueLengthStateReader;
import ExpectedSarsa.SoftmaxPolicyChooser;
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
	public static 	Gauge			thNumber	=	Gauge.build().name("bench_thLevel").help("Number of working threads").register();
	public static	Gauge			queueDim	=	Gauge.build().name("bench_queueDim").help("Number of queued elements").register();
	public static	Gauge			rewardVal	=	Gauge.build().name("bench_rewardVal").help("Reward received value").register();
	public static	Gauge.Child[][]	qMatrix;
	public static 	int statesN		=	3;
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
		
		
		qMatrix			=	new Gauge.Child[statesN][concurrentThreads];
		String[] labels	=	new String[2];
		labels[0]		=	"row";
		labels[1]		=	"column";
		Gauge q			=	Gauge.build().name("QValue").help("Value of Q matrix in position").labelNames(labels).register();
		for(int i=0;i<statesN;i++){// metrics, 3 == number of states
			for(int j=0;j<concurrentThreads;j++){
				String[] labelst	=	new String[2];
				labelst[0]		=	i+"";
				labelst[1]		=	j+"";
				qMatrix[i][j]	=	new Gauge.Child();
				q.setChild(qMatrix[i][j], labelst);
			}
		}
		
		
		ExpectedSarsa	expSarsa	=	new ExpectedSarsa(statesN,concurrentThreads,0,new EpsilonGreedyVBDEChooser(100.0),new FibonacciActionExecutor(new QueueLengthRewarder()),new QueueLengthStateReader(15,45),new StaticAlphaCalculator());
		aThread	=	new Thread(expSarsa);
		aThread.start();
		for(int i=0;i<concurrentThreads;i++){
			FibonacciWorker		fw		=	new FibonacciWorker(i,queue,queueLock);
			aThread						=	new Thread(fw);
			aThread.start();
		}
	}

}
