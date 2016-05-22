package ExpectedSarsa;

import FibonacciBenchmark.MainClass;

public class QueueLengthStateReader implements StateReader {

	int lowThresh	=	0;
	int highThresh	=	0;
	
	public QueueLengthStateReader(int lowThresh, int highThresh) {
		super();
		this.lowThresh = lowThresh;
		this.highThresh = highThresh;
	}


	@Override
	public int getCurrentState() {
		if(MainClass.queue.size()<lowThresh){
			return 0;
		}
		else if(MainClass.queue.size()>highThresh){
			return 2;
		}
		return 1;
	}

}
