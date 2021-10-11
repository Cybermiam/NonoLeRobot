package nono.adrien.test;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;

public class Main {

	public static void main(String[] args) {
		//Robot r=new Robot();
		//r.setVitesse(500);
		//r.avancerAvecToucher();
		final Moteur m=new Moteur();
		Capteur c = new Capteur();
		//m.travelArc(100, 90);
		//m.travel(50);
		
		
		
		GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();
		
		new Thread(new Runnable() {
		    public void run() {
		    	m.tourneCentre(360);
		    }
		}).start();

		
		while(m.getAngleRotated()<360) {
			brick.drawString("distance"+c.distanceMetre(), 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(10);
			if(c.distanceMetre()<0.50) {
				m.stop();
			}
			brick.clear();
		}
		
		//m.travel(50);
		//m.tourneCentre(45);
		
	
	}
}

