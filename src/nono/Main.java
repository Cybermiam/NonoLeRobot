package nono;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.utility.Delay;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.BaseRegulatedMotor;

public class Main {

	public static void main(String[] args) {
		final Robot r=new Robot();
		final Pause p=new Pause(); // l'objet pause est un thread qui nous permet de mettre fin au programme en appuyant sur un bouton
		
		GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();
		System.out.println("Haut pour phase 1 \n Bas pour phase 2 \n");
		r.getMoteur().getPilot().setLinearAcceleration(65);
		r.getMoteur().getPilot().setAngularAcceleration(40);
		
		Button.waitForAnyPress();
		if(Button.UP.isDown()) { //on choisit de faire un debut de partie normale 
			System.out.println("Phase 1.");
			p.start();
			r.premierPalet(true);
			r.paletsSuivants();
		}else if(Button.DOWN.isDown()) { // ou de faire directement la fonction qui permet d attraper les palets suivants, utile pour les tests 
			System.out.println("Phase 2.");
			p.start();
			r.paletsSuivants(); 
		}else {
			System.out.println("Phase 1, buguée."); //parfois en cas de double clic imprévu on préfere executer quand mème la phase 1 (pour la compet)
			p.start();
			r.premierPalet(true);
			r.paletsSuivants();
		}
	}
}