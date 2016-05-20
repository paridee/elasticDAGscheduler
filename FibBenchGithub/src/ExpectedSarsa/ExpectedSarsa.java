package ExpectedSarsa;

import java.text.DecimalFormat;

public class ExpectedSarsa implements Runnable{
	int states				=	1;
	int actions				=	1;
	int	evalInterval		=	1000;
	int currentState		=	0;
	double yotaParameter	=	0.9;
	double[] V;
	double[][] Q;
	PolicyChooser 	policy;
	ActionExecutor 	executor;
	StateReader		stateReader;
	AlphaCalculator	alphaCalculator;
	
	public ExpectedSarsa(int states, int actions,int initialState,PolicyChooser chooser,ActionExecutor actionExecutor,StateReader stateReader,AlphaCalculator alphaCalculator){
		V	=	new double[states];
		Q	=	new double[states][actions];
		this.states				=	states;
		this.actions			=	actions;
		this.policy				=	chooser;
		this.executor			=	actionExecutor;
		this.stateReader		=	stateReader;
		this.alphaCalculator	=	alphaCalculator;
		currentState	=	0;
		for(int i=0;i<states;i++){
			V[i]	=	0;
			for(int j=0;j<actions;j++){
				Q[i][j]	=	0;
			}
		}
	}

	@Override
	public void run() {
		System.out.println("Start Expected Sarsa Algorithm");
		currentState		=	this.stateReader.getCurrentState();
		System.out.println("State initialization "+currentState);
		while(true){
			int action			=	this.policy.actionForState(currentState,Q);
			System.out.println("Action chosen "+action);	//test
			double reward		=	this.executor.execute(action);
			System.out.println("Reward obtained "+reward);	//test
			int oldState		=	this.currentState;
			int newState		=	this.stateReader.getCurrentState();
			this.currentState	=	newState;
			double[] policy		=	this.policy.policyForState(newState,Q);
			
			System.out.print("Policy for state "+newState+":");	//test
			for(int i=0;i<policy.length;i++){
				System.out.print("Act:"+i+":"+policy[i]);
			}
			System.out.println("");								//endtest
			
			double temp			=	0;
			for(int i=0;i<actions;i++){
				temp			=	temp	+	(policy[i]*Q[newState][i]);
			}
			V[newState]			=	temp;
			Q[oldState][action]	=	Q[oldState][action]+
									this.alphaCalculator.getAlpha()*(
											reward+(this.yotaParameter*V[newState])-Q[oldState][action]);
			
			System.out.println("Updated Q["+oldState+"]["+action+"]");
			System.out.println("Q matrix:"+states+" "+actions);
			for(int i=0;i<states;i++){
				for(int j=0;j<actions;j++){
					double qij					=	Q[i][j];
					DecimalFormat numberFormat = new DecimalFormat("0.00");
					System.out.print(numberFormat.format(qij)+"\t");
				}
				System.out.print("\n");
			}
			try {
				Thread.sleep(this.evalInterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
