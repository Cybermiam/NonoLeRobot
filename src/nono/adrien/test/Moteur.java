package nono.adrien.test;

import lejos.hardware.port.Port;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;

public class Moteur {
	private EV3LargeRegulatedMotor MotorL;
	private EV3LargeRegulatedMotor MotorR;
	private Port Claw;
	private int Speed;

	public Moteur() {
		MotorL = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
		MotorR = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("C"));
		Claw = LocalEV3.get().getPort("B");
		Speed=500;
		MotorL.setSpeed(Speed);
		MotorR.setSpeed(Speed);
	}
	public void setVitesse(int s) {
		Speed=s;
		MotorL.setSpeed(s);
		MotorR.setSpeed(s);
	}
	public void acceleration(int s) {
		for (int i=Speed;i<s;i++) {
			Speed=i;
			avancer();
			Delay.msDelay(10);
		}
	}
	public void avancer() {
		MotorL.forward();
		MotorR.forward();
		Delay.msDelay(20);
	}
	public void reculer() {
		MotorL.backward();
		MotorR.backward();
		Delay.msDelay(20);
	}
	public void arreter() {
		MotorL.stop();
		MotorR.stop();
	}
	public void avancerPendant(int t) {
		for(int i=0;i<t;i++) {
			MotorL.forward();
			MotorR.forward();
			Delay.msDelay(100);
		}
	}
	public void tourneG90() {
		MotorL.forward();
		MotorR.backward();
		Delay.msDelay(200);
	}
	public void tourneD90() {
		MotorR.forward();
		MotorL.backward();
		Delay.msDelay(200);
	}
	


}
