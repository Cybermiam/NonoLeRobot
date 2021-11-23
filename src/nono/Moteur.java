package nono;

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
	//======== Attributs =======//

	private static final double diametreRoue= 5.5;
	private static final int largeurChassis=13; //distance inter-roue
	private static final int vitesse=500;

	private MovePilot pilot;//diametre roue 5/5 largeur 3,distance entre roue 16.5,centre 8;
	private Port lefthandMotorort = LocalEV3.get().getPort("A");
	private Port righthandMotorort = LocalEV3.get().getPort("C");
	private Port handhandMotorort = LocalEV3.get().getPort("B");
	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;
	private EV3MediumRegulatedMotor handMotor;
	private int status = 0; // -1 recule ; 0 a l'arret ; 1  avance
	private int VitessePince = 500;
	private int DureeFermeturePince = 1000;
	private boolean ouvert = false;


	/**Constructeur de la classe moteur
	 *
	 */
	public Moteur() {
		handMotor = new EV3MediumRegulatedMotor(handhandMotorort);
		leftMotor = new EV3LargeRegulatedMotor(lefthandMotorort);
		rightMotor = new EV3LargeRegulatedMotor(righthandMotorort);
		pilot=new MovePilot(diametreRoue,largeurChassis,leftMotor,rightMotor);
		pilot.setAngularSpeed(vitesse);
	}

	public boolean isMoving() {
		return this.pilot.isMoving();
	}
	//autre methode constructeur
	//pilot=new MovePilot(diametreRoue,chassis,leftMotor,rightMotor); //autre �thode, a explorer ?


	/**Fonction permettant au robot d'avancer sur une distance donn�e par l'utilisateur.
	 * @param f est un float indiquant la distance du robot � parcourir en cm.
	 */
	public void travel (float f, boolean b) {
		pilot.travel(f,b);
	}

	/**Fonction modifiant la vitesse du robot en degr�e par seconde.
	 * @param i est un entier modifiant la vitesse de rotation des roues du robot en degre par seconde.
	 */
	public void setSpeed (int i) {
		pilot.setAngularSpeed(i);
	}

	/**Fonction permettant au robot d'avancer en arc,ou de tourner sur lui m�me.
	 * @param radius est un double si positif le robot tourne vers la droite,si negatif vers la gauche et si neutre (= 0) tourne sur lui meme.
	 * @param angle est un double,si il est positif,le robot tourne dans le sens d'une horloge,si negatif tourne dans le sens contraire d'une horloge,et si neutre (= 0),un retour de la fonction immediat.
	 */
	public void travelArc(double radius,double angle, boolean b) {
		pilot.arc(radius,angle,b);
	}

	/**Fonction permettant au robot de tourner sur lui m�me d'un angle defini.
	 * @param angle est un double,si positif tourne � gauche,si negatif a droite.
	 */
	public void tourneCentre(double angle,boolean b) {
		pilot.rotate(angle,b);
	}

	/**Fonction donnant la valeur de l'angle tourn� actuellement.
	 * @return un float qui correspond � l'angle tourn�.
	 */
	public float getAngleRotated() {
		return pilot.getMovement().getAngleTurned();
	}

	/**Fonction d'arret du robot (stop le robot).
	 */

	public float getDistanceParcourue() {
		return pilot.getMovement().getDistanceTraveled();
	}


	public void stop () {
		pilot.stop();
	}

	/**Fonction retournant l'Etat de la pince
	 * @return true si la pince est ouverte
	 */
	public boolean estOuvert() {
		return ouvert;
	}

	/** Methode qui ferme la pince du robot si elle est ouverte, si non indique que la pince est ferm�
	 */
	public void fermerPince() {
		handMotor.setSpeed(VitessePince);
		handMotor.backward();
		Delay.msDelay(DureeFermeturePince);
		handMotor.stop();
		ouvert=false;
	}


		/** Methode qui ouvre la pince du robot si elle est ferm�, si non indique que la pince est ouverte
		 */
	}
	public void forcefermerPince() {

			handMotor.setSpeed(VitessePince);
			handMotor.backward();
			Delay.msDelay(DureeFermeturePince);
			handMotor.stop();
			ouvert=false;



		/** Methode qui ouvre la pince du robot si elle est ferm�, si non indique que la pince est ouverte
		 */
	}
	public void ouvrirPince() {
		handMotor.setSpeed(VitessePince);
		handMotor.forward();
		Delay.msDelay(DureeFermeturePince);
		handMotor.stop();
		ouvert=true;
	}
	public void forceouvrirPince() {

			handMotor.setSpeed(VitessePince);
			handMotor.forward();
			Delay.msDelay(DureeFermeturePince);
			handMotor.stop();
			ouvert=true;

	}

	public MovePilot getPilot() {
		return pilot;
	}

	public void setPilot(MovePilot pilot) {
		this.pilot = pilot;
	}




}

// Code conserv� pour tests �ventuels


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


	leftMotor.setSpeed(Speed);
	rightMotor.setSpeed(Speed);


}
public void avancer() {
smooth(1);
if(status != 1) {
	status = 1;
leftMotor.forward();
rightMotor.forward();
}
//Delay.msDelay(20);
}
public void reculer() {

smooth(1);
if(status != -1) {
	status = -1;
	leftMotor.backward();
	rightMotor.backward();
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
leftMotor.stop();
rightMotor.stop();
	status = 0;
}
public void avancerPendant(int t) {
for(int i=0;i<t;i++) {
	leftMotor.forward();
	rightMotor.forward();
	Delay.msDelay(100);
}
}
public void tourneG90() {
leftMotor.forward();
rightMotor.backward();
Delay.msDelay(20);
}
public void tourneD90() {
rightMotor.forward();
leftMotor.backward();
Delay.msDelay(20);
}
public int getSpeed() {
return Speed;
}
}
 */
