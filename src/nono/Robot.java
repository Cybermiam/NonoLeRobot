package nono;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

public class Robot {
	//======== Attributs =======//

	private Moteur moteurs;
	private Capteur capteurs;

	//======== Constructeurs =======//

	public Robot() {
		this(new Moteur(),new Capteur());
	}

	public Robot(Moteur moteurs,Capteur capteurs) {
		this.moteurs=moteurs;
		this.capteurs=capteurs;
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
		float temp1 = (float) 2.9 , temp2 = (float) 2.9;
		moteurs.setSpeed(10);
		moteurs.tourneCentre(360);
		while(moteurs.getAngleRotated()<=360  && ( Math.abs(temp1-temp2) < 0.1)){
						
			temp2=temp1;
			temp1 =  capteurs.distanceMetre();
			if(temp1>=2.9) {
				temp1=(float) 2.9;
			}
			brick.drawString(temp1+" vs "+temp2, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(10);			
			brick.clear();
		}
		
		moteurs.stop();
		moteurs.setSpeed(100);

		moteurs.travel(temp1*100, false);
		

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

