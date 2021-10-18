package nono.adrien.test;


import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.utility.Delay;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.BaseRegulatedMotor;

public class Main {

	public static void main(String[] args) {
		 final Robot r=new Robot();
		//r.setVitesse(500);
		//r.avancerAvecToucher();
		//final Moteur r.getM()=new Moteur();
		//Capteur r.getC() = new Capteur();
		//r.getM().travelArc(100, 90);
		//r.getM().travel(50);
		
		
		
		GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();
		
	

		
		new Thread(new Runnable() {
		    public void run() {
		    	r.getM().setSpeed(30);
		    	r.getM().tourneCentre(360);
		    }
		}).start();
		
		boolean atrouve = false;
		
		while(r.getM().getAngleRotated()<360 && atrouve==false) {
			brick.drawString("distance"+r.getC().distanceMetre(), 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(5);
			if(r.getC().distanceMetre()<0.33) {
				r.getM().stop();
				atrouve=true;
			}
			brick.clear();
		}
		
	
		new Thread(new Runnable() {
		    public void run() {
		    	r.getM().setSpeed(100);
		    	r.getM().travel(r.getC().distanceMetre()*100);
		    }
		}).start();	
		
		while(!r.getC().estToucher()) {
			brick.drawString("distance"+r.getC().distanceMetre(), 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(10);
			brick.clear();
		}
		r.getM().stop();
		
		//r.getM().travel(50);
		//r.getM().tourneCentre(45);
		
	
	}
}

