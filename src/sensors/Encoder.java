package sensors;

import com.pi4j.io.gpio.GpioPinDigitalInput;

public class Encoder implements Runnable{
	
	GpioPinDigitalInput pin1;
	GpioPinDigitalInput pin2;
	
	private int count;
	Thread encoderThread;
	boolean running;

	public Encoder(GpioPinDigitalInput pin1, GpioPinDigitalInput pin2) {
		System.out.println("Created an Encoder");
		this.pin1 = pin1;
		this.pin2 = pin2;
		
		count = 0;
		encoderThread = new Thread(this);
		start();
	}
	
	public void start(){
		System.out.println("Ran Start");
		running = true;
		if(!encoderThread.isAlive())
			encoderThread.start();
		System.out.println("Ended Start");
	}
	
	public int get(){
		System.out.println("Ran Get");
		return count;
	}
	
	public void reset(){
		System.out.println("Ran Reset");
		count = 0;
	}
	
	public void stop(){
		System.out.println("Ran Stop");
		running = false;
	}

	public void run() {
		boolean prevStatePin1 = false, prevStatePin2 = false, beenFalse = true;
		while(running){
			if(beenFalse){
				if(pin1.isHigh() && pin2.isHigh() && !(prevStatePin2)){
					beenFalse = false;
					count++;
				}
				else if(pin1.isHigh() && pin2.isHigh() && !(prevStatePin1)){
					beenFalse = false;
					count--;
				}
			} else if (pin1.isLow() && pin2.isLow())
				beenFalse = true;
		}
	}

}
