package ExpectedSarsa;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import FibonacciBenchmark.MainClass;

public class QueueLengthRewarder implements RewardCalculator {
	public final static Logger logger	=	LogManager.getLogger(QueueLengthRewarder.class);
	//keeps 5 elements in queue, parabolic
	
	double a	=	-((double)4/9);
	double b	=	((double)80)/3;
	double c	=	-((double)300)/1;//-((double)525);
	@Override
	public double giveReward() {
		double x			=	MainClass.queueSize;
		double firstpart	=	a*x*x;
		double secondpart	=	b*x;
		double reward	=	firstpart+secondpart+c;
		logger.debug("Reward calculated for queue size "+x+": "+reward+"("+firstpart+"+"+secondpart+",a="+a+" b="+b+")");
		return reward;
	}

}
