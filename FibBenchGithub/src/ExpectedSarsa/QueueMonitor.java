package ExpectedSarsa;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import FibonacciBenchmark.MainClass;

public class QueueMonitor implements Runnable{
	public final static Logger logger	=	LogManager.getLogger(QueueMonitor.class);
	public int 			timesPerInterval	=	1;
	public double 		weight				=	0.3;
	private EvalIntervalManager evalIntManager;
	
	public QueueMonitor(int timesPerInterval,double weight, EvalIntervalManager evalIntManager){
		super();
		this.timesPerInterval	=	timesPerInterval;
		this.weight				=	weight;
		this.evalIntManager		=	evalIntManager;
	}
	
	@Override
	public void run() {
		while(true){
			MainClass.queueSize	=	((1-this.weight)*MainClass.queueSize)+(this.weight*MainClass.queue.size());
			//logger.debug("Estimated queue size "+MainClass.queueSize+" instant value "+MainClass.queue.size());
			try {
				Thread.sleep((this.evalIntManager.getInterval())/this.timesPerInterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
