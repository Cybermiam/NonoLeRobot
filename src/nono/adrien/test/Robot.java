package nono.adrien.test;

import lejos.hardware.port.Port;
import lejos.utility.Delay;

public class Robot {
	private Moteur m;
	private Capteur c;

	public Robot() {
		this(new Moteur(),new Capteur());
	}
	public Robot(Moteur m1,Capteur c1) {
		m=m1;
		c=c1;
	}
	/**
	 * Fonction permettant au robot d'avancer de la distance affichée par le capteur d'ultrason.
	 */
	public void avancerVers() {
		if (c.distanceMetre()>0.09) {
			m.travel(c.distanceMetre()*100,false);
		}
	}
	/**
	 * Fonction reculant le robot de 100cm.
	 */
	public void reculer() {
		m.travel(-100,false);
	}
	
	//public void afficherTableauDistance() {
	//	c.afficherTableauDistance();
	//}
	/*public void avancerAvecToucher() {
		while (! c.estToucher()){
			float d=c.afficherTableauDistance();
			while(d>0.10) {
				//m.acceleration(1000);
				m.setVitesse(500);
				m.avancerPendant(1);
			}
		}
		m.arreter();
	}
	public void setVitesse(int i) {
		m.smooth(i);
	}*/
	public Moteur getMoteur() {
		return m;
	}
	public void setM(Moteur m) {
		this.m = m;
	}
	public Capteur getCapteur() {
		return c;
	}
	public void setCapteur(Capteur c) {
		this.c = c;
	}
	public void search() {
		m.tourneCentre(-90);
		while(m.getAngleRotated()!=180){
			m.tourneCentre(1);
			Delay.msDelay(1000);
		}
	}
}

