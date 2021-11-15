package nono;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

public class Robot {
	//======== Attributs =======//

	private Moteur moteurs;
	private Capteur capteurs;

	private double distancePalet;
	private double distanceMax;
	//======== Constructeurs =======//

	public Robot() {
		this(new Moteur(),new Capteur());
	}

	public Robot(Moteur moteurs,Capteur capteurs) {
		this.moteurs=moteurs;
		this.capteurs=capteurs;
		distancePalet=0.3;
		distanceMax=2.9;
	}


	//======== Methodes simples =======//

	/**
	 * @param distance int definissant le nombre de cm à parcourir
	 * Fonction reculant le robot de distance cm
	 */
	public void avancer(int distance) {
		moteurs.travel(distance,false);
	}

	/**
	 * @param distance int definissant le nombre de cm à parcourir
	 * Fonction reculant le robot de distance cm
	 */
	public void reculer(int distance) {
		moteurs.travel(-distance,false);
	}


	//======== Methodes haut niveau =======//


	public void search() {
		GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();
		double temp1 = distancePalet , temp2 = distancePalet;
		moteurs.setSpeed(10);
		moteurs.tourneCentre(360);
		while(moteurs.getAngleRotated()<=360  && ( Math.abs(temp1-temp2) < 0.1)){
			temp2=temp1;
			temp1 =  capteurs.distanceMetre();
			if(temp1>=distanceMax) {
				temp1=distanceMax;
			}
			brick.drawString(temp1+" vs "+temp2, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(10);			
			brick.clear();
		}
		moteurs.stop();
		moteurs.setSpeed(100);
		moteurs.travel((float)temp1*100, false);
	}
	
	public boolean recuperePalet() {
			moteurs.ouvrirPince();
			moteurs.travel(15, false);
			if(capteurs.estTouche()==true) {
			moteurs.fermerPince();
			return true;
		}
		return false;
	}
	public void avanceVersPalet() {
		
	}


	//======== Getter / Setter =======//


	public Moteur getMoteur() {
		return moteurs;
	}

	public void setMoteur(Moteur moteurs) {
		this.moteurs = moteurs;
	}

	public Capteur getCapteur() {
		return capteurs;
	}

	public void setCapteur(Capteur capteurs) {
		this.capteurs = capteurs;
	}

}

