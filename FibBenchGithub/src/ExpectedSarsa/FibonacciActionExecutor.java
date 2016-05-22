package ExpectedSarsa;

import FibonacciBenchmark.MainClass;

public class FibonacciActionExecutor implements ActionExecutor{
	RewardCalculator rewardCalculator;
	
	public FibonacciActionExecutor(RewardCalculator calculator){
		this.rewardCalculator	=	calculator;
		
	}

	@Override
	public double execute(int action) {
		MainClass.concurrencyLevel	=	action+1;
		System.out.println("Set concurrency level to "+	MainClass.concurrencyLevel);
		MainClass.thNumber.set(MainClass.concurrencyLevel);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.rewardCalculator.giveReward();
	}

}
