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

/*
	//	boolean atrouve = false;
		float temp = 0;

		while(true) {
			temp =  r.getCapteur().distanceMetre();
			if(temp==Float.POSITIVE_INFINITY) {
				brick.drawString("distance inf : "+temp, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			} else {
			brick.drawString("distance : "+temp, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			}
			Delay.msDelay(50);
			//if(temp<1) {
			//	r.getMoteur().stop();
			//	atrouve=true;
			//}
			brick.clear();
		
		}*/
  /*
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
	//	r.premierPalets();


	//	r.getMoteur().forcefermerPince();
		//r.getMoteur().forcefermerPince();
		r.getMoteur().getPilot().setLinearAcceleration(50);
		r.getMoteur().getPilot().setAngularAcceleration(50);
		r.premierPalet();

		
		//r.getMoteur().forceouvrirPince();
		//r.getMoteur().forceouvrirPince();
		

/*

		
		r.search();
		

		r.avancer((int)r.getVisuel().distance,false);
		r.setEtat(r.getEtats().Arret);

		r.recuperePalet();
	
*/
		

		/*
		brick.drawString("radius"+300 + "angle" + 10, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		r.getMoteur().travelArc(500, 90, false);
		brick.clear();
		
		brick.drawString("radius"+50 + "angle" + 50, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		r.getMoteur().travelArc(50, 50, false);
		brick.clear();

		brick.drawString("radius"+200 + "angle" + 50, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		r.getMoteur().travelArc(200, 50, false);
		brick.clear();


		brick.drawString("radius"+200 + "angle" + 50, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		r.getMoteur().travelArc(200, 50, false);
		brick.clear();
*/


	}
}

