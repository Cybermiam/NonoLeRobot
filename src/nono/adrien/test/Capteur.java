package nono.adrien.test;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;
import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;

public class Capteur {
	private Ultrason Ultrasound;
	private Couleur Color;
	private Toucher Touch;
	
	public Capteur(){
		Touch=new Toucher();
		Color=new Couleur();
		Ultrasound=new Ultrason();
	}
	public boolean estToucher() {
		return Touch.isPressed();
	}
	public float afficherTableauDistance() {
		return Ultrasound.tableauDistance();
	}
	
	
	
	
	private class Toucher extends EV3TouchSensor{
		private Toucher() {
			this(LocalEV3.get().getPort("S2"));
		}
		private Toucher(Port p) {
			super(p);
		}
		private boolean isPressed() {
			float[] sample = new float[1];
			fetchSample(sample, 0);
			return sample[0] != 0;
		}
	}
	
	private class Ultrason{

		private EV3UltrasonicSensor s;
		private	SampleProvider distance ;//= Ultrasound.getMode("Distance");
		private	SampleProvider average;// = new MeanFilter(distance, 5);
		private	float[] sample ;//= new float[average.sampleSize()];
		
		private Ultrason() {
			s = new EV3UltrasonicSensor((LocalEV3.get().getPort("S4")));
			 distance = s.getMode("Distance");
			 average = new MeanFilter(distance, 5);
			 sample = new float[average.sampleSize()];
			
		}
	
		private float tableauDistance() {
			average.fetchSample(sample, 0);
			return(sample[0]);
		}
	}
	
	private class Couleur extends EV3ColorSensor{
		private Couleur() {
			this(LocalEV3.get().getPort("S3"));
		}
		private Couleur(Port p) {
			super(p);
		}
	}

}
