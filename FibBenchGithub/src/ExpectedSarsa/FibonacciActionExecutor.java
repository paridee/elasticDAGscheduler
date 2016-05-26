package ExpectedSarsa;

import FibonacciBenchmark.MainClass;

public class FibonacciActionExecutor implements ActionExecutor{
	RewardCalculator rewardCalculator;
	public int evalInterval;
	
	public FibonacciActionExecutor(RewardCalculator calculator,int evalInterval){
		super();
		this.rewardCalculator	=	calculator;
		this.evalInterval		=	evalInterval;
	}

	@Override
	public double execute(int action) {
		MainClass.concurrencyLevel	=	action+1;
		System.out.println("Set concurrency level to "+	MainClass.concurrencyLevel);
		MainClass.thNumber.set(MainClass.concurrencyLevel);
		try {
			Thread.sleep(evalInterval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.rewardCalculator.giveReward();
	}

}
