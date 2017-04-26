package qed;


public class PIDController implements Runnable{
	
	private double p = 0, i = 0, d = 0, feedForward = 0, setpoint = 0, pidOutput = 0;
	private boolean enabled = false, isContinuous = false;
	
	private PIDSource source;
	private Thread pidThread;
	private Timer timer;
	
	PIDController(double p, PIDSource source){
		this.p = p;
		this.source = source;
		pidThread = new Thread(this);
	}
	
	PIDController(double p, double i, PIDSource source){
		this.p = p;
		this.i = i;
		this.source = source;
		pidThread = new Thread(this);
	}
	
	PIDController(double p, double i, double d, PIDSource source){
		this.p = p;
		this.i = i;
		this.d = d;
		this.source = source;
		pidThread = new Thread(this);
	}
	
	PIDController(double p, PIDSource source, double feedForward){
		this.p = p;
		this.feedForward = feedForward;
		this.source = source;
		pidThread = new Thread(this);
	}
	
	PIDController(double p, double i, PIDSource source, double feedForward){
		this.p = p;
		this.i = i;
		this.feedForward = feedForward;
		this.source = source;
		pidThread = new Thread(this);
	}
	
	PIDController(double p, double i, double d, PIDSource source, double feedForward){
		this.p = p;
		this.i = i;
		this.d = d;
		this.feedForward = feedForward;
		this.source = source;
		pidThread = new Thread(this);
	}
	
	public void setSetpoint(double setpoint){
		this.setpoint = setpoint;
	}
	
	public void setContinuous(){
		isContinuous = true;
	}
	
	public void setContinuous(boolean continuous){
		isContinuous = continuous;
	}
	
	public void enable(){
		enabled = true;
		run();
	}
	
	public void disable(){
		enabled = false;
	}

	public void run() {
		double prevError = 0;
		double error = 0;
		double integral = 0;
		double derivative = 0;
		timer.start();
		while(enabled){
			
			error = setpoint - source.getPIDInput();
			integral += error * timer.getPeriodPassed();
			derivative = (error - prevError) / timer.getPeriodPassed();
			pidOutput = p * error + i * integral + d * derivative + feedForward;
			prevError = error;
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public double getPidOutput(){
		return pidOutput;
	}

}
