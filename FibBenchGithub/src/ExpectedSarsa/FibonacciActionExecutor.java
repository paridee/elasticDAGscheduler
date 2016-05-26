package ExpectedSarsa;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import FibonacciBenchmark.MainClass;

public class FibonacciActionExecutor implements ActionExecutor{
	RewardCalculator rewardCalculator;
	public EvalIntervalManager evalIntManager;
	public final static Logger logger	=	LogManager.getLogger(FibonacciActionExecutor.class);
	
	public FibonacciActionExecutor(RewardCalculator calculator,EvalIntervalManager evalIntManager){
		super();
		this.rewardCalculator	=	calculator;
		this.evalIntManager		=	evalIntManager;
	}

	@Override
	public double execute(int action) {
		MainClass.concurrencyLevel	=	action+1;
		logger.debug("Set concurrency level to "+	MainClass.concurrencyLevel);
		MainClass.thNumber.set(MainClass.concurrencyLevel);
		try {
			Thread.sleep(evalIntManager.getInterval());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.rewardCalculator.giveReward();
	}

}
