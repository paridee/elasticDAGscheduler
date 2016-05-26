package ExpectedSarsa;

import FibonacciBenchmark.MainClass;

public class QueueMonitor implements Runnable{

	public int 		interval	=	1000;
	public double 	weight		=	0.3;
	
	public QueueMonitor(int interval,double weight){
		super();
		this.interval	=	interval;
		this.weight		=	weight;
	}
	
	@Override
	public void run() {
		while(true){
			MainClass.queueSize	=	((1-this.weight)*MainClass.queueSize)+(this.weight*MainClass.queue.size());
			System.out.println("QueueMonitor: Estimated queue size "+MainClass.queueSize+" instant value "+MainClass.queue.size());
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
