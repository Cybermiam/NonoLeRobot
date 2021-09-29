import nono.tests.TestClasses.TouchSensor;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;
import nono.tests.DifferentialDrive;

public class first {

	public static void main(String[] args) {
		GraphicsLCD brick = BrickFinder.getDefault().getGraphicsLCD();
		//brick.drawString("Le premier programme fonctionne, on est connécté.", 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		//Delay.msDelay(5000);
		//Port port = LocalEV3.get().getPort("S4");
		//EV3 Ultrason = new EV3UltrasonicSensor(port);
		//SampleProvider distance = Ultrason.getMode("Distance");
		//SampleProvider average = new MeanFilter(distance, 5);
		//float[] sample = new float[average.sampleSize()];

		//int i = 0;
		//while(i<20){
		//	average.fetchSample(sample, 0);
		//	String s = Float.toString(sample[0]);
		//	brick.drawString(s,0,0,GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		//	i++;
		//	Delay.msDelay(1000);
		//	brick.clear();
		//}

		//ultrason S4
		//touché S2
		//couleur S3

		//Port port = LocalEV3.get().getPort("S3");
		//EV3ColorSensor colors = new EV3ColorSensor(port);
		//colors.setCurrentMode("Ambient");
		//sample = new float[colors.sampleSize()];
		//SensorMode couleur = colors.getColorIDMode();
		//SampleProvider couleurprovided = new MeanFilter(distance, 5);
		//Color[] sample = new float[average.sampleSize()];

		//int i = 0;
		//while(i<20){
		//	average.fetchSample(sample, 0);
		//	String s = Float.toString(sample[0]);
		//	brick.drawString(s,0,0,GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		//	i++;
		//	Delay.msDelay(1000);
		//	brick.clear();
		//	}


		// PINCE
		//Motor.B.setSpeed(1000);
		//Motor.B.rotateTo(-500);


		TouchSensor sensor=new TouchSensor(LocalEV3.get().getPort("S2"));
        DifferentialDrive Driver = new DifferentialDrive(LocalEV3.get().getPort("A"),LocalEV3.get().getPort("C"));
        EV3LargeRegulatedMotor mPincemotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("B"));
        mPincemotor.setSpeed(1000);

        
       mPincemotor.rotateTo(1000);
        
		Driver.forward();
        while (! sensor.isPressed()){
            Delay.msDelay(10);
            if(Driver.getSpeed()<250) {
            Driver.setSpeed(Driver.getSpeed()+10);
            }
           }
        
        while (Driver.getSpeed()>50) {
        	Delay.msDelay(10);
        	Driver.setSpeed(Driver.getSpeed()-50);
        }
        Driver.stop();

        //mPincemotor.rotateTo(-400);

      	Driver.rotateCounterClockwise();
        Delay.msDelay(500);
        Driver.stop();

	}
}
