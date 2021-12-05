package nono.tests.TestClasses;

import lejos.hardware.Button;
import nono.*;


//Button.ENTER.waitForPressAndRelease();

public class StartAndStop {

	public void stop() {
		Button.waitForAnyPress();
		if(Button.ENTER.isDown()) {
			System.exit(0);;
		}
	}

	public void start() {
		Button.waitForAnyPress();
		if(Button.ENTER.isDown()) {
			r.search();
		}
	}

	public static void main(String[] args) {


	}
}
