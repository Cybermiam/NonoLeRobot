package nono;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.utility.Delay;
import java.lang.Enum;
import java.sql.Struct;


//Enum Etat{Recherche,Attrape,Depose,Arret}

public class Robot {
	//======== Attributs =======//
	//Enum Etat{Recherche,Attrape,Depose,Arret};
	private Moteur moteurs;
	private Capteur capteurs;

	private double distancePalet;
	private double distanceMax;
	
	
	class Visuelc { 
		public boolean atrouve = false;
		public double distance=10000;}
	
	class Etatc { 
			  public static final int Arret = 0;
			  public static final int Recherche = 1;
			  public static final int Attrape = 2;
			  public static final int Depose = 3;
			  public static final int DeplacementDivers = 4;
			}
	

	private Visuelc visuel;
	private Etatc etats;
	private int etat;

	
	private boolean fecth = false;

	private double lastMesure;

	//Etat etat = etat.Arret;

	//======== Constructeurs =======//

	public Robot() {
		this(new Moteur(),new Capteur());
		visuel=new Visuelc();
	}

	public Visuelc getVisuel() {
		return visuel;
	}

	public void setVisuel(Visuelc visuel) {
		this.visuel = visuel;
	}

	public Robot(Moteur moteurs,Capteur capteurs) {
		this.moteurs=moteurs;
		this.capteurs=capteurs;
		distancePalet=0.3;
		distanceMax=2.9;
		etats = new Etatc();
		etat=etats.Arret;
	}


	//======== Methodes simples =======//

	public void FecthDistance() {
		lastMesure=capteurs.distanceMetre();
		Delay.msDelay(5);
	}


	/**
	 * @param distance int definissant le nombre de cm à parcourir
	 * Fonction reculant le robot de distance cm
	 */
	public void avancer(int distance) {
		moteurs.travel(distance,false);
		this.etat = etats.Arret;
	}

	/**
	 * @param distance int definissant le nombre de cm à parcourir
	 * Fonction reculant le robot de distance cm
	 */
	public void reculer(int distance) {
		moteurs.travel(-distance,false);
	}




	//======== Methodes haut niveau =======//

	/* Prendre le premier palet sur un cote ,ensuite se decale pour sur le cote afin de ne pas deranger les autres palets
	 * Distance 57cm
	 */
	public void premierPalets() {
		moteurs.travel(50,true);
		boolean b=recuperePalet();
		if(b) {
		}else {
			moteurs.fermerPince();
		}
		moteurs.tourneCentre(45,false);
		moteurs.travel(30, true);
		moteurs.tourneCentre(-45,false);
		avancer(165);
		deposerPalet();
	}

	public void search() {
		GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();
		double temp1 = distancePalet , temp2 = distancePalet;
		moteurs.setSpeed(10);
		moteurs.tourneCentre(360,true);
		while(moteurs.getAngleRotated()<=360  && ( Math.abs(temp1-temp2) < 0.1)){
			temp2=temp1;
			temp1 =  lastMesure;
			if(temp1>=distanceMax) {
				temp1=distanceMax;
			}
			brick.drawString(temp1+" vs "+temp2, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(2);			
			brick.clear();
		}
		moteurs.stop();
		moteurs.setSpeed(100);
		visuel.atrouve=true;
		visuel.distance=temp1*100;

	}

	public boolean recuperePalet() {
		moteurs.ouvrirPince();
		moteurs.travel(20, false);
		if(capteurs.estTouche()) {
			moteurs.fermerPince();
			return true;
		}
		return false;
	}

	public void deposerPalet() {
		moteurs.ouvrirPince();
		reculer(20);
	}

	public void avanceVersPalet() {


	}
	/*public void avanceVersPalet() {
		if(this.search()) {

		}

	}*/


	//======== Getter / Setter =======//
 

	public Moteur getMoteur() {
		return moteurs;
	}

	public Etatc getEtats() {
		return etats;
	}

	public void setEtats(Etatc etats) {
		this.etats = etats;
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

	public double getDistancePalet() {
		return distancePalet;
	}

	public void setDistancePalet(double distancePalet) {
		this.distancePalet = distancePalet;
	}

	public double getDistanceMax() {
		return distanceMax;
	}

	public void setDistanceMax(double distanceMax) {
		this.distanceMax = distanceMax;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public boolean isFecth() {
		return fecth;
	}

	public void setFecth(boolean startFecth) {
		fecth = startFecth;
	}

	public double getLastMesure() {
		return lastMesure;
	}

	public void setLastMesure(double lastMesure) {
		this.lastMesure = lastMesure;
	}




}

