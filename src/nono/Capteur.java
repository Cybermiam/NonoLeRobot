package nono;

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
	//======== Attributs =======//
	
	private EV3UltrasonicSensor ultrasound;
	private EV3ColorSensor color;
	private EV3TouchSensor touch;
	private	SampleProvider distance ;//= Ultrasound.getMode("Distance");
	private	SampleProvider average;// = new MeanFilter(distance, 5);
	private	float[] sample ;//= new float[average.sampleSize()];

	/**
	 * Constructeur de la classe Capteur
	 */
	public Capteur(){
		touch=new EV3TouchSensor(LocalEV3.get().getPort("S2"));
		color=new EV3ColorSensor(LocalEV3.get().getPort("S3"));
		ultrasound=new EV3UltrasonicSensor(LocalEV3.get().getPort("S4"));
		distance = ultrasound.getMode("Distance");
		average = new MeanFilter(distance,5);
	}
	/**
	 * 
	 * @return true si le robot est toucher,et false si le robot n est pas touche
	 */
	public boolean estTouche() {
		float[] sample = new float[1];
		touch.fetchSample(sample, 0);	
		//	GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();
		//	brick.drawString(""+sample[0], 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		//	Delay.msDelay(5);
		//	brick.clear();
		return sample[0] != 0;

	}
	
	/**
	 * 
	 * @return un float avec la distance capté par l'ultrason (en mètre)
	 */
	public float distanceMetre() {
		sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);
		return sample[sample.length-1];
	}
	/**
	 * 
	 * @return un boolean si la couleur captee est blanc
	 */
	public boolean estBlanc() {
         color.setFloodlight(Color.WHITE);
         if(color.getColorID() == 6) {
             return true;
         }
         return false;
	}



}



