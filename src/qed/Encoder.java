package qed;


import com.pi4j.io.gpio.GpioPinDigitalInput;

public class Encoder implements Runnable, PIDSource{
	
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
		boolean statePin1, statePin2, prevStatePin1 = false, prevStatePin2 = false, beenFalse = true;
		while(running){
			statePin1 = pin1.isHigh();
			statePin2 = pin2.isHigh();
			if(beenFalse){
				if(statePin1 && statePin2 && !(prevStatePin2)){
					beenFalse = false;
					count++;
				}
				else if(statePin1 && statePin2 && !(prevStatePin1)){
					beenFalse = false;
					count--;
				}
			}
			if (!statePin1 && !statePin2){
				beenFalse = true;
			}
			prevStatePin1 = statePin1;
			prevStatePin2 = statePin2;
		}
	}

	public double getPIDInput() {
		return get();
	}
}
