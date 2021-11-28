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
		System.out.println("Appuie sur Entrer pour phase 1 \n Bas pour phase 2");
		Button.waitForAnyPress();
		if(Button.ENTER.isDown()) {
			System.out.println("phase 1");
			p.start();
			r.paletsSuivants2();
			//la suite du programme
		}else if(Button.DOWN.isDown()) {
			System.out.println("phase 2");
			p.start();
			//la suite du programme
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
		//	r.getMoteur().forcefermerPince();
		//	r.getMoteur().forceouvrirPince();
		//	r.getMoteur().forceouvrirPince();

		r.getMoteur().getPilot().setLinearAcceleration(50);
		r.getMoteur().getPilot().setAngularAcceleration(50);
		r.getMoteur().tourneCentre(-55, false);
		//r.premierPalet();
		r.paletsSuivants2();



		/*	r.getMoteur().fermerPince();
		r.setFecth(true);


		new Thread(new Runnable() {
			public void run() {
				while(r.isFecth()) {
					r.FecthDistance();
				}
			}
		}).start();


		r.search();


		r.avancer((int)r.getVisuel().distance,false);
		r.setEtat(r.getEtats().Arret);

		r.recuperePalet();
		r.setFecth(false);
		 */
		/*new Thread(new Runnable() {
			public void run() {
				System.out.println("ggggg");
				Button.ENTER.waitForPress();

				System.out.println("eerreeeeeeee");
				r.getMoteur().stop();
				Button.ENTER.waitForPress();

				if(Button.ESCAPE.isDown()) {
					System.exit(0);
				}
			}
		}).start();*/
				/*r.getMoteur().tourneCentre(90,false);
				r.angle=90;
				r.avancerBut();*/



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
