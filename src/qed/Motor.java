package qed;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class Motor implements Runnable{
	
	GpioPinDigitalOutput forwardChannel;
	GpioPinDigitalOutput reverseChannel;
	double speed = 0;
	
	public Motor(GpioPinDigitalOutput forwardChannel, GpioPinDigitalOutput reverseChannel){
		this.forwardChannel = forwardChannel;
		this.reverseChannel = reverseChannel;
	}
	
	public void set(double speed){
		this.speed = (Math.abs(speed) <=1)? speed : (speed > 0)? 1: -1;
	}

	public void run() {
		setDutyCycle(speed);
	}
	
	private void setDutyCycle(double cycle){
		
		if (cycle > 0) {
			forwardChannel.setState(true);
			reverseChannel.setState(false);
		} else if (cycle < 0) {
			forwardChannel.setState(false);
			reverseChannel.setState(true);
		}
		
		try {
			Thread.sleep(0, (int) (100 * Math.abs(cycle)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		forwardChannel.setState(false);
		reverseChannel.setState(false);
		
		try {
			Thread.sleep(0, (int) (100 * (1 - Math.abs(cycle))));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
