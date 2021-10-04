package nono.tests;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class Basic {
	private EV3UltrasonicSensor Ultrasound;
	private EV3ColorSensor Color;
	private EV3TouchSensor Touch;
	private EV3LargeRegulatedMotor MotorL;
	private EV3LargeRegulatedMotor MotorR;
	private Port Claw;

	private int Speed;

	public Basic() {
		this("S4","S3","S2","A","C","B",500);
	}
	public Basic(String u,String c,String t,String mL,String mR,String cl,int s) {
		Ultrasound =new EV3UltrasonicSensor(LocalEV3.get().getPort(u));
		Color =new EV3ColorSensor(LocalEV3.get().getPort(c));
		Touch = new EV3TouchSensor(LocalEV3.get().getPort(t));
		MotorL = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(mL));
		MotorR = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(mR));
		Claw = LocalEV3.get().getPort(cl);
		Speed=s;
	}
	public void setSpeed(int s) {
		Speed=s;
	}
	public void stop() {
		MotorL.stop();
		MotorR.stop();
	}
	public void goForward() {
		MotorL.forward();
		MotorR.forward();
	}
	public void goForwardDuring(int d) {
		for(int i=0;i<d;i++) {
			goForward();
			Delay.msDelay(600);
		}
	}
	public void goForwardUltrasoundTouch() {
		if(ultrasound()<0||ultrasound()>20) {
			throw new IllegalArgumentException();
		}
		while (!pressedTouch()){
			goForwardDuring(1);
		}
		stop();

	}
	public float ultrasound() {
		SampleProvider distance = Ultrasound.getMode("Distance");
		SampleProvider average = new MeanFilter(distance, 5);
		float[] sample = new float[average.sampleSize()];
		return sample[0];
	}
	public boolean pressedTouch(){
		float[] sample = new float[1];
		Touch.fetchSample(sample,0);
		return sample[0] != 0;

	}

}
