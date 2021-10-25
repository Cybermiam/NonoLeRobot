package nono;


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


		//r.getM().setSpeed(30);
		//r.getM().tourneCentre(360);

	/*new Thread(new Runnable() {
		public void run() {
				r.getM().fermerPince();
			}
		}).start();*/


		/*boolean atrouve = false;
		float temp = 0;
		
		while(r.getM().getAngleRotated()<360 && atrouve==false) {
			temp =  r.getC().distanceMetre();
			brick.drawString("distance"+temp, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(5);
			if(temp<1) {
				r.getM().stop();
				atrouve=true;
			}
			brick.clear();
		}

		r.getM().setSpeed(100);
		r.getM().travel(temp*100-5,false);
		
		r.getM().ouvrirPince();
		r.getM().travel(10,true);
		
		brick.drawString("on vas a"+temp*100, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		
		while(r.getC().estToucher()==false && r.getM().getDistanceParcourue()<=10) {
			Delay.msDelay(1);
		}
		r.getM().stop();
		r.getM().fermerPince();
	

	
		
		//r.getM().travel(50);
		//r.getM().tourneCentre(45);

      */
		//r.getM().fermerPince();
		//r.getM().ouvrirPince();
		
	}
}

