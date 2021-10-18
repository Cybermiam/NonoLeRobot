package nono.adrien.test;

import lejos.hardware.port.Port;

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
			m.travel(c.distanceMetre()*100);
		}
	}
	/**
	 * Fonction reculant le robot de 100cm.
	 */
	public void reculer() {
		m.travel(-100);
	}
	/**
	 * Fonction avancant jusqu'a ce que le capteur de toucher soit activer.
	 */
	public void avancerVersToucher() {
		avancerVers();
		if(c.estToucher()) {
			return;
		}else {
			reculer();
			avancerVersToucher();
		}
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
	public Moteur getM() {
		return m;
	}
	public void setM(Moteur m) {
		this.m = m;
	}
	public Capteur getC() {
		return c;
	}
	public void setC(Capteur c) {
		this.c = c;
	}
}

