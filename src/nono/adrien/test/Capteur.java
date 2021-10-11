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
	private EV3UltrasonicSensor ultrasound;
	private EV3ColorSensor color;
	private EV3TouchSensor touch;


	private	SampleProvider distance ;//= Ultrasound.getMode("Distance");
	private	SampleProvider average;// = new MeanFilter(distance, 5);
	private	float[] sample ;//= new float[average.sampleSize()];

	
	public Capteur(){
		touch=new EV3TouchSensor(LocalEV3.get().getPort("S2"));
		color=new EV3ColorSensor(LocalEV3.get().getPort("S3"));
		ultrasound=new EV3UltrasonicSensor(LocalEV3.get().getPort("S4"));
		distance = ultrasound.getMode("Distance");
		average = new MeanFilter(distance,5);
	}
	public boolean estToucher() {
		float[] sample = new float[1];
		touch.fetchSample(sample, 0);
		return sample[0] != 0;

	}

	public float distanceMetre() {
		sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);
		return sample[sample.length-1];
	}
	

	/*public float ObjetLePlusProche() {
		distance = ultrasound.getMode("Distance");
		average = new MeanFilter(distance, 5);
		sample = new float[average.sampleSize()];
		return sample[0];
		}*/
	
}


/*

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
*/

