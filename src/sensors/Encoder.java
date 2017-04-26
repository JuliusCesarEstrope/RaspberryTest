package sensors;

import com.pi4j.io.gpio.GpioPinDigitalInput;

public class Encoder implements Runnable{
	
	GpioPinDigitalInput pin1;
	GpioPinDigitalInput pin2;
	
	private int count;
	Thread encoderThread;
	boolean running;

	public Encoder(GpioPinDigitalInput pin1, GpioPinDigitalInput pin2) {
		this.pin1 = pin1;
		this.pin2 = pin2;
		
		count = 0;
		encoderThread = new Thread(this);
		encoderThread.start();
	}
	
	public void start(){
		running = true;
		if(!encoderThread.isAlive())
			encoderThread.start();
	}
	
	public int get(){
		return count;
	}
	
	public void reset(){
		count = 0;
	}
	
	public void stop(){
		running = false;
	}

	public void run() {
		boolean prevStatePin1 = false, prevStatePin2 = false;
		while(running){
			if(pin1.isHigh() && pin2.isHigh() && !(prevStatePin2))
				count++;
			else if(pin1.isHigh() && pin2.isHigh() && !(prevStatePin1))
				count--;
				
			prevStatePin1 = pin1.isHigh();
			prevStatePin2 = pin2.isHigh();
		}
	}

}
