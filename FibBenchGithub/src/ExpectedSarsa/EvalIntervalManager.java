package ExpectedSarsa;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import FibonacciBenchmark.MainClass;

public class EvalIntervalManager {
	private double 	worstCase	=	10;
	private int 	expiration	=	100;
	private int 	counter			=	0;
	public final static Logger logger	=	LogManager.getLogger(EvalIntervalManager.class);

	public EvalIntervalManager(int expiration){
		super();
		this.expiration	=	expiration;
	}
	
	public void evaluateRespTime(double time){
		counter++;
		if(time>this.worstCase||counter>expiration){
			logger.debug("EvalIntervalManager set worst case to "+time);
			this.worstCase	=	time;
			counter			=	0;
			MainClass.evalInterval.set(this.worstCase*3);
		}
	}
	public double getWorstCase(){
		return this.worstCase;
	}

	public int getInterval() {
		return (int)this.worstCase*3;
	}
}
