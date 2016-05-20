package ExpectedSarsa;

import FibonacciBenchmark.MainClass;

public class QueueLengthRewarder implements RewardCalculator {

	//keeps 5 elements in queue, parabolic
	
	double a	=	-((double)-1/16);
	double b	=	((double)25)/2;
	double c	=	-((double)525);
	@Override
	public double giveReward() {
		int x			=	MainClass.queue.size();
		double firstpart	=	a*x*x;
		double secondpart	=	b*x;
		double reward	=	firstpart+secondpart+c;
		System.out.println("Reward calculated for queue size "+x+": "+reward+"("+firstpart+"+"+secondpart+",a="+a+" b="+b+")");
		return reward;
	}

}
