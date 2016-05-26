package FibonacciBenchmark;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import ExpectedSarsa.EvalIntervalManager;


public class FibonacciWorker implements Runnable {
	int thId					=	0;
	ReentrantLock			queueLock;
	ArrayList<Integer>		queue;
	EvalIntervalManager 	evalIntManager;
	
	//testing
	public static int 		nCalculations=0;
	public static double	totaltime;
	ReentrantLock			counterTimeLock=new ReentrantLock();
	//fine testing
	
	public FibonacciWorker(int i,ArrayList<Integer> aQueue,ReentrantLock queueLock, EvalIntervalManager evalIntManager){
		super();
		this.evalIntManager	=	evalIntManager;
		thId				=	i;
		this.queue			=	aQueue;
		this.queueLock		=	queueLock;
	}
	
	@Override
	public void run() {
		while(true){
			if(this.thId<MainClass.concurrencyLevel){
				int queueSize	=	queue.size();
				//logger.debug("lunghezza coda "+queueSize);
				this.queueLock.lock();
				if(this.queue.size()>0){
					int value	=	queue.get(0);
					queue.remove(0);
					this.queueLock.unlock();
					long now	=	System.currentTimeMillis();//test
					long fibResult	=	fibonacci(value);
					long after	=	System.currentTimeMillis();//test
					double diff	=	(after-now);
					
					//testing
					this.counterTimeLock.lock();
					this.nCalculations++;
					this.totaltime	=	totaltime+diff;
					this.counterTimeLock.unlock();
					this.evalIntManager.evaluateRespTime(diff);
					//logger.debug("Average processing time "+this.totaltime/nCalculations);
					//finetesting
					//logger.debug("valore "+fibResult+"temp esecuzione "+diff);
				} else
					try {
							this.queueLock.unlock();
							Thread.sleep(this.evalIntManager.getInterval()/50);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			}
			else{
				try {
					Thread.sleep(this.evalIntManager.getInterval()/50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
	}
	
	
    public static long fibonacci(long i) {
	/* F(i) non e` definito per interi i negativi! */
	if (i == 0) return 0;
	else if (i == 1) return 1;
	else return fibonacci(i-1) + fibonacci(i-2);
    }
    
}
