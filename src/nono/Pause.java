package nono;

import lejos.hardware.Button;

public class Pause extends Thread{
	public void run() {
		Button.ENTER.waitForPressAndRelease();
		System.exit(0);
	}
}
