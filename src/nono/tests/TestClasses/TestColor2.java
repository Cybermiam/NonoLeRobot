package nono.tests.TestClasses;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class TestColor2 {

	public static void main(String[] args) {
		try {

			boolean again = true;

			Port port = LocalEV3.get().getPort("S3");
			EV3ColorSensor colorSensor = new EV3ColorSensor(port);
			colorSensor.setFloodlight(Color.WHITE);

			while (again) {
				colorSensor.setFloodlight(true);
				
				System.out.println("\n C'est du blanc ?");
				Button.ENTER.waitForPressAndRelease();
				if(colorSensor.getColorID() == 6) {
					System.out.println("Oui, c'est bien du blanc.");
					again=false;
				}
				else {
					System.out.println("Non.");
				}
				
				Button.waitForAnyPress();
				if(Button.ESCAPE.isDown()) {
					colorSensor.setFloodlight(false);
					again = false;
				}
			}
			
		} catch (Throwable t) {
			t.printStackTrace();
			Delay.msDelay(10000);
			System.exit(0);
		}
	}

	public static double scalaire(float[] v1, float[] v2) {
		return Math.sqrt (Math.pow(v1[0] - v2[0], 2.0) +
				Math.pow(v1[1] - v2[1], 2.0) +
				Math.pow(v1[2] - v2[2], 2.0));
	}

}
