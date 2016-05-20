package FibonacciBenchmark;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

public class Decisor implements Runnable {
	int interval;
	
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
			System.out.println("Switched to "+MainClass.concurrencyLevel+" threads");
			counter++;
		}

	}

}
