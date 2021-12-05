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
	//=============== Attributs ==============//

	private final static int ANGLEUTILE = 110;
	private final static double MARGEdERREUR = 0.15;
	private final static double DISTANCEprobablePALET = 0.8;

	private Moteur moteurs;
	private Capteur capteurs;
	private double distancePalet;
	private double distanceMax;
	private int distanceEtape2 = 0;
	private int etape =1;



	class Etatc {	//finalement pas utilisé
		public static final int Arret = 0;
		public static final int Recherche = 1;
		public static final int Attrape = 2;
		public static final int Depose = 3;
		public static final int DeplacementDivers = 4;
	}
 
	/** Classe secondaire permettant de stocker les positions des palets lors d'une recherche*/
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

	private Etatc etats;
	private int etat;

	private Visuelc[] mesVisuels;
	private Visuelc[] paletsSuivants;

	private double lastMesure;

	//Etat etat = etat.Arret;

	//======================================================================================//
	//==================================== Constructeurs ===================================//
	//======================================================================================//

	/**Constructeur de la classe robot par défaut*/
	public Robot() {
		this(new Moteur(),new Capteur());
	}

	/**Constructeur de la classe robot avec des paramètres choisis
	 * @param moteurs est un objet Moteur contenant l'ensemble des moteurs du robot
	 * @param capteurs est un objet Capteur contenant l'ensemble des capteurs du robot*/
	
	public Robot(Moteur moteurs,Capteur capteurs) {
		this.moteurs=moteurs;
		this.capteurs=capteurs;
		distancePalet=0.3;
		distanceMax=1.95;
		etats = new Etatc();
		etat=etats.Arret;
		mesVisuels = new Visuelc[100];
		paletsSuivants = new Visuelc[10];
	}

	//======================================================================================//
	//==================================== Methodes simples ================================//
	//======================================================================================//
	
	
	/**Fonction servant a recupérer en boucle le retour du capteur à ulrason ( a utiliser dans un thread par exemple)*/
	public void FecthDistance() {		
		lastMesure=capteurs.distanceMetre();
		Delay.msDelay(5);
	}


	/**
	 * Fonction qui fait avancer le robot de distance cm
	 * @param distance int definissant le nombre de cm a parcourir
	 * @param b est un boolean permettant de mettre travel en symétrique ou asymétrique
	 * 
	 */
	public void avancer(int distance, boolean b) {
		moteurs.travel(distance,b);

	}

	/**
	 * Fonction reculant le robot de distance cm
	 * @param distance int definissant le nombre de cm à parcourir
	 * 
	 */
	public void reculer(int distance) {
		moteurs.travel(-distance,false);
	}



	//======================================================================================//
	//================================= Methodes haut niveau ===============================//
	//======================================================================================//


	/** Prendre le premier palet sur un cote ,ensuite se decale pour sur le cote afin de ne pas deranger les autres palets
	 * Distance 57cm
	 * 
	 * @param direction est un boolean qui permet de choisir si on commence a gauche ou droite 
	 */

	public void premierPalet(boolean direction) {

		int temp;
		if(direction) {temp=1;}	else {temp=-1;}


		avancer(47,true); // ajustement de 7 

		moteurs.ouvrirPince();
		moteurs.fermerPince();
		moteurs.setSpeed(50);
		moteurs.tourneCentre(temp*13,false);
		moteurs.setSpeed(200);
		avancer(207, false); //mettre en asynchrone et verifier si robot en face dans une boucle, exception si robot traitant le cas, sinon sortie si blanc (pas eut le temps d'implementer)
		moteurs.setSpeed(140);
		deposerPalet();
		moteurs.tourneCentre(temp*150,false);
		reculer(20);
	}

	
	
	/**
	 * ramasse les palets présents sur une mèmes ligne, tiens compte de leur présence
	 * */
	public void paletsSuivants() {
		mesVisuels = new Visuelc[100];		//reinitialisation des tableaux
		paletsSuivants = new Visuelc[10];
		int count = 0;
		int i = 0;
		boolean pasdePalet=true;
		double borne1 = 0.0,borne2 = 0.0;
		int moy=0; int nbm=0;
		int angledep=0;
		boolean secu =false;

		//=====================================Rotation=======================================//

		moteurs.setSpeed(50);
		moteurs.tourneCentre(-ANGLEUTILE/2, false);

		//====================================Acquisition=====================================//

		while(pasdePalet) {	//Dans cette boucle on enregistre, tous les 5 dégré parcouru, un nouveau visuel (angle parcouru, distance) dans un array, ce sont des positions probable de palets

			moteurs.tourneCentre(ANGLEUTILE, true);
			count=0;

			while(moteurs.getAngleRotated()<ANGLEUTILE-1) {// -1 car imperfection provocant une erreur (du au déplacement en asynchrone)

				count=(int) moteurs.getAngleRotated();
				//System.out.println(moteurs.getAngleRotated());

				if(count%5==0 && count>5) {
					//	System.out.print( count +" - "+capteurs.distanceMetre());
					if(capteurs.distanceMetre()<DISTANCEprobablePALET) {
						mesVisuels[i] = new Visuelc(true, count,capteurs.distanceMetre());

						System.out.println( " VALID ");

						pasdePalet=false;
						i++;

					}else {
						System.out.println( " NON ");
					}
				}


				Delay.msDelay(2);
			}
			//si rien vu on relance l'observation, il faudrait rajouter une variable de nombre d'essai qui si dépassée fait avancer a la prochaine ligne
			if(pasdePalet) {
				moteurs.setSpeed(240);
				moteurs.tourneCentre(-ANGLEUTILE, true);
				moteurs.setSpeed(100);
				mesVisuels = new Visuelc[100];
				paletsSuivants = new Visuelc[10];
				count=0;
			}
		}

		mesVisuels[i] = new Visuelc(false, count,capteurs.distanceMetre());

		i=0; int j=0;

		//====================================Traitement=======================================//

		while(mesVisuels[i].atrouve) {	// Dans cette boucle on enregistre dans un array "paletsSuivants" les palets détecté, 
										//pour cela on parcours notre précedent array "mesVisuels" et pour une ouverture de 30 degré 
										//et une position comprise dans des borne on considère qu'il n'y a qu'un palet a la moyenne de ces positions .
			if(borne1==0.0 && borne2==0.0 ) {
				angledep = mesVisuels[i].angle;
				borne1 = mesVisuels[i].distance-MARGEdERREUR; borne2 = mesVisuels[i].distance+MARGEdERREUR;
				moy+=mesVisuels[i].angle;
				nbm++;
				secu=false;
			}else if (mesVisuels[i].distance>=borne1 && mesVisuels[i].distance<=borne2 && mesVisuels[i].angle-30 <= angledep) {
				moy+=mesVisuels[i].angle;
				nbm++;
				secu=false;
			}else {
				moy = moy/nbm;
				paletsSuivants[j]=new Visuelc(true,moy-10,(borne1+borne2)/2);
				borne1 = mesVisuels[i].distance-MARGEdERREUR; borne2 = mesVisuels[i].distance+MARGEdERREUR;
				angledep = mesVisuels[i].angle;
				moy=mesVisuels[i].angle;nbm=1;j++;
				secu = true;
			}
			i++;

		}
		if(mesVisuels[i].atrouve==false && secu == false) {
			i--;
			moy = moy/nbm;
			paletsSuivants[j]=new Visuelc(true,moy-10,(borne1+borne2)/2);
			j++;
		}
		paletsSuivants[j]=new Visuelc(false,0,0);


		//affichage de contrôle
		for(int a=0; a<paletsSuivants.length;a++) {
			try {
				System.out.println(paletsSuivants[a].angle+" - "+paletsSuivants[a].distance);
			} catch (Exception e) {
				System.out.println("un de trop");
			}
		}
		i=0;

		
		//======================================Parcours========================================//

		
		while(paletsSuivants[i].atrouve) {	// ici on vas chercher un a un les palet e notre array, helas c'est la que les imprecision du capteur pose de gros problème,
											// il y a de nombreuse vaaleurs d'ajustement, insuffisantes (-20, +40 ...)
			i++;
		}
		i--;

		if(i>=0){	//premier de la liste
			moteurs.setSpeed(100);
			moteurs.tourneCentre(-(ANGLEUTILE-paletsSuivants[i].angle), false);
			//angle-=(110-paletsSuivants[i].angle);
			moteurs.ouvrirPince();
			moteurs.setSpeed(250);
			moteurs.travel((float) paletsSuivants[i].distance*100+20, false);
			moteurs.fermerPince();
			moteurs.setSpeed(100);

			if(this.etape==1) {
				moteurs.tourneCentre(180, false);
			}else {
				moteurs.tourneCentre((ANGLEUTILE-paletsSuivants[i].angle), false);
			}
			moteurs.setSpeed(250);
			moteurs.travel((float) paletsSuivants[i].distance*100+20+distanceEtape2, false);
			deposerPalet();
			i--;

			while(i>=0) { //suivants
				moteurs.tourneCentre(180-(paletsSuivants[i+1].angle-paletsSuivants[i].angle)-20, false);
				moteurs.ouvrirPince();
				moteurs.setSpeed(250);
				moteurs.travel((float) paletsSuivants[i].distance*100+20+distanceEtape2, false);
				moteurs.fermerPince();
				moteurs.setSpeed(100);

				if(this.etape==1) {
					moteurs.tourneCentre(-180, false);
				}else {
					moteurs.tourneCentre(180-(paletsSuivants[i+1].angle-paletsSuivants[i].angle), false);
				}
				moteurs.setSpeed(250);
				moteurs.travel((float) paletsSuivants[i].distance*100+20+distanceEtape2, false);
				deposerPalet();
				moteurs.setSpeed(100);
				i--;
			}
			i++;
			moteurs.tourneCentre(-paletsSuivants[i].angle-ANGLEUTILE, false);
		}

		if(this.etape==1) {
			this.etape=2;
			this.distanceEtape2=70; //valeur beaucoup modifiée, correspond a la distance premiere se placer au milieu de la primière ligne
			moteurs.setSpeed(250);
			moteurs.travel(distanceEtape2,false);
			moteurs.setSpeed(100);

			this.paletsSuivants();

		}

		//metton que l'on ait 3 palets en x1=15° x2=40° x3=90° en général
		//POUR LE PREMIER JUSTE rotate ANGLEUTILE-x3
		//POUR LES SUIVANT MOUVEMENT rotate 180-(X3-X2) afin d'aller chercher les suivants
	}



	/**
	 * Fonction permettant de recuperer un palet present devant le robot
	 * @return un boolean qui indique si le palet a ete recupere*/
	
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

	/**
	 * Fonction permettant de deposer un palet
	 */
	
	public void deposerPalet() {
		moteurs.ouvrirPince();
		moteurs.travel(-20, false);
		moteurs.fermerPince();
	}

	//======================================================================================//
	//================================ Getter / Setter =====================================//
	//======================================================================================//
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

	public double getLastMesure() {
		return lastMesure;
	}

	public void setLastMesure(double lastMesure) {
		this.lastMesure = lastMesure;
	}
}
