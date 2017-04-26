package main;
import sensors.Encoder;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;


public class Pi {
	
	public static void main(String[] args) throws InterruptedException {

		System.out.println("Starting up program");
        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn off
        final GpioPinDigitalOutput pin0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "MotorForwardChannel", PinState.LOW);
        final GpioPinDigitalOutput pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MotorReverseChannel", PinState.LOW);
        final GpioPinDigitalInput pin2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "EncoderChannel1");
        final GpioPinDigitalInput pin3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, "EncoderChannel2");
        
		Encoder encoder = new Encoder(pin2, pin3);
		
        // set shutdown state for this pin
        pin0.setShutdownOptions(new Boolean(true), PinState.LOW);
        pin1.setShutdownOptions(new Boolean(true), PinState.LOW);

        //turn pin1 on and pin2 off to spin motor
        pin0.high();
        pin1.low();
        System.out.println("Rotating...");

        Thread.sleep(5000);

        //switch direction of motor
        pin0.toggle();
        pin1.toggle();
        System.out.println("Encoder count " + encoder.get() + "\nSwitched Direction");

        Thread.sleep(5000);

        //switch direction again
        pin0.toggle();
        pin1.toggle();
        System.out.println("Encoder count " + encoder.get() + "\nSwitched Again");

        Thread.sleep(5000);

        // toggle the current state of gpio pin #01  (should turn off)
        pin0.low();
        pin1.low();
        System.out.println("Encoder count " + encoder.get() + "\nSTOP!");

        Thread.sleep(5000);

        // turn on gpio pin #01 for 1 second and then off
        System.out.println("Hammer Time");
        pin0.pulse(1000, true); // set second argument to 'true' use a blocking call
        System.out.println("Encoder count " + encoder.get() + "\nSTOP!");

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpio.shutdown();

        System.out.println("End of transmission");

	}

}
