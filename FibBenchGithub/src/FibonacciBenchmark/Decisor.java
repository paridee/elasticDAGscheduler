package FibonacciBenchmark;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ExpectedSarsa.SoftmaxPolicyChooser;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

public class Decisor implements Runnable {
	int interval;
	public final static Logger logger	=	LogManager.getLogger(Decisor.class);
	int counter	=	0;
	
	
	public Decisor(int interval){
		this.interval	=	interval;
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MainClass.concurrencyLevel	=	(counter%4)+1;
			MainClass.thNumber.set(MainClass.concurrencyLevel);
			logger.debug("Switched to "+MainClass.concurrencyLevel+" threads");
			counter++;
		}

	}

}
