package ExpectedSarsa;

import FibonacciBenchmark.MainClass;

public class QueueLengthStateReader implements StateReader {

	@Override
	public int getCurrentState() {
		return MainClass.concurrencyLevel-1;
	}

}
