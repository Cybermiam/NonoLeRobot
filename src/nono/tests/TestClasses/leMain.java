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

public class leMain {

	static Port port = LocalEV3.get().getPort("S3");
	static EV3ColorSensor colorSensor = new EV3ColorSensor(port);
	static SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);

	public static boolean goMessage() {

		GraphicsLCD g = LocalEV3.get().getGraphicsLCD();
		g.clear();
		g.drawString("Color Sensor", 5, 0, 0);
		g.setFont(Font.getSmallFont());

		g.drawString("The code for this sample     ", 2, 20, 0);
		g.drawString("shows how to work with the ", 2, 30, 0);
		g.drawString("Color Sensor ", 2, 40, 0);
		g.drawString("To run the ", 2, 60, 0);
		g.drawString("code one needs an EV3 ", 2, 70, 0);
		g.drawString("brick with a EV3 color sensor", 2, 80, 0); 
		g.drawString("attached to port 4.", 2, 90, 0);

		// Quit GUI button:
		g.setFont(Font.getSmallFont()); // can also get specific size using Font.getFont()
		int y_quit = 100;
		int width_quit = 45;
		int height_quit = width_quit/2;
		int arc_diam = 6;
		g.drawString("QUIT", 9, y_quit+7, 0);
		g.drawLine(0, y_quit,  45, y_quit); // top line
		g.drawLine(0, y_quit,  0, y_quit+height_quit-arc_diam/2); // left line
		g.drawLine(width_quit, y_quit,  width_quit, y_quit+height_quit/2); // right line
		g.drawLine(0+arc_diam/2, y_quit+height_quit,  width_quit-10, y_quit+height_quit); // bottom line
		g.drawLine(width_quit-10, y_quit+height_quit, width_quit, y_quit+height_quit/2); // diagonal
		g.drawArc(0, y_quit+height_quit-arc_diam, arc_diam, arc_diam, 180, 90);

		// Enter GUI button:
		g.fillRect(width_quit+10, y_quit, height_quit, height_quit);
		g.drawString("GO", width_quit+15, y_quit+7, 0,true);

		Button.waitForAnyPress();
		if(Button.ESCAPE.isDown()) {
			return false;
		}
		else {
			g.clear();
			return true;
		}
	}


	public static void main(String[] args) {

		String color = "";

		try {
			boolean again = true;

			if (!goMessage()) System.exit(0);

			colorSensor.setFloodlight(Color.WHITE);

			System.out.println("Press enter to calibrate blue...");
			Button.ENTER.waitForPressAndRelease();
			float[] blue = new float[average.sampleSize()];
			average.fetchSample(blue, 0);


			System.out.println("Press enter to calibrate red...");
			Button.ENTER.waitForPressAndRelease();
			float[] red = new float[average.sampleSize()];
			average.fetchSample(red, 0);

			System.out.println("Press enter to calibrate green...");
			Button.ENTER.waitForPressAndRelease();
			float[] green = new float[average.sampleSize()];
			average.fetchSample(green, 0);

			System.out.println("Press enter to calibrate black...");
			Button.ENTER.waitForPressAndRelease();
			float[] black = new float[average.sampleSize()];
			average.fetchSample(black, 0);
			System.out.println("Black calibrated");

			System.out.println("Press enter to calibrate yellow...");
			Button.ENTER.waitForPressAndRelease();
			float[] yellow = new float[average.sampleSize()];
			average.fetchSample(yellow, 0);
			System.out.println("yellow calibrated");

			System.out.println("Press enter to calibrate white...");
			Button.ENTER.waitForPressAndRelease();
			float[] white = new float[average.sampleSize()];
			average.fetchSample(white, 0);
			System.out.println("white calibrated");

			while (again) {
				float[] sample = new float[average.sampleSize()];
				average.fetchSample(sample, 0);
				double minscal = Double.MAX_VALUE;

				double scalaire = TestColor.scalaire(sample, blue);
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "blue";
				}
				
				scalaire = TestColor.scalaire(sample, red);
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "red";
				}

				scalaire = TestColor.scalaire(sample, yellow);
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "yellow";
				}

				scalaire = TestColor.scalaire(sample, green);
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "green";
				}

				scalaire = TestColor.scalaire(sample, black);
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "black";
				}

				scalaire = TestColor.scalaire(sample, white);
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "white";
				}
				
				//Delay.msDelay(5000);
				
	//			
				
				if(color.equals("white")) {
					System.out.print("Oui c'est du blanc... ouvre les yeux. T'es moche, Damdam.");
				}
				else {
					System.out.print(color);
				}
				//Delay.msDelay(5000);
				//colorSensor.setFloodlight(false);
				//System.exit(0);
				if(Button.ESCAPE.isDown()) {
					colorSensor.setFloodlight(false);
					again = false;
				}
				
				Button.waitForAnyPress();
			}
				

			

		} catch (Throwable t) {
			t.printStackTrace();
			Delay.msDelay(8000);
			System.exit(0);
		}
	}
	
	public String getColor() {
		String color = "";
		float[] sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);
		double minscal = Double.MAX_VALUE;

		double scalaire = TestColor.scalaire(sample, blue);
		if (scalaire < minscal) {
			minscal = scalaire;
			color = "blue";
		}
		
		scalaire = TestColor.scalaire(sample, red);
		if (scalaire < minscal) {
			minscal = scalaire;
			color = "red";
		}

		scalaire = TestColor.scalaire(sample, yellow);
		if (scalaire < minscal) {
			minscal = scalaire;
			color = "yellow";
		}

		scalaire = TestColor.scalaire(sample, green);
		if (scalaire < minscal) {
			minscal = scalaire;
			color = "green";
		}

		scalaire = TestColor.scalaire(sample, black);
		if (scalaire < minscal) {
			minscal = scalaire;
			color = "black";
		}

		scalaire = TestColor.scalaire(sample, white);
		if (scalaire < minscal) {
			minscal = scalaire;
			color = "white";
		}
		return color;
	}
		
}
