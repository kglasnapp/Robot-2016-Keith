package org.usfirst.frc3932;


import edu.wpi.first.wpilibj.I2C;

import java.util.TimerTask;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class LIDAR implements PIDSource{
	private I2C i2c;
	private byte[] distance;
	private int dist;
	private java.util.Timer updater;
	
	private final int LIDAR_ADDR = 0x62;
	private final int LIDAR_CONFIG_REGISTER = 0x00;
	private final int LIDAR_DISTANCE_REGISTER = 0x8f;
	
	int i= 0;
	
	public LIDAR(Port port) {
		i2c = new I2C(port, LIDAR_ADDR);
		
		distance = new byte[1];
		
		updater = new java.util.Timer();
	}
	
	// Distance in cm
	public int getDistance() {
		return dist;
	}

	public double pidGet() {
		return getDistance();
	}
	
	// Start 10Hz polling
	public void start() {
		updater.scheduleAtFixedRate(new LIDARUpdater(), 0, 100);
	}
	
	// Start polling for period in milliseconds
	public void start(int period) {
		updater.scheduleAtFixedRate(new LIDARUpdater(), 0, period);
	}
	
	public void stop() {
		updater.cancel();
		updater = new java.util.Timer();
	}
	
	// Update distance variable
	public void update() {
		int d = 0;
		i2c.write(LIDAR_CONFIG_REGISTER, 0x04); // Initiate measurement
		Timer.delay(0.04); // Delay for measurement to be taken
		i2c.read(0x0f, 1, distance); // Read in measurement
		Timer.delay(0.008); // Delay to prevent over polling
		d += Integer.toUnsignedLong(distance[0] << 8);
		i2c.read(0x10, 1, distance);
		d += Byte.toUnsignedInt(distance[0]);
		dist = d;
	//	System.out.println("LIDAR Distance:" + dist);
	}
	
	// Timer task to keep distance updated
	private class LIDARUpdater extends TimerTask {
		public void run() {
			while(true) {
				update();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
	}
}
