package nono;


import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.utility.Delay;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.BaseRegulatedMotor;

public class Main {

	public static void main(String[] args) {
		final Robot r=new Robot();
		final Pause p=new Pause();
		GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();
		System.out.println("Entrer pour phase 1 \n Bas pour phase 2");
		Button.waitForAnyPress();
		if(Button.ENTER.isDown()) {
			System.out.println("phase 1");
			p.start();
			r.premierPalet();
			//la suite du programme
		}else if(Button.DOWN.isDown()) {
			System.out.println("phase 2");
			p.start();
			//la suite du programme 
		}
	}
}