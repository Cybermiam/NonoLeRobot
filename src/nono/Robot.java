package nono;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.utility.Delay;
import java.awt.event.ActionEvent;
import java.lang.Enum;
import java.sql.Struct;
import java.util.ArrayList;


//Enum Etat{Recherche,Attrape,Depose,Arret}

public class Robot {
	//======== Attributs =======//
	//Enum Etat{Recherche,Attrape,Depose,Arret};
	private Moteur moteurs;
	private Capteur capteurs;

	private double distancePalet;
	private double distanceMax;
	private boolean pause;
	public int angle;


	class Visuelc {
		public boolean atrouve = false;
		public int angle = 0;
		public double distance=0;
		public int numObj=0;

		public Visuelc() {}

		public Visuelc(boolean trouve, int angle, double distance) {
			this.atrouve = trouve;
			this.angle = angle;
			this.distance = distance;
		}

	}


	//private ArrayList<Integer> liste = new ArrayList<Integer>();

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
	
	private Visuelc[] mesVisuels;
	private Visuelc[] paletsSuivants;


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
		distanceMax=1.95;
		etats = new Etatc();
		etat=etats.Arret;
		mesVisuels = new Visuelc[100];
		paletsSuivants = new Visuelc[3];

		pause=false;
		angle=0;
	}


	//======== Methodes simples =======//

	public void FecthDistance() {
		lastMesure=capteurs.distanceMetre();
		Delay.msDelay(5);
	}


	/**
	 * @param distance int definissant le nombre de cm � parcourir
	 * Fonction reculant le robot de distance cm
	 */
	public void avancer(int distance, boolean b) {
		moteurs.travel(distance,b);

	}

	/**
	 * @param distance int definissant le nombre de cm � parcourir
	 * Fonction reculant le robot de distance cm
	 */
	public void reculer(int distance) {
		moteurs.travel(-distance,false);
	}




	//======== Methodes haut niveau =======//

	/* Prendre le premier palet sur un cote ,ensuite se decale pour sur le cote afin de ne pas deranger les autres palets
	 * Distance 57cm
	 */
	public void premierPalet() {
		System.out.println(this.moteurs.getPilot().getLinearAcceleration());
		avancer(50,false);
		boolean b=recuperePalet();

		moteurs.setSpeed(50);
		moteurs.tourneCentre(-15,false);
		moteurs.setSpeed(150);

		avancer(207, false);
		//metre en asynchrone et verifier si robot en face dans une boucle, exception si robot traitant le cas, sinon sortie si blanc d�tect�

		deposerPalet();
		moteurs.tourneCentre(-150,false);
		reculer(20);



	}

	public void paletsSuivants2() {

		moteurs.tourneCentre(-55, false);
		moteurs.tourneCentre(110, true);

		int count = 0;
		int i = 0;
		int lastmes = 2000;
		int obj = 0;

		while(moteurs.getAngleRotated()<109) {

			count=(int) moteurs.getAngleRotated();
			//System.out.println(moteurs.getAngleRotated());

			if(count%5==0) {	
				System.out.println( count +" - "+capteurs.distanceMetre());
				if(capteurs.distanceMetre()<0.9) {

					
					mesVisuels[i] = new Visuelc(true, count,capteurs.distanceMetre());
					System.out.print( "VALID");
				/*	if(Math.abs(count-lastmes) <= 20) {
						mesVisuels[i].numObj=obj;
					}else {
						obj++;
						mesVisuels[i].numObj=obj;
					}
					lastmes=count;*/
					i++;
				}
			}
			
			Delay.msDelay(2);
			
			

		}
		mesVisuels[i] = new Visuelc(false, count,capteurs.distanceMetre());

		System.out.println("  ttttttttttttttt   ");
		System.out.println("  ttttttttttttttt   ");
		System.out.println("  ttttttttttttttt   ");


		i=0; int j=0;
		double borne1 = 0.0,borne2 = 0.0;
		int moy=0; int nbm=0;
		int angledep=0;
		
		
		while(mesVisuels[i].atrouve) {
			System.out.println("passsage : "+borne1+" ? "+mesVisuels[i].distance+" / "+borne2+" ? "+mesVisuels[i].distance);
			
			if(borne1==0.0 && borne2==0.0 ) {
				angledep = mesVisuels[i].angle;
				borne1 = mesVisuels[i].distance-0.15; borne2 = mesVisuels[i].distance+0.15;
				System.out.println(mesVisuels[i].angle + " - " + (borne1+borne2)/2 + " - START ");
				moy+=mesVisuels[i].angle;
				nbm++;
			}else if (mesVisuels[i].distance>=borne1 && mesVisuels[i].distance<=borne2 && mesVisuels[i].angle-30 <= angledep) {
				System.out.println(mesVisuels[i].angle + " - " + (borne1+borne2)/2 + " - Middle ");
				moy+=mesVisuels[i].angle;
				nbm++;
			}else {				
				moy = moy/nbm;
				paletsSuivants[j]=new Visuelc(true,moy,(borne1+borne2)/2);
				System.out.println(mesVisuels[i].angle + " - " + (borne1+borne2)/2 + " - FIN ");
				borne1 = mesVisuels[i].distance-0.15; borne2 = mesVisuels[i].distance+0.15;
				angledep = mesVisuels[i].angle;
				moy=0;nbm=0;j++;
			}
			i++;

		}
		i--;
		moy = moy/nbm;
		paletsSuivants[j]=new Visuelc(true,moy,(borne1+borne2)/2);
		System.out.println(mesVisuels[i].angle + " - " + (borne1+borne2)/2 + " - FIN ");
		borne1 = mesVisuels[i].distance-0.15; borne2 = mesVisuels[i].distance+0.15;
		angledep = mesVisuels[i].angle;
		moy=0;nbm=0;j++;
	
		for(int a=0; a<paletsSuivants.length;a++) {
			try {
				
				System.out.println(paletsSuivants[a].angle+" - "+paletsSuivants[a].distance +" - "+paletsSuivants[a].numObj);
			} catch (Exception e) {
				System.out.println("un de trop");
			}
		}

	}



	public void paletsSuivants(){

		int nbPalets = 0;
		int nbPaletsMarques = 0;

		if (capteurs.distanceMetre() < 0.8) {
			System.out.println(capteurs.distanceMetre());
			Visuelc vc1 = new Visuelc(true, 0, (double) capteurs.distanceMetre());
			paletsSuivants[0] = vc1;
		}

		moteurs.tourneCentre(-40, false);
		if (capteurs.distanceMetre() < 0.8) {
			System.out.println(capteurs.distanceMetre());
			Visuelc vc2 = new Visuelc(true, -40, (double) capteurs.distanceMetre());
			paletsSuivants[1] = vc2;
		}

		moteurs.tourneCentre(80, false);
		if (capteurs.distanceMetre() < 0.8) {
			System.out.println(capteurs.distanceMetre());
			Visuelc vc3 = new Visuelc(true, 80, (double) capteurs.distanceMetre());
			paletsSuivants[2] = vc3;
		}
		moteurs.tourneCentre(-40, false);

		for (int i = 0; i < 3; i++) {
			if (paletsSuivants[i].atrouve==true) {
				System.out.println("test");
				nbPalets++;
			}
		}

		int securite = 0;
		while (nbPaletsMarques < nbPalets && securite < 3) {
			if (paletsSuivants[0].atrouve) {
				avancer(50, false);
				recuperePalet();
				moteurs.tourneCentre(150, false);
				avancer(80, false);
				deposerPalet();
				moteurs.tourneCentre(180, false);
				nbPaletsMarques++;
				paletsSuivants[0].atrouve = false;
			} else if (paletsSuivants[1].atrouve) {
				moteurs.tourneCentre(-40, false);
				avancer(78, false);
				recuperePalet();
				moteurs.tourneCentre(180, false);
				avancer(108, false);
				deposerPalet();
				moteurs.tourneCentre(220, false);
				nbPaletsMarques++;
				paletsSuivants[1].atrouve = false;
			} else if (paletsSuivants[2].atrouve) {
				moteurs.tourneCentre(40, false);
				avancer(78, false);
				recuperePalet();
				moteurs.tourneCentre(180, false);
				avancer(108, false);
				deposerPalet();
				moteurs.tourneCentre(140, false);
				nbPaletsMarques++;
				paletsSuivants[2].atrouve = false;
			}
			securite++;
		}




	}

	public void search() {
		GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();
		double temp1 = capteurs.distanceMetre() , temp2 = temp1;
		moteurs.setSpeed(25);
		moteurs.tourneCentre(360,true);
		while(moteurs.isMoving() && Math.abs(temp1-temp2) < 0.2){
			temp2=temp1;
			temp1 =  capteurs.distanceMetre();
			//if(temp1==Float.POSITIVE_INFINITY) {
			//	temp1=distanceMax;
			//}
			brick.drawString(temp1+" vs "+temp2, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(5);
			brick.clear();
		}
		moteurs.stop();

		moteurs.tourneCentre(15,false);


		/*anglesup = (int) moteurs.getAngleRotated();
		while(moteurs.getAngleRotated()-anglesup>-20) {
			brick.drawString("on y est presque", 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(1);
		}*/

		moteurs.setSpeed(100);
		visuel.atrouve=true;
		visuel.distance=temp1*100;
	}

	public void search2() {
		GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();

		double temp1 = distanceMax , temp2 = distanceMax;

		moteurs.setSpeed(25);

		moteurs.tourneCentre(360,true);

		while(moteurs.getAngleRotated()<=360){

			temp2=temp1;
			temp1 =  capteurs.distanceMetre();
			//if(temp1==Float.POSITIVE_INFINITY) {
			//	temp1=distanceMax;
			//}
			if( Math.abs(temp1-temp2) < 0.1) {

			}

			brick.drawString(temp1+" vs "+temp2, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(5);
			brick.clear();
		}
		moteurs.stop();
		moteurs.setSpeed(100);
		visuel.atrouve=true;
		visuel.distance=temp1*100;

	}

	public boolean recuperePalet() {
		moteurs.ouvrirPince();
		moteurs.travel(10, false);
		if(capteurs.estTouche()) {
			moteurs.fermerPince();
			return true;
		}
		moteurs.fermerPince();
		return false;
	}

	public void deposerPalet() {
		moteurs.ouvrirPince();
		reculer(20);
		moteurs.fermerPince();

	}

	public void avancerBut() {
		if(angle==0)
			moteurs.travel(capteurs.distanceMetre()*100-20,false);
		else {
			moteurs.tourneCentre(-angle, false);
			moteurs.travel(capteurs.distanceMetre()*100-20,false);
		}
	}
	public void pause() {
		Button.waitForAnyPress(1000);
		if(Button.ENTER.isDown()) {
			System.exit(0);
		}
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
