package org.usfirst.frc3932;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Simple sample client for Network tables that can 
 * demonstrate how to read the values from the roborealm program
 * @author adam.gresh
 *
 */
public class NetworkTablesRoboRealmClient {

	public static void main(String args[]) {
		System.out.println("Hello");
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("127.0.0.1");

		NetworkTable table = null;
		try {
			table = NetworkTable.getTable("SmartDashboard");
		} catch (Exception e) {
			System.out.println("TableHelloError");
			e.printStackTrace();
			
		}
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(NetworkTable.class.getName()).log(Level.SEVERE, null, ex);
			}
			double x = table.getNumber("RoboRealmDistance", 0.0);
			//This is broken because BFSC is being captured as an
			//array, it needs to be either read as an array or
			//captured as a flat number.
			double y = table.getNumber("XRoboRealmBlob", 0.0);
			System.out.println("distance" + x);
			System.out.println("Blob" + y);
		}
	}
}
