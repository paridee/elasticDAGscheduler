package FibonacciBenchmark;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class Seeder implements Runnable{
	public ReentrantLock		queueLock;
	public ArrayList<Integer> 	queue;
	public int 					interval;
	public int 					currentInterval;
	public int 					refValue	=	27;
	public long					changeTime;
	int counter					=	0;
	
	public int generate(){	//generator to be substituted
		return (int)((Math.random()*10)+refValue);
		//return 42;
	}
	
	@Override
	public void run() {
		currentInterval	=	interval;
		this.changeTime	=	System.currentTimeMillis();	//level switching reference
		while(true){
			
			//level switching
			if(System.currentTimeMillis()-this.changeTime>120000){
				System.out.println("Seeder: switching reference value to "+(this.refValue+5));
				this.changeTime	=	System.currentTimeMillis();
				//this.refValue	=	this.refValue+5;
				if(refValue>35){
					refValue	=	20;
				}
			}
			//level switching end
			
			queueLock.lock();
			int value	=	generate();
			queue.add(value);
			//System.out.println("added element on queue "+value+" size "+MainClass.queue.size());
			MainClass.queueDim.set(MainClass.queue.size());
			queueLock.unlock();
			try {
				Thread.sleep(currentInterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*counter++;
			if(counter%10==0){
				currentInterval--;
				System.out.println("Interval decreased");
			}
			if(currentInterval==0){
				currentInterval	=	interval;
				System.out.println("Interval restored");
			}*/
		}
		
	}
	
	public Seeder(ArrayList<Integer> queue,int interval,ReentrantLock lock){
		this.queue		=	queue;
		this.interval	=	interval;
		this.queueLock	=	lock;
	}
	

}
