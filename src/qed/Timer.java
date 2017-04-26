package qed;

public class Timer {
	
	double startTime = 0;
	
	public void start(){
		startTime = (double) (System.currentTimeMillis() / 1000);
	}
	
	public double getPeriodPassed(){
		return (double) (System.currentTimeMillis() / 1000);
	}
	
	public boolean hasPeriodPassed(double time){
		if (getPeriodPassed() - startTime > time)
			return true;
		else
			return false;
	}
	
}
