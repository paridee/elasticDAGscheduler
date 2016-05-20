package FibonacciBenchmark;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class FibonacciWorker implements Runnable {
	int thId					=	0;
	ReentrantLock		queueLock;
	ArrayList<Integer>	queue;
	@Override
	public void run() {
		while(true){
			if(this.thId<MainClass.concurrencyLevel){
				int queueSize	=	queue.size();
				//System.out.println("lunghezza coda "+queueSize);
				this.queueLock.lock();
				if(this.queue.size()>0){
					int value	=	queue.get(0);
					queue.remove(0);
					this.queueLock.unlock();
					//long now	=	System.nanoTime();//test
					long fibResult	=	fibonacci(value);
					//long after	=	System.nanoTime();//test
					//double diff	=	(after-now)/1000000;
					//System.out.println("valore "+fibResult+"temp esecuzione "+diff);
				} else
					try {
							this.queueLock.unlock();
							Thread.sleep(10);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			}
			else{
				try {
					Thread.sleep(100);
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
    
    
	public FibonacciWorker(int i,ArrayList<Integer> aQueue,ReentrantLock queueLock){
		thId			=	i;
		this.queue		=	aQueue;
		this.queueLock	=	queueLock;
	}

}
