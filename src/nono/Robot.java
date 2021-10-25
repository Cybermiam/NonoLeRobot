package nono;

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
		moteurs.tourneCentre(-90);
		while(moteurs.getAngleRotated()!=180){
			moteurs.tourneCentre(1);
			Delay.msDelay(1000);
		}
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

