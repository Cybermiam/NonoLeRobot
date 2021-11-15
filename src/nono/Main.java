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
		//final Moteur r.getMoteur()=new Moteur();
		//Capteur r.getCapteur() = new Capteur();
		//r.getMoteur().travelArc(100, 90);
		//r.getMoteur().travel(50);



		GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();


		//r.getMoteur().setSpeed(30);
		//r.getMoteur().tourneCentre(360);

	/*new Thread(new Runnable() {
		public void run() {
				r.getMoteur().fermerPince();
			}
		}).start();*/


		/*boolean atrouve = false;
		float temp = 0;
		
		while(r.getMoteur().getAngleRotated()<360 && atrouve==false) {
			temp =  r.getCapteur().distanceMetre();
			brick.drawString("distance"+temp, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(10);
			if(temp<1) {
				r.getMoteur().stop();
				atrouve=true;
			}
			brick.clear();
		}

		r.getMoteur().setSpeed(100);
		r.getMoteur().travel(temp*100-5,false);
		
		r.getMoteur().ouvrirPince();
		r.getMoteur().travel(10,true);
		
		brick.drawString("on vas a"+temp*100, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		
		while(r.getCapteur().estTouche()==false && r.getMoteur().getDistanceParcourue()<=10) {
			Delay.msDelay(1);
		}
		r.getMoteur().stop();
		r.getMoteur().fermerPince();
	*/

	
		
		//r.getMoteur().travel(50);
		//r.getMoteur().tourneCentre(45);

      
		//r.getMoteur().fermerPince();
		//r.getMoteur().ouvrirPince();
		//r.search();
		r.premierPalets();
	}
}

