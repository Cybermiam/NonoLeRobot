package nono.adrien.test;

import lejos.hardware.port.Port;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;

public class Moteur {
	private EV3LargeRegulatedMotor MotorL;
	private EV3LargeRegulatedMotor MotorR;
	private Port Claw;
	private int Speed;
	private int MAXSPEED = 600;
	public int status = 0; // -1 recule ; 0 a l'arret ; 1  avance
	
	public Moteur() {
		MotorL = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
		MotorR = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("C"));
		Claw = LocalEV3.get().getPort("B");
		Speed=10;
		MotorL.setSpeed(Speed);
		MotorR.setSpeed(Speed);
	}
	public void setVitesse(int s) {
		MAXSPEED = s;
	}
	
	public void smooth(int O) {// -1 ou 1  
		
			
			Speed+=10*O;
			if (Speed>MAXSPEED) {
				Speed = MAXSPEED;
			}else if (Speed < 0 )
			{
				Speed = 0;
			}
			
			
			MotorL.setSpeed(Speed);
			MotorR.setSpeed(Speed);
		
			
	}
	public void avancer() {
		smooth(1);
		if(status != 1) {
			status = 1;
		MotorL.forward();
		MotorR.forward();
		}
		//Delay.msDelay(20);
	}
	public void reculer() {
		
		smooth(1);
		if(status != -1) {
			status = -1;			
			MotorL.backward();
			MotorR.backward();
		}
		
	}
	public void arreter() {
		//GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();
	//	int a = 0;
		while (Speed > 10) {
		//	a++;
			//brick.drawString("speed : " + Speed + " a " +a , 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			smooth(-1);	
			Delay.msDelay(10);
			//brick.clear();
		}
		MotorL.stop();
		MotorR.stop();
			status = 0;
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
		Delay.msDelay(20);
	}
	public void tourneD90() {
		MotorR.forward();
		MotorL.backward();
		Delay.msDelay(20);
	}
	
	public int getSpeed() {
		return Speed;
	}

}
