package ExpectedSarsa;

import FibonacciBenchmark.MainClass;

public class QueueLengthRewarder implements RewardCalculator {

	//keeps 5 elements in queue, parabolic
	
	double a	=	-((double)1/10);
	double b	=	((double)2)/1;
	double c	=	((double)0)/1;//-((double)525);
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
