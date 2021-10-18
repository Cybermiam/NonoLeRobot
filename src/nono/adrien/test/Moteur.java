package nono.adrien.test;

import lejos.hardware.port.Port;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.MovePilot;

public class Moteur {
	private MovePilot pilot;//diametre roue 5/5 largeur 3,distance entre roue 16.5,centre 8;
	private Port ml=LocalEV3.get().getPort("A");
	private Port mr=LocalEV3.get().getPort("C");
	final private double diametreRoue= 5.5;
	final private int chassis=13;
	final private int vitesse=100;
	private EV3LargeRegulatedMotor motorL;
	private EV3LargeRegulatedMotor motorR;

	//public Moteur() {
	//	motorL = new EV3LargeRegulatedMotor(ml);
	//	motorR = new EV3LargeRegulatedMotor(mr);
	//	pilot=new MovePilot(diametreRoue,chassis,motorL,motorR);
	//	pilot.setAngularSpeed(vitesse);

	private EV3MediumRegulatedMotor motorP;
	public int status = 0; // -1 recule ; 0 a l'arret ; 1  avance
	 final int VitessePince=500;
	 final int DureeFermeturePince = 1000;
	 private boolean ouvert= false;
	/**Constructeur de la classe moteur
	 * 
	 */
	public Moteur() {
		motorP = new EV3MediumRegulatedMotor(LocalEV3.get().getPort("B"));
		motorL = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
		motorR = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("C"));
		pilot=new MovePilot(5.5,13,motorL,motorR);
		pilot.setAngularSpeed(100);
		
		//motorL.setSpeed(Speed);
		//motorR.setSpeed(Speed);
	}
	/*public void setVitesse(int s) {
		MAXSPEED = s;
	}

	public void smooth(int O) {// -1 ou 1  

			Speed+=10*O;
			if (Speed>MAXSPEED) {
				Speed = MAXSPEED;
			}else if (Speed < 0 )
			{
				Speed = 0;
			}


			motorL.setSpeed(Speed);
			motorR.setSpeed(Speed);


	}
	public void avancer() {
		smooth(1);
		if(status != 1) {
			status = 1;
		motorL.forward();
		motorR.forward();
		}
		//Delay.msDelay(20);
	}
	public void reculer() {

		smooth(1);
		if(status != -1) {
			status = -1;			
			motorL.backward();
			motorR.backward();
		}

	}
	public void arreter() {
		//GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();
		//	int a = 0;
		while (Speed > 10) {
		//	a++;
			//brick.drawString("speed : " + Speed + " a " +a , 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			smooth(-1);	
			Delay.msDelay(10);
			//brick.clear();
		}
		motorL.stop();
		motorR.stop();
			status = 0;
	}
	public void avancerPendant(int t) {
		for(int i=0;i<t;i++) {
			motorL.forward();
			motorR.forward();
			Delay.msDelay(100);
		}
	}
	public void tourneG90() {
		motorL.forward();
		motorR.backward();
		Delay.msDelay(20);
	}
	public void tourneD90() {
		motorR.forward();
		motorL.backward();
		Delay.msDelay(20);
	}
	public int getSpeed() {
		return Speed;
	}
}
//class Pilot a voir,dans leJOS (methode asynchone et synchrone); 
	 */
	/**Fonction permettant au robot d'avancer sur une distance donnée par l'utilisateur.
	 * @param f est un float indiquant la distance du robot à parcourir en cm.
	 */
	public void travel (float f) {
		pilot.travel(f);		
	}
	/**Fonction modifiant la vitesse du robot en degrée par seconde.
	 * @param i est un entier modifiant la vitesse de rotation des roues du robot en degre par seconde.
	 */
	public void setSpeed (int i) {
		pilot.setAngularSpeed(i);
	}
	/**Fonction permettant au robot d'avancer en arc,ou de tourner sur lui même.
	 * @param radius est un double si positif le robot tourne vers la droite,si negatif vers la gauche et si neutre (= 0) tourne sur lui meme.
	 * @param angle est un double,si il est positif,le robot tourne dans le sens d'une horloge,si negatif tourne dans le sens contraire d'une horloge,et si neutre (= 0),un retour de la fonction immediat.
	 */
	public void travelArc(double radius,double angle) {
		pilot.arc(radius,angle);
	}
	/**Fonction permettant au robot de tourner sur lui même d'un angle defini.
	 * @param angle est un double,si positif tourne à gauche,si negatif a droite.
	 */
	public void tourneCentre(double angle) {
		pilot.rotate(angle);
	}
	/**Fonction donnant la valeur de l'angle tourné actuellement.
	 * @return un float qui correspond à l'angle tourné.
	 */
	public float getAngleRotated() {
		return pilot.getMovement().getAngleTurned();
	}
	/**Fonction d'arret du robot (stop le robot).
	 */
	public void stop () {
		pilot.stop();
	}
		
	
	public boolean estOuvert() {
		return ouvert;
	}
	public void fermerPince() {
		if (ouvert=true) {
		motorP.setSpeed(VitessePince);
		motorP.backward();
		Delay.msDelay(DureeFermeturePince);
		motorP.stop();
		ouvert=false;
		}
		
	}
 public void ouvrirPince() {
	 if (ouvert=false) {
	 motorP.setSpeed(VitessePince);
	 motorP.forward();
	 Delay.msDelay(DureeFermeturePince);
	 motorP.stop();
	 ouvert=true;
	 }
 }




}