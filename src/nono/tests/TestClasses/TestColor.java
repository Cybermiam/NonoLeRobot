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

public class TestColor {

	public static void main(String[] args) {
		try {

			boolean again = true;
			Port port = LocalEV3.get().getPort("S3");
			EV3ColorSensor colorSensor = new EV3ColorSensor(port);
			SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
			
			colorSensor.setFloodlight(Color.WHITE);
			
			System.out.println("Blanc ?");
			Button.ENTER.waitForPressAndRelease();
			float[] white = new float[average.sampleSize()];
			average.fetchSample(white, 0);
			System.out.println("Blanc calibré." + colorSensor.getColorID());
			
			System.out.println("Autre ?");
			Button.ENTER.waitForPressAndRelease();
			float[] autre = new float[average.sampleSize()];
			average.fetchSample(autre, 0);
			System.out.println("Autre calibré." + colorSensor.getColorID());

			while (again) {
				colorSensor.setFloodlight(Color.WHITE);
				float[] sample = new float[average.sampleSize()];
				System.out.println("\n Un test ?");
				Button.ENTER.waitForPressAndRelease();
				average.fetchSample(sample, 0);
				double minscal = Double.MAX_VALUE;
				String color = "";
				double scalaire;

				colorSensor.setFloodlight(Color.WHITE);
				scalaire = TestColor.scalaire(sample, autre);
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "autre";
				}

				scalaire = TestColor.scalaire(sample, white);
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "blanc";
				}

				System.out.println("C'est du  " + color + ". D'ID :" + colorSensor.getColorID() + ". \n");
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
