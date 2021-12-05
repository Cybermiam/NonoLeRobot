package nono;

import lejos.hardware.Button;


public class Pause extends Thread{
	
	/** Fonction ayant pour but de mettre fin au thread quand executee*/
	public void run() { 
		Button.ENTER.waitForPressAndRelease();
		System.exit(0);
	}
}
